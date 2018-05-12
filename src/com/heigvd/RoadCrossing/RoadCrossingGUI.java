package com.heigvd.RoadCrossing;

import javax.swing.*;

/**
 * Created by leonard.bise on 12.05.18.
 */
public class RoadCrossingGUI {
    private JTable table1;
    private JPanel NorthSouthBottom;

    private void createUIComponents() {
        table1 = new JTable(5, 1);
        System.out.println (this.table1.getColumnCount());

        // TODO: place custom component creation code here
    }
}
