package com.example.catangame.devcards;

import java.util.ArrayList;

public class DevCardStack {

    private ArrayList<DevCard> stack = new ArrayList<>(28);
    private int i = 0;
    private int j = 0;
    private int k = 0;

    public DevCardStack() {
        this.stack.add(new KnightCard());
        this.stack.add(new BuildStreetCard());
        this.stack.add(new VictoryPointCard());

        while (i < 13) {
            this.stack.add(new KnightCard());
            i++;
        }

        while (j < 5) {
            this.stack.add(new VictoryPointCard());
            j++;
        }

        while (k < 2) {
            this.stack.add(new InventionCard());
            this.stack.add(new MonopolCard());
        }

        this.stack.add(new BuildStreetCard());
    }

    public ArrayList<DevCard> getDevCardStack() {
        return this.stack;
    }
}
