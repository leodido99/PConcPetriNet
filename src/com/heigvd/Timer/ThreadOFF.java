package com.heigvd.Timer;

import com.heigvd.PetriNetManager.PetriNetManager;
import com.heigvd.RoadCrossing.RoadCrossingManager;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class ThreadOFF extends Thread {
    private RoadCrossingManager crossingManager;
    private PetriNetManager petriNetManagerTimer;

    /**
     * Create a new ThreadOFF
     * @param crossingManager Instance of the RoadCrossingManager
     */
    public ThreadOFF(PetriNetManager petriNetManagerTimer, RoadCrossingManager crossingManager) {
        this.crossingManager = crossingManager;
        this.petriNetManagerTimer = petriNetManagerTimer;
    }

    /**
     * Evalutes the thread condition
     * @return true = condition is satisfied, false = condition is not satisfied
     */
    public boolean evaluateCondition() {
        /* (Check if signal A is green and Vehicles detected in lane B) OR (Check if signal B is green and Vehicles detected in lane A) */
        if ((crossingManager.getNorthSouthSignal().isGreen() && crossingManager.getDetectorBeforeCrossingWE().isLastState()) ||
                (crossingManager.getWestEastSignal().isGreen() && crossingManager.getDetectorBeforeCrossingNS().isLastState())) {
            return true;
        }
        return false;
    }

    /**
     * Thread job
     */
    public void run() {
        while(evaluateCondition() == false) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /* Trigger Petri Net Event */
        petriNetManagerTimer.setEventState("ChangeSignalStart", true);
    }
}
