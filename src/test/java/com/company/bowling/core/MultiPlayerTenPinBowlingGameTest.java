package com.company.bowling.core;

import com.company.bowling.core.exception.PlayerConsecutiveRollsNotValidException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class MultiPlayerTenPinBowlingGameTest {

    private MultiPlayerBowlingGame game;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.game = new MultiPlayerTenPinBowlingGame();
    }

    private void roll(String playerName, String... pins) {
        Stream.of(pins).forEach(pinsRoll -> game.roll(playerName, pinsRoll));
    }

    @Test
    public void givenAPlayerRollsWhenGetResultsItShouldContainsABowlingGameForThatPlayer() {
        // given
        String playerName = "Jeff";
        roll(playerName, "10", "0", "0", "0", "0", "0", "0", "0", "0",
                "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        Map<String, BowlingGame> score = game.score();
        // then
        assertTrue(score.containsKey(playerName));
        assertNotNull(score.get(playerName));
    }

    @Test
    public void givenAPlayerGameRollsWhenGetResultsItShouldContainsThePlayerScore() {
        // given
        String playerName = "Jeff";
        roll(playerName, "5", "4", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        Map<String, BowlingGame> score = game.score();
        // then
        assertTrue(score.containsKey(playerName));
        assertNotNull(score.get(playerName));
        int firstFrameIndex = 0;
        int firstFrameScore = score.get(playerName).score().get(firstFrameIndex).getScore();
        int expectedFirstFrameScore = 9;
        assertEquals(expectedFirstFrameScore, firstFrameScore);
    }

    @Test
    public void givenTwoPlayersGameRollsWhenGetResultsItShouldContainsABowlingGameForEachPlayer() {
        // given
        String firstPlayerName = "Jeff";
        roll(firstPlayerName,
                "10", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        String secondPlayerName = "John";
        roll(secondPlayerName,
                "5", "0", "0", "0", "0", "0", "0", "0", "0", "0",
                "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        Map<String, BowlingGame> score = game.score();
        // then
        assertTrue(score.containsKey(firstPlayerName));
        assertNotNull(score.get(firstPlayerName));
        assertTrue(score.containsKey(secondPlayerName));
        assertNotNull(score.get(secondPlayerName));
        int expectedNumberOfPlayers = 2;
        assertEquals(expectedNumberOfPlayers, score.keySet().size());
    }

    @Test
    public void givenTwoGameRollsForTwoPlayersWhenGetResultsItShouldContainsBowlingGamesForEachPlayer() {
        // given
        String firstPlayerName = "Jeff";
        roll(firstPlayerName,
                "3", "3", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        String secondPlayerName = "John";
        roll(secondPlayerName,
                "2", "F", "0", "0", "0", "0", "0", "0", "0", "0",
                "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        // when
        Map<String, BowlingGame> score = game.score();
        // then
        assertTrue(score.containsKey(firstPlayerName));
        assertNotNull(score.get(firstPlayerName));
        int firstFrameIndex = 0;
        int firstPlayerFirstFrameScore = score.get(firstPlayerName).score().get(firstFrameIndex).getScore();
        int expectedFirstPlayerFirstFrameScore = 6;
        assertEquals(expectedFirstPlayerFirstFrameScore, firstPlayerFirstFrameScore);
        assertTrue(score.containsKey(secondPlayerName));
        assertNotNull(score.get(secondPlayerName));
        int secondPlayerFirstFrameScore = score.get(secondPlayerName).score().get(firstFrameIndex).getScore();
        int expectedSecondPlayerFirstFrameScore = 2;
        assertEquals(expectedSecondPlayerFirstFrameScore, secondPlayerFirstFrameScore);
    }

    @Test(expected = PlayerConsecutiveRollsNotValidException.class)
    public void givenTwoInvalidConsecutivePlayersRollsWhenGetResultsItShouldShowError() {
        // given
        String playerName = "Jeff";
        this.game.roll(playerName, "5");
        this.game.roll(playerName, "7");
    }
}
