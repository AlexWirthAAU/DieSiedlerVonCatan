package com.example.catangame.devcards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Christina Senger
 * <p>
 * Representation of all available Cards per Game
 */
public class DevCardStack implements Serializable {

    private ArrayList<DevCard> stack = new ArrayList<>(26); // List of all available Cards

    /**
     * Constructor - adds Cards the the List
     * The first 3 Cards are a Knight-, a BuildStreet- and a VictoryPointCard.
     * Totally there a 14 Knight- 6 VictoryPoint-, 2 Invention-, Monopol and BuildStreetCards.
     */
    public DevCardStack() {
        this.stack.add(new KnightCard());
        this.stack.add(new BuildStreetCard());
        this.stack.add(new VictoryPointCard());

        int i = 0;
        while (i < 13) {
            this.stack.add(new KnightCard());
            i++;
        }

        int j = 0;
        while (j < 5) {
            this.stack.add(new VictoryPointCard());
            j++;
        }

        int k = 0;
        while (k < 2) {
            this.stack.add(new InventionCard());
            this.stack.add(new MonopolCard());
            k++;
        }

        this.stack.add(new BuildStreetCard());
    }

    // Get a new Stack
    public List<DevCard> getDevCardStack() {
        return this.stack;
    }
}
