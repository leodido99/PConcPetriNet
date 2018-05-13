package com.heigvd.RoadCrossing;
import com.heigvd.PetriNetManager.PetriNetManager;
import java.util.ArrayList;

/**
 * Created by leonard.bise on 10.05.18.
 */
public class EventManager {
    private PetriNetManager petriNetManager;
    private ArrayList<Event> eventList;

    /**
     * Constructor of the EventManager
     * @param petriNetManager Petri Net Manager on which to fire events
     */
    public EventManager(PetriNetManager petriNetManager) {
        this.petriNetManager = petriNetManager;
        eventList = new ArrayList<>();
    }

    /**
     * Create a new event
     * @param eventName Name of the event
     * @param petriTransition The associated Petri Net transition to fire
     */
    public void newEvent(String eventName, String petriTransition) {
        eventList.add(new Event(eventName, petriTransition));
    }

    /**
     * Fire an event
     * @param eventName Event name
     */
    public void fireEvent(String eventName) {
        for(Event ev : eventList) {
            if (ev.getName().equals(eventName)) {
                if (!checkAlreadyExists(eventName)) {
                    petriNetManager.newEvent(ev.getPetriNetTransition());
                }
                break;
            }
        }
    }

    /**
     * Check if the event already exists in the list
     * @param eventName Event name
     * @return true = already exists, false = doesn't exist
     */
    public boolean checkAlreadyExists(String eventName) {
        for(String event : petriNetManager.getEventList()) {
            if (event.equals(eventName)) {
                return true;
            }
        }
        return false;
    }
}
