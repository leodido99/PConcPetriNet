package com.heigvd.RoadCrossing;

import com.heigvd.Tools.RandomNumber;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class VehicleCreator extends Thread {
    private boolean debug;
    private int creatorID;
    private int vehicleCounter;
    private boolean northSouth;
    private RoadCrossingManager crossingManager;

    public VehicleCreator(RoadCrossingManager crossingManager, int ID, boolean northSouth) {
        creatorID = ID;
        vehicleCounter = 0;
        this.northSouth = northSouth;
        this.crossingManager = crossingManager;
    }

    /**
     * Set debug mode
     * @param debug debug mode
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Returns if it is possible to create a vehicle
     * @return true = possible to create new vehicle
     */
    private boolean canCreateVehicle() {
        if (this.crossingManager.getCrossing().getPosition(this.northSouth, 0)) {
            return false;
        }
        return true;
    }

    /**
     * Job of the VehicleCreator
     */
    public void run() {
        while(true) {
            try {
                /* Sleep random amount of time */
                Thread.sleep(RandomNumber.randomNumberInRange(0, 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /* Check if the road is clear to create a new vehicle */
            if (canCreateVehicle()) {
                vehicleCounter++;
                /* Create new vehicle */
                Vehicle myVehicle = new Vehicle(crossingManager, creatorID << 8 | vehicleCounter, 0, northSouth, RandomNumber.randomNumberInRange(500, 1000));
                if (this.debug) {
                    myVehicle.setDebug(true);
                }
                /* Start thread */
                myVehicle.start();
            }
        }
    }

}
