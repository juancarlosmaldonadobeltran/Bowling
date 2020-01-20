package com.company.bowling.core;

import com.company.bowling.printer.MultiPlayerBowlingGamePrinter;
import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;

public class MultiPlayerTenPinBowlingGame implements MultiPlayerBowlingGame {

    private Map<String, BowlingGame> board;
    private MultiPlayerBowlingGamePrinter printer;

    @Inject
    public MultiPlayerTenPinBowlingGame(MultiPlayerBowlingGamePrinter printer) {
        this.board = new HashMap<>();
        this.printer = printer;
    }

    @Override
    public void printResults() {
        this.printer.print(this.board);
    }

    @Override
    public void roll(String playerName, String rollInputMark) {
        String key = playerName.trim();
        if (!this.board.containsKey(key)) {
            this.board.put(key, BowlingGameFactory.getInstance());
        }
        this.board.get(key).roll(rollInputMark);
    }

}
