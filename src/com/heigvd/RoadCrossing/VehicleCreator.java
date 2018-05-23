package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriNetManager;

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

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     *
     * @return
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
            /* Sleep random amount of time */
            try {
                Thread.sleep((long) (Math.random()*10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /* Check if the road is clear to create a new vehicle */
            if (canCreateVehicle()) {
                vehicleCounter++;
                /* Create new vehicle */
                Vehicle myVehicle = new Vehicle(crossingManager, creatorID << 8 | vehicleCounter, 0, northSouth, (Math.random() * 2 + 1) * 1000);
                if (this.debug) {
                    myVehicle.setDebug(true);
                }
                /* Start thread */
                myVehicle.start();
            }
        }
    }

}
