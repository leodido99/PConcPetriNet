package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriPlace;
import com.heigvd.PetriNetManager.PetriPlaceInterface;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class SignalStateAction implements PetriPlaceInterface {
    private boolean debug;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private RoadSignal signal;
    private boolean signalState;

    public SignalStateAction(RoadSignal signal, boolean signalState) {
        this.signal = signal;
        this.signalState = signalState;
        this.debug = false;
    }

    public void execute(PetriPlace place) {
        if (this.debug) {
            System.out.println("SignalStateAction: " + this.signal.getName() + " State = " + (this.signalState ? "Green" : "Red"));
        }
        this.signal.setGreen(this.signalState);
    }
}
