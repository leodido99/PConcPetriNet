package com.heigvd.RoadCrossing;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class RoadCrossing {
    private boolean[] northSouthRoad;
    private RoadSignal northSouthSignal;
    private boolean[] westEastRoad;
    private RoadSignal westEastSignal;
    private int crossingCenterPosition;

    /**
     * Initialize a road
     * @param road The road to initialize
     */
    private void initRoad(boolean[] road) {
        for(int i = 0; i < road.length; i++) {
            road[i] = false;
        }
    }

    /**
     * Returns the signal of the specified road
     * @param northSouth
     * @return
     */
    public RoadSignal getSignal(boolean northSouth) {
        if (northSouth) {
            return northSouthSignal;
        } else {
            return westEastSignal;
        }
    }

    /**
     * Creates a new road crossing
     * @param roadLength Length of the roads
     */
    public RoadCrossing(int roadLength) {
        northSouthRoad = new boolean[roadLength];
        westEastRoad = new boolean[roadLength];
        crossingCenterPosition = roadLength / 2;
        initRoad(northSouthRoad);
        initRoad(westEastRoad);
        northSouthSignal = new RoadSignal("northSouthSignal");
        westEastSignal = new RoadSignal("westEastSignal");

    }

    /**
     * Returns the length of the roads
     * @return Road length
     */
    public int getRoadLength() {
        return northSouthRoad.length;
    }

    /**
     * Returns the state of the given position
     * @param northSouth True = North South Road, False = West East Road
     * @param position Position on the road
     * @return true = occupied, false = free
     */
    public synchronized boolean getPosition(boolean northSouth, int position) {
        if (northSouth) {
            return northSouthRoad[position];
        } else {
            return westEastRoad[position];
        }
    }

    /**
     * Returns the position at which is the crossing
     * @return Crossing position
     */
    public synchronized int getCrossingPosition() {
        return this.crossingCenterPosition;
    }

    /**
     * Move one place in the North South road
     * @param position New position
     */
    public synchronized void move(boolean northSouth, int position) {
        if (northSouth) {
            if (northSouthRoad[position]) {
                /* Next position is already filled */
                throw new RuntimeException();
            }
            if (getCrossingPosition() == position && westEastRoad[position]) {
                /* Accident! */
                throw new RuntimeException();
            }
            northSouthRoad[position] = true;
            northSouthRoad[position - 1] = false;
        } else {
            if (westEastRoad[position]) {
                /* Next position is already filled */
                throw new RuntimeException();
            }
            if (getCrossingPosition() == position && northSouthRoad[position]) {
                /* Accident! */
                throw new RuntimeException();
            }
            westEastRoad[position] = true;
            westEastRoad[position -1] = false;
        }
    }

    /**
     * Leave the crossing
     * @param northSouth
     */
    public synchronized void leave(boolean northSouth) {
        if (northSouth) {
            if (northSouthRoad[this.getRoadLength() - 1] == false) {
                /* No one at end of road */
                throw new RuntimeException();
            }
            northSouthRoad[this.getRoadLength() - 1] = false;
        } else {
            if (westEastRoad[this.getRoadLength() - 1] == false) {
                /* No one at end of road */
                throw new RuntimeException();
            }
            westEastRoad[this.getRoadLength() - 1] = false;
        }
    }

    public RoadSignal getNorthSouthSignal() {
        return northSouthSignal;
    }

    public void setNorthSouthSignal(RoadSignal northSouthSignal) {
        this.northSouthSignal = northSouthSignal;
    }

    public RoadSignal getWestEastSignal() {
        return westEastSignal;
    }

    public void setWestEastSignal(RoadSignal westEastSignal) {
        this.westEastSignal = westEastSignal;
    }
}
