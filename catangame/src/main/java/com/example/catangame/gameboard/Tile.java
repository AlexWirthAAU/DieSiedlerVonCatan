package com.example.catangame.gameboard;


import java.io.Serializable;

public class Tile implements Serializable {
    //Each backgroundTile has a red dot as thief -> thief.setFillAlpha(1)
    private boolean isThief;                    //to notate that the Thief is on this certain Tile
    private int id;                             //gives each Tile a id
    private int diceValue;                      //each Tile needs to have a certain value between 2-12
    private String resource;                    //Each Tile has a certain resource (Wood, Clay,...)
    private Knot[] knots;                       //Each Tile has a set of 6 adjoining knots (needed to check which settlement gets resources after rolling the dice


    public Tile(int Tid) {
        id = Tid;
        setDiceValue();
        setResource();
        knots = new Knot[6];
        if (resource.equals("DESERT")) {
            isThief = true;
        } else {
            isThief = false;
        }
    }

    private void setResource() {
        int id = this.id;
        switch (id) {
            case 1:
            case 9:
            case 15:
            case 19:
                this.resource = "WOOD";
                break;
            case 2:
            case 4:
            case 18:
                this.resource = "ORE";
                break;
            case 3:
            case 8:
            case 10:
            case 16:
                this.resource = "WHEAT";
                break;
            case 5:
            case 12:
            case 17:
                this.resource = "CLAY";
                break;
            case 6:
            case 7:
            case 13:
            case 14:
                this.resource = "WOOL";
                break;
            case 11:
                this.resource = "DESERT";
                break;
        }

    }

    public int getId() {
        return id;
    }

    public String getResource() {
        return resource;
    }

    public boolean isThief() {
        return isThief;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThief(boolean thief) {
        this.isThief = thief;
    }

    public void setKnots(Knot[] knots) {
        this.knots = knots;
    }

    public Knot[] getKnots() {
        return knots;
    }

    private void setDiceValue() {
        switch (this.id) {
            case 1:
            case 14: {
                this.diceValue = 11;
                break;
            }
            case 2:
            case 16: {
                this.diceValue = 10;
                break;
            }
            case 3:
            case 15: {
                this.diceValue = 9;
                break;
            }
            case 4:
            case 13: {
                this.diceValue = 3;
                break;
            }
            case 5:
            case 18: {
                this.diceValue = 8;
                break;
            }
            case 6: {
                this.diceValue = 12;
                break;
            }
            case 7:
            case 19: {
                this.diceValue = 5;
                break;
            }
            case 8:
            case 11: {
                this.diceValue = 6;
                break;
            }
            case 9:
            case 17: {
                this.diceValue = 4;
                break;
            }
            case 10:
            case 12: {
                this.diceValue = 2;
                break;
            }
        }
    }

    public int getDiceValue() {
        return diceValue;
    }
}

