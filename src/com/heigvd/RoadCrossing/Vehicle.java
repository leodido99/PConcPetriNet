package com.heigvd.RoadCrossing;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class Vehicle extends Thread {
    private boolean debug;
    private Double delay;
    private int ID;
    private int crossingPosition;
    private boolean northSouthRoad;
    private RoadCrossingManager crossingManager;

    /**
     * Create a new vehicle
     * @param crossingManager The crossing manager instance
     * @param ID The ID of the vehicle
     * @param crossingPosition The starting position (index) of the vehicle
     * @param northSouthRoad If the vehicle is on the north-south or west-east road
     * @param delay Delay between each thread iteration
     */
    public Vehicle(RoadCrossingManager crossingManager, int ID, int crossingPosition, boolean northSouthRoad, Double delay) {
        this.ID = ID;
        this.crossingPosition = crossingPosition;
        this.crossingManager = crossingManager;
        this.northSouthRoad = northSouthRoad;
        this.delay = delay;
        /* Vehicle enters the crossing */
        this.crossingManager.getCrossing().enter(this.northSouthRoad);
    }

    /**
     * Sets the debug mode
     * @param debug true = enable, false = disable
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Returns the signal on the road the vehicle is moving
     * @return The road signal
     */
    private RoadSignal getSignal() {
        if (this.northSouthRoad) {
            return this.crossingManager.getNorthSouthSignal();
        } else {
            return this.crossingManager.getWestEastSignal();
        }
    }

    /**
     * Returns whether the vehicle can advance
     * @return true = can advance, false = cannot advance
     */
    private boolean canAdvance() {
        /* Check if next position is occupied */
        if (this.crossingManager.getCrossing().getPosition(this.northSouthRoad, this.crossingPosition + 1) == true) {
            return false;
        }
        /* If a signal is present, check its red */
        if (this.getSignal().getSignalPosition() == this.crossingPosition && this.getSignal().isGreen() == false) {
            return false;
        }
        return true;
    }

    /**
     * Thread content for the vehicle
     */
    public void run() {
        System.out.println("Vehicle " + this.ID + " created");
        /* Loop as long as the vehicle is on the road */
        while(crossingPosition < this.crossingManager.getCrossing().getRoadLength() - 1) {
            synchronized (this.crossingManager.getCrossing()) {
                /* Check if the vehicle can advance (i.e. if there isn't any car in front and if a signal is present
                   that it is green */
                if (this.canAdvance()) {
                    this.crossingPosition++;
                    try {
                        this.crossingManager.getCrossing().move(this.northSouthRoad,this.crossingPosition);
                    } catch (RuntimeException e) {
                        System.out.println("Vehicle " + this.ID + " was in an accident");
                        throw new RuntimeException();
                    }
                    if (debug && this.crossingManager.getCrossing().getRoadCrossingIndex() == this.crossingPosition) {
                        System.out.println("Vehicle " + this.ID + " entered the crossing");
                    }
                }
            }
            try {
                Thread.sleep(this.delay.intValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /* The vehicle leaves the crossing */
        this.crossingManager.getCrossing().leave(this.northSouthRoad);
        System.out.println("Vehicle " + this.ID + " reached the end of the road");
    }
}
