package com.company.bowling.config;

import com.company.bowling.BowlingGameApp;
import com.company.bowling.command.BowlingGameCommand;
import com.company.bowling.command.BowlingGameFileReaderCommand;
import com.company.bowling.core.BowlingGame;
import com.company.bowling.core.MultiPlayerBowlingGame;
import com.company.bowling.core.MultiPlayerTenPinBowlingGame;
import com.company.bowling.core.TenPinBowlingGame;
import com.company.bowling.printer.MultiPlayerBowlingGameConsolePrinter;
import com.company.bowling.printer.MultiPlayerBowlingGamePrinter;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class ModuleConfig extends AbstractModule {

    @Override
    protected void configure() {
        bind(BowlingGameApp.class).in(Singleton.class);
        // Interfaces
        bind(BowlingGameCommand.class).to(BowlingGameFileReaderCommand.class);
        bind(BowlingGame.class).to(TenPinBowlingGame.class);
        bind(MultiPlayerBowlingGame.class).to(MultiPlayerTenPinBowlingGame.class);
        bind(MultiPlayerBowlingGamePrinter.class).to(MultiPlayerBowlingGameConsolePrinter.class);
    }
}