package com.company.bowling.core;

import java.util.*;
import java.util.stream.IntStream;

public final class TenPinBowlingGame implements BowlingGame {

    private static final String STRIKE_MARK = "X";
    private static final String SPARE_MARK = "/";
    private static final String FOUL_MARK = "F";
    private static final int TOTAL_FRAMES = 10;

    private LinkedList<Frame> frames;

    public TenPinBowlingGame() {
        this.frames = new LinkedList<>();
        addFrame();
    }

    @Override
    public void roll(String knockedPinsMark) {
        validateGameState();
        validateKnockedPins(knockedPinsMark);
        int knockedPins = parseKnockedPinsMark(knockedPinsMark);
        markRoll(knockedPins, knockedPinsMark);
        calculateScores();
    }

    private void validateGameState() {
        if (this.frames.size() == TOTAL_FRAMES && this.frames.getLast().getScore().isPresent()) {
            throw new IllegalStateException("No more rolls accepted because the game has finished after "
                    + TOTAL_FRAMES + " frames");
        }
    }

    @Override
    public List<Frame> getFrames() {
        return this.frames;
    }

    private void validateKnockedPins(String knockedPinsMark) {
        String regex = "[0-9]|10|F";
        if (!knockedPinsMark.matches(regex)) {
            throw new IllegalArgumentException("Accepted values are: numeric strings between 0 and 10 " +
                    "for the number of knocked pins or F for fouls");
        }
    }

    private void addFrame() {
        this.frames.add(Frame.builder().rolls(new ArrayList<>()).boardMarks(new ArrayList<>()).build());
    }

    private void calculateScores() {
        IntStream.range(0, this.frames.size())
                .filter(frameIndex -> !this.frames.get(frameIndex).getScore().isPresent())
                .forEach(this::calculateScore);
    }

    private void calculateScore(int frameIndex) {
        Frame currentFrame = this.frames.get(frameIndex);
        if (currentFrame.isSpare()) {
            calculateSpareScore(frameIndex);
        } else if (currentFrame.isStrike()) {
            calculateStrikeScore(frameIndex);
        } else if (currentFrame.isSimpleFrame()) {
            calculateSimpleFrameScore(frameIndex);
        }
    }

    private void calculateSimpleFrameScore(int frameIndex) {
        Frame currentFrame = this.frames.get(frameIndex);
        int lastCalculatedScore = getLastCalculatedScore();
        int currentFrameScore = lastCalculatedScore + currentFrame.getKnockedPinsSum();
        currentFrame.setScore(currentFrameScore);
    }

    private void calculateStrikeScore(int frameIndex) {
        Frame currentFrame = this.frames.get(frameIndex);

        if (isLastFrame(frameIndex)) {
            int lastCalculatedScore = getLastCalculatedScore();
            currentFrame.getFirstRoll()
                    .flatMap(firstRoll -> currentFrame.getSecondRoll())
                    .flatMap(secondRoll -> currentFrame.getThirdRoll()).ifPresent(thirdRoll -> {
                int currentFrameScore = lastCalculatedScore + currentFrame.getKnockedPinsSum();
                currentFrame.setScore(currentFrameScore);
            });
        } else {
            this.getNextFrame(frameIndex).ifPresent(nextFrame -> {
                        if (nextFrame.isStrike()) {
                            calculateStrikeSequenceScore(frameIndex);
                        } else {
                            calculateSingleStrikeScore(currentFrame, nextFrame);
                        }
                    }
            );
        }
    }

    private void calculateSingleStrikeScore(Frame currentFrame, Frame nextFrame) {
        int lastCalculatedScore = getLastCalculatedScore();
        nextFrame.getFirstRoll().ifPresent(firstRoll ->
                nextFrame.getSecondRoll().ifPresent(secondRoll -> {
                    int currentFrameScore = lastCalculatedScore +
                            currentFrame.getKnockedPinsSum() +
                            firstRoll + secondRoll;
                    currentFrame.setScore(currentFrameScore);
                })
        );
    }

    private void calculateStrikeSequenceScore(int frameIndex) {
        int nextFrameIndex = frameIndex + 1;
        if (isLastFrame(nextFrameIndex)) {
            calculateStrikeSequenceScoreInLastFrame(frameIndex);
        } else {
            calculateStrikeSequenceScoreInFirstNineFrames(frameIndex);
        }
    }

    private void calculateStrikeSequenceScoreInFirstNineFrames(int frameIndex) {
        int nextFrameIndex = frameIndex + 1;
        int lastCalculatedScore = getLastCalculatedScore();
        Frame currentFrame = this.frames.get(frameIndex);
        this.getNextFrame(frameIndex).ifPresent(nextFrame ->
                this.getNextFrame(nextFrameIndex)
                        .flatMap(Frame::getFirstRoll).ifPresent(secondNextFrameFirstRoll -> {
                    int currentFrameScore = lastCalculatedScore +
                            currentFrame.getKnockedPinsSum() +
                            nextFrame.getKnockedPinsSum() +
                            secondNextFrameFirstRoll;
                    currentFrame.setScore(currentFrameScore);
                })
        );
    }

    private void calculateStrikeSequenceScoreInLastFrame(int frameIndex) {
        int lastCalculatedScore = getLastCalculatedScore();
        Frame currentFrame = this.frames.get(frameIndex);
        this.getNextFrame(frameIndex).ifPresent(nextFrame ->
                nextFrame.getFirstRoll().ifPresent(nextFrameFirstRoll ->
                        nextFrame.getSecondRoll().ifPresent(nextFrameSecondRoll -> {
                            int currentFrameScore = lastCalculatedScore +
                                    currentFrame.getKnockedPinsSum() +
                                    nextFrameFirstRoll +
                                    nextFrameSecondRoll;
                            currentFrame.setScore(currentFrameScore);
                        })
                )
        );
    }

    private void calculateSpareScore(int frameIndex) {
        Frame currentFrame = this.frames.get(frameIndex);
        int lastCalculatedScore = getLastCalculatedScore();
        if (isLastFrame(frameIndex)) {
            currentFrame.getThirdRoll().ifPresent(currentFrameThirdRoll -> {
                int currentFrameScore = lastCalculatedScore + currentFrame.getKnockedPinsSum();
                currentFrame.setScore(currentFrameScore);
            });
        } else {
            this.getNextFrame(frameIndex).flatMap(Frame::getFirstRoll).ifPresent(nextFrameFirstRoll -> {
                int currentFrameScore = lastCalculatedScore + currentFrame.getKnockedPinsSum() + nextFrameFirstRoll;
                currentFrame.setScore(currentFrameScore);
            });
        }
    }

    private boolean isLastFrame(int frameIndex) {
        return frameIndex == TOTAL_FRAMES - 1;
    }

    private int getLastCalculatedScore() {
        return this.frames.stream()
                .map(Frame::getScore)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce((first, second) -> second).orElse(0);
    }

    private Optional<Frame> getNextFrame(int frameIndex) {
        int nextFrameIndex = frameIndex + 1;
        return this.frames.size() > nextFrameIndex ? Optional.of(this.frames.get(nextFrameIndex)) : Optional.empty();
    }

    private void markRoll(int knockedPins, String knockedPinsMark) {
        Frame currentFrame = this.frames.getLast();
        if (!isLastFrame(this.frames.size() - 1) && currentFrame.isComplete()) {
            addFrame();
            currentFrame = this.frames.getLast();
        }
        currentFrame.getRolls().add(knockedPins);
        currentFrame.getBoardMarks().add(knockedPinsMark);
        addSpecialBoardMarks(currentFrame);
    }

    private void addSpecialBoardMarks(Frame currentFrame) {
        if (isLastFrame(this.frames.size() - 1)) {
            addSpecialMarksInLastFrame(currentFrame);
        } else if (currentFrame.isSpare()) {
            int secondRollMarkIndex = 1;
            currentFrame.getBoardMarks().set(secondRollMarkIndex, SPARE_MARK);
        } else if (currentFrame.isStrike()) {
            currentFrame.setBoardMarks(Arrays.asList("", STRIKE_MARK));
        }
    }

    private void addSpecialMarksInLastFrame(Frame currentFrame) {
        int firstRollMarkIndex = 0;
        int secondRollMarkIndex = 1;
        int thirdRollMarkIndex = 2;
        int rollsSize = currentFrame.getRolls().size();
        if (rollsSize == 1 && currentFrame.isStrike()) {
            currentFrame.getBoardMarks().set(firstRollMarkIndex, STRIKE_MARK);
        } else if (rollsSize == 2 && currentFrame.isSpare()) {
            currentFrame.getBoardMarks().set(secondRollMarkIndex, SPARE_MARK);
        } else if (rollsSize == 2 && currentFrame.getRolls().get(secondRollMarkIndex) == Frame.TOTAL_PINS) {
            currentFrame.getBoardMarks().set(secondRollMarkIndex, STRIKE_MARK);
        } else if (rollsSize == 3 && currentFrame.getRolls().get(secondRollMarkIndex) == Frame.TOTAL_PINS) {
            currentFrame.getBoardMarks().set(thirdRollMarkIndex, STRIKE_MARK);
        }
    }

    private int parseKnockedPinsMark(String pins) {
        if (FOUL_MARK.equals(pins)) {
            return 0;
        }
        return Integer.parseInt(pins);
    }
}