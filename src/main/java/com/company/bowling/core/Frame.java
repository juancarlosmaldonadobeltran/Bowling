package com.company.bowling.core;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Builder
public final class Frame {

    public static final int TOTAL_PINS = 10;

    private Integer score;
    private List<Integer> rolls;
    private List<String> boardMarks;

    public Optional<Integer> getScore() {
        return Optional.ofNullable(this.score);
    }

    public boolean isComplete() {
        return this.isStrike() || this.hasAllRolls();
    }

    private boolean hasAllRolls() {
        return this.getRolls().size() > 1;
    }

    public boolean isStrike() {
        return getFirstRoll().map(firstRoll -> firstRoll == TOTAL_PINS).orElse(false);
    }

    public boolean isSpare() {
        return getFirstRoll().flatMap(firstRoll -> getSecondRoll().map(secondRoll -> firstRoll + secondRoll))
                .map(knockedPins -> knockedPins == TOTAL_PINS).orElse(false);
    }

    public int getKnockedPinsSum() {
        return this.rolls.stream().mapToInt(i -> i).sum();
    }

    public boolean isSimpleFrame() {
        return getFirstRoll().isPresent() && getSecondRoll().isPresent() && getKnockedPinsSum() < TOTAL_PINS;
    }

    public Optional<Integer> getFirstRoll() {
        int firstRollIndex = 0;
        return this.rolls.isEmpty()? Optional.empty(): Optional.ofNullable(this.rolls.get(firstRollIndex));
    }

    public Optional<Integer> getSecondRoll() {
        int secondRollIndex = 1;
        return this.rolls.size() > 1? Optional.ofNullable(this.rolls.get(secondRollIndex)) : Optional.empty();
    }

    public Optional<Integer> getThirdRoll() {
        int thirdRollIndex = 2;
        return this.rolls.size() > 2? Optional.ofNullable(this.rolls.get(thirdRollIndex)) : Optional.empty();
    }
}
