package com.company.bowling.printer;

import com.company.bowling.core.MultiPlayerBowlingGame;
import com.company.bowling.core.MultiPlayerTenPinBowlingGame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class MultiPlayerBowlingGameConsolePrinterIT {

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
    public void givenTwoPlayersGameResultsWhenPrintItShouldPrintTheBoard() {
        // given
        MultiPlayerBowlingGamePrinter printer = new MultiPlayerBowlingGameConsolePrinter();
        MultiPlayerBowlingGame game = new MultiPlayerTenPinBowlingGame(printer);
        // when
        Stream.of("10", "7", "3", "9", "0", "10", "0", "8", "8", "2", "F", "6", "10", "10", "10", "8", "1")
                .forEach(mark -> game.roll("Jeff", mark));
        Stream.of("3", "7", "6", "3", "10", "8", "1", "10", "10", "9", "0", "7", "3", "4", "4", "10", "9", "0")
                .forEach(mark -> game.roll("John", mark));
        game.printResults();
        // then
        StringBuilder expectedToPrint = new StringBuilder("\nFrame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10")
                .append("\nJeff")
                .append("\nPinfalls\t\tX\t7\t/\t9\t0\t\tX\t0\t8\t8\t/\tF\t6\t\tX\t\tX\tX\t8\t1")
                .append("\nScore\t\t20\t\t39\t\t48\t\t66\t\t74\t\t84\t\t90\t\t120\t\t148\t\t167")
                .append("\nJohn")
                .append("\nPinfalls\t3\t/\t6\t3\t\tX\t8\t1\t\tX\t\tX\t9\t0\t7\t/\t4\t4\tX\t9\t0")
                .append("\nScore\t\t16\t\t25\t\t44\t\t53\t\t82\t\t101\t\t110\t\t124\t\t132\t\t151\n");
        assertEquals(expectedToPrint.toString(), outContent.toString());
    }
}
