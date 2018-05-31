package com.heigvd.Timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class ThreadON extends Thread {
    private boolean debug = true;
    private final int timerDuration = 10;
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
        private boolean debug = false;

        public boolean isTimerElapsed() {
            return timerElapsed;
        }

        public void setTimerElapsed(boolean timerElapsed) {
            this.timerElapsed = timerElapsed;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
        }

        public void run() {
            timerElapsed = true;
            if (debug) {
                System.out.println("Timer Elapsed");
            }
            timer.cancel(); //Terminate the timer thread
        }
    }

    /**
     * Start the timer
     * @param seconds Time in seconds
     */
    public void startTimer(int seconds) {
        if (debug) {
            System.out.println("Started timer");
        }
        this.timerTask = new RemindTask();
        this.timerTask.setDebug(this.debug);
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
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /* Trigger Petri Net Event */
        timerManager.getTimerPetriNetManager().setEventState("ChangeSignalEnd", true);
        if (debug) {
            System.out.println("Thread end");
        }
        this.timerTask.cancel();
    }

    /**
     * Set debug mode
     * @param debug debug mode
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
