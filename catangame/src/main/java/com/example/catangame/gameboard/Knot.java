package com.example.catangame.gameboard;


import com.example.catangame.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Alex Wirth
 * @author Christina Senger (edit)
 */
public class Knot implements Serializable {

    private int row;
    private int column;
    private String id; // Each Knot is identified by Row and Column

    private ArrayList<Knot> path; // As soon as a Knots Player != null -> path = new ArrayList and push the Knot in this List (beginning of a Path)

    private boolean isSettled; // States whether a Knot is settled
    private boolean hasCity; // States whether a Knot has a City

    private Player player;

    private boolean isHarbourKnot;  // States whether a Knot has a Harbour or not
    private boolean isWoodPort; // States whether a Knot has a specific Harbour
    private boolean isWoolPort;
    private boolean isWheatPort;
    private boolean isOrePort;
    private boolean isClayPort;

    /**
     * Constructor - creates ID, sets other Values null
     * and check whether it is a Harbou-Knot
     *
     * @param row    Row of the Knot
     * @param column Column of the Knot
     */
    public Knot(int row, int column) {
        this.player = null;
        this.row = row;
        this.column = column;
        this.id = "settlement_" + row + "_" + column;
        this.path = null;
        this.isSettled = false;
        this.hasCity = false;
        setIsHarbourKnot(this.id);
    }

    /**
     * @return String Representation of a Settlement with Row and Column
     */
    public String toString() {
        return "settlement_" + row + "_" + column;
    }

    // Setter
    public void setPath(ArrayList<Knot> path) {
        this.path = path;
    }

    public void setHasCity(boolean hasCity) {
        this.hasCity = hasCity;
    }

    // Getter
    public int getRow() {
        return row;
    }

    public String getId() {
        return id;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public int getColumn() {
        return column;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }

    public ArrayList<Knot> getPath() {
        return path;
    }

    public boolean hasCity() {
        return hasCity;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // Set Ports
    private void setIsHarbourKnot(String id) {
        switch (id) {
            case "settlement_1_5":
            case "settlement_1_6":
            case "settlement_2_2":
            case "settlement_2_3":
            case "settlement_6_5":
            case "settlement_6_6":
                this.isHarbourKnot = true;
                this.isOrePort = true;
                break;
            case "settlement_1_8":
            case "settlement_1_9":
            case "settlement_3_1":
            case "settlement_4_1":
                this.isHarbourKnot = true;
                this.isWheatPort = true;
                break;
            case "settlement_2_10":
            case "settlement_3_10":
                this.isHarbourKnot = true;
                this.isWoolPort = true;
                break;
            case "settlement_4_10":
            case "settlement_4_11":
            case "settlement_5_3":
            case "settlement_6_3":
                this.isHarbourKnot = true;
                this.isClayPort = true;
                break;
            case "settlement_6_8":
            case "settlement_6_9":
                this.isHarbourKnot = true;
                this.isWoodPort = true;
                break;
            default: {
                setFalsePorts();
                break;
            }
        }
    }

    // Get Ports
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


    private void setFalsePorts() {
        this.isHarbourKnot = false;
        this.isOrePort = false;
        this.isClayPort = false;
        this.isWheatPort = false;
        this.isWoodPort = false;
        this.isWoolPort = false;
    }
}

