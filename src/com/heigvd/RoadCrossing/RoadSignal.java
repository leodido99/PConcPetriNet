package com.heigvd.RoadCrossing;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class RoadSignal {
    private boolean green;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoadSignal(String name) {
        this.name = name;

        green = false;
    }

    /**
     * Toggle the signal colour from green to red or inversely
     */
    public void toggleSignal() {
        if (green) {
            green = false;
        } else {
            green = true;
        }
    }

    /**
     * Return if the signal is green
     * @return Signal is green
     */
    public boolean isGreen() {
        return green;
    }

    /**
     * Sets the signal colour
     * @param green
     */
    public void setGreen(boolean green) {
        this.green = green;
    }
}
