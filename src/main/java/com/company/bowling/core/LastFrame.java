package com.company.bowling.core;

import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SuperBuilder
public final class LastFrame extends Frame {

    private Integer thirdRoll;
    private String thirdRollBoardMark;

    public Optional<Integer> getThirdRoll() {
        return Optional.ofNullable(this.thirdRoll);
    }

    @Override
    public void roll(String knockedPinsMark) {
        int pins = this.parseKnockedPinsMark(knockedPinsMark);
        if (!this.getFirstRoll().isPresent()) {
            this.firstRoll = pins;
            this.firstRollBoardMark = knockedPinsMark;
        } else if (!this.getSecondRoll().isPresent()) {
            this.secondRoll = pins;
            this.secondRollBoardMark = knockedPinsMark;
        } else if (!this.getThirdRoll().isPresent()) {
            this.thirdRoll = pins;
            this.thirdRollBoardMark = knockedPinsMark;
        } else {
            throw new IllegalStateException("Current frame contains all possible rolls");
        }
        this.addSpecialMarks();
    }

    @Override
    protected void addSpecialMarks() {
        if (this.isSpare()) {
            this.secondRollBoardMark = SPARE_MARK;
        } else if (this.isStrike()) {
            this.firstRollBoardMark = STRIKE_MARK;
        }
        this.getSecondRoll().filter(second -> second.equals(MAX_PINS_PER_FRAME))
                .ifPresent(second -> this.secondRollBoardMark = STRIKE_MARK);
        this.getThirdRoll().filter(third -> third.equals(MAX_PINS_PER_FRAME))
                .ifPresent(third -> this.thirdRollBoardMark = STRIKE_MARK);
    }

    @Override
    public int getScore() {
        this.score = this.getLast().map(Frame::getScore).orElse(0);
        this.score += + this.firstRoll + this.secondRoll;
        if (this.isSpare() || this.isStrike()) {
            this.score += this.thirdRoll;
        }
        return this.score;
    }

    @Override
    public boolean isValid() {
        return this.getFirstRoll().map(
                firstRoll -> getSecondRoll().map(
                        secondRoll ->
                                super.isValid() || this.isSpare() || this.isStrike()
                ).orElse(true)
        ).orElse(true);
    }

    @Override
    public List<String> getBoardMarks() {
        return this.isStrike() || this.isSpare() ?
                Arrays.asList(this.firstRollBoardMark, this.secondRollBoardMark, this.thirdRollBoardMark) :
                Arrays.asList(this.firstRollBoardMark, this.secondRollBoardMark);
    }

    @Override
    public boolean isCompleted() {
        return (this.isSpare() && this.getThirdRoll().isPresent())
                || (this.isStrike() && this.getThirdRoll().isPresent())
                || (this.isSimpleFrame() && this.getSecondRoll().isPresent());
    }
}
