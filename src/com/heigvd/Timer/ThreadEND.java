package com.heigvd.Timer;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class ThreadEND extends Thread {
    private TimerManager timerManager;

    /**
     * Create a new ThreadEND
     * @param timerManager Instance of TimerManager
     */
    public ThreadEND(TimerManager timerManager) {
        this.timerManager = timerManager;
    }

    /**
     * Evalutes the thread condition
     * @return true = condition is satisfied, false = condition is not satisfied
     */
    public boolean evaluateCondition() {
        /* Check if both of the crossings are empty */
        if (timerManager.getCrossingManager().getDetectorInCrossingNS().isLastState() == true
                && timerManager.getCrossingManager().getDetectorInCrossingWE().isLastState() == true) {
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
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /* Trigger Petri Net Event */
        timerManager.getTimerPetriNetManager().setEventState("EmptyCrossings", true);
        /* Trigger RoadCrossing Event */
        this.timerManager.setRoadCrossingTransition(true);
    }
}
