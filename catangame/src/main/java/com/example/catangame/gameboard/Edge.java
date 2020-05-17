package com.example.catangame.gameboard;

import com.example.catangame.Player;

import java.io.Serializable;

/**
 * @author Alex Wirth
 * <p>
 * Representation of a Edge between 2 Knots
 */
public class Edge implements Serializable {

    private String id; // Each Edge has a Id named: edge_<Knot1_Row><Knot1_Column><Knot2_Row><Knot2_Column>
    private Knot one;
    private Knot two;
    private Player player; // When a player builds a road -> player owns this specific road/edge

    /**
     * Constructor - Each edge is defined as Straight between two Knots
     *
     * @param one Startingknot
     * @param two Endingknot
     */
    public Edge(Knot one, Knot two) {
        this.one = one;
        this.two = two;
        this.id = "edge_" + one.getRow() + "" + one.getColumn() + "" + two.getRow() + "" + two.getColumn();
        this.player = null;
    }

    // Get Knots
    public Knot getOne() {
        return one;
    }

    public Knot getTwo() {
        return two;
    }

    // Get Edge-ID
    public String getId() {
        return id;
    }

    // Get Set Player
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
