package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriPlace;
import com.heigvd.PetriNetManager.PetriPlaceInterface;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class SignalStateAction implements PetriPlaceInterface {
    private boolean debug;
    private RoadSignalTimerAction signalTimerAction;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private RoadSignal signal;
    private boolean signalState;
    private RoadCrossing crossing;
    private RoadCrossingEventManager evManager;
    private boolean northSouth;

    public SignalStateAction(RoadSignal signal, boolean signalState, RoadSignalTimerAction signalTimerAction, RoadCrossing crossing, RoadCrossingEventManager evManager, boolean northSouth) {
        this.signal = signal;
        this.signalState = signalState;
        this.debug = false;
        this.signalTimerAction = signalTimerAction;
        this.crossing = crossing;
        this.evManager = evManager;
        this.northSouth = northSouth;
    }

    public void execute(PetriPlace place) {
        if (this.debug) {
            System.out.println("SignalStateAction: " + this.signal.getName() + " State = " + (this.signalState ? "Green" : "Red"));
        }
        this.signal.setGreen(this.signalState);
        if (!this.signalState) {
            /* When setting the signal to red we have to create a detector for the empty crossing */
            RoadCrossingDetector emptyDetector = new RoadCrossingDetector(crossing, crossing.getCrossingPosition(), this.northSouth, false, false, evManager);
            emptyDetector.start();
        }
        if (this.signalTimerAction != null) {
            /* Enable the signal timer again */
            this.signalTimerAction.setEnabled(true);
        }
    }
}
