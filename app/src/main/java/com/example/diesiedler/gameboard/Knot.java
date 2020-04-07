package com.example.diesiedler.gameboard;

public class Knot {
    private int row;
    private int column;
    //Player p;
    //Color c;

    public Knot(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public String toString() {
        return "Zeile " + this.row + " Spalte " + this.column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
