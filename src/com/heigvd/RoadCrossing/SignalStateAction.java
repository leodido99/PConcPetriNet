package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriPlace;
import com.heigvd.PetriNetManager.PetriPlaceInterface;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class SignalStateAction implements PetriPlaceInterface {
    private RoadSignal signal;
    private boolean signalState;

    public SignalStateAction(RoadSignal signal, boolean signalState) {
        this.signal = signal;
        this.signalState = signalState;
    }

    public void execute(PetriPlace place) {
        this.signal.setGreen(this.signalState);
    }
}
