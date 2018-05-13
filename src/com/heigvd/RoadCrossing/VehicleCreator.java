package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriNetManager;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class VehicleCreator extends Thread {
    private boolean debug;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private int creatorID;
    private int vehicleCounter;
    private boolean northSouth;
    private RoadCrossing crossing;
    private RoadCrossingEventManager evManager;

    public VehicleCreator(int ID, boolean northSouth, RoadCrossing crossing, RoadCrossingEventManager evManager) {
        creatorID = ID;
        vehicleCounter = 0;
        this.northSouth = northSouth;
        this.crossing = crossing;
        this.evManager = evManager;
    }

    private boolean canCreateVehicle() {
        if (this.crossing.getPosition(this.northSouth, 0)) {
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
                Vehicle myVehicle = new Vehicle(creatorID << 8 | vehicleCounter, 0, crossing, northSouth, evManager, (Math.random() * 2 + 1) * 1000);
                if (this.debug) {
                    myVehicle.setDebug(true);
                }
                /* Start thread */
                myVehicle.start();
            }
        }
    }

}
