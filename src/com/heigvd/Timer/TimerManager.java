package com.heigvd.Timer;

import com.heigvd.PetriNetManager.PetriNetManager;
import com.heigvd.RoadCrossing.RoadCrossingManager;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class TimerManager {
    private PetriNetManager timerPetriNetManager;
    private PetriNetManager roadCrossingPetriNetManager;
    private RoadCrossingManager crossingManager;
    private TimerAction timerAction;

    public TimerManager(PetriNetManager roadCrossingPetriNetManager, RoadCrossingManager crossingManager) {
        this.crossingManager = crossingManager;
        /* Create and setup petri net manager */
        timerPetriNetManager = new PetriNetManager();
        /* TODO xml */
        timerPetriNetManager.loadFromTextFile("com/heigvd/config/TimerRDP.cfg");
        this.roadCrossingPetriNetManager = roadCrossingPetriNetManager;
        /* Create and setup actions */
        timerAction = new TimerAction(this);
        timerAction.setDebug(true);
        timerPetriNetManager.setPlaceAction("OFF", timerAction);
        timerPetriNetManager.setPlaceAction("ON", timerAction);
        timerPetriNetManager.setPlaceAction("END", timerAction);
    }

    public void setRoadCrossingTransition(boolean state) {
        this.roadCrossingPetriNetManager.setEventState("ALaneFilled", state);
        this.roadCrossingPetriNetManager.setEventState("BLaneFilled", state);
    }

    public PetriNetManager getTimerPetriNetManager() {
        return timerPetriNetManager;
    }

    public PetriNetManager getRoadCrossingPetriNetManager() {
        return roadCrossingPetriNetManager;
    }

    public RoadCrossingManager getCrossingManager() {
        return crossingManager;
    }

    public void start() {
        /* Start petri net manager*/
        timerPetriNetManager.start();
    }
}
