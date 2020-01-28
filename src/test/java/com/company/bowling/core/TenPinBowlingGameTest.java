package com.company.bowling.core;

import com.company.bowling.core.exception.ConsecutiveRollsNotValidException;
import com.company.bowling.core.exception.IncompleteGameException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TenPinBowlingGameTest {

    private TenPinBowlingGame game;

    @Before
    public void setUp() {
        this.game = new TenPinBowlingGame();
    }

    private void roll(String... pins) {
        Stream.of(pins).forEach(game::roll);
    }

    @Test
    public void givenZeroPinsGameWhenGetScoreThenItShouldBeZero() {
        // given
        roll("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        List<Frame> score = game.score();
        // then
        assertFalse(score.isEmpty());
        int expectedScore = 0;
        score.forEach(frame -> {
            assertEquals(expectedScore, frame.getScore());
            assertEquals("0", frame.getBoardMarks().get(0));
            assertEquals("0", frame.getBoardMarks().get(1));
        });
    }

    @Test(expected = ConsecutiveRollsNotValidException.class)
    public void givenMoreThanTenPinsInTwoConsecutiveRollsBeforeTenthFrameWhenRollTheSecondOneThenItShouldShowAnError() {
        // given
        game.roll("5");
        // when
        game.roll("6");
    }

    @Test
    public void givenASimpleFrameWhenGetScoreItShouldCalculateTheScore() {
        // given
        roll("2", "2", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.score().get(firstFrameIndex);
        int actualScore = firstFrame.getScore();
        List<String> boardMarks = firstFrame.getBoardMarks();
        // then
        int expectedScore = 4;
        assertEquals(expectedScore, actualScore);
        assertEquals("2", boardMarks.get(0));
        assertEquals("2", boardMarks.get(1));
    }

    @Test
    public void givenTwoFoulsFrameWhenGetScoreItShouldBeZero() {
        // given
        roll("F", "F", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.score().get(firstFrameIndex);
        int actualScore = firstFrame.getScore();
        List<String> boardMarks = firstFrame.getBoardMarks();
        // then
        int expectedScore = 0;
        assertEquals(expectedScore, actualScore);
        assertEquals("F", boardMarks.get(0));
        assertEquals("F", boardMarks.get(1));
    }

    @Test
    public void givenFiveAndFoulWhenGetScoreItShouldBeFive() {
        // given
        roll("5", "F", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.score().get(firstFrameIndex);
        int actualScore = firstFrame.getScore();
        List<String> boardMarks = firstFrame.getBoardMarks();
        // then
        int expectedScore = 5;
        assertEquals(expectedScore, actualScore);
        assertEquals("5", boardMarks.get(0));
        assertEquals("F", boardMarks.get(1));
    }

    @Test
    public void givenFoulAndFiveWhenGetScoreItShouldBeFive() {
        // given
        roll("F", "5", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.score().get(firstFrameIndex);
        int actualScore = firstFrame.getScore();
        List<String> boardMarks = firstFrame.getBoardMarks();
        // then
        int expectedScore = 5;
        assertEquals(expectedScore, actualScore);
        assertEquals("F", boardMarks.get(0));
        assertEquals("5", boardMarks.get(1));
    }

    @Test
    public void givenSpareAndOneWhenGetFirstAndSecondFrameScoresTheyShouldBeElevenAndEmpty() {
        // given
        roll("5", "5", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.score().get(firstFrameIndex);
        int firstFrameActualScore = firstFrame.getScore();
        int secondFrameIndex = 1;
        Integer secondFrameScore = game.score().get(secondFrameIndex).getScore();
        List<String> boardMarks = firstFrame.getBoardMarks();
        // then
        int firstFrameExpectedScore = 11;
        assertEquals(firstFrameExpectedScore, firstFrameActualScore);
        assertEquals("5", boardMarks.get(0));
        assertEquals("/", boardMarks.get(1));
    }

    @Test
    public void givenStrikeAndFiveAndFourWhenGetFirstAndSecondFrameScoresTheyShouldBeNineteenAndNine() {
        // given
        roll("10", "5", "4", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.score().get(firstFrameIndex);
        int firstFrameActualScore = firstFrame.getScore();
        int secondFrameIndex = 1;
        Frame secondFrame = game.score().get(secondFrameIndex);
        int secondFrameActualScore = secondFrame.getScore();
        List<String> firstFrameBoardMarks = firstFrame.getBoardMarks();
        List<String> secondFrameBoardMarks = secondFrame.getBoardMarks();
        // then
        int firstFrameExpectedScore = 19;
        int secondFrameExpectedScore = 28;
        assertEquals(firstFrameExpectedScore, firstFrameActualScore);
        assertEquals(secondFrameExpectedScore, secondFrameActualScore);
        assertEquals("", firstFrameBoardMarks.get(0));
        assertEquals("X", firstFrameBoardMarks.get(1));
        assertEquals("5", secondFrameBoardMarks.get(0));
        assertEquals("4", secondFrameBoardMarks.get(1));
    }

    @Test
    public void givenStrikeAndSpareWhenGetFirstAndSecondFrameScoresTheyShouldBeTwentyAndEmpty() {
        // given
        roll("10", "5", "5", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.score().get(firstFrameIndex);
        int firstFrameActualScore = firstFrame.getScore();
        int secondFrameIndex = 1;
        Frame secondFrame = game.score().get(secondFrameIndex);
        Integer secondFrameScore = secondFrame.getScore();
        List<String> firstFrameBoardMarks = firstFrame.getBoardMarks();
        List<String> secondFrameBoardMarks = secondFrame.getBoardMarks();
        // then
        int firstFrameExpectedScore = 20;
        assertEquals(firstFrameExpectedScore, firstFrameActualScore);
        assertEquals("", firstFrameBoardMarks.get(0));
        assertEquals("X", firstFrameBoardMarks.get(1));
        assertEquals("5", secondFrameBoardMarks.get(0));
        assertEquals("/", secondFrameBoardMarks.get(1));
    }

    @Test
    public void givenStrikeAndStrikeWhenGetFirstAndSecondFrameScoresTheyShouldBeEmptyAndEmpty() {
        // given
        roll("10", "10", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.score().get(firstFrameIndex);
        Integer firstFrameScore = firstFrame.getScore();
        int secondFrameIndex = 1;
        Frame secondFrame = game.score().get(secondFrameIndex);
        Integer secondFrameScore = secondFrame.getScore();
        List<String> firstFrameBoardMarks = firstFrame.getBoardMarks();
        List<String> secondFrameBoardMarks = secondFrame.getBoardMarks();
        // then
        assertEquals("", firstFrameBoardMarks.get(0));
        assertEquals("X", firstFrameBoardMarks.get(1));
        assertEquals("", secondFrameBoardMarks.get(0));
        assertEquals("X", secondFrameBoardMarks.get(1));
    }

    @Test
    public void givenThreeStrikesWhenGetFirstSecondAndThirdFrameScoresTheyShouldBeThirtyAndEmptyAndEmpty() {
        // given
        roll("10", "10", "10", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.score().get(firstFrameIndex);
        int firstFrameActualScore = firstFrame.getScore();
        int secondFrameIndex = 1;
        Frame secondFrame = game.score().get(secondFrameIndex);
        Integer secondFrameScore = secondFrame.getScore();
        int thirdFrameIndex = 2;
        Frame thirdFrame = game.score().get(thirdFrameIndex);
        Integer thirdFrameScore = thirdFrame.getScore();
        List<String> firstFrameBoardMarks = firstFrame.getBoardMarks();
        List<String> secondFrameBoardMarks = secondFrame.getBoardMarks();
        List<String> thirdFrameBoardMarks = thirdFrame.getBoardMarks();
        // then
        int firstFrameExpectedScore = 30;
        assertEquals(firstFrameExpectedScore, firstFrameActualScore);
        assertEquals("", firstFrameBoardMarks.get(0));
        assertEquals("X", firstFrameBoardMarks.get(1));
        assertEquals("", secondFrameBoardMarks.get(0));
        assertEquals("X", secondFrameBoardMarks.get(1));
        assertEquals("", thirdFrameBoardMarks.get(0));
        assertEquals("X", thirdFrameBoardMarks.get(1));
    }

    @Test
    public void givenTwentyFoulsWhenScoreItShouldBeZero() {
        // given
        roll("F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F");
        // when
        int tenthFrameIndex = 9;
        Frame tenthFrame = game.score().get(tenthFrameIndex);
        int tenthFrameActualScore = tenthFrame.getScore();
        List<String> tenthFrameBoardMarks = tenthFrame.getBoardMarks();
        // then
        int tenthFrameExpectedScore = 0;
        assertEquals(tenthFrameExpectedScore, tenthFrameActualScore);
        assertEquals("F", tenthFrameBoardMarks.get(0));
        assertEquals("F", tenthFrameBoardMarks.get(1));
    }

    @Test
    public void givenEighteenZerosAndSpareAndFiveInTenthFrameWhenScoreItShouldBeFifteen() {
        // given
        roll("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "7", "3", "5");
        // when
        int tenthFrameIndex = 9;
        Frame tenthFrame = game.score().get(tenthFrameIndex);
        int tenthFrameActualScore = tenthFrame.getScore();
        List<String> tenthFrameBoardMarks = tenthFrame.getBoardMarks();
        // then
        int tenthFrameExpectedScore = 15;
        assertEquals(tenthFrameExpectedScore, tenthFrameActualScore);
        assertEquals(tenthFrameExpectedScore, tenthFrameActualScore);
        assertEquals("7", tenthFrameBoardMarks.get(0));
        assertEquals("/", tenthFrameBoardMarks.get(1));
        assertEquals("5", tenthFrameBoardMarks.get(2));
    }

    @Test
    public void givenAPerfectGameWhenScoreItShouldBeThreeHundred() {
        // given
        roll("10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10");
        // when
        int tenthFrameIndex = 9;
        Frame tenthFrame = game.score().get(tenthFrameIndex);
        int tenthFrameActualScore = tenthFrame.getScore();
        List<String> tenthFrameBoardMarks = tenthFrame.getBoardMarks();
        // then
        int tenthFrameExpectedScore = 300;
        assertEquals(tenthFrameExpectedScore, tenthFrameActualScore);
        assertEquals("X", tenthFrameBoardMarks.get(0));
        assertEquals("X", tenthFrameBoardMarks.get(1));
        assertEquals("X", tenthFrameBoardMarks.get(2));
    }

    @Test
    public void givenGameWhenGetAllFrameScoreItShouldBeWellCalculated() {
        roll("8", "2", "7", "3", "3", "4", "10", "2", "8", "10", "10", "8", "0", "10", "8", "2", "9");
        // when
        List<Frame> frames = game.score();
        // then
        int expectedFrames = 10;
        assertEquals(expectedFrames, game.score().size());
        List<Integer> expectedScores = Arrays.asList(17, 30, 37, 57, 77, 105, 123, 131, 151, 170);
        List<List<String>> expectedBoardMarks = Arrays.asList(
                Arrays.asList("8", "/"),
                Arrays.asList("7", "/"),
                Arrays.asList("3", "4"),
                Arrays.asList("", "X"),
                Arrays.asList("2", "/"),
                Arrays.asList("", "X"),
                Arrays.asList("", "X"),
                Arrays.asList("8", "0"),
                Arrays.asList("", "X"),
                Arrays.asList("8", "/", "9")
        );
        IntStream.range(0, expectedScores.size()).forEach(frameIndex -> {
            Frame actualFrame = frames.get(frameIndex);
            assertEquals(expectedScores.get(frameIndex).intValue(), actualFrame.getScore());
            List<String> expectedFrameBoardMarks = expectedBoardMarks.get(frameIndex);
            List<String> actualFrameBoardMarks = actualFrame.getBoardMarks();
            IntStream.range(0, expectedBoardMarks.get(frameIndex).size()).forEach(boardMarkIndex ->
                    assertEquals(expectedFrameBoardMarks.get(boardMarkIndex), actualFrameBoardMarks.get(boardMarkIndex))
            );
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenANegativeNumberOfKnockedPinsWhenRollThenShouldShowError() {
        // when
        game.roll("-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenMoreThanTenKnockedPinsWhenRollThenShouldShowError() {
        // when
        game.roll("11");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenUnexpectedKnockedPinsMarkWhenRollThenShouldShowError() {
        // when
        game.roll("M");
    }

    @Test(expected = IllegalStateException.class)
    public void givenMoreRollsThanMaxAllowedPerGameWhenRollItShouldShowAnError() {
        // given
        for (int i = 0; i < 12; i++) {
            game.roll("10");
        }
        // when
        game.roll("10");
    }

    @Test(expected = IncompleteGameException.class)
    public void givenLessThanTenFramesWhenScoreThenItShouldShowError() {
        // given
        roll("0", "0");
        // when
        game.score();
    }
}
