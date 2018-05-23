package com.heigvd.Timer;

import com.heigvd.PetriNetManager.PetriNetManager;

/**
 * Created by leonard.bise on 23.05.18.
 */
public class TimerManager {
    private PetriNetManager timerPetriNetManager;
    private PetriNetManager roadCrossingPetriNetManager;

    public TimerManager(PetriNetManager roadCrossingPetriNetManager) {
        timerPetriNetManager = new PetriNetManager();
        /* TODO Get config file from resources + xml if possible */
        timerPetriNetManager.loadFromTextFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/TimerRDP.cfg");
        this.roadCrossingPetriNetManager = roadCrossingPetriNetManager;






        /* Start petri net manager*/
        timerPetriNetManager.start();
    }

}
