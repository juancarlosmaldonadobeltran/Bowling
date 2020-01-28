package com.company.bowling.core;

import com.company.bowling.core.factory.BowlingGameFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BowlingGameFactoryTest {

    @Test
    public void whenGetInstanceItShouldBeATenPinBowlingGame() {
        // when
        BowlingGame game = BowlingGameFactory.getInstance();
        // then
        assertTrue(game instanceof TenPinBowlingGame);
    }

}
