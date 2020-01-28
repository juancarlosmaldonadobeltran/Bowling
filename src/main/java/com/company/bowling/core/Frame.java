package com.company.bowling.core;

import com.company.bowling.core.exception.ConsecutiveRollsNotValidException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
@SuperBuilder
public class Frame {

    public static final int MAX_PINS_PER_FRAME = 10;
    public static final String STRIKE_MARK = "X";
    public static final String SPARE_MARK = "/";
    private static final String FOUL_MARK = "F";

    protected int score;
    protected Integer firstRoll;
    protected String firstRollBoardMark;
    protected Integer secondRoll;
    protected String secondRollBoardMark;
    protected Frame next;
    protected Frame last;

    public int getScore() {
        this.score = this.getLast().map(Frame::getScore).orElse(0);
        this.score += this.firstRoll + this.getSecondRoll().orElse(0);
        if (this.isSpare()) {
            this.score += this.next.firstRoll;
        } else if (this.isStrike()) {
            this.score += this.next.firstRoll;
            if (this.next.isStrike()) {
                this.score += this.next.getSecondRoll()
                        .orElse(this.next.getNext().map(secondNext -> secondNext.firstRoll)
                                .orElse(0));
            } else {
                this.score += this.next.secondRoll;
            }
        }
        return this.score;
    }

    public boolean isCompleted() {
        return this.isStrike() || this.getSecondRoll().isPresent();
    }

    public boolean isStrike() {
        return getFirstRoll().map(first -> first == MAX_PINS_PER_FRAME).orElse(false);
    }

    public boolean isSpare() {
        return getFirstRoll().flatMap(first -> getSecondRoll().map(second -> first + second))
                .map(knockedPins -> knockedPins == MAX_PINS_PER_FRAME).orElse(false);
    }

    public boolean isSimpleFrame() {
        return !this.isSpare() && !this.isStrike();
    }

    public Optional<Integer> getFirstRoll() {
        return Optional.ofNullable(this.firstRoll);
    }

    public Optional<Integer> getSecondRoll() {
        return Optional.ofNullable(this.secondRoll);
    }

    public boolean isValid() {
        return this.getFirstRoll().map(
                first -> getSecondRoll().map(
                        second -> first + second <= MAX_PINS_PER_FRAME
                ).orElse(true)
        ).orElse(true);
    }

    public void roll(String knockedPinsMark) {
        int pins = this.parseKnockedPinsMark(knockedPinsMark);
        if (!this.getFirstRoll().isPresent()) {
            this.firstRoll = pins;
            this.firstRollBoardMark = knockedPinsMark;
        } else if (!this.getSecondRoll().isPresent()) {
            this.secondRoll = pins;
            this.secondRollBoardMark = knockedPinsMark;
            this.validateConsecutiveRolls();
        } else {
            throw new IllegalStateException("Current frame contains all possible rolls");
        }
        this.addSpecialMarks();
    }

    protected void addSpecialMarks() {
        if (this.isStrike()) {
            this.firstRollBoardMark = "";
            this.secondRollBoardMark = STRIKE_MARK;
        } else if (this.isSpare()) {
            this.secondRollBoardMark = SPARE_MARK;
        }
    }

    protected void validateConsecutiveRolls() {
        if (!this.isValid()) {
            String message = "Consecutive rolls not valid: " + this.firstRoll + ", " + this.secondRoll;
            throw new ConsecutiveRollsNotValidException(message);
        }
    }

    protected int parseKnockedPinsMark(String knockedPinsMark) {
        if (FOUL_MARK.equals(knockedPinsMark)) {
            return 0;
        }
        return Integer.parseInt(knockedPinsMark);
    }

    public List<String> getBoardMarks() {
        return Arrays.asList(this.firstRollBoardMark, this.secondRollBoardMark);
    }

    protected Optional<Frame> getLast() {
        return Optional.ofNullable(this.last);
    }

    protected Optional<Frame> getNext() {
        return Optional.ofNullable(this.next);
    }
}
