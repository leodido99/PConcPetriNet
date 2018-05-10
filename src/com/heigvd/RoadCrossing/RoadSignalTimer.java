package com.heigvd.RoadCrossing;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class RoadSignalTimer extends Timer {
    private RoadCrossingEventManager evManager;

    public RoadSignalTimer(RoadCrossingEventManager evManager) {
        this.evManager = evManager;
    }

    public void schedule(TimerTask task) {
        this.schedule(task, 1000);
    }
}
