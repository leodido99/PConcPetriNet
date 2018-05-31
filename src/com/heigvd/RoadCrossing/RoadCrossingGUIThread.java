package com.heigvd.RoadCrossing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by leonard.bise on 12.05.18.
 */
public class RoadCrossingGUIThread extends Thread {
    private RoadCrossingManager crossingManager;
    private ArrayList<JLabel> labels;
    private int nbCol;

    public RoadCrossingGUIThread(RoadCrossingManager crossingManager, ArrayList<JLabel> labels, int nbCol) {
        this.crossingManager = crossingManager;
        this.labels = labels;
        this.nbCol = nbCol;
    }

    private JLabel getLabel(int x, int y) {
        return labels.get(y * this.nbCol + x);
    }

    public void run() {
        while(true) {
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
