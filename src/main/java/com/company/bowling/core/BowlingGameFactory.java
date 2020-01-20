package com.company.bowling.core;

public class BowlingGameFactory {

    public static BowlingGame getInstance() {
        return new TenPinBowlingGame();
    }

}
