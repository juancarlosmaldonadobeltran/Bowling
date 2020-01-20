package com.company.bowling.core;

import java.util.List;

public interface BowlingGame {

    void roll(String knockedPinsMark);

    List<Frame> getFrames();

}
