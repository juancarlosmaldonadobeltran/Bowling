package com.company.bowling.core;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TenPinBowlingGameTest {

    @Test
    public void givenZeroAndZeroPinsWhenGetScoreItShouldBeZero() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        game.roll("0");
        game.roll("0");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.getFrames().get(firstFrameIndex);
        int actualScore = firstFrame.getScore().orElseThrow(IllegalStateException::new);
        List<String> boardMarks = firstFrame.getBoardMarks();
        // then
        int expectedScore = 0;
        assertEquals(expectedScore, actualScore);
        assertEquals("0", boardMarks.get(0));
        assertEquals("0", boardMarks.get(1));
    }

    @Test
    public void givenTwoAndTwoPinsWhenGetScoreItShouldBeFour() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        game.roll("2");
        game.roll("2");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.getFrames().get(firstFrameIndex);
        int actualScore = firstFrame.getScore().orElseThrow(IllegalStateException::new);
        List<String> boardMarks = firstFrame.getBoardMarks();
        // then
        int expectedScore = 4;
        assertEquals(expectedScore, actualScore);
        assertEquals("2", boardMarks.get(0));
        assertEquals("2", boardMarks.get(1));
    }

    @Test
    public void givenTwoFoulsWhenGetScoreItShouldBeZero() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        game.roll("F");
        game.roll("F");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.getFrames().get(firstFrameIndex);
        int actualScore = firstFrame.getScore().orElseThrow(IllegalStateException::new);
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
        TenPinBowlingGame game = new TenPinBowlingGame();
        game.roll("5");
        game.roll("F");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.getFrames().get(firstFrameIndex);
        int actualScore = firstFrame.getScore().orElseThrow(IllegalStateException::new);
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
        TenPinBowlingGame game = new TenPinBowlingGame();
        game.roll("F");
        game.roll("5");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.getFrames().get(firstFrameIndex);
        int actualScore = firstFrame.getScore().orElseThrow(IllegalStateException::new);
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
        TenPinBowlingGame game = new TenPinBowlingGame();
        game.roll("5");
        game.roll("5");
        game.roll("1");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.getFrames().get(firstFrameIndex);
        int firstFrameActualScore = firstFrame.getScore()
                .orElseThrow(IllegalStateException::new);
        int secondFrameIndex = 1;
        Optional<Integer> secondFrameScore = game.getFrames().get(secondFrameIndex).getScore();
        List<String> boardMarks = firstFrame.getBoardMarks();
        // then
        int firstFrameExpectedScore = 11;
        assertEquals(firstFrameExpectedScore, firstFrameActualScore);
        assertFalse(secondFrameScore.isPresent());
        assertEquals("5", boardMarks.get(0));
        assertEquals("/", boardMarks.get(1));
    }

    @Test
    public void givenStrikeAndFiveAndFourWhenGetFirstAndSecondFrameScoresTheyShouldBeNineteenAndNine() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        game.roll("10");
        game.roll("5");
        game.roll("4");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.getFrames().get(firstFrameIndex);
        int firstFrameActualScore = firstFrame.getScore()
                .orElseThrow(IllegalStateException::new);
        int secondFrameIndex = 1;
        Frame secondFrame = game.getFrames().get(secondFrameIndex);
        int secondFrameActualScore = secondFrame.getScore()
                .orElseThrow(IllegalStateException::new);
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
        TenPinBowlingGame game = new TenPinBowlingGame();
        game.roll("10");
        game.roll("5");
        game.roll("5");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.getFrames().get(firstFrameIndex);
        int firstFrameActualScore = firstFrame.getScore()
                .orElseThrow(IllegalStateException::new);
        int secondFrameIndex = 1;
        Frame secondFrame = game.getFrames().get(secondFrameIndex);
        Optional<Integer> secondFrameScore = secondFrame.getScore();
        List<String> firstFrameBoardMarks = firstFrame.getBoardMarks();
        List<String> secondFrameBoardMarks = secondFrame.getBoardMarks();
        // then
        int firstFrameExpectedScore = 20;
        assertEquals(firstFrameExpectedScore, firstFrameActualScore);
        assertFalse(secondFrameScore.isPresent());
        assertEquals("", firstFrameBoardMarks.get(0));
        assertEquals("X", firstFrameBoardMarks.get(1));
        assertEquals("5", secondFrameBoardMarks.get(0));
        assertEquals("/", secondFrameBoardMarks.get(1));
    }

    @Test
    public void givenStrikeAndStrikeWhenGetFirstAndSecondFrameScoresTheyShouldBeEmptyAndEmpty() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        game.roll("10");
        game.roll("10");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.getFrames().get(firstFrameIndex);
        Optional<Integer> firstFrameScore = firstFrame.getScore();
        int secondFrameIndex = 1;
        Frame secondFrame = game.getFrames().get(secondFrameIndex);
        Optional<Integer> secondFrameScore = secondFrame.getScore();
        List<String> firstFrameBoardMarks = firstFrame.getBoardMarks();
        List<String> secondFrameBoardMarks = secondFrame.getBoardMarks();
        // then
        assertFalse(firstFrameScore.isPresent());
        assertFalse(secondFrameScore.isPresent());
        assertEquals("", firstFrameBoardMarks.get(0));
        assertEquals("X", firstFrameBoardMarks.get(1));
        assertEquals("", secondFrameBoardMarks.get(0));
        assertEquals("X", secondFrameBoardMarks.get(1));
    }

    @Test
    public void givenThreeStrikesWhenGetFirstSecondAndThirdFrameScoresTheyShouldBeThirtyAndEmptyAndEmpty() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        game.roll("10");
        game.roll("10");
        game.roll("10");
        // when
        int firstFrameIndex = 0;
        Frame firstFrame = game.getFrames().get(firstFrameIndex);
        int firstFrameActualScore = firstFrame.getScore()
                .orElseThrow(IllegalStateException::new);
        int secondFrameIndex = 1;
        Frame secondFrame = game.getFrames().get(secondFrameIndex);
        Optional<Integer> secondFrameScore = secondFrame.getScore();
        int thirdFrameIndex = 2;
        Frame thirdFrame = game.getFrames().get(thirdFrameIndex);
        Optional<Integer> thirdFrameScore = thirdFrame.getScore();
        List<String> firstFrameBoardMarks = firstFrame.getBoardMarks();
        List<String> secondFrameBoardMarks = secondFrame.getBoardMarks();
        List<String> thirdFrameBoardMarks = thirdFrame.getBoardMarks();
        // then
        int firstFrameExpectedScore = 30;
        assertEquals(firstFrameExpectedScore, firstFrameActualScore);
        assertFalse(secondFrameScore.isPresent());
        assertFalse(thirdFrameScore.isPresent());
        assertEquals("", firstFrameBoardMarks.get(0));
        assertEquals("X", firstFrameBoardMarks.get(1));
        assertEquals("", secondFrameBoardMarks.get(0));
        assertEquals("X", secondFrameBoardMarks.get(1));
        assertEquals("", thirdFrameBoardMarks.get(0));
        assertEquals("X", thirdFrameBoardMarks.get(1));
    }

    @Test
    public void givenTwentyZerosWhenGetTenthFrameScoreItShouldBeZero() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        for (int i = 0; i < 20; i++) {
            game.roll("0");
        }
        // when
        int tenthFrameIndex = 9;
        Frame tenthFrame = game.getFrames().get(tenthFrameIndex);
        int tenthFrameActualScore = tenthFrame.getScore()
                .orElseThrow(IllegalStateException::new);
        List<String> tenthFrameBoardMarks = tenthFrame.getBoardMarks();
        // then
        int tenthFrameExpectedScore = 0;
        assertEquals(tenthFrameExpectedScore, tenthFrameActualScore);
        assertEquals("0", tenthFrameBoardMarks.get(0));
        assertEquals("0", tenthFrameBoardMarks.get(1));
    }

    @Test
    public void givenTwentyFoulsWhenGetTenthFrameScoreItShouldBeZero() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        for (int i = 0; i < 20; i++) {
            game.roll("F");
        }
        // when
        int tenthFrameIndex = 9;
        Frame tenthFrame = game.getFrames().get(tenthFrameIndex);
        int tenthFrameActualScore = tenthFrame.getScore()
                .orElseThrow(IllegalStateException::new);
        List<String> tenthFrameBoardMarks = tenthFrame.getBoardMarks();
        // then
        int tenthFrameExpectedScore = 0;
        assertEquals(tenthFrameExpectedScore, tenthFrameActualScore);
        assertEquals("F", tenthFrameBoardMarks.get(0));
        assertEquals("F", tenthFrameBoardMarks.get(1));
    }

    @Test
    public void givenEighteenZerosAndSpareAndFiveInTenthFrameWhenGetTenthFrameScoreItShouldBeFifteen() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        for (int i = 0; i < 18; i++) {
            game.roll("0");
        }
        game.roll("7");
        game.roll("3");
        game.roll("5");
        // when
        int tenthFrameIndex = 9;
        Frame tenthFrame = game.getFrames().get(tenthFrameIndex);
        int tenthFrameActualScore = tenthFrame.getScore()
                .orElseThrow(IllegalStateException::new);
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
    public void givenTwelveStrikesWhenGetTenthFrameScoreItShouldBeThreeHundred() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        for (int i = 0; i < 12; i++) {
            game.roll("10");
        }
        // when
        int tenthFrameIndex = 9;
        Frame tenthFrame = game.getFrames().get(tenthFrameIndex);
        int tenthFrameActualScore = tenthFrame.getScore()
                .orElseThrow(IllegalStateException::new);
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
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        // See: https://www.youtube.com/watch?v=aBe71sD8o8c
        Stream.of("8", "2", "7", "3",
                "3", "4", "10",
                "2", "8", "10",
                "10", "8", "0",
                "10", "8", "2", "9").forEach(game::roll);
        // when
        List<Frame> frames = game.getFrames();
        // then
        int expectedFrames = 10;
        assertEquals(expectedFrames, game.getFrames().size());
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
            assertEquals(expectedScores.get(frameIndex), actualFrame.getScore()
                    .orElseThrow(IllegalStateException::new));
            List<String> expectedFrameBoardMarks = expectedBoardMarks.get(frameIndex);
            List<String> actualFrameBoardMarks = actualFrame.getBoardMarks();
            IntStream.range(0, expectedBoardMarks.get(frameIndex).size()).forEach(boardMarkIndex ->
                    assertEquals(expectedFrameBoardMarks.get(boardMarkIndex), actualFrameBoardMarks.get(boardMarkIndex))
            );
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenANegativeNumberOfKnockedPinsWhenRollThenShouldShowError() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        // when
        game.roll("-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenMoreThanTenKnockedPinsWhenRollThenShouldShowError() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        // when
        game.roll("11");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenUnexpectedKnockedPinsMarkWhenRollThenShouldShowError() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        // when
        game.roll("M");
    }

    @Test(expected = IllegalStateException.class)
    public void givenMoreRollsThanMaxAllowedPerGameWhenRollItShouldShowAnError() {
        // given
        TenPinBowlingGame game = new TenPinBowlingGame();
        for (int i = 0; i < 12; i++) {
            game.roll("10");
        }
        // when
        game.roll("10");
    }

}
