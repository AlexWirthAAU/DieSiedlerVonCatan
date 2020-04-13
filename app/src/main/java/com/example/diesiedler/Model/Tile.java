package com.example.diesiedler.Model;

import android.content.Context;
import com.richpath.RichPath;
import com.richpath.RichPathView;


public class Tile {
    private RichPathView gameBoardView;
    private RichPath surfaceTile;               //Touchable Surface
    private RichPath backgroundTile;            //Only the underlying TileDesign
    private RichPath thief;                     //Each backgroundTile has a red dot as thief -> thief.setFillAlpha(1)
    private boolean isThief;                    //to notate that the Thief is on this certain Tile
    private int id;                             //gives each Tile a id
    private int diceValue;                      //each Tile needs to have a certain value between 2-12
    //private Resource resource;                //TODO: Each Tile has a certain resource (Wood, Clay,...)
    private Knot[] knots;                       //Each Tile has a set of 6 adjoining knots (needed to check which settlement gets resources after rolling the dice


    public Tile(int Tid, Context c, RichPathView rpv) {
        isThief = false;
        id = Tid;
        setDiceValue();
        gameBoardView = rpv;
        surfaceTile = gameBoardView.findRichPathByName("tile_" + id);
        backgroundTile = gameBoardView.findRichPathByName("backgroundTile_" + id);
        thief = gameBoardView.findRichPathByName("thief_" + id);
        setResource();
        knots = new Knot[54];
    }

    private void setResource() {
        //int color = backgroundTile.getFillColor();
        //TODO: set resource depending on backgroundTile.getFillAlpha
    }

    public RichPath getBackgroundTile() {
        return backgroundTile;
    }

    public RichPath getThief() {
        return thief;
    }

    public RichPathView getGameBoardView() {
        return gameBoardView;
    }

    public RichPath getSurfaceTile() {
        return surfaceTile;
    }

    public int getId() {
        return id;
    }

    //public Resource getResource() {
    //    return resource;
    //}

    public boolean isThief() {
        return isThief;
    }

    public void setGameBoardView(RichPathView gameBoardView) {
        this.gameBoardView = gameBoardView;
    }

    public void setSurfaceTile(RichPath surfaceTile) {
        this.surfaceTile = surfaceTile;
    }

    public void setBackgroundTile(RichPath backgroundTile) {
        this.backgroundTile = backgroundTile;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThief(boolean isthief) {
        isThief = isthief;
        if (isthief == true) {
            this.thief.setFillAlpha(1);
        } else {
            this.thief.setFillAlpha(0);
        }
    }

    public void setKnots(Knot[] knots) {
        this.knots = knots;
    }

    public Knot[] getKnots() {
        return knots;
    }

    private void setDiceValue() {
        switch (this.id) {
            case 1:
            case 14: {
                this.diceValue = 11;
                break;
            }
            case 2:
            case 16: {
                this.diceValue = 10;
                break;
            }
            case 3:
            case 15: {
                this.diceValue = 9;
                break;
            }
            case 4:
            case 13: {
                this.diceValue = 3;
                break;
            }
            case 5:
            case 18: {
                this.diceValue = 8;
                break;
            }
            case 6: {
                this.diceValue = 12;
                break;
            }
            case 7:
            case 19: {
                this.diceValue = 5;
                break;
            }
            case 8:
            case 11: {
                this.diceValue = 6;
                break;
            }
            case 9:
            case 17: {
                this.diceValue = 4;
                break;
            }
            case 10:
            case 12: {
                this.diceValue = 2;
                break;
            }


        }
    }

    public int getDiceValue() {
        return diceValue;
    }
}
