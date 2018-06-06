package com.heigvd.RoadCrossing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by leonard.bise on 12.05.18.
 */
public class RoadCrossingGUIThread extends Thread {
    private RoadCrossingManager crossingManager;
    private ArrayList<JLabel> labels;
    private int nbCol;
    private boolean enable = true;

    public RoadCrossingGUIThread(RoadCrossingManager crossingManager, int nbCol) {
        this.crossingManager = crossingManager;
        this.labels = labels;
        this.nbCol = nbCol;
        this.labels =  new ArrayList<>();
        createGUI(crossingManager);
        setDaemon(true);
    }

    private JLabel getLabel(int x, int y) {
        return labels.get(y * this.nbCol + x);
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    private void createGUI(RoadCrossingManager crossingManager) {
        final JFrame frame = new JFrame("RDP GUI");

        GridBagConstraints c = new GridBagConstraints();

        frame.getContentPane().setLayout(new GridLayout(crossingManager.getCrossing().getRoadLength(),crossingManager.getCrossing().getRoadLength()));

        for(int i = 0; i < crossingManager.getCrossing().getRoadLength(); i++) {
            for(int j = 0; j < crossingManager.getCrossing().getRoadLength(); j++) {
                if (i == 0 && j == 0) {
                    c.gridx = j;
                    c.gridy = i;
                    JButton button = new JButton( "X");
                    button.addActionListener(e -> {
                        crossingManager.stop();
                    });
                    frame.getContentPane().add(button, c);
                    labels.add(null);
                } else {
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
    }

    public void run() {
        while(enable) {
            /* Draw cars */
            for(int i = 0; i < this.nbCol; i++) {
                JLabel label = getLabel(crossingManager.getCrossing().getRoadCrossingIndex(), i);
                label.setText(crossingManager.getCrossing().getPosition(true, i) ? "Car" : "");
                label = getLabel(i, crossingManager.getCrossing().getRoadCrossingIndex());
                label.setText(crossingManager.getCrossing().getPosition(false, i) ? "Car" : "");
            }
            /* Draw cars in crossing */
            JLabel label = getLabel(crossingManager.getCrossing().getRoadCrossingIndex(), crossingManager.getCrossing().getRoadCrossingIndex());
            if (crossingManager.getCrossing().getPosition(true, crossingManager.getCrossing().getRoadCrossingIndex()) || crossingManager.getCrossing().getPosition(false, crossingManager.getCrossing().getRoadCrossingIndex())) {
                label.setText("Car");
            } else {
                label.setText("");
            }
            /* Signal color */
            label = getLabel(crossingManager.getCrossing().getRoadCrossingIndex() - 1, crossingManager.getCrossing().getRoadCrossingIndex() - 1);
            if (crossingManager.getNorthSouthSignal().isGreen()) {
                label.setBackground(Color.GREEN);
            } else {
                label.setBackground(Color.RED);
            }
            /* Signal color */
            label = getLabel(crossingManager.getCrossing().getRoadCrossingIndex() - 1, crossingManager.getCrossing().getRoadCrossingIndex() + 1);
            if (crossingManager.getWestEastSignal().isGreen()) {
                label.setBackground(Color.GREEN);
            } else {
                label.setBackground(Color.RED);
            }
            /* Detector */
            for(int i = 0; i < crossingManager.getDetectorBeforeCrossingNS().getNbSlotsToCheck(); i++) {
                label = getLabel(crossingManager.getCrossing().getRoadCrossingIndex(),crossingManager.getDetectorBeforeCrossingNS().getDetectorPosition() + i);
                if (crossingManager.getDetectorBeforeCrossingNS().isLastState()) {
                    label.setBackground(new Color(255,255,0));
                } else {
                    label.setBackground(new Color(255,255,204));
                }
            }
            for(int i = 0; i < crossingManager.getDetectorBeforeCrossingWE().getNbSlotsToCheck(); i++) {
                label = getLabel(crossingManager.getDetectorBeforeCrossingWE().getDetectorPosition() + i,crossingManager.getCrossing().getRoadCrossingIndex());
                if (crossingManager.getDetectorBeforeCrossingWE().isLastState()) {
                    label.setBackground(new Color(255,255,0));
                } else {
                    label.setBackground(new Color(255,255,204));
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
