package com.heigvd.RoadCrossing;

import java.util.TimerTask;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class RoadSignalTimerTask extends TimerTask {
    private boolean timer1;
    private RoadCrossingManager crossingManager;

    public RoadSignalTimerTask(boolean timer1, RoadCrossingManager crossingManager) {
        this.timer1 = timer1;
        this.crossingManager = crossingManager;
    }

    /**
     * When the timer elapses the timer event is triggered
     */
    public void run() {

        //crossingManager.triggerTimer(this.timer1);
    }
}
