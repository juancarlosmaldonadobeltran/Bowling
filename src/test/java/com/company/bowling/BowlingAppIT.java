package com.company.bowling;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public class BowlingAppIT {

    private ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void givenPlayersRollsInputFileWhenExecuteMainThenItShouldRun() throws IOException {
        String playersRawRollsInputPath = "perfect_game.txt";
        URL resource = classLoader.getResource(playersRawRollsInputPath);
        assert resource != null;
        String args[] = {resource.getPath()};
        BowlingGameApp.main(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAMissingArgPlayersRollsInputFileWhenExecuteMainThenItShouldShowAnError() throws IOException {
        String args[] = {};
        BowlingGameApp.main(args);
    }
}
