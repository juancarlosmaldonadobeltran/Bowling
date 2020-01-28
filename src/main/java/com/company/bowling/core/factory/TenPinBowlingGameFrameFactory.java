package com.company.bowling.core.factory;

import com.company.bowling.core.Frame;
import com.company.bowling.core.LastFrame;
import com.company.bowling.core.TenPinBowlingGame;

public class TenPinBowlingGameFrameFactory {

    public static Frame getInstance(int frameNumber) {
        if (frameNumber < TenPinBowlingGame.TOTAL_FRAMES) {
            return Frame.builder().build();
        }
        return LastFrame.builder().build();
    }
}
