package com.heigvd.Timer;

import com.heigvd.PetriNetManager.PetriPlace;
import com.heigvd.PetriNetManager.PetriPlaceInterface;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class TimerAction implements PetriPlaceInterface {
    private TimerManager timerManager;
    private boolean debug;

    /**
     * Create a new action
     * @param timerManager Timer manager instance
     */
    public TimerAction(TimerManager timerManager) {
        this.timerManager = timerManager;
    }

    /**
     * Method executed when entering the place
     * @param place The place that triggered the action
     */
    public void execute(PetriPlace place) {
        Thread myThread;
        if (place.getName().contentEquals("ON")) {
            /* Create and start the Thread that checks the specific condition */
            myThread = new ThreadON(this.timerManager);
        } else if (place.getName().contentEquals("OFF")) {
            /* Create and start the Thread that checks the specific condition */
            myThread = new ThreadOFF(this.timerManager);
        } else if (place.getName().contentEquals("END")) {
            /* Create and start the Thread that checks the specific condition */
            myThread = new ThreadEND(this.timerManager);
        } else {
            throw new RuntimeException("Unknown Petri Place " + place.getName());
        }
        myThread.start();
        if (this.debug) {
            System.out.println("TimerAction : " + place.getName() + " executed");
        }
    }

    /**
     * Set debug mode
     * @param debug debug mode
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
