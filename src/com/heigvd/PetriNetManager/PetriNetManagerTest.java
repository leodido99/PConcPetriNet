package com.heigvd.PetriNetManager;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by leonard.bise on 25.04.18.
 */
class PetriNetManagerTest {
    public void printMatrix(String msg, int[][] matrix) {
        System.out.println(msg);
        System.out.println("---------------------------");
        for(int j = 0; j < matrix.length; j++) {
            for(int i = 0; i < matrix[j].length; i++) {
                System.out.print(matrix[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------");
    }

    public void printVector(String msg, int[] vector) {
        System.out.println(msg);
        System.out.println("---------------------------");
        for(int j = 0; j < vector.length; j++) {
            System.out.print(vector[j] + " ");
        }
        System.out.println();
        System.out.println("---------------------------");
    }

    private void smallPetrinet1Steps(PetriNetManager petriNet) {
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
        int expectedInitialMarking[] = { 1, 0, 1, 0 };
        int expectedStep2Marking[] = { 0, 1, 1, 0 };
        int expectedStep4Marking[] = { 0, 0, 0, 1 };
        int expectedStep6Marking[] = { 0, 0, 0, 0 };
        this.printMatrix("Pre-Incidence Matrix", petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix", petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Initial Marking", petriNet.getMarkingPost());
        assertArrayEquals(expectedInitialMarking, petriNet.getMarkingPost());
        /* --- Step 1 --- */
        /* Verify nothing changes if transition is not fired */
        /* Perform a step */
        int step = 1;
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedInitialMarking, petriNet.getMarkingPost());
        /* --- Step 2 --- */
        /* Fire transition */
        petriNet.fireTransition("T1");
        step = 2;
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep2Marking, petriNet.getMarkingPost());
        /* --- Step 3 --- */
        /* Verify nothing changes if transition is not fired */
        step = 3;
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep2Marking, petriNet.getMarkingPost());
        /* --- Step 4 --- */
        step = 4;
        /* Fire transition */
        petriNet.fireTransition("T23");
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep4Marking, petriNet.getMarkingPost());
        /* --- Step 5 --- */
        /* Verify nothing changes if transition is not fired */
        step = 5;
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep4Marking, petriNet.getMarkingPost());
        /* --- Step 6 --- */
        /* Verify only one consumption when two transition enabled on same place */
        /* Fire transition */
        petriNet.fireTransition("T41");
        petriNet.fireTransition("T42");
        step = 6;
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep6Marking, petriNet.getMarkingPost());
        /* --- Step 7 --- */
        /* Verify nothing changes if transition is not fired */
        step = 7;
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep6Marking, petriNet.getMarkingPost());
    }

    @org.junit.jupiter.api.Test
    void loadFromTextFileTxt1() {
        PetriNetManager petriNet = new PetriNetManager();
        /* Basé sur le réseau de petri de l'exemple des slides C05_projet_MT2018 p15 */
        petriNet.loadFromTextFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/small_petrinet1.cfg");
        /* Vérifie que l'éxecution des steps est correcte */
        this.smallPetrinet1Steps(petriNet);
    }

    @org.junit.jupiter.api.Test
    void loadFromTextFileXml1() {
        /*PetriNetManager petriNet = new PetriNetManager();
        petriNet.loadFromXMLFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/small_petrinet1.xml");
        this.printMatrix("Pre-Incidence Matrix", petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix", petriNet.getPostIncidenceMatrix());*/
    }

}