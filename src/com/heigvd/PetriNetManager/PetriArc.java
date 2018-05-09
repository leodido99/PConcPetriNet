package com.heigvd.PetriNetManager;

/**
 * Created by leonard.bise on 18.04.18.
 */
public class PetriArc {
    private PetriArcType type;
    private int weight;
    private String fromPlace;
    private String toTransition;

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToTransition() {
        return toTransition;
    }

    public void setToTransition(String toTransition) {
        this.toTransition = toTransition;
    }

    public PetriArc(PetriArcType type, int weight, String fromPlace, String toTransition) {
        this.type = type;
        this.weight = weight;
        this.fromPlace = fromPlace;
        this.toTransition = toTransition;
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
