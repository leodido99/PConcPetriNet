package com.heigvd.RoadCrossing;

/**
 * Created by leonard.bise on 09.05.18.
 */
public class RoadSignal {
    private boolean green;
    private String name;
    private int signalPosition;

    /**
     * Create a new road signal
     * @param name Name of the signal
     * @param signalPosition The position of the signal on the crossing
     */
    public RoadSignal(String name, int signalPosition) {
        this.name = name;
        green = false;
        this.signalPosition = signalPosition;

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

    /**
     * Get name of signal
     * @return Signal name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of signal
     * @param name Signal name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get signal position
     * @return Signal position on the crossing
     */
    public int getSignalPosition() {
        return signalPosition;
    }

    /**
     * Set the signal position
     * @param signalPosition Signal position on the crossing
     */
    public void setSignalPosition(int signalPosition) {
        this.signalPosition = signalPosition;
    }
}
