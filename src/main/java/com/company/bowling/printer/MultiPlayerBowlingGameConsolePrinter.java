package com.company.bowling.printer;

import com.company.bowling.core.Frame;
import com.company.bowling.core.MultiPlayerBowlingGame;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class MultiPlayerBowlingGameConsolePrinter implements MultiPlayerBowlingGamePrinter {

    @Override
    public void print(MultiPlayerBowlingGame game) {

        Map<String, List<Frame>> score = game.score();
        // frame numbers
        StringBuilder toPrint = new StringBuilder("\n").append("Frame");
        IntStream.range(1, 11).mapToObj(frameNumber -> "\t\t" + frameNumber).forEach(toPrint::append);
        // players
        score.forEach((key, value) -> {
            toPrint.append("\n").append(key);
            toPrint.append("\n").append("Pinfalls");
            value.forEach(frame -> frame.getBoardMarks().stream().map(mark -> "\t" + mark).forEach(toPrint::append));
            toPrint.append("\n").append("Score");
            value.forEach(frame -> toPrint.append("\t\t").append(frame.getScore()));
        });
        toPrint.append("\n");
        System.out.print(toPrint.toString());
    }
}
