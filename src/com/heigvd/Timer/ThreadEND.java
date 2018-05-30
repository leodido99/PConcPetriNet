package com.heigvd.Timer;

import com.heigvd.PetriNetManager.PetriNetManager;
import com.heigvd.RoadCrossing.RoadCrossingManager;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class ThreadEND {
    private RoadCrossingManager crossingManager;
    private PetriNetManager petriNetManagerTimer;

    /**
     * Create a new ThreadEND
     * @param crossingManager Instance of the RoadCrossingManager
     */
    public ThreadEND(PetriNetManager petriNetManagerTimer, RoadCrossingManager crossingManager) {
        this.crossingManager = crossingManager;
        this.petriNetManagerTimer = petriNetManagerTimer;
    }

    /**
     * Evalutes the thread condition
     * @return true = condition is satisfied, false = condition is not satisfied
     */
    public boolean evaluateCondition() {
        /* Check if both of the crossings are empty */
        if (crossingManager.getDetectorInCrossingNS().isLastState() == true
                && crossingManager.getDetectorInCrossingWE().isLastState() == true) {
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
        petriNetManagerTimer.setEventState("EmptyCrossings", true);
    }
}
