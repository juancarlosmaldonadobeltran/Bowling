package com.company.bowling.command;

import java.io.IOException;

public interface BowlingGameCommand {

    void execute(String inputPath) throws IOException;
}
