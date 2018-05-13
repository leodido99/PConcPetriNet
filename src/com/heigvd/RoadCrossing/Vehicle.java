package com.heigvd.RoadCrossing;

import org.omg.SendingContext.RunTime;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class Vehicle extends Thread {
    private boolean debug;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private Double delay;
    private int ID;
    private int crossingPosition;
    private boolean northSouthRoad;
    private RoadCrossing crossing;
    private RoadCrossingEventManager evManager;

    public Vehicle(int ID, int crossingPosition, RoadCrossing crossing, boolean northSouthRoad, RoadCrossingEventManager evManager, Double delay) {
        this.ID = ID;
        this.crossingPosition = crossingPosition;
        this.crossing = crossing;
        this.northSouthRoad = northSouthRoad;
        this.evManager = evManager;
        this.delay = delay;
    }

    /**
     * Thread content for the vehicle
     */
    public void run() {
        System.out.println("Vehicle " + this.ID + " created");
        /* Loop as long as the vehicle is on the road */
        while(crossingPosition < crossing.getRoadLength() - 1) {
            /* Check if next position is free */
            if (crossing.getPosition(this.northSouthRoad, this.crossingPosition + 1) == false) {
                /* If next position is the crossing, we have to check the signal */
                    if (crossing.getCrossingPosition() == this.crossingPosition + 1) {
                    /* Only move if the signal is green */
                        if (crossing.getSignal(this.northSouthRoad).isGreen()) {
                            this.crossingPosition++;
                            try {
                                crossing.move(this.northSouthRoad, this.crossingPosition);
                            } catch (RuntimeException e) {
                                System.out.println("Vehicle " + this.ID + " was in an accident");
                                throw new RuntimeException();
                            }
                            if (debug) {
                                System.out.println("Vehicle " + this.ID + " entered the crossing");
                            }
                            evManager.triggerGreenLight(this.northSouthRoad);
                        }
                    } else {
                    /* Next spot is free */
                        this.crossingPosition++;
                        crossing.move(this.northSouthRoad, this.crossingPosition);
                    }
                    if (this.crossingPosition == crossing.getCrossingPosition() + 1) {
                    /* The car left the crossing */
                        evManager.triggerCarExitCrossing(this.northSouthRoad);
                    }
                    System.out.println("Vehicle " + this.ID + " position = " + this.crossingPosition);
                }
            try {
                Thread.sleep(this.delay.intValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        crossing.leave(this.northSouthRoad);
        System.out.println("Vehicle " + this.ID + " reached the end of the road");
    }
}
