package com.example.diesiedler.Model.Gameboard;

import com.richpath.RichPath;
import com.richpath.RichPathView;

public class Edge {
    private String id;              //Each Edge has a Id named: edge_<Knot1_Row><Knot1_Column><Knot2_Row><Knot2_Column>
    private Knot one;               //Each edge is defined as straight between two knots
    private Knot two;
    private RichPathView gameBoard;
    private RichPath road;                  //Richpath Element to click on
    //Player p;                     //When a player builds a road -> player owns this specific road/edge

    public Edge(Knot one, Knot two, RichPathView gameBoard) {
        this.one = one;
        this.two = two;
        this.id = "edge_" + one.getRow() + "" + one.getColumn() + "" + two.getRow() + "" + two.getColumn();
        this.road = gameBoard.findRichPathByName(this.id);
        this.gameBoard = gameBoard;
    }

    public Knot getOne() {
        return one;
    }

    public Knot getTwo() {
        return two;
    }

    public String getId() {
        return id;
    }

    public RichPath road() {
        return this.road;
    }

    /*TODO:
    public void buildRoad(Player p){
        this.p=p;
        road.setFillColor(p.getColor);
    }
     */
}
