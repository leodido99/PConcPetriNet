package com.heigvd;

import com.heigvd.RoadCrossing.*;
import com.heigvd.Timer.TimerManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    private static void createGUI(RoadCrossingManager crossingManager) {
        ArrayList<JLabel> labels = new ArrayList<>();
        final JFrame frame = new JFrame("RDP GUI");

        GridBagConstraints c = new GridBagConstraints();

        frame.getContentPane().setLayout(new GridLayout(crossingManager.getCrossing().getRoadLength(),crossingManager.getCrossing().getRoadLength()));

        for(int i = 0; i < crossingManager.getCrossing().getRoadLength(); i++) {
            for(int j = 0; j < crossingManager.getCrossing().getRoadLength(); j++) {
                JLabel label = new JLabel("");
                c.gridy = j;
                c.gridx = i;
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
                label.setBorder(border);
                if (i != crossingManager.getCrossing().getRoadCrossingIndex() && j != crossingManager.getCrossing().getRoadCrossingIndex()) {
                    label.setVisible(false);
                }
                frame.getContentPane().add(label, c);
                label.setOpaque(true);
                labels.add(label);
            }
        }
        JLabel label = labels.get((crossingManager.getCrossing().getRoadCrossingIndex() - 1) * crossingManager.getCrossing().getRoadLength() + (crossingManager.getCrossing().getRoadCrossingIndex() - 1));
        label.setVisible(true);
        label.setText("\u21E9");
        label.setOpaque(true);
        label.setBackground(Color.RED);
        label = labels.get((crossingManager.getCrossing().getRoadCrossingIndex() + 1) * crossingManager.getCrossing().getRoadLength() + (crossingManager.getCrossing().getRoadCrossingIndex() - 1));
        label.setVisible(true);
        label.setText("\u21E8");
        label.setOpaque(true);
        label.setBackground(Color.RED);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
        RoadCrossingGUIThread guiupdater = new RoadCrossingGUIThread(crossingManager, labels, crossingManager.getCrossing().getRoadLength());
        guiupdater.start();
    }

    public static void main(String[] args) {
        RoadCrossingManager roadManager = new RoadCrossingManager();
        System.out.println("Road Length : " + roadManager.getCrossing().getRoadLength());
        System.out.println("Road Crossing Position : " + roadManager.getCrossing().getRoadCrossingIndex());
        /* Timer RDP */
        TimerManager timerManager = new TimerManager(roadManager.getPetriNetManagerRoadCrossing(), roadManager);
        /* Create GUI */
        Main.createGUI(roadManager);
        /* Start simulation */
        roadManager.start();
        timerManager.start();
    }
}
