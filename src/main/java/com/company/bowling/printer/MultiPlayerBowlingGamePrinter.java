package com.company.bowling.printer;

import com.company.bowling.core.BowlingGame;

import java.util.Map;

public interface MultiPlayerBowlingGamePrinter {

    void print(Map<String, BowlingGame> board);
}
