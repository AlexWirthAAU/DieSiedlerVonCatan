package com.example.diesiedler.Model;

import android.content.Context;

import com.richpath.RichPathView;

public class Gameboard {
    private Tile[] tiles = new Tile[19];        //Gameboard is always compound of 19 hexagons
    private Knot[] knots = new Knot[54];        //Gameboard is always compound of 54 knots
    public Edge[] edges = new Edge[72];         //Gameboard is always compound of 72 edges

    public Gameboard(final Context c, RichPathView gameBoardView) {
        for (int i = 1; i < 20; i++) {
            tiles[i - 1] = new Tile(i, c, gameBoardView);
        }
        initKnots(gameBoardView);
        initTileKnots();
        initEdges(gameBoardView);
    }

    private void initKnots(RichPathView gameBoardView) {
        int row = 1;
        int column = 1;
        for (int i = 0; i < 54; i++) {
            if (row == 1 && column < 10) {
                knots[i] = new Knot(row, column + 2, gameBoardView);
                column++;
                if (column + 2 == 10) {
                    row = 2;
                    column = 1;
                }
            } else if (row == 2 && column < 11) {
                knots[i] = new Knot(row, column + 1, gameBoardView);
                column++;
                if (column + 1 == 11) {
                    row = 3;
                    column = 1;
                }
            } else if (row == 3 && column < 12) {
                knots[i] = new Knot(row, column, gameBoardView);
                column++;
                if (column == 12) {
                    row = 4;
                    column = 1;
                }
            } else if (row == 4 && column < 12) {
                knots[i] = new Knot(row, column, gameBoardView);
                column++;
                if (column == 12) {
                    row = 5;
                    column = 1;
                }
            } else if (row == 5 && column < 11) {
                knots[i] = new Knot(row, column + 1, gameBoardView);
                column++;
                if (column + 1 == 11) {
                    row = 6;
                    column = 1;
                }
            } else if (row == 6 && column < 10) {
                knots[i] = new Knot(row, column + 2, gameBoardView);
                column++;
                if (column + 2 == 10) {
                    row = 7;
                    column = 1;
                }
            }
        }
    }

    private void initTileKnots() {
        Knot[] tile1Knots = new Knot[6];
        Knot[] tile2Knotes = new Knot[6];
        Knot[] tile3Knots = new Knot[6];
        Knot[] tile4Knotes = new Knot[6];
        Knot[] tile5Knots = new Knot[6];
        Knot[] tile6Knotes = new Knot[6];
        Knot[] tile7Knots = new Knot[6];
        Knot[] tile8Knotes = new Knot[6];
        Knot[] tile9Knots = new Knot[6];
        Knot[] tile10Knotes = new Knot[6];
        Knot[] tile11Knots = new Knot[6];
        Knot[] tile12Knotes = new Knot[6];
        Knot[] tile13Knots = new Knot[6];
        Knot[] tile14Knotes = new Knot[6];
        Knot[] tile15Knots = new Knot[6];
        Knot[] tile16Knotes = new Knot[6];
        Knot[] tile17Knotes = new Knot[6];
        Knot[] tile18Knots = new Knot[6];
        Knot[] tile19Knotes = new Knot[6];

        for (int i = 0; i < 3; i++) {
            tile1Knots[i] = knots[i];
        }
        for (int i = 3; i < 6; i++) {
            tile1Knots[i] = knots[i + 5];
        }

        for (int i = 0; i < 3; i++) {
            tile2Knotes[i] = knots[i + 2];
        }
        for (int i = 3; i < 6; i++) {
            tile2Knotes[i] = knots[i + 6];
        }

        for (int i = 0; i < 3; i++) {
            tile3Knots[i] = knots[i + 4];
        }
        for (int i = 3; i < 6; i++) {
            tile3Knots[i] = knots[i + 9];
        }

        for (int i = 0; i < 3; i++) {
            tile4Knotes[i] = knots[i + 7];
        }
        for (int i = 3; i < 6; i++) {
            tile4Knotes[i] = knots[i + 13];
        }

        for (int i = 0; i < 3; i++) {
            tile5Knots[i] = knots[i + 9];
        }
        for (int i = 3; i < 6; i++) {
            tile5Knots[i] = knots[i + 15];
        }

        for (int i = 0; i < 3; i++) {
            tile6Knotes[i] = knots[i + 11];
        }
        for (int i = 3; i < 6; i++) {
            tile6Knotes[i] = knots[i + 17];
        }

        for (int i = 0; i < 3; i++) {
            tile7Knots[i] = knots[i + 13];
        }
        for (int i = 3; i < 6; i++) {
            tile7Knots[i] = knots[i + 19];
        }

        for (int i = 0; i < 3; i++) {
            tile8Knotes[i] = knots[i + 15];
        }
        for (int i = 3; i < 6; i++) {
            tile8Knotes[i] = knots[i + 23];
        }

        for (int i = 0; i < 3; i++) {
            tile9Knots[i] = knots[i + 17];
        }
        for (int i = 3; i < 6; i++) {
            tile9Knots[i] = knots[i + 25];
        }

        for (int i = 0; i < 3; i++) {
            tile10Knotes[i] = knots[i + 19];
        }
        for (int i = 3; i < 6; i++) {
            tile10Knotes[i] = knots[i + 27];
        }

        for (int i = 0; i < 3; i++) {
            tile11Knots[i] = knots[i + 21];
        }
        for (int i = 3; i < 6; i++) {
            tile11Knots[1] = knots[i + 29];
        }

        for (int i = 0; i < 3; i++) {
            tile12Knotes[i] = knots[i + 23];
        }
        for (int i = 3; i < 6; i++) {
            tile12Knotes[i] = knots[i + 31];
        }

        for (int i = 0; i < 3; i++) {
            tile13Knots[i] = knots[i + 27];
        }
        for (int i = 3; i < 6; i++) {
            tile13Knots[i] = knots[i + 33];
        }

        for (int i = 0; i < 3; i++) {
            tile14Knotes[i] = knots[i + 29];
        }
        for (int i = 3; i < 6; i++) {
            tile14Knotes[i] = knots[i + 35];
        }

        for (int i = 0; i < 3; i++) {
            tile15Knots[i] = knots[i + 31];
        }
        for (int i = 3; i < 6; i++) {
            tile15Knots[i] = knots[i + 37];
        }

        for (int i = 0; i < 3; i++) {
            tile16Knotes[i] = knots[i + 33];
        }
        for (int i = 3; i < 6; i++) {
            tile16Knotes[i] = knots[i + 39];
        }

        for (int i = 0; i < 3; i++) {
            tile17Knotes[i] = knots[i + 37];
        }
        for (int i = 3; i < 6; i++) {
            tile17Knotes[i] = knots[i + 41];
        }

        for (int i = 0; i < 3; i++) {
            tile18Knots[i] = knots[i + 39];
        }
        for (int i = 3; i < 6; i++) {
            tile18Knots[i] = knots[i + 43];
        }

        for (int i = 0; i < 3; i++) {
            tile19Knotes[i] = knots[i + 41];
        }
        for (int i = 3; i < 6; i++) {
            tile19Knotes[i] = knots[i + 45];
        }

        tiles[0].setKnots(tile1Knots);
        tiles[1].setKnots(tile2Knotes);
        tiles[2].setKnots(tile3Knots);
        tiles[3].setKnots(tile4Knotes);
        tiles[4].setKnots(tile5Knots);
        tiles[5].setKnots(tile6Knotes);
        tiles[6].setKnots(tile7Knots);
        tiles[7].setKnots(tile8Knotes);
        tiles[8].setKnots(tile9Knots);
        tiles[9].setKnots(tile10Knotes);
        tiles[10].setKnots(tile11Knots);
        tiles[11].setKnots(tile12Knotes);
        tiles[12].setKnots(tile13Knots);
        tiles[13].setKnots(tile14Knotes);
        tiles[14].setKnots(tile15Knots);
        tiles[15].setKnots(tile16Knotes);
        tiles[16].setKnots(tile17Knotes);
        tiles[17].setKnots(tile18Knots);
        tiles[18].setKnots(tile19Knotes);

    }

    private void initEdges(RichPathView gameBoard) {
        for (int i = 0; i < 6; i++) {
            edges[i] = new Edge(knots[i], knots[i + 1], gameBoard);
        }
        int d = 0;
        for (int i = 6; i < 10; i++) {
            edges[i] = new Edge(knots[d], knots[d + 8], gameBoard);
            d = d + 2;
        }
        for (int i = 10; i < 18; i++) {
            edges[i] = new Edge(knots[i - 3], knots[i - 2], gameBoard);
        }
        int c = 7;
        for (int i = 18; i < 23; i++) {
            edges[i] = new Edge(knots[c], knots[c + 10], gameBoard);
            c = c + 2;
        }
        for (int i = 23; i < 33; i++) {
            edges[i] = new Edge(knots[i - 7], knots[i - 6], gameBoard);
        }
        int e = 16;
        for (int i = 33; i < 39; i++) {
            edges[i] = new Edge(knots[e], knots[e + 11], gameBoard);
            e = e + 2;
        }
        for (int i = 39; i < 49; i++) {
            edges[i] = new Edge(knots[i - 12], knots[i - 11], gameBoard);
        }
        int f = 28;
        for (int i = 49; i < 54; i++) {
            edges[i] = new Edge(knots[f], knots[f + 10], gameBoard);
            f = f + 2;
        }
        for (int i = 54; i < 62; i++) {
            edges[i] = new Edge(knots[i - 16], knots[i - 15], gameBoard);
        }
        int g = 39;
        for (int i = 62; i < 66; i++) {
            edges[i] = new Edge(knots[g], knots[g + 8], gameBoard);
            g = g + 2;
        }
        for (int i = 66; i < 72; i++) {
            edges[i] = new Edge(knots[i - 19], knots[i - 18], gameBoard);
            {
            }
        }
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public Knot[] getKnots() {
        return knots;
    }

    public Edge[] getEdges() {
        return edges;
    }
}
