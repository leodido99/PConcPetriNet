package com.heigvd.Timer;

import com.heigvd.PetriNetManager.PetriNetManager;
import com.heigvd.RoadCrossing.RoadCrossingManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class ThreadON extends Thread {
    private final int timerDuration = 20;
    private Timer timer;
    private RemindTask timerTask;
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
        timer = new Timer();
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
                (this.crossingManager.getNorthSouthSignal().isGreen() && !this.crossingManager.getDetectorBeforeCrossingNS().isLastState()) ||
                (this.crossingManager.getWestEastSignal().isGreen() && !this.crossingManager.getDetectorBeforeCrossingWE().isLastState())) {
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
