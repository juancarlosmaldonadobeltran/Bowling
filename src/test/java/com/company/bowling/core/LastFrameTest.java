package com.company.bowling.core;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LastFrameTest {

    private LastFrame frame;

    @Before
    public void setUp() {
        frame = LastFrame.builder().build();
    }

    @Test
    public void givenARollThenWhenAddThenTheFrameShouldStoreTheRole() {
        // given
        frame.roll("1");
        // when
        Optional<Integer> firstRoll = frame.getFirstRoll();
        // then
        assertTrue(firstRoll.isPresent());
    }

    @Test
    public void givenTwoRollsWhenGetRollsThenItShouldStoreBothRolls() {
        // given
        frame.roll("3");
        frame.roll("4");
        // when
        Optional<Integer> secondRoll = frame.getSecondRoll();
        Optional<Integer> firstRoll = frame.getFirstRoll();
        // then
        assertTrue(firstRoll.isPresent());
        assertTrue(secondRoll.isPresent());
    }

    @Test
    public void givenThreeRollsWhenGetRollsThenItShouldStoreAllRolls() {
        // given
        frame.roll("3");
        frame.roll("4");
        frame.roll("4");
        // when
        Optional<Integer> firstRoll = frame.getFirstRoll();
        Optional<Integer> secondRoll = frame.getSecondRoll();
        Optional<Integer> thirdRoll = frame.getThirdRoll();
        // then
        assertTrue(firstRoll.isPresent());
        assertTrue(secondRoll.isPresent());
        assertTrue(thirdRoll.isPresent());
    }

    @Test(expected = IllegalStateException.class)
    public void givenMoreThanThreeRollsWhenRollTheFourthOneThenItShouldShowError() {
        // given
        frame.roll("3");
        frame.roll("7");
        frame.roll("5");
        // when
        frame.roll("4");
    }

    @Test
    public void givenTwoValidConsecutiveRollsWhenIsValidThenShouldBeValid() {
        // given
        Frame frame = LastFrame.builder().firstRoll(2).secondRoll(2).build();
        // when
        boolean valid = frame.isValid();
        // then
        assertTrue(valid);
    }

    @Test
    public void givenTwoInvalidConsecutiveRollsWhenIsValidThenShouldBeNotValid() {
        // given
        Frame frame = LastFrame.builder().firstRoll(2).secondRoll(9).build();
        // when
        boolean valid = frame.isValid();
        // then
        assertFalse(valid);
    }
}
