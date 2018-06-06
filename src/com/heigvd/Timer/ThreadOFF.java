package com.heigvd.Timer;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class ThreadOFF extends Thread {
    private TimerManager timerManager;

    /**
     * Create a new ThreadOFF
     * @param timerManager Instance of TimerManager
     */
    public ThreadOFF(TimerManager timerManager) {
        this.timerManager = timerManager;
        this.timerManager.setRoadCrossingTransition(false);
        setDaemon(true);
    }

    /**
     * Evalutes the thread condition
     * @return true = condition is satisfied, false = condition is not satisfied
     */
    public boolean evaluateCondition() {
        /* (Check if signal A is green and Vehicles detected in lane B) OR (Check if signal B is green and Vehicles detected in lane A) */
        if ((timerManager.getCrossingManager().getNorthSouthSignal().isGreen() && timerManager.getCrossingManager().getDetectorBeforeCrossingWE().isLastState()) ||
                (timerManager.getCrossingManager().getWestEastSignal().isGreen() && timerManager.getCrossingManager().getDetectorBeforeCrossingNS().isLastState())) {
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
        timerManager.getTimerPetriNetManager().setEventState("ChangeSignalStart", true);
    }
}
