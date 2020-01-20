package com.company.bowling.core;

import com.company.bowling.printer.MultiPlayerBowlingGamePrinter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class MultiPlayerTenPinBowlingGameTest {

    @Mock
    private MultiPlayerBowlingGamePrinter printer;
    @Captor
    private ArgumentCaptor<Map<String, BowlingGame>> toPrint;

    private MultiPlayerBowlingGame game;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.game = new MultiPlayerTenPinBowlingGame(this.printer);
    }

    @Test
    public void givenANewPlayerRollWhenGetResultsItShouldContainsANewBowlingGameForThatPlayer() {
        // given
        String playerName = "Jeff";
        game.roll(playerName, "10");
        // when
        game.printResults();
        verify(this.printer).print(toPrint.capture());
        Map<String, BowlingGame> results = toPrint.getValue();
        // then
        assertTrue(results.containsKey(playerName));
        assertNotNull(results.get(playerName));
    }

    @Test
    public void givenANewPlayerSimpleFrameRollsWhenGetResultsItShouldContainsCurrentPlayerScore() {
        // given
        String playerName = "Jeff";
        game.roll(playerName, "5");
        game.roll(playerName, "4");
        // when
        game.printResults();
        verify(this.printer).print(toPrint.capture());
        Map<String, BowlingGame> results = toPrint.getValue();
        // then
        assertTrue(results.containsKey(playerName));
        assertNotNull(results.get(playerName));
        int firstFrameIndex = 0;
        int firstFrameScore = results.get(playerName).getFrames().get(firstFrameIndex).getScore()
                .orElseThrow(IllegalStateException::new);
        int expectedFirstFrameScore = 9;
        assertEquals(expectedFirstFrameScore, firstFrameScore);
    }

    @Test
    public void givenTwoNewPlayersRollsWhenGetResultsItShouldContainNewBowlingGamesForEachPlayer() {
        // given
        String firstPlayerName = "Jeff";
        game.roll(firstPlayerName, "10");
        String secondPlayerName = "John";
        game.roll(secondPlayerName, "5");
        // when
        game.printResults();
        verify(this.printer).print(toPrint.capture());
        Map<String, BowlingGame> results = toPrint.getValue();
        // then
        assertTrue(results.containsKey(firstPlayerName));
        assertNotNull(results.get(firstPlayerName));
        assertTrue(results.containsKey(secondPlayerName));
        assertNotNull(results.get(secondPlayerName));
        int expectedNumberOfPlayers = 2;
        assertEquals(expectedNumberOfPlayers, results.keySet().size());
    }

    @Test
    public void givenTwoSimpleFramesForTwoNewPlayersRollsWhenGetResultsItShouldContainsNewBowlingGamesForEachPlayer() {
        // given
        String firstPlayerName = "Jeff";
        game.roll(firstPlayerName, "3");
        game.roll(firstPlayerName, "3");
        String secondPlayerName = "John";
        game.roll(secondPlayerName, "2");
        game.roll(secondPlayerName, "F");
        // when
        game.printResults();
        verify(this.printer).print(toPrint.capture());
        Map<String, BowlingGame> results = toPrint.getValue();
        // then
        assertTrue(results.containsKey(firstPlayerName));
        assertNotNull(results.get(firstPlayerName));
        int firstFrameIndex = 0;
        int firstPlayerFirstFrameScore = results.get(firstPlayerName).getFrames().get(firstFrameIndex).getScore()
                .orElseThrow(IllegalStateException::new);
        int expectedFirstPlayerFirstFrameScore = 6;
        assertEquals(expectedFirstPlayerFirstFrameScore, firstPlayerFirstFrameScore);
        assertTrue(results.containsKey(secondPlayerName));
        assertNotNull(results.get(secondPlayerName));
        int secondPlayerFirstFrameScore = results.get(secondPlayerName).getFrames().get(firstFrameIndex).getScore()
                .orElseThrow(IllegalStateException::new);
        int expectedSecondPlayerFirstFrameScore = 2;
        assertEquals(expectedSecondPlayerFirstFrameScore, secondPlayerFirstFrameScore);
    }
}
