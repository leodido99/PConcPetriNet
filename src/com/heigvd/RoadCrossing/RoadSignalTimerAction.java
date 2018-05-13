package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriPlace;
import com.heigvd.PetriNetManager.PetriPlaceInterface;

import java.util.Timer;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class RoadSignalTimerAction extends Timer implements PetriPlaceInterface {
    private boolean enabled;
    private RoadCrossingEventManager evManager;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean timer1;
    private RoadCrossing crossing;
    private boolean northSouth;

    public RoadSignalTimerAction(RoadCrossingEventManager evManager, boolean timer1, boolean northSouth, RoadCrossing crossing) {
        this.timer1 = timer1;
        this.evManager = evManager;
        this.crossing = crossing;
        this.northSouth = northSouth;
        this.enabled = true;
    }

    /**
     * This methode is called by the RDP manager when the appropriate action is triggered
     * @param place The place which triggered the action
     */
    public void execute(PetriPlace place) {
        /* Only start timer if signal is red */
        if (enabled && !crossing.getSignal(this.northSouth).isGreen()) {
            this.schedule(new RoadSignalTimerTask(this.timer1, evManager), 5000);
            this.enabled = false;
        }
    }
}
