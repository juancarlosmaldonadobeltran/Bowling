package com.company.bowling.core;

import com.company.bowling.core.factory.TenPinBowlingGameFrameFactory;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TenPinBowlingGameFrameFactoryTest {

    @Test
    public void givenTheFirstFrameWhenGetInstanceThenShouldNotReturnALastFrameInstance() {
        // given
        int frameNumber = 1;
        // when
        Frame frame = TenPinBowlingGameFrameFactory.getInstance(frameNumber);
        // then
        assertFalse(frame instanceof LastFrame);
    }

    @Test
    public void givenTheLastFrameWhenGetInstanceThenShouldReturnALastFrameInstance() {
        // given
        int frameNumber = 10;
        // when
        Frame frame = TenPinBowlingGameFrameFactory.getInstance(frameNumber);
        // then
        assertTrue(frame instanceof LastFrame);
    }

    @Test
    public void givenAFrameBeforeTheLastOneWhenGetInstanceThenShouldNotReturnALastFrameInstance() {
        // given
        IntStream.range(1, 10).forEach(frameNumber -> {
            // when
            Frame frame = TenPinBowlingGameFrameFactory.getInstance(frameNumber);
            // then
            assertFalse(frame instanceof LastFrame);
        });
    }
}
