package com.heigvd.RoadCrossing;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class RoadCrossing {
    private boolean[] northSouthRoad;
    private boolean[] westEastRoad;
    private int crossingCenterPosition;

    private void initRoad(boolean[] road) {
        for(int i = 0; i < road.length; i++) {
            road[i] = false;
        }
    }

    /**
     * Creates a new road crossing
     * @param segmentLength Segment length after and before the crossing
     */
    public RoadCrossing(int segmentLength) {
        northSouthRoad = new boolean[2 * segmentLength + 1];
        westEastRoad = new boolean[2 * segmentLength + 1];
        crossingCenterPosition = segmentLength;
        initRoad(northSouthRoad);
        initRoad(westEastRoad);
    }

    /**
     * Returns the length of the roads
     * @return Road length
     */
    public int getRoadLength() {
        return northSouthRoad.length;
    }

    /**
     * Returns
     * @param position
     * @return
     */
    public boolean getNorthSouthPosition(int position) {
        return northSouthRoad[position];
    }

    public boolean getWestEastPosition(int position) {
        return westEastRoad[position];
    }
}
