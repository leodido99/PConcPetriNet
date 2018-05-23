package com.heigvd.PetriNetManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by leonard.bise on 25.04.18.
 */
class PetriNetManagerTest {
    class PetriNetAction implements PetriPlaceInterface {
        ArrayList<String> executedActions;

        public ArrayList<String> getExecutedActions() {
            return executedActions;
        }

        public PetriNetAction() {
            executedActions = new ArrayList<>();
        }

        public void execute(PetriPlace place) {
            System.out.println("!!! Execute action for place " + place.getName() + " !!!");
            executedActions.add(place.getName());
        }
    }

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

    private void smallPetrinet1Steps(PetriNetManager petriNet, PetriNetAction actionHandler) {
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
        assertEquals(0, actionHandler.getExecutedActions().size());
        /* --- Step 1 --- */
        /* Verify nothing changes if transition is not fired */
        /* Perform a step */
        int step = 1;
        System.out.println("--- Step " + step + " ---");
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedInitialMarking, petriNet.getMarkingPost());
        assertEquals(2, actionHandler.getExecutedActions().size());
        ArrayList<String> expectedExecutedActions = new ArrayList<>();
        expectedExecutedActions.add("P1");
        expectedExecutedActions.add("P3");
        assertArrayEquals(expectedExecutedActions.toArray(), actionHandler.getExecutedActions().toArray());
        actionHandler.getExecutedActions().clear();
        /* --- Step 2 --- */
        /* Fire transition */
        petriNet.setEventState("T1", true);
        step = 2;
        System.out.println("--- Step " + step + " ---");
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep2Marking, petriNet.getMarkingPost());
        /* Actions are triggered the step after */
        assertEquals(0, actionHandler.getExecutedActions().size());
        /* --- Step 3 --- */
        /* Verify nothing changes if transition is not fired */
        step = 3;
        System.out.println("--- Step " + step + " ---");
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep2Marking, petriNet.getMarkingPost());
        assertEquals(1, actionHandler.getExecutedActions().size());
        expectedExecutedActions.clear();
        expectedExecutedActions.add("P2");
        assertArrayEquals(expectedExecutedActions.toArray(), actionHandler.getExecutedActions().toArray());
        actionHandler.getExecutedActions().clear();
        /* --- Step 4 --- */
        step = 4;
        System.out.println("--- Step " + step + " ---");
        /* Fire transition */
        petriNet.setEventState("T23", true);
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep4Marking, petriNet.getMarkingPost());
        assertEquals(0, actionHandler.getExecutedActions().size());
        expectedExecutedActions.clear();
        assertArrayEquals(expectedExecutedActions.toArray(), actionHandler.getExecutedActions().toArray());
        /* --- Step 5 --- */
        /* Verify nothing changes if transition is not fired */
        step = 5;
        System.out.println("--- Step " + step + " ---");
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep4Marking, petriNet.getMarkingPost());
        assertEquals(1, actionHandler.getExecutedActions().size());
        expectedExecutedActions.add("P4");
        assertArrayEquals(expectedExecutedActions.toArray(), actionHandler.getExecutedActions().toArray());
        expectedExecutedActions.clear();
        actionHandler.getExecutedActions().clear();
        /* --- Step 6 --- */
        /* Verify only one consumption when two transition enabled on same place */
        /* Fire transition */
        petriNet.setEventState("T41", true);
        petriNet.setEventState("T42", true);
        step = 6;
        System.out.println("--- Step " + step + " ---");
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep6Marking, petriNet.getMarkingPost());
        assertEquals(0, actionHandler.getExecutedActions().size());
        assertArrayEquals(expectedExecutedActions.toArray(), actionHandler.getExecutedActions().toArray());
        /* --- Step 7 --- */
        /* Verify nothing changes if transition is not fired */
        step = 7;
        System.out.println("--- Step " + step + " ---");
        petriNet.step();
        this.printMatrix("Pre-Incidence Matrix after step " + step, petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix after step " + step, petriNet.getPostIncidenceMatrix());
        assertArrayEquals(expectedPreIncidence, petriNet.getPreIncidenceMatrix());
        assertArrayEquals(expectedPostIncidence, petriNet.getPostIncidenceMatrix());
        this.printVector("Marking after step " + step, petriNet.getMarkingPost());
        assertArrayEquals(expectedStep6Marking, petriNet.getMarkingPost());
        assertEquals(0, actionHandler.getExecutedActions().size());
        assertArrayEquals(expectedExecutedActions.toArray(), actionHandler.getExecutedActions().toArray());
    }

    @org.junit.jupiter.api.Test
    void loadFromTextFileTxt1() {
        PetriNetManager petriNet = new PetriNetManager();
        /* Basé sur le réseau de petri de l'exemple des slides C05_projet_MT2018 p15 */
        petriNet.loadFromTextFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/small_petrinet1.cfg");
        /* Ajoute des actions */
        PetriNetAction actionHandler = new PetriNetAction();
        petriNet.setPlaceAction("P1", actionHandler);
        petriNet.setPlaceAction("P2", actionHandler);
        petriNet.setPlaceAction("P3", actionHandler);
        petriNet.setPlaceAction("P4", actionHandler);
        /* Vérifie que l'éxecution des steps est correcte */
        this.smallPetrinet1Steps(petriNet, actionHandler);
    }

    @org.junit.jupiter.api.Test
    void transitionWithoutPreIncidence() {
        int expectedInitialMarking[] = { 0 };
        int expectedStep1Marking[] = { 1 };
        int expectedStep2Marking[] = { 1 };
        PetriNetManager petriNet = new PetriNetManager();
        petriNet.loadFromTextFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/transition_without_preincidence.cfg");
        this.printVector("Initial Marking", petriNet.getMarkingPost());
        assertArrayEquals(expectedInitialMarking, petriNet.getMarkingPost());
        /* --- Step 1 --- */
        /* Perform a step */
        int step = 1;
        System.out.println("--- Step " + step + " ---");
        petriNet.setEventState("T1", true);
        petriNet.step();
        this.printVector("Step Marking", petriNet.getMarkingPost());
        assertArrayEquals(expectedStep1Marking, petriNet.getMarkingPost());
        /* --- Step 2 --- */
        /* Verify nothing changes after another step */
        /* Perform a step */
        step = 2;
        System.out.println("--- Step " + step + " ---");
        petriNet.step();
        this.printVector("Step Marking", petriNet.getMarkingPost());
        assertArrayEquals(expectedStep2Marking, petriNet.getMarkingPost());
    }

    /*@org.junit.jupiter.api.Test
    void loadFromTextFileXml1() {
        PetriNetManager petriNet = new PetriNetManager();
        petriNet.loadFromXMLFile("/Users/leonard.bise/gitrepo/PConcPetriNet/config/small_petrinet1.xml");
        this.printMatrix("Pre-Incidence Matrix", petriNet.getPreIncidenceMatrix());
        this.printMatrix("Post-Incidence Matrix", petriNet.getPostIncidenceMatrix());
    }*/

}