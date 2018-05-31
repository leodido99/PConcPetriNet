package com.heigvd.PetriNetManager;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by leonard.bise on 18.04.18.
 */
public class PetriNetManager extends Thread {
    private boolean debug = true;

    public ArrayList<String> getEventList() {
        return eventList;
    }

    /* Post and Pre marking.

         * The number of token in each places */
    private int[] markingPost;
    private int[] markingPre;

    /* The pre-incidence matrix
     * Link between places and transitions
     * Indexing [Place][Transition] */
    private int[][] preIncidenceMatrix;

    /* The post-incidence matrix
     * Link between transitions and places
     * Indexing [Place][Transition] */
    private int[][] postIncidenceMatrix;

    /* List of enabled transitions (sensibilisées) */
    private Vector<PetriTransition> enabledTransitions;
    /* List of fired transitions (franchies) */
    private Vector<PetriTransition> firedTransitions;

    private Vector<PetriTransition> transitions;
    private Vector<PetriPlace> places;
    private Vector<PetriArc> preIncidenceArcs;

    private int nbTransitions = -1;
    private int nbPlaces = -1;
    private int nbPreIncidenceArcs = -1;
    private int nbPostIncidenceArcs = -1;

    private ArrayList<String> eventList;

    public int[][] getPreIncidenceMatrix() {
        return preIncidenceMatrix;
    }

    public int[][] getPostIncidenceMatrix() {
        return postIncidenceMatrix;
    }


    /**
     * Constructor for the PetriNetManager
     */
    public PetriNetManager() {
        places = new Vector<>();
        transitions = new Vector<>();
        preIncidenceArcs = new Vector<>();
        enabledTransitions = new Vector<>();
        firedTransitions = new Vector<>();
        eventList = new ArrayList<>();
        setDaemon(true);
    }

    /**
     * Print debug message if debug enabled
     * @param message
     */
    private void printDebug(String message) {
        if (this.debug) {
            System.out.println(message);
        }
    }

    /**
     * Fire a transition
     * @param transitionName Transition to fire
     */
    public void setEventState(String transitionName, boolean state) {
        transitions.get(findTransitionIndex(transitionName)).setFired(state);
    }

    /**
     * Set all transition to not be enabled (Sensibilisée)
     */
    private void clearAllTransitionsEnableStatus() {
        for(PetriTransition transition : transitions) {
            transition.setEnabled(false);
        }
        this.enabledTransitions.clear();
    }

    /**
     * Sets the action to be executed for a certain place
     * @param placeName The place name
     * @param action The action to perform
     */
    public void setPlaceAction(String placeName, PetriPlaceInterface action) {
        this.places.get(findPlaceIndex(placeName)).setAction(action);
    }

    /**
     * Waits the duration of a tick
     */
    private void waitTick() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Perform a single step of the RDP
     */
    public void step() {
        clearAllTransitionsEnableStatus();
        executePhase0();
        executePhase1();
        executePhase2();
        executePhase3();
    }

    /**
     * The RDP engine
     */
    public void run(){
        while(true) {
            /* Wait for tick */
            waitTick();
            this.step();
            if (this.debug) {
                System.out.println("---------------------------");
                System.out.println("Marking Post:");
                for(int j = 0; j < markingPost.length; j++) {
                    System.out.print(markingPost[j] + " ");
                }
                System.out.println();
                System.out.println("---------------------------");
            }
        }
    }

    /**
     * Executes the activity for the given place
     * @param placeIndex Index of the place
     */
    private void executeActivity(int placeIndex) {
        if (places.get(placeIndex).getAction() != null) {
            places.get(placeIndex).getAction().execute(places.get(placeIndex));
        }
    }

    /**
     * Returns the post marking
     * @return Post marking
     */
    public int[] getMarkingPost() {
        return markingPost;
    }

    /**
     * Returns the pre marking
     * @return Pre marking
     */
    public int[] getMarkingPre() {
        return markingPre;
    }

    /**
     * Execute Phase 0 of the RDP
     * Execute the actions of each places if required
     */
    private void executePhase0() {
        for(int i = 0; i < this.nbPlaces; i++) {
            if (this.markingPost[i] > this.markingPre[i]) {
                /* Start activity */
                executeActivity(i);
            }
        }
        /* Marking Post = Marking Pre */
        System.arraycopy(this.markingPost, 0, this.markingPre, 0, this.nbPlaces);
    }

    /**
     * Returns a vector containing all the arcs connected to a given place
     * @param placeIndex Place index
     * @return List with all connected arcs
     */
    private Vector<PetriArc> getAllConnectedArcs(int placeIndex, int transitionIndex) {
        Vector<PetriArc> connectedArcs = new Vector<>();
        for(PetriArc arc : preIncidenceArcs) {
            if (arc.getFromPlace().equals(places.get(placeIndex).getName()) && arc.getToTransition().equals(transitions.get(transitionIndex).getName())) {
                connectedArcs.add(arc);
            }
        }
        return connectedArcs;
    }

    /**
     * Returns if a transition at index is enabled (sensibilisée)
     * @param transitionIndex Index of the transition
     * @return true = enabled, false = not enabled
     */
    private boolean isEnabled(int transitionIndex) {
        /* By default transitions are enabled
         * Used to allow transition with no connected places to be enabled */
        boolean enabled = true;
        /* For each place get all the arcs that are connected to the transition */
        for(int i = 0; i < nbPlaces; i++) {
            Vector<PetriArc> connectedArcs = getAllConnectedArcs(i, transitionIndex);
            for(PetriArc arc : connectedArcs) {
                /* Depending on the type of the arc verify if the transition is enabled */
                if (arc.getType() == PetriArcType.SIMPLE || arc.getType() == PetriArcType.TEST) {
                    /* For the simple or test arcs if the marking is lesser than what is in the
                     * pre-incidence matrix, the transition is not enabled */
                    if (markingPre[i] < preIncidenceMatrix[i][transitionIndex]) {
                        return false;
                    }
                } else if (arc.getType() == PetriArcType.INHIBIT) {
                    /* For inhibit arcs the marking must be equal to zero for the transition to be enabled */
                    if (markingPre[i] != 0) {
                        return false;
                    }
                }
            }
        }
        return enabled;
    }

    /**
     * Execute Phase 1 of the RDP
     * Determine which transitions are enabled (sensibilisée)
     */
    private void executePhase1() {
        for(int i = 0; i < nbTransitions; i++) {
            if (isEnabled(i)) {
                /* Add transition to list of enabled transition */
                enabledTransitions.add(transitions.get(i));
                /* Update the transition status */
                transitions.get(i).setEnabled(true);
            }
        }
        /* Shuffle enabled transitions */
        Collections.shuffle(enabledTransitions);
    }

    /**
     * Consume all tokens from the places that are linked to the transition
     * @param transitionIndex Index of the transition
     * @return List of the index of the modified places
     */
    private Vector<Integer> consumeAllTokens(int transitionIndex) {
        Vector<Integer> placesModified = new Vector<>();
        for(int i = 0; i < nbPlaces; i++) {
            markingPre[i] = markingPre[i] - preIncidenceMatrix[i][transitionIndex];
            if (preIncidenceMatrix[i][transitionIndex] > 0) {
                placesModified.add(i);
            }
        }
        return placesModified;
    }

    /**
     * Returns whether a place is connected to a transition with a pre-incidence arc
     * @param placeIndex Place index
     * @param transitionIndex Transition index
     * @return true = connected, false = not connected
     */
    private boolean isPreIncidence(int placeIndex, int transitionIndex) {
        if (preIncidenceMatrix[placeIndex][transitionIndex] > 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns a vector with all the transitions connected with a pre-incidence arc to the given place
     * @param placeIndex Place index
     * @return Vector of transition index connected to the place
     */
    private Vector<Integer> getPreIncidenceTransitions(int placeIndex) {
        Vector<Integer> connected = new Vector<>();
        for(int i = 0; i < nbTransitions; i++) {
            if (preIncidenceMatrix[placeIndex][i] > 0) {
                connected.add(i);
            }
        }
        return connected;
    }

    /**
     * Returns whether the passed transition has been fired (An event is present in the queue)
     * @param transitionName Name of the transition
     * @return true = event happened, false = event didn't happen
     */
    private boolean TransitionEventHappened(String transitionName) {
        return transitions.get(findTransitionIndex(transitionName)).isFired();
    }

    /**
     * Set debug mode
     * @param debug true = debug mode on, false = debug mode off
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Execute Phase 2 of the RDP
     * Check for all enabled transitions (sensibilisée) which one have been fired
     */
    private void executePhase2() {
        Vector<PetriTransition> updatedTransitions = new Vector<>();
        for(PetriTransition transition : enabledTransitions) {
            if (TransitionEventHappened(transition.getName()) && transition.isEnabled()) {
                Vector<Integer> modifiedPlaces = consumeAllTokens(findTransitionIndex(transition.getName()));
                firedTransitions.add(transition);
                /* Transition has been fired */
                transition.setFired(false);
                /* Check if there are any other transitions that are linked to the same place, remove them from the list */
                for(Integer placeIdx : modifiedPlaces) {
                    /* Get a list of all the transitions connected to the place */
                    Vector<Integer> connectedTransitions = getPreIncidenceTransitions(placeIdx);
                    /* Check if any of the transitions connected to the place are part of the list */
                    for(Integer transitionIdx : connectedTransitions) {
                        if (isPreIncidence(placeIdx, transitionIdx)) {
                            /* Update enabled status of the transition */
                            transitions.get(transitionIdx).setEnabled(false);
                            updatedTransitions.add(transitions.get(transitionIdx));
                        }
                    }
                }
            }
        }
        /* Remove fired transitions from the enabled list */
        enabledTransitions.removeAll(firedTransitions);
        enabledTransitions.removeAll(updatedTransitions);
    }

    /**
     * Execute Phase 3 of the RDP
     * Produce tokens in the place that are connected to the fired transitions
     */
    private void executePhase3() {
        System.arraycopy(this.markingPre, 0, this.markingPost, 0, this.nbPlaces);
        for(PetriTransition transition : firedTransitions) {
            for(int i = 0; i < nbPlaces; i++) {
                markingPost[i] = markingPost[i] + postIncidenceMatrix[i][findTransitionIndex(transition.getName())];
            }
        }
        firedTransitions.clear();
    }

    /**
     * Find a place from its name
     * @param placeName Place name
     * @return The place
     */
    @Nullable
    private PetriPlace findPlace(String placeName) {
        for(PetriPlace place : places) {
            if (place.getName().equals(placeName)) {
                return place;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Find a place index from its name
     * @param placeName Place name
     * @return Index of the place
     */
    private int findPlaceIndex(String placeName) {
        int idx = places.indexOf(findPlace(placeName));
        //printDebug("findPlaceIndex: " + placeName + " = " + idx);
        return idx;
    }

    /**
     * Find a transition from its name
     * @param transitionName Transition name
     * @return The transition
     */
    @Nullable
    private PetriTransition findTransition(String transitionName) {
        for(PetriTransition transition : transitions) {
            if (transition.getName().equals(transitionName)) {
                return transition;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Find a transition index from its name
     * @param transitionName Transition name
     * @return Index of the transition
     */
    private int findTransitionIndex(String transitionName) {
        int idx = transitions.indexOf(findTransition(transitionName));
        return idx;
    }

    /**
     * Returns if a certain line of the text configuration is valid
     * @param line Line of configuration
     * @return validity
     */
    private boolean isValidLine(String line) {
        boolean valid = false;
        // Check line is not comment or empty
        if (line != null) {
            if (line.length() == 1 || (line.length() >= 2 && line.charAt(0) != '/' && line.charAt(1) != '/')) {
                valid = true;
            }
        }
        return valid;
    }

    /**
     * Create a new place
     * @param name Place name
     * @param capacity Capacity of the place
     * @param initToken Number of starting tokens
     */
    private void newPlace(String name, int initToken, int capacity) {
        places.add(new PetriPlace(name, capacity, initToken));
        markingPost[findPlaceIndex(name)] = initToken;
    }

    /**
     * Setup all places from a text file
     * @param bufferedReader Buffer to the text file
     * @param nbPlaces Number of places
     * @throws IOException
     */
    private void setupPlaces(BufferedReader bufferedReader, int nbPlaces) throws IOException {
        String line = null;
        /* Read the Places */
        int i = 0;
        do {
            line = bufferedReader.readLine();
            if (this.isValidLine(line)) {
                String[] placeProps = line.split(" ");
                //printDebug("New Place: " + Arrays.toString(placeProps));
                /* Create new place and add to vector */
                newPlace(placeProps[0], Integer.parseInt(placeProps[1]), Integer.parseInt(placeProps[2]));
                i++;
            }
        } while(i < nbPlaces);
        printDebug("Places: " + places);
    }

    /**
     * Setup all places from a list of nodes
     * @param list Node list of xml elements
     */
    public void setupPlaces(NodeList list) {
        for (int temp = 0; temp < list.getLength(); temp++) {
            Node nNode = list.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("Name : " + eElement.getAttribute("name"));
                newPlace(eElement.getAttribute("name"), Integer.parseInt(eElement.getAttribute("capacity")), Integer.parseInt(eElement.getAttribute("initToken")));
            }
        }
        printDebug("Places: " + places);
    }

    private void setupTransitions(BufferedReader bufferedReader, int nbTransitions) throws IOException {
        String line = null;
        /* Read the Places */
        int i = 0;
        do {
            line = bufferedReader.readLine();
            if (this.isValidLine(line)) {
                String[] transitionsNames = line.split(" ");
                //printDebug("New Transitions: " + Arrays.toString(transitionsNames));
                for(String transition : transitionsNames) {
                    this.transitions.add(new PetriTransition(transition));
                }
                i++;
            }
        } while(i < 1);
        printDebug("Transitions: " + transitions);
    }

    private void setIncidence(int[][] matrix, String placeName, String transitionName, int weight) {
        // Transition = X, Place = Y
        matrix[findPlaceIndex(placeName)][findTransitionIndex(transitionName)] = weight;
    }

    /**
     * Create a new pre-incidence arc object and add it to the arc vector
     * @param type Type of arc
     * @param weight Weight of arc
     * @param fromPlace Connected from place
     * @param toTransition Connected to transition
     */
    private void newPreIncidenceArc(int type, int weight, String fromPlace, String toTransition) {
        PetriArcType arcType = PetriArcType.SIMPLE;
        if (type == 1) {
            arcType = PetriArcType.TEST;
            weight = 0;
        } else if (type == 2) {
            arcType = PetriArcType.INHIBIT;
            weight = 0;
        }
        preIncidenceArcs.add(new PetriArc(arcType, weight, fromPlace, toTransition));
    }

    private void setupPreIncidenceArcs(BufferedReader bufferedReader, int nbArcs) throws IOException {
        String line;
        /* Read the Places */
        int i = 0;
        do {
            line = bufferedReader.readLine();
            if (this.isValidLine(line)) {
                String[] props = line.split(" ");
                //printDebug("New Pre-incidence arc: " + Arrays.toString(props));
                setIncidence(preIncidenceMatrix, props[0], props[1], Integer.parseInt(props[2]));
                newPreIncidenceArc(Integer.parseInt(props[3]), Integer.parseInt(props[2]), props[0], props[1]);
                i++;
            }
        } while(i < nbArcs);
    }

    private void setupPostIncidenceArcs(BufferedReader bufferedReader, int nbArcs) throws IOException {
        String line;
        /* Read the Places */
        int i = 0;
        do {
            line = bufferedReader.readLine();
            if (this.isValidLine(line)) {
                String[] props = line.split(" ");
                //printDebug("New Post-incidence arc: " + Arrays.toString(props));
                setIncidence(postIncidenceMatrix, props[1], props[0], Integer.parseInt(props[2]));
                i++;
            }
        } while(i < nbArcs);
    }

    public void setupTransitions(NodeList list) {
        for (int temp = 0; temp < list.getLength(); temp++) {
            Node nNode = list.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("Name : " + eElement.getAttribute("name"));
                this.transitions.add(new PetriTransition(eElement.getAttribute("name")));
            }
        }
        printDebug("Places: " + places);
    }

    /**
     * Loads a petri net from an xml file
     * @param fileName Xml file name
     */
    public void loadFromXMLFile(String fileName) {
        printDebug("Loading Petri Net from " + fileName);
        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            // Places
            NodeList nList = doc.getElementsByTagName("place");
            setupPlaces(nList);

            // Transitions
            nList = doc.getElementsByTagName("transition");
            setupTransitions(nList);

            /* TODO */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a petri net from a text file
     * @param fileName File name
     */
    public void loadFromTextFile(String fileName) {
        int phase = 0;

        printDebug("Loading Petri Net from " + fileName);

        // This will reference one line at a time
        String line = null;

        try {
            File txtFile = new File(getClass().getClassLoader().getResource(fileName).getFile());

            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(txtFile);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                //printDebug("Line: " + line);
                /* Ignore comments */
                if (this.isValidLine(line)) {
                    switch(phase) {
                        case 0:
                            /* Places */
                            nbPlaces = Integer.parseInt(line);
                            markingPost = new int[nbPlaces];
                            markingPre = new int[nbPlaces];
                            printDebug("Number of Places = " + nbPlaces);
                            this.setupPlaces(bufferedReader, nbPlaces);
                            phase++;
                            break;
                        case 1:
                            /* Transitions */
                            nbTransitions = Integer.parseInt(line);
                            printDebug("Number of Transitions = " + nbTransitions);
                            this.setupTransitions(bufferedReader, nbTransitions);
                            phase++;
                            break;
                        case 2:
                            /* pre-incidence arcs */
                            /* Create the matrix */
                            preIncidenceMatrix = new int[nbPlaces][nbTransitions];
                            nbPreIncidenceArcs = Integer.parseInt(line);
                            printDebug("Number of Pre-incidence arcs = " + nbPreIncidenceArcs);
                            this.setupPreIncidenceArcs(bufferedReader, nbPreIncidenceArcs);
                            phase++;
                            break;
                        case 3:
                            /* post-incidence arcs */
                            /* Create the matrix */
                            postIncidenceMatrix = new int[nbPlaces][nbTransitions];
                            nbPostIncidenceArcs = Integer.parseInt(line);
                            printDebug("Number of Post-incidence arcs = " + nbPostIncidenceArcs);
                            this.setupPostIncidenceArcs(bufferedReader, nbPostIncidenceArcs);
                            phase++;
                            break;
                    }
                }
            }
            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
    }
}
