package com.company.bowling;

import com.company.bowling.command.BowlingGameCommand;
import com.company.bowling.config.ModuleConfig;
import com.company.bowling.core.MultiPlayerBowlingGame;
import com.company.bowling.core.exception.IncompleteGameException;
import com.company.bowling.command.exception.MalformedPlayerInputRoll;
import com.company.bowling.core.exception.PlayerConsecutiveRollsNotValidException;
import com.company.bowling.printer.MultiPlayerBowlingGamePrinter;
import com.google.inject.Guice;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.Arrays;

public class BowlingGameApp {

    private BowlingGameCommand command;
    private MultiPlayerBowlingGamePrinter printer;

    @Inject
    public BowlingGameApp(BowlingGameCommand command, MultiPlayerBowlingGamePrinter printer) {
        this.command = command;
        this.printer = printer;
    }

    void run(String playersRawRollsInputPath) throws IOException {
        try {
            MultiPlayerBowlingGame game = this.command.execute(playersRawRollsInputPath);
            this.printer.print(game);
        } catch (IncompleteGameException | MalformedPlayerInputRoll e) {
            System.out.println(e.getMessage());
        } catch (PlayerConsecutiveRollsNotValidException e) {
            System.out.println(e.getMessage() + ": " + e.getCause().getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        String playersRawRollsInputPath = Arrays.stream(args).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Players rolls input file path is required."));

        Guice.createInjector(new ModuleConfig())
                .getInstance(BowlingGameApp.class)
                .run(playersRawRollsInputPath);
    }
}
