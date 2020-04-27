package com.example.catanserver.businessLogic.model.gameboard;

import com.example.catanserver.businessLogic.model.player.User;
import com.example.catanserver.businessLogic.model.player.UserImpl;

public class Edge {
    private String id;              //Each Edge has a Id named: edge_<Knot1_Row><Knot1_Column><Knot2_Row><Knot2_Column>
    private Knot one;               //Each edge is defined as straight between two knots
    private Knot two;
    private User user;                   //When a player builds a road -> player owns this specific road/edge

    public Edge(Knot one, Knot two) {
        this.one = one;
        this.two = two;
        this.id = "edge_" + one.getRow() + "" + one.getColumn() + "" + two.getRow() + "" + two.getColumn();
        this.user = new UserImpl("nullUser", "nullip");
        this.user.setUserId(0);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
