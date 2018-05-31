package com.heigvd.RoadCrossing;

/**
 * Created by leonard.bise on 09.05.18.
 * Class for a Detector placed on a road crossing
 */
public class RoadCrossingDetector extends Thread {
    private RoadCrossingManager roadCrossingManager;
    private String transitionToFire;
    private int detectorPosition;
    private boolean[] road;
    private int nbSlotsToCheck;
    private boolean positionExpectedState;
    private boolean debug;
    private boolean lastState;
    private DetectorModes mode;

    /**
     * Create a new Road Crossing Detector
     * @param roadCrossingManager Road Crossing Manager
     * @param road The road to check
     * @param detectorPosition The position of the detector on the road
     * @param nbPositionToCheck The number of position to check start from the detector position
     * @param transitionToFire The petri transition to fire
     * @param positionExpectedState The expected state of all the position
     */
    public RoadCrossingDetector(RoadCrossingManager roadCrossingManager, boolean[] road, int detectorPosition, int nbPositionToCheck, String transitionToFire, boolean positionExpectedState) {
        this.setDaemon(true);
        this.roadCrossingManager = roadCrossingManager;
        this.detectorPosition = detectorPosition;
        this.road = road;
        this.nbSlotsToCheck = nbPositionToCheck;
        this.positionExpectedState = positionExpectedState;
        this.transitionToFire = transitionToFire;
        this.lastState = false;
        this.mode = DetectorModes.ALL_POSITION_STATE;
    }

    /**
     * Check if ALL the position are in the expected state
     * @return true = all position in state, false = not all position in state
     */
    private boolean isAllState() {
        for(int i = 0; i < this.nbSlotsToCheck; i++) {
            if (road[this.detectorPosition + i] != positionExpectedState) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if a single position is in the expected state
     * @return true = a single position was in state, false = no position in state
     */
    private boolean isSingleState() {
        for(int i = 0; i < this.nbSlotsToCheck; i++) {
            if (road[this.detectorPosition + i] == positionExpectedState) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the position of the detector
     * @return Position of the detector
     */
    public int getDetectorPosition() {
        return detectorPosition;
    }

    /**
     * Returns the number of position to check
     * @return Nb position to check

     */
    public int getNbSlotsToCheck() {
        return nbSlotsToCheck;
    }

    /**
     * Returns if the state of the road is as expected or false otherwise
     * @return true = all positions are equal to the expected state, false = not all position are as expected
     */
    private boolean isState() {
        if (mode == DetectorModes.ALL_POSITION_STATE) {
            return isAllState();
        } else if (mode == DetectorModes.SINGLE_POSITION_STATE) {
            return isSingleState();
        } else {
            throw new RuntimeException("Unknown detector mode");

        }
    }

    /**
     * Sets the RDP transition if one is set
     * @param state State of transition
     */
    private void setTransition(boolean state) {
        if (!this.transitionToFire.isEmpty()) {
            roadCrossingManager.getPetriNetManagerRoadCrossing().setEventState(this.transitionToFire, state);
        }
    }

    /**
     *
     */
    public enum DetectorModes {
        /**
         * All position must be in the expected state to trigger the detector
         */
        ALL_POSITION_STATE,
        /**
         * A single position must be in the expected state to trigger the detector
         */
        SINGLE_POSITION_STATE
    }

    /**
     * Sets the detector mode
     * @param mode
     */
    public void setDetectorMode(DetectorModes mode) {
        this.mode = mode;
    }

    /**
     * Job of the Thread
     * Periodically checks the crossing and updates the event state
     */
    public void run() {
        while(true) {
            if (isState()) {
                setTransition(true);
                if (this.debug && this.lastState != true) {
                    System.out.println(this.getName() + " - Event " + this.transitionToFire + " true");
                }
                this.lastState = true;
            } else {
                setTransition(false);
                if (this.debug && this.lastState != false) {
                    System.out.println(this.getName() + " - Event " + this.transitionToFire + " false");
                }
                this.lastState = false;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets debug mode
     * @param debug true = debug mode on; false = debug mode off
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Return the last state of the detector
     * @return true = condition was met, false = condition was not met
     */
    public boolean isLastState() {
        return lastState;
    }
}
