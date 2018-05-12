package com.heigvd;

import com.heigvd.PetriNetManager.PetriNetManager;
import com.heigvd.RoadCrossing.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static void createGUI() {
        final JFrame frame = new JFrame("RDP GUI");

        JTable table = new JTable(5, 1);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.getContentPane().setLayout(new BorderLayout());



        table.setFillsViewportHeight(true);

        JLabel lblHeading = new JLabel("Stock Quotes");
        lblHeading.setFont(new Font("Arial",Font.TRUETYPE_FONT,24));

        frame.getContentPane().setLayout(new BorderLayout());

            //frame.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
        frame.getContentPane().add(scrollPane,BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 200);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        PetriNetManager petriNet = new PetriNetManager();
        petriNet.loadFromTextFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/roadCrossingRDP.cfg");
        /* Create the roads and the signals */
        RoadCrossing crossing = new RoadCrossing(10);
        /* Create the manager for events */
        RoadCrossingEventManager evManager = new RoadCrossingEventManager(petriNet);
        /* Timer actions */
        RoadSignalTimerAction NSSignalTimerAction = new RoadSignalTimerAction(evManager, false, true, crossing);
        RoadSignalTimerAction WESignalTimerAction = new RoadSignalTimerAction(evManager, true, false, crossing);
        /* Create actions that manage the signal change */
        SignalStateAction NSSignalGreenAction = new SignalStateAction(crossing.getSignal(true), true, null);
        SignalStateAction NSSignalRedAction = new SignalStateAction(crossing.getSignal(true), false, NSSignalTimerAction);
        SignalStateAction WESignalGreenAction = new SignalStateAction(crossing.getSignal(false), true, null);
        SignalStateAction WESignalRedAction = new SignalStateAction(crossing.getSignal(false), false, WESignalTimerAction);
        /* Add actions to Petri Net Places */
        petriNet.setPlaceAction("FABlock", NSSignalRedAction);
        petriNet.setPlaceAction("FAAllow", NSSignalGreenAction);
        petriNet.setPlaceAction("FBBlock", WESignalRedAction);
        petriNet.setPlaceAction("FBAllow", WESignalGreenAction);
        petriNet.setPlaceAction("FBBeforeX", NSSignalTimerAction);
        petriNet.setPlaceAction("FABeforeX", WESignalTimerAction);
        NSSignalRedAction.setDebug(true);
        NSSignalGreenAction.setDebug(true);
        WESignalRedAction.setDebug(true);
        WESignalGreenAction.setDebug(true);
        /* Create vehicle creator */
        VehicleCreator creatorNS = new VehicleCreator(0, true, crossing, evManager);
        creatorNS.setDebug(true);
        VehicleCreator creatorWE = new VehicleCreator(1, false, crossing, evManager);
        creatorWE.setDebug(true);
        /* Create detectors */
        RoadCrossingDetector detectorBeforeCrossingNS = new RoadCrossingDetector(crossing, crossing.getCrossingPosition() - 1, true, true, true, evManager);
        RoadCrossingDetector detectorBeforeCrossingWE = new RoadCrossingDetector(crossing, crossing.getCrossingPosition() - 1, false, true, true, evManager);
        RoadCrossingDetector detectorInCrossingNS = new RoadCrossingDetector(crossing, crossing.getCrossingPosition(), true, false, false, evManager);
        RoadCrossingDetector detectorInCrossingWE = new RoadCrossingDetector(crossing, crossing.getCrossingPosition(), false, false, false, evManager);
        /* Start actors */
        petriNet.start();
        detectorBeforeCrossingNS.start();
        detectorInCrossingNS.start();
        creatorNS.start();
        detectorBeforeCrossingWE.start();
        detectorInCrossingWE.start();
        creatorWE.start();

        Main.createGUI();
    }
}
