package com.example.diesiedler.Model.Gameboard;

import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.ArrayList;

public class Knot {
    private int row;                    //Each Knot is identified by row and column
    private int column;
    private String id;
    private RichPathView gameBoardView;
    private RichPath settlement;        //Richpath Element to click on, when building a new settlement
    private RichPath city;              //Richpath Element that gets visible, as soon as a player upgrades his settlement to a city
    private ArrayList<Knot> path;       //As soon as a knots Player != null -> path = new ArrayList and push the knot in this list (beginning of a path)
    //Player p;                         //TODO: When a player builds a settlement, he owns this knot
    private boolean isHarbourKnot;      //States whether a Knot has a harbour or not

    public Knot(int row, int column, RichPathView gameBoardView) {
        this.gameBoardView = gameBoardView;
        this.row = row;
        this.column = column;
        this.id = row + "" + column;
        this.settlement = gameBoardView.findRichPathByName("settlement_" + row + "_" + column);
        this.city = gameBoardView.findRichPathByName("city_" + row + "_" + column);
        this.path = null;
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

    public RichPath getSettlement() {
        return settlement;
    }

    public RichPath getCity() {
        return city;
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


    /*
    TODO: method to build a settlement or city on a specific knot

    public void buildSettlement(Player p){
         this.p=p;
         this.settlement.setFillColor(p.getColor);
    }

    public void buildCity(){
          this.city.setFillAlpha(1);
    }
     */
}
