package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriNetManager;

/**
 * Created by leonard.bise on 09.05.18.
 * Class for a Detector placed on a road crossing
 */
public class RoadCrossingDetector extends Thread {
    private boolean enable = true;
    private RoadCrossing crossing;
    private int detectorPosition;
    private boolean northSouth;
    private RoadCrossingEventManager evManager;
    private boolean beforeCrossing;
    private boolean positionExpectedState;

    /**
     * Constructor of the detector
     * @param crossing Crossing to verify
     * @param detectorPosition Position on the crossing
     */
    public RoadCrossingDetector(RoadCrossing crossing, int detectorPosition, boolean northSouth, boolean beforeCrossing, boolean positionExpectedState, RoadCrossingEventManager evManager) {
        this.crossing = crossing;
        this.detectorPosition = detectorPosition;
        this.northSouth = northSouth;
        this.evManager = evManager;
        this.beforeCrossing = beforeCrossing;
        this.positionExpectedState = positionExpectedState;
    }

    /**
     * Returns whether the detector is enabled
     * @return detector state
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * Sets the state of the detector
     * @param enable State of the detector
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * Job of the Thread
     * Periodically checks if the position on the crossing is filled with a car
     */
    public void run() {
        while(enable) {
            if (crossing.getPosition(this.northSouth, this.detectorPosition) == this.positionExpectedState) {
                /* Fire Event */
                if (this.beforeCrossing) {
                    this.evManager.triggerCarBeforeCrossing(this.northSouth);
                } else {
                    this.evManager.triggerCarExitCrossing(this.northSouth);
                }
            }
        }
    }
}
