package com.company.bowling.printer;

import com.company.bowling.core.BowlingGame;
import com.company.bowling.core.Frame;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class MultiPlayerBowlingGameConsolePrinter implements MultiPlayerBowlingGamePrinter {

    @Override
    public void print(Map<String, BowlingGame> board) {

        // frame numbers
        StringBuilder toPrint = new StringBuilder("\nFrame");
        IntStream.range(1, 11).mapToObj(frameNumber -> "\t\t" + frameNumber).forEach(toPrint::append);
        // players
        board.entrySet().stream().forEach(playerGame -> {
            toPrint.append("\n" + playerGame.getKey());
            toPrint.append("\nPinfalls");
            List<Frame> frames = playerGame.getValue().getFrames();
            frames.stream().forEach(frame -> {
                frame.getBoardMarks().stream().map(mark -> "\t" + mark).forEach(toPrint::append);
            });
            toPrint.append("\nScore");
            frames.stream().forEach(frame -> {
                frame.getScore().map(mark -> "\t\t" + mark).ifPresent(toPrint::append);
            });
        });
        toPrint.append("\n");
        System.out.print(toPrint.toString());
    }
}
