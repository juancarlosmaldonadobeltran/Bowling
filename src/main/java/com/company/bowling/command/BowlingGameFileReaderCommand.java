package com.company.bowling.command;

import com.company.bowling.core.MultiPlayerBowlingGame;
import com.google.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

public class BowlingGameFileReaderCommand implements BowlingGameCommand {

    private static final String SEPARATED_BY = "\t";

    private MultiPlayerBowlingGame game;

    @Inject
    public BowlingGameFileReaderCommand(MultiPlayerBowlingGame game) {
        this.game = game;
    }

    @Override
    public void execute(String playersRawRollsInputPath) throws IOException {
        File file = FileUtils.getFile(playersRawRollsInputPath);
        try (LineIterator it = FileUtils.lineIterator(file, "UTF-8")) {
            while (it.hasNext()) {
                String rawRoll = it.nextLine().trim();
                if (rawRoll.trim().isEmpty()) continue;
                executeRawRoll(rawRoll.trim());
            }
        }
        game.printResults();
    }

    private void executeRawRoll(String rawRoll) {
        String[] playerRoll = rawRoll.split(SEPARATED_BY);
        if (playerRoll.length != 2) {
            throw new IllegalArgumentException("Malformed input roll: " + rawRoll);
        }
        int playerNameIndex = 0;
        int playerKnockedPinsMarkIndex = 1;
        this.game.roll(playerRoll[playerNameIndex], playerRoll[playerKnockedPinsMarkIndex]);
    }
}
