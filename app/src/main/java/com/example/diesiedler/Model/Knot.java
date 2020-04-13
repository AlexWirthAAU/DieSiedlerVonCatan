package com.example.diesiedler.Model;

import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.ArrayList;

public class Knot {
    private int row;                    //Each Knot is identified by row and column
    private int column;
    private RichPathView gameBoardView;
    private RichPath settlement;        //Richpath Element to click on, when building a new settlement
    private RichPath city;              //Richpath Element that gets visible, as soon as a player upgrades his settlement to a city
    private ArrayList<Knot> path;       //As soon as a knots Player != null -> path = new ArrayList and push the knot in this list (beginning of a path)
    //Player p;                         //TODO: When a player builds a settlement, he owns this knot

    public Knot(int row, int column, RichPathView gameBoardView) {
        this.gameBoardView = gameBoardView;
        this.row = row;
        this.column = column;
        this.settlement = gameBoardView.findRichPathByName("settlement_" + row + "_" + column);
        this.city = gameBoardView.findRichPathByName("city_" + row + "_" + column);
        this.path = null;
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
