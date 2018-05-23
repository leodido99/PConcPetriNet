package com.heigvd.Timer;

import com.heigvd.PetriNetManager.PetriNetManager;
import com.heigvd.RoadCrossing.RoadCrossingManager;

import java.util.Timer;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class ThreadON extends Thread {
    private Timer timer;
    private RoadCrossingManager crossingManager;
    private PetriNetManager petriNetManagerTimer;

    /**
     * Create a new ThreadON
     * @param crossingManager Instance of the RoadCrossingManager
     */
    public ThreadON(PetriNetManager petriNetManagerTimer, RoadCrossingManager crossingManager, Timer timer) {
        this.crossingManager = crossingManager;
        this.timer = timer;
        this.petriNetManagerTimer = petriNetManagerTimer;
    }

    /**
     * Evalutes the thread condition
     * @return true = condition is satisfied, false = condition is not satisfied
     */
    public boolean evaluateCondition() {
        /* Check if (Time elapsed OR (Signal A is green AND no more cars in lane A) OR (Signal B is green AND no more cars in lane B) */




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
        petriNetManagerTimer.setEventState("ChangeSignalEnd", true);
    }
}
