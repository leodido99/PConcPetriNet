package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriPlace;
import com.heigvd.PetriNetManager.PetriPlaceInterface;

import java.util.Timer;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class RoadSignalTimerAction extends Timer implements PetriPlaceInterface {
    private RoadCrossingEventManager evManager;
    private boolean timer1;

    public RoadSignalTimerAction(RoadCrossingEventManager evManager, boolean timer1) {
        this.timer1 = timer1;
        this.evManager = evManager;
    }

    /**
     * This methode is called by the RDP manager when the appropriate action is triggered
     * @param place The place which triggered the action
     */
    public void execute(PetriPlace place) {
        this.schedule(new RoadSignalTimerTask(this.timer1, evManager), 5000);
    }
}
