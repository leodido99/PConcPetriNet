package com.heigvd.PetriNetManager;

/**
 * Created by leonard.bise on 18.04.18.
 */
public class PetriTransition {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetriTransition(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }
}
