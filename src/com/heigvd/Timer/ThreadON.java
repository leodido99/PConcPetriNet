package com.heigvd.Timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class ThreadON extends Thread {
    private final int timerDuration = 20;
    private Timer timer;
    private RemindTask timerTask;
    private TimerManager timerManager;

    /**
     * Create a new ThreadON
     * @param timerManager Instance of TimerManager
     */
    public ThreadON(TimerManager timerManager) {
        this.timerManager = timerManager;
        this.timer = new Timer();
        this.startTimer(this.timerDuration);
    }

    /**
     * Class managing the timer elapsing
     */
    class RemindTask extends TimerTask {
        private boolean timerElapsed = false;

        public boolean isTimerElapsed() {
            return timerElapsed;
        }

        public void setTimerElapsed(boolean timerElapsed) {
            this.timerElapsed = timerElapsed;
        }

        public void run() {
            timerElapsed = true;
            timer.cancel(); //Terminate the timer thread
        }
    }

    /**
     * Start the timer
     * @param seconds Time in seconds
     */
    public void startTimer(int seconds) {
        this.timerTask = new RemindTask();
        timer.schedule(this.timerTask, seconds*1000);
    }

    /**
     * Evalutes the thread condition
     * @return true = condition is satisfied, false = condition is not satisfied
     */
    public boolean evaluateCondition() {
        /* Check if (Time elapsed OR (Signal A is green AND no more cars in lane A) OR (Signal B is green AND no more cars in lane B) */
        if (this.timerTask.isTimerElapsed() ||
                (timerManager.getCrossingManager().getNorthSouthSignal().isGreen() && !timerManager.getCrossingManager().getDetectorBeforeCrossingNS().isLastState()) ||
                (timerManager.getCrossingManager().getWestEastSignal().isGreen() && !timerManager.getCrossingManager().getDetectorBeforeCrossingWE().isLastState())) {
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
        timerManager.getTimerPetriNetManager().setEventState("ChangeSignalEnd", true);
    }
}
