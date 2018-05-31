package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriNetManager;

/**
 * Created by leonard.bise on 16.05.18.
 */
public class RoadCrossingManager {
    private static final boolean debug = true;
    private PetriNetManager petriNetManagerRoadCrossing;
    private RoadCrossing crossing;
    private static final int roadCrossingSegmentLength = 10;
    private static final int detectedRoadCrossingSegmentLength = 1;
    private RoadSignal northSouthSignal;
    private RoadSignal westEastSignal;
    private RoadCrossingDetector detectorBeforeCrossingNS;
    private RoadCrossingDetector detectorBeforeCrossingWE;
    private RoadCrossingDetector detectorInCrossingNS;
    private RoadCrossingDetector detectorInCrossingWE;
    private VehicleCreator creatorNS;
    private VehicleCreator creatorWE;

    /**
     * Create a new instance of the RoadCrossingManager
     */
    public RoadCrossingManager() {
        /* Create Petri Net Manager and load it */
        this.petriNetManagerRoadCrossing = new PetriNetManager();
        /* TODO xml */
        petriNetManagerRoadCrossing.loadFromTextFile("com/heigvd/config/roadCrossingRDP.cfg");
        /* Create the crossing */
        crossing = new RoadCrossing(this.roadCrossingSegmentLength);
        /* Create the signals */
        northSouthSignal = new RoadSignal("NorthSouthSignal", this.crossing.getRoadCrossingIndex() - 1);
        westEastSignal = new RoadSignal("WestEastSignal", this.crossing.getRoadCrossingIndex() - 1);
        /* Create the detectors */
        detectorBeforeCrossingNS = new RoadCrossingDetector(this, this.getCrossing().getNorthSouthRoad(), this.roadCrossingSegmentLength - this.detectedRoadCrossingSegmentLength, this.detectedRoadCrossingSegmentLength, "", true);
        detectorBeforeCrossingWE = new RoadCrossingDetector(this, this.getCrossing().getWestEastRoad(), this.roadCrossingSegmentLength - this.detectedRoadCrossingSegmentLength, this.detectedRoadCrossingSegmentLength, "", true);
        detectorInCrossingNS = new RoadCrossingDetector(this, this.getCrossing().getNorthSouthRoad(), this.crossing.getRoadCrossingIndex(), 1, "CrossingEmptyA", false);
        detectorInCrossingWE = new RoadCrossingDetector(this, this.getCrossing().getWestEastRoad(), this.crossing.getRoadCrossingIndex(), 1, "CrossingEmptyB", false);
        /* Create the signal actions which are triggered when certain places are reached */
        SignalStateAction NSSignalGreenAction = new SignalStateAction(this.northSouthSignal, true);
        NSSignalGreenAction.setDebug(this.debug);
        this.petriNetManagerRoadCrossing.setPlaceAction("GreenA", NSSignalGreenAction);
        SignalStateAction NSSignalRedAction = new SignalStateAction(this.northSouthSignal, false);
        NSSignalRedAction.setDebug(this.debug);
        this.petriNetManagerRoadCrossing.setPlaceAction("RedA", NSSignalRedAction);
        SignalStateAction WESignalGreenAction = new SignalStateAction(this.westEastSignal, true);
        WESignalGreenAction.setDebug(this.debug);
        this.petriNetManagerRoadCrossing.setPlaceAction("GreenB", WESignalGreenAction);
        SignalStateAction WESignalRedAction = new SignalStateAction(this.westEastSignal, false);
        WESignalRedAction.setDebug(this.debug);
        this.petriNetManagerRoadCrossing.setPlaceAction("RedB", WESignalRedAction);
        /* Create vehicle creator */
        creatorNS = new VehicleCreator(this, 0, true);
        creatorNS.setDebug(this.debug);
        creatorWE = new VehicleCreator(this, 1, false);
        creatorWE.setDebug(this.debug);
    }

    /**
     * Start the RoadCrossingManager
     */
    public void start() {
        /* Start Threads */
        petriNetManagerRoadCrossing.start();
        detectorBeforeCrossingNS.start();
        detectorBeforeCrossingNS.setName("detectorBeforeCrossingNS");
        detectorBeforeCrossingNS.setDebug(this.debug);
        detectorInCrossingNS.start();
        detectorInCrossingNS.setName("detectorInCrossingNS");
        detectorInCrossingNS.setDebug(this.debug);
        detectorBeforeCrossingWE.start();
        detectorBeforeCrossingWE.setName("detectorBeforeCrossingWE");
        detectorBeforeCrossingWE.setDebug(this.debug);
        detectorInCrossingWE.start();
        detectorInCrossingWE.setName("detectorInCrossingWE");
        detectorInCrossingWE.setDebug(this.debug);
        creatorNS.start();
        creatorWE.start();
    }

    /**
     * Returns the instance of the PetriNetManager
     * @return PetriNetManager instance
     */
    public PetriNetManager getPetriNetManagerRoadCrossing() {
        return petriNetManagerRoadCrossing;
    }

    /**
     * Returns the RoadCrossing instance
     * @return RoadCrossing instance
     */
    public RoadCrossing getCrossing() {
        return crossing;
    }

    /**
     * Returns the RoadSignal on the NorthSouth road
     * @return RoadSignal instance
     */
    public RoadSignal getNorthSouthSignal() {
        return northSouthSignal;
    }

    /**
     * Returns the RoadSignal on the WestEast road
     * @return RoadSignal instance
     */
    public RoadSignal getWestEastSignal() {
        return westEastSignal;
    }

    public RoadCrossingDetector getDetectorBeforeCrossingNS() {
        return detectorBeforeCrossingNS;
    }

    public RoadCrossingDetector getDetectorBeforeCrossingWE() {
        return detectorBeforeCrossingWE;
    }

    public RoadCrossingDetector getDetectorInCrossingWE() {
        return detectorInCrossingWE;
    }

    public RoadCrossingDetector getDetectorInCrossingNS() {
        return detectorInCrossingNS;
    }
}
