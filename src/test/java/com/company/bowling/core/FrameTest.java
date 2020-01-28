package com.company.bowling.core;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FrameTest {

    private Frame frame;

    @Before
    public void setUp() {
        frame = Frame.builder().build();
    }

    @Test
    public void givenARollWhenAddThenTheFrameShouldStoreTheRole() {
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

    @Test(expected = IllegalStateException.class)
    public void givenMoreThanTwoRollsWhenRollTheThirdOneThenItShouldShowError() {
        // given
        frame.roll("3");
        frame.roll("4");
        // when
        frame.roll("5");
    }

    @Test
    public void givenTwoRollsInTheFirstFrameWhenGetScoreThenItShouldReturnTheScore() {
        // given
        frame.roll("3");
        frame.roll("4");
        // when
        int score = frame.getScore();
        // then
        assertEquals(7, score);
        assertEquals("3", frame.getFirstRollBoardMark());
        assertEquals("4", frame.getSecondRollBoardMark());
    }

    @Test
    public void givenASpareInTheFirstFrameWhenGetScoreThenItShouldReturnTheScore() {
        // given
        Frame next = Frame.builder().firstRoll(2).build();
        Frame frame = Frame.builder().firstRoll(2).secondRoll(8).next(next).build();
        // when
        int score = frame.getScore();
        // then
        assertEquals(12, score);
    }
}
