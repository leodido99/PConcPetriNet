package com.heigvd.RoadCrossing;

import java.util.TimerTask;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class RoadSignalTimerTask extends TimerTask {
    private boolean timer1;
    private RoadCrossingEventManager evManager;

    public RoadSignalTimerTask(boolean timer1, RoadCrossingEventManager evManager) {
        this.timer1 = timer1;
        this.evManager = evManager;
    }

    public void run() {
        evManager.triggerTimer(this.timer1);
    }
}
