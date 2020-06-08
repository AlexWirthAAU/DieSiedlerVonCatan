package com.example.catangame.gameboard;


import java.io.Serializable;

public class Tile implements Serializable {

    private int id;
    private String resource; // Each Tile has a certain Resource (Wood, Clay,...)
    private int diceValue; // each Tile needs to have a certain Value between 2-12

    private Knot[] knots; //Each Tile has a Set of 6 adjoining knots (needed to check which Settlement gets Resources after rolling the Dice

    //Each backgroundTile has a red dot as thief -> thief.setFillAlpha(1)
    private boolean isThief; // to notate that the Thief is on this certain Tile

    /**
     * Constructor - Sets the Start-Values
     *
     * @param Tid gives each Tile a id
     */
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

    // Getter
    public int getId() {
        return id;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public Knot[] getKnots() {
        return knots;
    }

    public void setKnots(Knot[] knots) {
        this.knots = knots;
    }

    private void setResource() {
        int idSwitch = this.id;
        switch (idSwitch) {
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
            default:
                break;
        }

    }

    public boolean isThief() {
        return isThief;
    }

    public void setThief(boolean thief) {
        this.isThief = thief;
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
            default:
                break;
        }
    }
}

