package com.company.bowling.core;

import com.company.bowling.core.exception.ConsecutiveRollsNotValidException;
import com.company.bowling.core.exception.PlayerConsecutiveRollsNotValidException;
import com.company.bowling.core.factory.BowlingGameFactory;

import java.util.HashMap;
import java.util.Map;

public class MultiPlayerTenPinBowlingGame implements MultiPlayerBowlingGame {

    private Map<String, BowlingGame> board;

    public MultiPlayerTenPinBowlingGame() {
        this.board = new HashMap<>();
    }

    @Override
    public void roll(String playerName, String rollInputMark) {
        String key = playerName.trim();
        if (!this.board.containsKey(key)) {
            this.board.put(key, BowlingGameFactory.getInstance());
        }
        try {
            this.board.get(key).roll(rollInputMark);
        } catch (ConsecutiveRollsNotValidException e) {
            throw new PlayerConsecutiveRollsNotValidException("Consecutive rolls not valid for player: " + key, e);
        }
    }

    public Map<String, BowlingGame> score() {
        this.board.forEach((key, value) -> value.score());
        return this.board;
    }

}
