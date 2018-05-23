package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriPlace;
import com.heigvd.PetriNetManager.PetriPlaceInterface;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class SignalStateAction implements PetriPlaceInterface {
    private boolean debug;
    private RoadSignalTimerAction signalTimerAction;
    private RoadSignal signal;
    private boolean signalState;

    /**
     * Create a new action managing a signal
     * @param signal The signal to change state
     * @param signalState The state to change the signal to
     * @param signalTimerAction
     */
    public SignalStateAction(RoadSignal signal, boolean signalState, RoadSignalTimerAction signalTimerAction) {
        this.signal = signal;
        this.signalState = signalState;
        this.debug = false;
        this.signalTimerAction = signalTimerAction;
    }

    /**
     * Execute the action
     * @param place The place that triggered the action
     */
    public void execute(PetriPlace place) {
        if (this.debug) {
            System.out.println("SignalStateAction: " + this.signal.getName() + " State = " + (this.signalState ? "Green" : "Red"));
        }
        this.signal.setGreen(this.signalState);

        if (this.signalTimerAction != null) {
            /* Enable the signal timer again */
            this.signalTimerAction.setEnabled(true);
        }
    }

    /**
     * Set debug mode
     * @param debug true = debug mode on, false = debug mode off
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
