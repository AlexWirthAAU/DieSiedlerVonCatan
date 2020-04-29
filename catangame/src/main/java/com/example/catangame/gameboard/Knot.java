package com.example.catangame.gameboard;


import com.example.catangame.*;

import java.util.ArrayList;

public class Knot {
    private int row;                    //Each Knot is identified by row and column
    private int column;
    private String id;
    private ArrayList<Knot> path;       //As soon as a knots Player != null -> path = new ArrayList and push the knot in this list (beginning of a path)
    private boolean isSettled;
    private boolean hasCity;
    private Player player;          //TODO: When a player builds a settlement, he owns this knot
    private boolean isHarbourKnot;      //States whether a Knot has a harbour or not

    public Knot(int row, int column) {
        this.player = null;
        this.row = row;
        this.column = column;
        this.id = row + "" + column;
        this.path = null;
        this.isSettled = false;
        this.hasCity = false;
        setIsHarbourKnot();
    }

    public String toString() {
        return "Zeile " + this.row + " Spalte " + this.column;        //Only for testing
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public ArrayList<Knot> getPath() {
        return path;
    }

    private void setIsHarbourKnot() {
        switch (this.id) {
            case "15":
            case "16":
            case "18":
            case "19":
            case "22":
            case "23":
            case "210":
            case "310":
            case "410":
            case "411":
            case "63":
            case "65":
            case "66":
            case "68":
            case "69":
            case "53":
            case "41":
            case "31": {
                this.isHarbourKnot = true;
                break;
            }
            default: {
                this.isHarbourKnot = false;
            }
        }
    }

    public boolean getIsHarbourKnot() {
        return this.isHarbourKnot;
    }

    public String getId() {
        return id;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public boolean HasCity() {
        return hasCity;
    }

    public boolean isHarbourKnot() {
        return isHarbourKnot;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPath(ArrayList<Knot> path) {
        this.path = path;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }

    public void setHasCity(boolean hasCity) {
        this.hasCity = hasCity;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

