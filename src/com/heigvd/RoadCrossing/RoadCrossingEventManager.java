package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriNetManager;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class RoadCrossingEventManager {
    private boolean debug = true;
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
        String debugMsg;
        if (northSouth) {
            eventManager.fireEvent("Green_Light_A");
            debugMsg = "New Event: Green Light A";
        } else {
            eventManager.fireEvent("Green_Light_B");
            debugMsg = "New Event: Green Light B";
        }
        if (debug) {
            System.out.println(debugMsg);
        }
    }

    public void triggerCarBeforeCrossing(boolean northSouth) {
        String debugMsg;
        if (northSouth) {
            eventManager.fireEvent("Car_Before_Crossing_A");
            debugMsg = "New Event: Car Before Crossing A";
        } else {
            eventManager.fireEvent("Car_Before_Crossing_B");
            debugMsg = "New Event: Car Before Crossing B";
        }
        if (debug) {
            System.out.println(debugMsg);
        }
    }

    public void triggerCarExitCrossing(boolean northSouth) {
        String debugMsg;
        if (northSouth) {
            eventManager.fireEvent("Car_Exit_Crossing_A");
            debugMsg = "New Event: Car Exit Crossing A";
        } else {
            eventManager.fireEvent("Car_Exit_Crossing_B");
            debugMsg = "New Event: Car Exit Crossing B";
        }
        if (debug) {
            System.out.println(debugMsg);
        }
    }

    public void triggerTimer(boolean timer1) {
        String debugMsg;
        if (timer1) {
            eventManager.fireEvent("Timer1");
            debugMsg = "New Event: Timer1 Elapsed";
        } else {
            eventManager.fireEvent("Timer2");
            debugMsg = "New Event: Timer2 Elapsed";
        }
        if (debug) {
            System.out.println(debugMsg);
        }

    }

    public void triggerCrossingEmpty(boolean northSouth) {
        String debugMsg;
        if (northSouth) {
            eventManager.fireEvent("Crossing_Empty_A");
            debugMsg = "New Event: Crossing Empty A";
        } else {
            eventManager.fireEvent("Crossing_Empty_B");
            debugMsg = "New Event: Crossing Empty B";
        }
        if (debug) {
            System.out.println(debugMsg);
        }
    }
}
