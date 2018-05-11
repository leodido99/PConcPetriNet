package com.heigvd;

import com.heigvd.PetriNetManager.PetriNetManager;
import com.heigvd.RoadCrossing.*;

public class Main {
    public static void main(String[] args) {
        PetriNetManager petriNet = new PetriNetManager();
        petriNet.loadFromTextFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/roadCrossingRDP.cfg");
        /* Create the roads and the signals */
        RoadCrossing crossing = new RoadCrossing(10);
        /* Create actions that manage the signal change */
        SignalStateAction NSSignalGreenAction = new SignalStateAction(crossing.getSignal(true), true);
        SignalStateAction NSSignalRedAction = new SignalStateAction(crossing.getSignal(true), false);
        SignalStateAction WESignalGreenAction = new SignalStateAction(crossing.getSignal(false), true);
        SignalStateAction WESignalRedAction = new SignalStateAction(crossing.getSignal(false), false);
        /* Add actions to Petri Net Places */
        petriNet.setPlaceAction("FABlock", NSSignalRedAction);
        petriNet.setPlaceAction("FAAllow", NSSignalGreenAction);
        petriNet.setPlaceAction("FBBlock", WESignalRedAction);
        petriNet.setPlaceAction("FBAllow", WESignalGreenAction);
        NSSignalRedAction.setDebug(true);
        NSSignalGreenAction.setDebug(true);
        WESignalRedAction.setDebug(true);
        WESignalGreenAction.setDebug(true);
        /* Create the manager for events */
        RoadCrossingEventManager evManager = new RoadCrossingEventManager(petriNet);
        /* Create vehicle creator */
        VehicleCreator creatorNS = new VehicleCreator(0, true, crossing, evManager);
        creatorNS.setDebug(true);
        VehicleCreator creatorWE = new VehicleCreator(1, false, crossing, evManager);
        /* Create detectors */
        RoadCrossingDetector detectorBeforeCrossingNS = new RoadCrossingDetector(crossing, crossing.getCrossingPosition() - 1, true, true, true, evManager);
        RoadCrossingDetector detectorBeforeCrossingWE = new RoadCrossingDetector(crossing, crossing.getCrossingPosition() - 1, false, true, true, evManager);
        RoadCrossingDetector detectorInCrossingNS = new RoadCrossingDetector(crossing, crossing.getCrossingPosition(), true, false, false, evManager);
        RoadCrossingDetector detectorInCrossingWE = new RoadCrossingDetector(crossing, crossing.getCrossingPosition(), false, false, false, evManager);



        petriNet.start();
        detectorBeforeCrossingNS.start();
        detectorInCrossingNS.start();
        creatorNS.start();
    }
}
