package com.heigvd.RoadCrossing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by leonard.bise on 12.05.18.
 */
public class RoadCrossingGUIThread extends Thread {
    private RoadCrossing crossing;
    private ArrayList<JLabel> labels;
    private int nbCol;

    public RoadCrossingGUIThread(RoadCrossing crossing, ArrayList<JLabel> labels, int nbCol) {
        this.crossing = crossing;
        this.labels = labels;
        this.nbCol = nbCol;
    }

    private JLabel getLabel(int x, int y) {
        return labels.get(y * this.nbCol + x);
    }

    public void run() {
        while(true) {
            for(int i = 0; i < this.nbCol; i++) {
                JLabel label = getLabel(crossing.getCrossingPosition(), i);
                label.setText(crossing.getPosition(true, i) ? "Car" : "Empty");
                label = getLabel(i, crossing.getCrossingPosition());
                label.setText(crossing.getPosition(false, i) ? "Car" : "Empty");
            }
            JLabel label = getLabel(crossing.getCrossingPosition(), crossing.getCrossingPosition());
            if (crossing.getPosition(true, crossing.getCrossingPosition()) || crossing.getPosition(false, crossing.getCrossingPosition())) {
                label.setText("Car");
            } else {
                label.setText("Empty");
            }
            label = getLabel(crossing.getCrossingPosition() - 1, crossing.getCrossingPosition() - 1);
            if (crossing.getSignal(true).isGreen()) {
                label.setBackground(Color.GREEN);
            } else {
                label.setBackground(Color.RED);
            }
            label = getLabel(crossing.getCrossingPosition() - 1, crossing.getCrossingPosition() + 1);
            if (crossing.getSignal(false).isGreen()) {
                label.setBackground(Color.GREEN);
            } else {
                label.setBackground(Color.RED);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
