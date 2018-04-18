package com.heigvd;

import com.heigvd.PetriNetManager.PetriNetManager;

public class Main {

    public static void main(String[] args) {
        PetriNetManager petriNet = new PetriNetManager();
        petriNet.loadFromTextFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/small_petrinet1.cfg");

    }
}
