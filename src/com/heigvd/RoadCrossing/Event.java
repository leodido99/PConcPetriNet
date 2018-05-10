package com.heigvd.RoadCrossing;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class Event {
    private String name;
    private String petriNetTransition;

    public Event(String name, String petriNetTransition) {
        this.name = name;
        this.petriNetTransition = petriNetTransition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPetriNetTransition() {
        return petriNetTransition;
    }

    public void setPetriNetTransition(String petriNetTransition) {
        this.petriNetTransition = petriNetTransition;
    }
}
