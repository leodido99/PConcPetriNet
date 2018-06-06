package com.heigvd;

import com.heigvd.RoadCrossing.*;
import com.heigvd.Timer.TimerManager;

public class Main {
    public static void main(String[] args) {
        RoadCrossingManager roadManager = new RoadCrossingManager();
        System.out.println("Road Length : " + roadManager.getCrossing().getRoadLength());
        System.out.println("Road Crossing Position : " + roadManager.getCrossing().getRoadCrossingIndex());
        /* Timer RDP */
        TimerManager timerManager = new TimerManager(roadManager.getPetriNetManagerRoadCrossing(), roadManager);
        /* Create GUI */
        RoadCrossingGUIThread guiupdater = new RoadCrossingGUIThread(roadManager, roadManager.getCrossing().getRoadLength());
        guiupdater.start();
        /* Start simulation */
        roadManager.start();
        timerManager.start();
    }
}
