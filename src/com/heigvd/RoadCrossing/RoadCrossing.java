package com.heigvd.RoadCrossing;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class RoadCrossing {
    private boolean[] northSouthRoad;
    private boolean[] westEastRoad;
    private int roadCrossingIndex;

    /**
     * Creates a new road crossing
     * @param roadLengthBeforeCrossing Length of the roads before and after the crossing
     */
    public RoadCrossing(int roadLengthBeforeCrossing) {
        northSouthRoad = new boolean[2 * roadLengthBeforeCrossing + 1];
        westEastRoad = new boolean[2 * roadLengthBeforeCrossing + 1];
        initRoad(northSouthRoad);
        initRoad(westEastRoad);
        roadCrossingIndex = roadLengthBeforeCrossing;
    }

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
     * Returns the index of the crossing
     * @return Road crossing index
     */
    public int getRoadCrossingIndex() {
        return roadCrossingIndex;
    }

    /**
     * Returns the total length of the roads
     * @return Total road length
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
     * Move one place forward in the road
     * @param northSouth The road on which to move
     * @param position The new position
     */
    public synchronized void move(boolean northSouth, int position) {
        if (northSouth) {
            if (northSouthRoad[position]) {
                /* Next position is already filled */
                throw new RuntimeException();
            }
            northSouthRoad[position] = true;
            northSouthRoad[position - 1] = false;
        } else {
            if (westEastRoad[position]) {
                /* Next position is already filled */
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

    /**
     * Enter the crossing
     * @param northSouth
     */
    public synchronized void enter(boolean northSouth) {
        if (northSouth) {
            northSouthRoad[0] = true;
        } else {
            westEastRoad[0] = true;
        }
    }

    /**
     * Get the North South Road
     * @return The north south road
     */
    public boolean[] getNorthSouthRoad() {
        return northSouthRoad;
    }

    /**
     * Set the North South Road
     * @param northSouthRoad The north south road
     */
    public void setNorthSouthRoad(boolean[] northSouthRoad) {
        this.northSouthRoad = northSouthRoad;
    }

    /**
     * Get the West East Road
     * @return The west east road
     */
    public boolean[] getWestEastRoad() {
        return westEastRoad;
    }

    /**
     * Set the West East Road
     * @param westEastRoad The west east road
     */
    public void setWestEastRoad(boolean[] westEastRoad) {
        this.westEastRoad = westEastRoad;
    }
}
