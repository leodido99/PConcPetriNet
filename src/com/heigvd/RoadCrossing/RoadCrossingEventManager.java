package com.heigvd.RoadCrossing;

import com.heigvd.PetriNetManager.PetriNetManager;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class RoadCrossingEventManager {
    private EventManager eventManager;

    public RoadCrossingEventManager(PetriNetManager petriNetManager) {
        // All transitions GenVA GenVB AllowFA AllowFB ExitFA ExitFB T1 T2 CheckFA CheckFB
        eventManager = new EventManager(petriNetManager);
        eventManager.newEvent("New_Car_A", "GenVA");
        eventManager.newEvent("New_Car_B", "GenVB");
        eventManager.newEvent("Car_Before_Crossing_A", "AllowFA");
        eventManager.newEvent("Car_Before_Crossing_B", "AllowFB");
        eventManager.newEvent("Car_Exit_Crossing_A", "ExitFA");
        eventManager.newEvent("Car_Exit_Crossing_B", "ExitFB");
        eventManager.newEvent("Timer1", "T1");
        eventManager.newEvent("Timer2", "T2");
        eventManager.newEvent("SwitchFA", "CheckFB");
        eventManager.newEvent("SwitchFB", "CheckFA");
    }

    public void triggerNewCar(boolean northSouth) {
        if (northSouth) {
            eventManager.fireEvent("New_Car_A");
        } else {
            eventManager.fireEvent("New_Car_B");
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

    public void triggerSwitch(boolean northSouth) {
        if (northSouth) {
            eventManager.fireEvent("SwitchFB");
        } else {
            eventManager.fireEvent("SwitchFA");
        }
    }
}
