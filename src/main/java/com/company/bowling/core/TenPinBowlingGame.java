package com.company.bowling.core;

import com.company.bowling.core.exception.IncompleteGameException;
import com.company.bowling.core.factory.TenPinBowlingGameFrameFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public final class TenPinBowlingGame implements BowlingGame {

    public static final int TOTAL_FRAMES = 10;

    private LinkedList<Frame> frames;

    public TenPinBowlingGame() {
        this.frames = new LinkedList<>();
        addFrame();
    }

    @Override
    public void roll(String inputPinsMark) {
        validateGameCompleted();
        validateInputPinsMark(inputPinsMark);
        markRoll(inputPinsMark);
    }

    @Override
    public List<Frame> score() {
        validateIncompleteGame();
        this.frames.forEach(Frame::getScore);
        return this.frames;
    }

    private void validateIncompleteGame() {
        if (this.frames.size() < TOTAL_FRAMES || !this.frames.getLast().isCompleted()) {
            String message = "Game has " + this.frames.size() + " of " + TOTAL_FRAMES + " frames";
            throw new IncompleteGameException(message);
        }
    }

    private void validateGameCompleted() {
        if (isGameCompleted()) {
            throw new IllegalStateException("No more rolls accepted because the game has finished after "
                    + TOTAL_FRAMES + " frames");
        }
    }

    private boolean isGameCompleted() {
        return this.getCurrentFrame().map(frame -> isLastFrame(frame) && frame.isCompleted()).orElse(false);
    }

    private void validateInputPinsMark(String inputPinsMark) {
        String regex = "[0-9]|10|F";
        if (!inputPinsMark.matches(regex)) {
            throw new IllegalArgumentException("Accepted values are: numeric strings between 0 and 10 " +
                    "for the number of knocked pins or F for fouls");
        }
    }

    private Frame addFrame() {
        int frameNumber = this.frames.size() + 1;
        Frame next = TenPinBowlingGameFrameFactory.getInstance(frameNumber);
        this.getCurrentFrame().ifPresent(last -> {
            last.setNext(next);
            next.setLast(last);
        });
        this.frames.add(next);
        return next;
    }

    private void markRoll(String inputPinsMark) {
        Frame currentFrame = this.frames.getLast();
        if (!isLastFrame(currentFrame) && currentFrame.isCompleted()) {
            addFrame();
            currentFrame = this.frames.getLast();
        }
        currentFrame.roll(inputPinsMark);
    }

    private boolean isLastFrame(Frame frame) {
        return frame instanceof LastFrame;
    }

    private boolean isNotLastFrame(Frame current) {
        return !isLastFrame(current);
    }

    private Optional<Frame> getCurrentFrame() {
        return Optional.ofNullable(this.frames.peekLast());
    }



}