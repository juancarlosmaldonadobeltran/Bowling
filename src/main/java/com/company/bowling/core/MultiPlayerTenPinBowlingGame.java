package com.company.bowling.core;

import com.company.bowling.core.exception.ConsecutiveRollsNotValidException;
import com.company.bowling.core.exception.PlayerConsecutiveRollsNotValidException;
import com.company.bowling.core.factory.BowlingGameFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<String, List<Frame>> score() {
        return this.board.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().score())
                );
    }

}
