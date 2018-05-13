package com.heigvd;

import com.heigvd.PetriNetManager.PetriNetManager;
import com.heigvd.RoadCrossing.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    private static void createGUI(RoadCrossing crossing) {
        ArrayList<JLabel> labels = new ArrayList<>();
        final JFrame frame = new JFrame("RDP GUI");

        GridBagConstraints c = new GridBagConstraints();

        frame.getContentPane().setLayout(new GridLayout(10,10));

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                JLabel label = new JLabel("Empty");
                c.gridy = j;
                c.gridx = i;
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
                label.setBorder(border);
                if (i != crossing.getCrossingPosition() && j != crossing.getCrossingPosition()) {
                    label.setVisible(false);
                }
                frame.getContentPane().add(label, c);
                labels.add(label);
            }
        }
        JLabel label = labels.get((crossing.getCrossingPosition() - 1) * 10 + (crossing.getCrossingPosition() - 1));
        label.setVisible(true);
        label.setText("\u21E9");
        label.setOpaque(true);
        label.setBackground(Color.RED);
        label = labels.get((crossing.getCrossingPosition() + 1) * 10 + (crossing.getCrossingPosition() - 1));
        label.setVisible(true);
        label.setText("\u21E8");
        label.setOpaque(true);
        label.setBackground(Color.RED);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
        RoadCrossingGUIThread guiupdater = new RoadCrossingGUIThread(crossing, labels, 10);
        guiupdater.start();
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
        /* Create detectors */
        RoadCrossingDetector detectorBeforeCrossingNS = new RoadCrossingDetector(crossing, crossing.getCrossingPosition() - 1, true, true, true, evManager);
        RoadCrossingDetector detectorBeforeCrossingWE = new RoadCrossingDetector(crossing, crossing.getCrossingPosition() - 1, false, true, true, evManager);
        RoadCrossingDetector detectorInCrossingNS = new RoadCrossingDetector(crossing, crossing.getCrossingPosition(), true, false, false, evManager);
        RoadCrossingDetector detectorInCrossingWE = new RoadCrossingDetector(crossing, crossing.getCrossingPosition(), false, false, false, evManager);
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
        /* Start actors */
        petriNet.start();
        detectorBeforeCrossingNS.setName("detectorBeforeCrossingNS");
        detectorBeforeCrossingNS.start();
        detectorInCrossingNS.setName("detectorInCrossingNS");
        detectorInCrossingNS.start();
        creatorNS.start();
        detectorBeforeCrossingWE.setName("detectorBeforeCrossingWE");
        detectorBeforeCrossingWE.start();
        detectorInCrossingWE.setName("detectorInCrossingWE");
        detectorInCrossingWE.start();
        creatorWE.start();

        Main.createGUI(crossing);
    }
}
