package com.heigvd.Timer;

import com.heigvd.PetriNetManager.PetriNetManager;
import com.heigvd.RoadCrossing.RoadCrossing;
import com.heigvd.RoadCrossing.RoadCrossingManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by leonard.bise on 30.05.18.
 */
class TimerManagerTest {

    class PetriNetManagerMock extends PetriNetManager {

        public PetriNetManagerMock() {

        }

        @Override
        public void setEventState(String transitionName, boolean state) {
            System.out.println("SetEventState " + transitionName + " " + state);
        }

        @Override
        public void run(){

        }
    }

    class RoadCrossingManagerMock extends RoadCrossingManager {
        public RoadCrossingManagerMock() {

        }

    }

    @Test
    public void TimerManagerTest() {
        TimerManager timerManager = new TimerManager(new PetriNetManagerMock(), new RoadCrossingManagerMock());

        //PetriNetManager roadCrossingPetriNetManager, RoadCrossingManager crossingManager

    }

}