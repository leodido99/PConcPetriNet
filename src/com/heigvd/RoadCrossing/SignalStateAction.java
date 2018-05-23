package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriPlace;
import com.heigvd.PetriNetManager.PetriPlaceInterface;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class SignalStateAction implements PetriPlaceInterface {
    private boolean debug;
    private RoadSignal signal;
    private boolean signalState;

    /**
     * Create a new action managing a signal
     * @param signal The signal to change state
     * @param signalState The state to change the signal to
     */
    public SignalStateAction(RoadSignal signal, boolean signalState) {
        this.signal = signal;
        this.signalState = signalState;
        this.debug = false;
    }

    /**
     * Execute the action
     * @param place The place that triggered the action
     */
    public void execute(PetriPlace place) {
        if (this.debug) {
            System.out.println("SignalStateAction: " + this.signal.getName() + " State = " + (this.signalState ? "Green" : "Red"));
        }
        /* Set signal state */
        this.signal.setGreen(this.signalState);
    }

    /**
     * Set debug mode
     * @param debug true = debug mode on, false = debug mode off
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
