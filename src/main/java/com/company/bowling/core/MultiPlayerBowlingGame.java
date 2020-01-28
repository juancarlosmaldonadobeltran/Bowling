package com.company.bowling.core;

import java.util.Map;

public interface MultiPlayerBowlingGame {

    void roll(String playerName, String rollInputMark);

    Map<String, BowlingGame> score();

}
