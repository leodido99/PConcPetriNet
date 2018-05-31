package com.heigvd.Timer;

import com.heigvd.PetriNetManager.PetriNetManager;
import com.heigvd.RoadCrossing.RoadCrossing;
import com.heigvd.RoadCrossing.RoadCrossingDetector;
import com.heigvd.RoadCrossing.RoadCrossingManager;
import com.heigvd.RoadCrossing.RoadSignal;
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

    class RoadCrossingDetectorMock extends RoadCrossingDetector {
        private boolean state;

        @Override
        public boolean isLastState() {
            return state;
        }

        public void setLastState(boolean state) { this.state = state; }
    }

    class RoadCrossingManagerMock extends RoadCrossingManager {
        private RoadCrossingDetectorMock detectorBeforeCrossingNS;
        private RoadCrossingDetectorMock detectorBeforeCrossingWE;
        private RoadCrossingDetectorMock detectorInCrossingWE;
        private RoadCrossingDetectorMock detectorInCrossingNS;

        public RoadCrossingManagerMock() {
            detectorBeforeCrossingNS = new RoadCrossingDetectorMock();
            detectorBeforeCrossingWE = new RoadCrossingDetectorMock();
            detectorInCrossingWE = new RoadCrossingDetectorMock();
            detectorInCrossingNS = new RoadCrossingDetectorMock();


        }

        @Override
        public RoadCrossingDetector getDetectorBeforeCrossingNS() {
            return detectorBeforeCrossingNS;
        }

        @Override
        public RoadCrossingDetector getDetectorBeforeCrossingWE() {
            return detectorBeforeCrossingWE;
        }

        @Override
        public RoadCrossingDetector getDetectorInCrossingWE() {
            return detectorInCrossingWE;
        }

        @Override
        public RoadCrossingDetector getDetectorInCrossingNS() {
            return detectorInCrossingNS;
        }
    }

    @Test
    public void TimerManagerTest() {
        TimerManager timerManager = new TimerManager(new PetriNetManagerMock(), new RoadCrossingManagerMock());

        //PetriNetManager roadCrossingPetriNetManager, RoadCrossingManager crossingManager

    }

}