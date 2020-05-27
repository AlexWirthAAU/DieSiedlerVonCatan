package com.example.catangame;

import java.io.Serializable;

/**
 * @author Fabian Schaffenrath
 *
 * Representation of the Cheat Request
 */
public class Grab implements Serializable {

    private Player grabber;
    private Player grabbed;
    private String resource;
    private Boolean revealed;

    /**
     * Initializes a new Cheating Request.
     * @param grabber The cheating Player
     * @param grabbed The Player to be stolen from.
     * @param resource A String Identifier of the resource to be stolen.
     */
    public Grab(Player grabber, Player grabbed, String resource){
        this.grabber = grabber;
        this.grabbed = grabbed;
        this.resource = resource;
        this.revealed = null;
    }

    public Player getGrabber() {
        return grabber;
    }

    public Player getGrabbed() {
        return grabbed;
    }

    public String getResource() {
        return resource;
    }

    public Boolean getRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
