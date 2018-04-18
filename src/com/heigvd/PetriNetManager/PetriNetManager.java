package com.heigvd.PetriNetManager;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        preIncidenceArcs = new Vector<>();
        postIncidenceArcs = new Vector<>();

    }

    private void printDebug(String message) {
        if (this.debug) {
            System.out.println(message);
        }
    }

    @Nullable
    private PetriPlace findPlace(String placeName) {
        for(PetriPlace place : places) {
            if (place.getName() == placeName) {
                return place;
            }
        }
        return null;
    }

    private int findPlaceIndex(String placeName) {
        return places.indexOf(findPlace(placeName));
    }

    @Nullable
    private PetriTransition findTransition(String transitionName) {
        for(PetriTransition transition : transitions) {
            if (transition.getName() == transitionName) {
                return transition;
            }
        }
        return null;
    }

    private int findTransitionIndex(String transitionName) {
        return places.indexOf(findPlace(transitionName));
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
                printDebug("Line: " + line);
                /* Ignore comments */
                if (line.length() > 0 && line.charAt(0) != '/' && line.charAt(1) != '/') {
                    switch(phase) {
                        case 0:
                            /* Places */
                            nbPlaces = Integer.parseInt(line);
                            printDebug("Number of Places = " + nbPlaces);
                                /* Read the Places */
                                for(int i = 0; i < nbPlaces; i++) {
                                    line = bufferedReader.readLine();
                                    if (line != null) {
                                        String[] placeProps = line.split(" ");
                                        printDebug("New Place: " + placeProps);
                                        /* Create new place and add to vector */
                                        places.add(new PetriPlace(placeProps[0], Integer.parseInt(placeProps[1]), Integer.parseInt(placeProps[2])));
                                    }
                                }
                            phase++;
                            break;
                        case 1:
                            /* Transitions */
                            nbTransitions = Integer.parseInt(line);
                            printDebug("Number of Transitions = " + nbTransitions);
                            /* Read the transitions */
                            for(int i = 0; i < nbTransitions; i++) {
                                line = bufferedReader.readLine();
                                if (line != null) {
                                    String[] transitionsNames = line.split(" ");
                                    printDebug("New Transitions: " + transitions);
                                    for(String transition : transitionsNames) {
                                        this.transitions.add(new PetriTransition(transition));
                                    }
                                }
                            }
                            phase++;
                            break;
                        case 2:
                            /* pre-incidence arcs */
                            /* Create the matrix */
                            preIncidenceMatrix = new int[nbTransitions][nbPlaces];
                            nbPreIncidenceArcs = Integer.parseInt(line);
                            printDebug("Number of Pre-incidence arcs = " + nbPreIncidenceArcs);
                            for(int i = 0; i < nbPreIncidenceArcs; i++) {
                                line = bufferedReader.readLine();
                                if (line != null) {
                                    String[] props = line.split(" ");
                                    printDebug("New Pre-incidence arc: " + props);
                                    preIncidenceMatrix[findPlaceIndex(props[1])][findTransitionIndex(props[0])] = Integer.parseInt(props[2]);
                                }
                            }
                            phase++;
                            break;
                        case 3:
                            /* post-incidence arcs */
                            /* Create the matrix */
                            postIncidenceMatrix = new int[nbTransitions][nbPlaces];
                            nbPostIncidenceArcs = Integer.parseInt(line);
                            printDebug("Number of Post-incidence arcs = " + nbPostIncidenceArcs);
                            for(int i = 0; i < nbPostIncidenceArcs; i++) {
                                line = bufferedReader.readLine();
                                if (line != null) {
                                    String[] props = line.split(" ");
                                    printDebug("New Post-incidence arc: " + props);
                                    postIncidenceMatrix[findPlaceIndex(props[1])][findTransitionIndex(props[0])] = Integer.parseInt(props[2]);
                                }
                            }
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
