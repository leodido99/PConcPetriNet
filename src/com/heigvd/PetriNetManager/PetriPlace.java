package com.heigvd.PetriNetManager;

/**
 * Created by leonard.bise on 18.04.18.
 */
public class PetriPlace {
    private String name;
    private int capacity;
    private int current;
    private PetriPlaceInterface action;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public PetriPlaceInterface getAction() {
        return action;
    }

    public void setAction(PetriPlaceInterface action) {
        this.action = action;
    }

    public PetriPlace(String name, int capacity, int nbInitialTokens) {
        this.name = name;
        this.capacity = capacity;
        this.current = nbInitialTokens;
    }



}
