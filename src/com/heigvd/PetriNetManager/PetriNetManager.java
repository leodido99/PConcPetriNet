package com.heigvd.PetriNetManager;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by leonard.bise on 18.04.18.
 */
public class PetriNetManager {
    /* The current marking of the petri net
     * Current number of token in each places */
    private int[] currMarking;

    /* The pre-incidence matrix
     * Link between places and transitions */
    private int[][] preIncidenceMatrix;

    /* The post-incidence matrix
     * Link between transitions and places */

    public int[][] getPreIncidenceMatrix() {
        return preIncidenceMatrix;
    }

    public int[][] getPostIncidenceMatrix() {
        return postIncidenceMatrix;
    }

    private int[][] postIncidenceMatrix;

    private Vector<PetriTransition> transitions;
    private Vector<PetriPlace> places;
    private Vector<PetriArc> preIncidenceArcs;
    private Vector<PetriArc> postIncidenceArcs;

    private boolean debug = true;

    private int nbTransitions = -1;
    private int nbPlaces = -1;
    private int nbPreIncidenceArcs = -1;
    private int nbPostIncidenceArcs = -1;

    //int[][] marking = new int[5][10];


    public PetriNetManager() {
        places = new Vector<>();
        transitions = new Vector<>();
        preIncidenceArcs = new Vector<>();
        postIncidenceArcs = new Vector<>();
    }

    private void printDebug(String message) {
        if (this.debug) {
            System.out.println(message);
        }
    }

    public void printMatrix(int[][] matrix) {
        for(int j = 0; j < matrix.length; j++) {
            for(int i = 0; i < matrix[j].length; i++) {
                System.out.print(matrix[j][i] + " ");
            }
            System.out.println();
        }
    }

    public void printPreIncidenceMatrix() {
        System.out.println("Pre-Incidence Matrix");
        System.out.println("---------------------------");
        this.printMatrix(this.preIncidenceMatrix);
        System.out.println("---------------------------");
    }

    public void printPostIncidenceMatrix() {
        System.out.println("Post-Incidence Matrix");
        System.out.println("---------------------------");
        this.printMatrix(this.postIncidenceMatrix);
        System.out.println("---------------------------");
    }

    @Nullable
    private PetriPlace findPlace(String placeName) {
        for(PetriPlace place : places) {
            if (place.getName().equals(placeName)) {
                return place;
            }
        }
        throw new RuntimeException();
    }

    private int findPlaceIndex(String placeName) {
        int idx = places.indexOf(findPlace(placeName));
        //printDebug("findPlaceIndex: " + placeName + " = " + idx);
        return idx;
    }

    @Nullable
    private PetriTransition findTransition(String transitionName) {
        for(PetriTransition transition : transitions) {
            if (transition.getName().equals(transitionName)) {
                return transition;
            }
        }
        throw new RuntimeException();
    }

    private int findTransitionIndex(String transitionName) {
        int idx = transitions.indexOf(findTransition(transitionName));
        //printDebug("findTransitionIndex: " + transitionName + " = " + idx);
        return idx;
    }

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
                places.add(new PetriPlace(placeProps[0], Integer.parseInt(placeProps[1]), Integer.parseInt(placeProps[2])));
                i++;
            }
        } while(i < nbPlaces);
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

    private void setupPreIncidenceArcs(BufferedReader bufferedReader, int nbArcs) throws IOException {
        String line = null;
        /* Read the Places */
        int i = 0;
        do {
            line = bufferedReader.readLine();
            if (this.isValidLine(line)) {
                String[] props = line.split(" ");
                //printDebug("New Pre-incidence arc: " + Arrays.toString(props));
                setIncidence(preIncidenceMatrix, props[0], props[1], Integer.parseInt(props[2]));
                i++;
            }
        } while(i < nbArcs);
    }

    private void setupPostIncidenceArcs(BufferedReader bufferedReader, int nbArcs) throws IOException {
        String line = null;
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

    public void setupPlaces(NodeList list) {
        for (int temp = 0; temp < list.getLength(); temp++) {
            Node nNode = list.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("Name : " + eElement.getAttribute("name"));
                places.add(new PetriPlace(eElement.getAttribute("name"), Integer.parseInt(eElement.getAttribute("capacity")), Integer.parseInt(eElement.getAttribute("capacity"))));
            }
        }
        printDebug("Places: " + places);
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
            NodeList nList = doc.getElementsByTagName("transition");
            setupTransitions(nList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFromTextFile(String fileName) {
        int phase = 0;

        printDebug("Loading Petri Net from " + fileName);

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

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
