package com.heigvd.RoadCrossing;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class Vehicle extends Thread {
    private int ID;
    private int crossingPosition;
    private boolean northSouthRoad;
    private RoadCrossing crossing;

    public Vehicle(int ID, int crossingPosition, RoadCrossing crossing, boolean northSouthRoad) {
        this.ID = ID;
        this.crossingPosition = crossingPosition;
        this.crossing = crossing;
        this.northSouthRoad = northSouthRoad;
    }

    /**
     * Thread content for the vehicle
     */
    public void run() {
        System.out.println("Vehicle " + this.ID + " created");
        /* Loop as long as the vehicle is on the road */
        while(crossingPosition < crossing.getRoadLength()) {
            /* Check if next position is free */
            if (crossing.getPosition(this.northSouthRoad, this.crossingPosition + 1) == false) {
                /* If next position is the crossing, we have to check the signal */
                if (crossing.getCrossingPosition() == this.crossingPosition + 1) {
                    /* Only move if the signal is green */
                    if (crossing.getSignal(this.northSouthRoad).isGreen()) {
                        this.crossingPosition++;
                        crossing.move(this.northSouthRoad, this.crossingPosition);
                    }
                } else {
                    /* Next spot is free */
                    this.crossingPosition++;
                    crossing.move(this.northSouthRoad, this.crossingPosition);
                }
            }
        }
        System.out.println("Vehicle " + this.ID + " reached the end of the road");
    }

}
