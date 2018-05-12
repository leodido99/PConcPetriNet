package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriNetManager;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class RoadCrossingEventManager {
    private EventManager eventManager;

    public RoadCrossingEventManager(PetriNetManager petriNetManager) {
        eventManager = new EventManager(petriNetManager);
        eventManager.newEvent("Green_Light_A", "AllowFA");
        eventManager.newEvent("Green_Light_B", "AllowFB");
        eventManager.newEvent("Car_Before_Crossing_A", "DetectedBeforeCrossingA");
        eventManager.newEvent("Car_Before_Crossing_B", "DetectedBeforeCrossingB");
        eventManager.newEvent("Car_Exit_Crossing_A", "ExitFA");
        eventManager.newEvent("Car_Exit_Crossing_B", "ExitFB");
        eventManager.newEvent("Timer1", "T1");
        eventManager.newEvent("Timer2", "T2");
        eventManager.newEvent("Crossing_Empty_A", "CheckFA");
        eventManager.newEvent("Crossing_Empty_B", "CheckFB");
    }

    public void triggerGreenLight(boolean northSouth) {
        if (northSouth) {
            eventManager.fireEvent("Green_Light_A");
        } else {
            eventManager.fireEvent("Green_Light_B");
        }
    }

    public void triggerCarBeforeCrossing(boolean northSouth) {
        if (northSouth) {
            eventManager.fireEvent("Car_Before_Crossing_A");
        } else {
            eventManager.fireEvent("Car_Before_Crossing_B");
        }
    }

    public void triggerCarExitCrossing(boolean northSouth) {
        if (northSouth) {
            eventManager.fireEvent("Car_Exit_Crossing_A");
        } else {
            eventManager.fireEvent("Car_Exit_Crossing_B");
        }
    }

    public void triggerTimer(boolean timer1) {
        if (timer1) {
            eventManager.fireEvent("Timer1");
        } else {
            eventManager.fireEvent("Timer2");
        }
    }

    public void triggerCrossingEmpty(boolean northSouth) {
        if (northSouth) {
            eventManager.fireEvent("Crossing_Empty_A");
        } else {
            eventManager.fireEvent("Crossing_Empty_B");
        }
    }
}
