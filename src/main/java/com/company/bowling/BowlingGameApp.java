package com.company.bowling;

import com.company.bowling.command.BowlingGameCommand;
import com.company.bowling.config.ModuleConfig;
import com.google.inject.Guice;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.Arrays;

public class BowlingGameApp {

    private BowlingGameCommand command;

    @Inject
    public BowlingGameApp(BowlingGameCommand command) {
        this.command = command;
    }

    void run(String playersRawRollsInputPath) throws IOException {
        this.command.execute(playersRawRollsInputPath);
    }

    public static void main(String[] args) throws IOException {
        String playersRawRollsInputPath = Arrays.stream(args).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Players rolls input file path is required."));

        Guice.createInjector(new ModuleConfig())
                .getInstance(BowlingGameApp.class)
                .run(playersRawRollsInputPath);
    }
}
