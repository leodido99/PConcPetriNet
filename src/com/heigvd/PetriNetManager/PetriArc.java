package com.heigvd.PetriNetManager;

/**
 * Created by leonard.bise on 18.04.18.
 */
public class PetriArc {
    private PetriArcType type;
    private int weight;

    public PetriArc(PetriArcType type, int weight) {
        this.type = type;
        this.weight = weight;
    }

    public PetriArcType getType() {
        return type;
    }

    public void setType(PetriArcType type) {
        this.type = type;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
