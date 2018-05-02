package com.heigvd.PetriNetManager;

/**
 * Created by leonard.bise on 18.04.18.
 */
public class PetriTransition {
    private String name;
    /* Event status */
    private boolean fired;
    /* Event enabled (i.e. sensibilisée)
     * This flag is used because when consume token, a transition might become disabled
      * because a token was consumed by a transition connected to the same place */
    private boolean enabled;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetriTransition(String name) {
        this.name = name;
        this.fired = false;
        this.enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String toString() {
        return this.getName();
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }
}
