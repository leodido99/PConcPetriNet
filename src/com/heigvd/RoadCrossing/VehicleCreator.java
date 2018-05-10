package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriNetManager;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class VehicleCreator extends Thread {
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
            vehicleCounter++;
            /* Create new vehicle */
            Vehicle myVehicle = new Vehicle(creatorID << 8 | vehicleCounter, 0, crossing, northSouth);
            /* Trigger new car event */
            evManager.triggerNewCar(northSouth);
            /* Start thread */
            myVehicle.run();
        }
    }

}
