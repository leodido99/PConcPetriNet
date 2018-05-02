package com.heigvd.PetriNetManager;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by leonard.bise on 25.04.18.
 */
class PetriNetManagerTest {
    @org.junit.jupiter.api.Test
    void loadFromTextFileTxt1() {
        /* Basé sur l'exemple des slides C05_projet_MT2018 p15 */
        int expectedPreIncidence[][] = {
                { 1, 0, 0, 0 },
                { 0, 1, 0, 0 },
                { 0, 1, 0, 0 },
                { 0, 0, 1, 1 }};
        int expectedPostIncidence[][] = {
                { 0, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 1, 0, 0 }};
        PetriNetManager petriNet = new PetriNetManager();
        petriNet.loadFromTextFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/small_petrinet1.cfg");
        petriNet.printPreIncidenceMatrix();
        petriNet.printPostIncidenceMatrix();
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
    }

    @org.junit.jupiter.api.Test
    void loadFromTextFileXml1() {
        PetriNetManager petriNet = new PetriNetManager();
        petriNet.loadFromXMLFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/small_petrinet1.xml");
        petriNet.printPreIncidenceMatrix();
        petriNet.printPostIncidenceMatrix();
    }

}