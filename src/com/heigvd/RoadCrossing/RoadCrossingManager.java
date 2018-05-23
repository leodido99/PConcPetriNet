package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriNetManager;

/**
 * Created by leonard.bise on 16.05.18.
 */
public class RoadCrossingManager {
    private static final boolean debug = true;
    private boolean petriNetTimer;
    private PetriNetManager petriNetManager;
    private RoadCrossing crossing;
    private static final int roadCrossingSegmentLength = 10;
    private static final int detectedRoadCrossingSegmentLength = 1;
    private int roadCrossingPosition;
    private RoadSignal northSouthSignal;
    private RoadSignal westEastSignal;
    private RoadCrossingDetector detectorBeforeCrossingNS;
    private RoadCrossingDetector detectorBeforeCrossingWE;
    private RoadCrossingDetector detectorInCrossingNS;
    private RoadCrossingDetector detectorInCrossingWE;

    public RoadCrossingManager(boolean petriNetTimer) {
        this.petriNetTimer = petriNetTimer;
        /* Create Petri Net Manager and load it */
        this.petriNetManager = new PetriNetManager();
        petriNetManager.loadFromTextFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/roadCrossingRDP.cfg");
        /* Create the crossing */
        crossing = new RoadCrossing(this.roadCrossingSegmentLength);
        roadCrossingPosition = this.roadCrossingSegmentLength;
        /* Create the signals */
        northSouthSignal = new RoadSignal("NorthSouthSignal", roadCrossingPosition - 1);
        westEastSignal = new RoadSignal("WestEastSignal", roadCrossingPosition - 1);
        /* Create the detectors */
        detectorBeforeCrossingNS = new RoadCrossingDetector(this, this.getCrossing().getNorthSouthRoad(), this.roadCrossingSegmentLength - this.detectedRoadCrossingSegmentLength, this.detectedRoadCrossingSegmentLength, "ALaneFilled", true);
        detectorBeforeCrossingWE = new RoadCrossingDetector(this, this.getCrossing().getWestEastRoad(), this.roadCrossingSegmentLength - this.detectedRoadCrossingSegmentLength, this.detectedRoadCrossingSegmentLength, "BLaneFilled", true);
        detectorInCrossingNS = new RoadCrossingDetector(this, this.getCrossing().getNorthSouthRoad(), this.roadCrossingPosition, 1, "CrossingEmptyA", false);
        detectorInCrossingWE = new RoadCrossingDetector(this, this.getCrossing().getWestEastRoad(), this.roadCrossingPosition, 1, "CrossingEmptyB", false);
        /* Create the signal actions which are triggered when certain places are reached */
        SignalStateAction NSSignalGreenAction = new SignalStateAction(this.northSouthSignal, true, null);
        NSSignalGreenAction.setDebug(this.debug);
        this.petriNetManager.setPlaceAction("GreenA", NSSignalGreenAction);
        SignalStateAction NSSignalRedAction = new SignalStateAction(this.northSouthSignal, false, null);
        NSSignalRedAction.setDebug(this.debug);
        this.petriNetManager.setPlaceAction("RedA", NSSignalRedAction);
        SignalStateAction WESignalGreenAction = new SignalStateAction(this.westEastSignal, true, null);
        WESignalGreenAction.setDebug(this.debug);
        this.petriNetManager.setPlaceAction("GreenB", WESignalGreenAction);
        SignalStateAction WESignalRedAction = new SignalStateAction(this.westEastSignal, false, null);
        WESignalRedAction.setDebug(this.debug);
        this.petriNetManager.setPlaceAction("RedB", WESignalRedAction);
        /* Create vehicle creator */
        VehicleCreator creatorNS = new VehicleCreator(this, 0, true);
        creatorNS.setDebug(this.debug);
        VehicleCreator creatorWE = new VehicleCreator(this, 1, false);
        creatorWE.setDebug(this.debug);

        /* Start Threads */
        petriNetManager.start();
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

        if (petriNetTimer) {
            /* TODO Create RDP Timer */
        }
    }

    public PetriNetManager getPetriNetManager() {
        return petriNetManager;
    }

    public void setPetriNetManager(PetriNetManager petriNetManager) {
        this.petriNetManager = petriNetManager;
    }

    public RoadCrossing getCrossing() {
        return crossing;
    }

    public void setCrossing(RoadCrossing crossing) {
        this.crossing = crossing;
    }

    public RoadSignal getNorthSouthSignal() {
        return northSouthSignal;
    }

    public RoadSignal getWestEastSignal() {
        return westEastSignal;
    }

    public int getRoadCrossingPosition() {
        return roadCrossingPosition;
    }


}
