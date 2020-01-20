package com.company.bowling.command;

import com.company.bowling.core.MultiPlayerBowlingGame;
import com.company.bowling.core.MultiPlayerTenPinBowlingGame;
import com.company.bowling.printer.MultiPlayerBowlingGameConsolePrinter;
import com.company.bowling.printer.MultiPlayerBowlingGamePrinter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class BowlingGameFileReaderIT {

    private ClassLoader classLoader = getClass().getClassLoader();
    private ByteArrayOutputStream outContent;
    private static final PrintStream originalOut = System.out;

    @Before
    public void setUpStream() {
        this.outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStream() {
        System.setOut(originalOut);
    }

    @Test
    public void givenAPerfectGameInputFileWhenExecuteItShouldPintTheGameResultBoard() throws IOException {
        // given
        MultiPlayerBowlingGamePrinter printer = new MultiPlayerBowlingGameConsolePrinter();
        MultiPlayerBowlingGame game = new MultiPlayerTenPinBowlingGame(printer);
        BowlingGameFileReaderCommand command = new BowlingGameFileReaderCommand(game);
        String playersRawRollsInputPath = "perfect_game.txt";
        URL resource = classLoader.getResource(playersRawRollsInputPath);
        // when
        command.execute(resource.getPath());
        // then
        StringBuilder expectedBoard = new StringBuilder("\nFrame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10")
                .append("\nCarl")
                .append("\nPinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX")
                .append("\nScore\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300\n");
        assertEquals(expectedBoard.toString(), outContent.toString());
        originalOut.print(outContent.toString());
    }

    @Test
    public void givenAZeroScoreGameInputFileWhenExecuteItShouldPintTheGameResultBoard() throws IOException {
        // given
        MultiPlayerBowlingGamePrinter printer = new MultiPlayerBowlingGameConsolePrinter();
        MultiPlayerBowlingGame game = new MultiPlayerTenPinBowlingGame(printer);
        BowlingGameFileReaderCommand command = new BowlingGameFileReaderCommand(game);
        String playersRawRollsInputPath = "zero_score.txt";
        URL resource = classLoader.getResource(playersRawRollsInputPath);
        // when
        assert resource != null;
        command.execute(resource.getPath());
        // then
        StringBuilder expectedBoard = new StringBuilder("\nFrame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10")
                .append("\nJeff")
                .append("\nPinfalls\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0")
                .append("\nScore\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\n");
        assertEquals(expectedBoard.toString(), outContent.toString());
        originalOut.print(outContent.toString());
    }

    @Test
    public void givenTwoPlayersGameInputFileWhenExecuteItShouldPintTheGameResultBoard() throws IOException {
        // given
        MultiPlayerBowlingGamePrinter printer = new MultiPlayerBowlingGameConsolePrinter();
        MultiPlayerBowlingGame game = new MultiPlayerTenPinBowlingGame(printer);
        BowlingGameFileReaderCommand command = new BowlingGameFileReaderCommand(game);
        String playersRawRollsInputPath = "two_players_game.txt";
        URL resource = classLoader.getResource(playersRawRollsInputPath);
        // when
        assert resource != null;
        command.execute(resource.getPath());
        // then
        StringBuilder expectedBoard = new StringBuilder("\nFrame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10")
                .append("\nJeff")
                .append("\nPinfalls\t\tX\t7\t/\t9\t0\t\tX\t0\t8\t8\t/\tF\t6\t\tX\t\tX\tX\t8\t1")
                .append("\nScore\t\t20\t\t39\t\t48\t\t66\t\t74\t\t84\t\t90\t\t120\t\t148\t\t167")
                .append("\nJohn")
                .append("\nPinfalls\t3\t/\t6\t3\t\tX\t8\t1\t\tX\t\tX\t9\t0\t7\t/\t4\t4\tX\t9\t0")
                .append("\nScore\t\t16\t\t25\t\t44\t\t53\t\t82\t\t101\t\t110\t\t124\t\t132\t\t151\n");
        assertEquals(expectedBoard.toString(), outContent.toString());
        originalOut.print(outContent.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenMalformedGameInputWhenExecuteItShouldShowAnError() throws IOException {
        // given
        MultiPlayerBowlingGamePrinter printer = new MultiPlayerBowlingGameConsolePrinter();
        MultiPlayerBowlingGame game = new MultiPlayerTenPinBowlingGame(printer);
        BowlingGameFileReaderCommand command = new BowlingGameFileReaderCommand(game);
        String playersRawRollsInputPath = "malformed_game.txt";
        URL resource = classLoader.getResource(playersRawRollsInputPath);
        // when
        assert resource != null;
        command.execute(resource.getPath());
    }
}
