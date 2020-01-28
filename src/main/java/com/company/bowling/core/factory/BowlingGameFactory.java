package com.company.bowling.core.factory;

import com.company.bowling.core.BowlingGame;
import com.company.bowling.core.TenPinBowlingGame;

public class BowlingGameFactory {

    public static BowlingGame getInstance() {
        return new TenPinBowlingGame();
    }

}
