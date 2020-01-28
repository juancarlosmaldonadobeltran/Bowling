package com.company.bowling.command;

import com.company.bowling.core.MultiPlayerBowlingGame;

import java.io.IOException;

public interface BowlingGameCommand {

    MultiPlayerBowlingGame execute(String inputPath) throws IOException;
}
