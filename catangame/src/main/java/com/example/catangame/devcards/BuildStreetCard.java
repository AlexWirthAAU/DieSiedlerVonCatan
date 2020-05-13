package com.example.catangame.devcards;

import java.io.Serializable;

public class BuildStreetCard implements DevCard, Serializable {

    private int counter;

    public BuildStreetCard() {
        this.counter = 2;
    }

    public int getCounter() {
        return this.counter;
    }

    public void removeCounter() {
        this.counter--;
    }
}
