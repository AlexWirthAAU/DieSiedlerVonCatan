package com.example.catangame.devcards;

import java.io.Serializable;

/**
 * @author Christina Senger
 * <p>
 * Representation of a BuilStreetCard
 */
public class BuildStreetCard implements DevCard, Serializable {

    private int counter;

    /**
     * Constructor - set Counter 2, cause one can build 2
     * Streets using this Card.
     */
    public BuildStreetCard() {
        this.counter = 2;
    }

    // Getter Counter
    public int getCounter() {
        return this.counter;
    }

    // Remove Counter
    public void removeCounter() {
        this.counter--;
    }
}
