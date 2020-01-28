package com.company.bowling.core;

import java.util.List;
import java.util.Map;

public interface MultiPlayerBowlingGame {

    void roll(String playerName, String rollInputMark);

    Map<String, List<Frame>> score();

}
