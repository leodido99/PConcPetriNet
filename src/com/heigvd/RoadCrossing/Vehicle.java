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
        System.out.println("Vehicle " + this.ID + " started");
        /* Loop as long as the vehicle is on the road */
        while(crossingPosition < crossing.getRoadLength()) {



        }
        System.out.println("Vehicle " + this.ID + " reached the end of the road");
    }

}
