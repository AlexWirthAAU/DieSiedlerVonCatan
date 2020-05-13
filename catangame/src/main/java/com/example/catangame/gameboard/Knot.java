package com.example.catangame.gameboard;


import com.example.catangame.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class Knot implements Serializable {
    private int row;                    //Each Knot is identified by row and column
    private int column;
    private String id;
    private ArrayList<Knot> path;       //As soon as a knots Player != null -> path = new ArrayList and push the knot in this list (beginning of a path)
    private boolean isSettled;
    private boolean hasCity;
    private Player player;          //TODO: When a player builds a settlement, he owns this knot
    private boolean isHarbourKnot;  //States whether a Knot has a harbour or not
    private boolean isWoodPort;
    private boolean isWoolPort;
    private boolean isWheatPort;
    private boolean isOrePort;
    private boolean isClayPort;

    public Knot(int row, int column) {
        this.player = null;
        this.row = row;
        this.column = column;
        this.id = "settlement_" + row + "_" + column;
        this.path = null;
        this.isSettled = false;
        this.hasCity = false;
        setIsHarbourKnot();
    }

    public String toString() {
        return "settlement_" + row + "_" + column;
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
            case "22":
            case "23":
            case "65":
            case "66":
                this.isHarbourKnot = true;
                this.isOrePort = true;
                break;
            case "18":
            case "19":
            case "31":
            case "41":
                this.isHarbourKnot = true;
                this.isWheatPort = true;
                break;
            case "210":
            case "310":
                this.isHarbourKnot = true;
                this.isWoolPort = true;
                break;
            case "410":
            case "411":
            case "53":
            case "63":
                this.isHarbourKnot = true;
                this.isClayPort = true;
                break;
            case "68":
            case "69":
                this.isHarbourKnot = true;
                this.isWoodPort = true;
                break;
            default: {
                this.isHarbourKnot = false;
                this.isOrePort = false;
                this.isClayPort = false;
                this.isWheatPort = false;
                this.isWoodPort = false;
                this.isWoolPort = false;
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

    public boolean hasCity() {
        return hasCity;
    }

    public boolean isHarbourKnot() {
        return isHarbourKnot;
    }

    public boolean isWoodPort() {
        return isWoodPort;
    }

    public boolean isWoolPort() {
        return isWoolPort;
    }

    public boolean isWheatPort() {
        return isWheatPort;
    }

    public boolean isOrePort() {
        return isOrePort;
    }

    public boolean isClayPort() {
        return isClayPort;
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

