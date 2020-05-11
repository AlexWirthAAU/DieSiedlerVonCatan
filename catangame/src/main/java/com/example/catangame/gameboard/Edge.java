package com.example.catangame.gameboard;

import com.example.catangame.Player;

import java.io.Serializable;

public class Edge implements Serializable {
    private String id;              //Each Edge has a Id named: edge_<Knot1_Row><Knot1_Column><Knot2_Row><Knot2_Column>
    private Knot one;               //Each edge is defined as straight between two knots
    private Knot two;
    private Player player;      //When a player builds a road -> player owns this specific road/edge

    public Edge(Knot one, Knot two) {
        this.one = one;
        this.two = two;
        this.id = "edge_" + one.getRow() + "" + one.getColumn() + "" + two.getRow() + "" + two.getColumn();
        this.player = null;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
