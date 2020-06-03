package com.example.catangame.gameboard;

import java.io.Serializable;

/**
 * @author Alex Wirth
 * The gameboard contains all information about the current settlements, citys and roads.
 */
public class Gameboard implements Serializable {

    private Tile[] tiles = new Tile[19];        //Gameboard is always compound of 19 hexagons
    private Knot[] knots = new Knot[54];        //Gameboard is always compound of 54 knots
    private Edge[] edges = new Edge[72];         //Gameboard is always compound of 72 edges

    public Gameboard() {
        for (int i = 1; i < 20; i++) {
            tiles[i - 1] = new Tile(i);
        }
        /**
         * The knots, tiles and edges are instantiated
         */
        initKnots();
        initTileKnots();
        initEdges();
    }

    private void initKnots() {
        int row = 1;
        int column = 1;
        for (int i = 0; i < 54; i++) {
            if (row == 1 && column < 10) {
                knots[i] = new Knot(row, column + 2);
                column++;
                if (column + 2 == 10) {
                    row = 2;
                    column = 1;
                }
            } else if (row == 2 && column < 11) {
                knots[i] = new Knot(row, column + 1);
                column++;
                if (column + 1 == 11) {
                    row = 3;
                    column = 1;
                }
            } else if (row == 3 && column < 12) {
                knots[i] = new Knot(row, column);
                column++;
                if (column == 12) {
                    row = 4;
                    column = 1;
                }
            } else if (row == 4 && column < 12) {
                knots[i] = new Knot(row, column);
                column++;
                if (column == 12) {
                    row = 5;
                    column = 1;
                }
            } else if (row == 5 && column < 11) {
                knots[i] = new Knot(row, column + 1);
                column++;
                if (column + 1 == 11) {
                    row = 6;
                    column = 1;
                }
            } else if (row == 6 && column < 10) {
                knots[i] = new Knot(row, column + 2);
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
        Knot[] tile2Knots = new Knot[6];
        Knot[] tile3Knots = new Knot[6];
        Knot[] tile4Knots = new Knot[6];
        Knot[] tile5Knots = new Knot[6];
        Knot[] tile6Knots = new Knot[6];
        Knot[] tile7Knots = new Knot[6];
        Knot[] tile8Knots = new Knot[6];
        Knot[] tile9Knots = new Knot[6];
        Knot[] tile10Knots = new Knot[6];
        Knot[] tile11Knots = new Knot[6];
        Knot[] tile12Knots = new Knot[6];
        Knot[] tile13Knots = new Knot[6];
        Knot[] tile14Knots = new Knot[6];
        Knot[] tile15Knots = new Knot[6];
        Knot[] tile16Knots = new Knot[6];
        Knot[] tile17Knots = new Knot[6];
        Knot[] tile18Knots = new Knot[6];
        Knot[] tile19Knots = new Knot[6];

        for (int i = 0; i < 3; i++) {
            tile1Knots[i] = knots[i];
        }
        for (int i = 3; i < 6; i++) {
            tile1Knots[i] = knots[i + 5];
        }

        for (int i = 0; i < 3; i++) {
            tile2Knots[i] = knots[i + 2];
        }
        for (int i = 3; i < 6; i++) {
            tile2Knots[i] = knots[i + 7];
        }

        for (int i = 0; i < 3; i++) {
            tile3Knots[i] = knots[i + 4];
        }
        for (int i = 3; i < 6; i++) {
            tile3Knots[i] = knots[i + 9];
        }

        for (int i = 0; i < 3; i++) {
            tile4Knots[i] = knots[i + 7];
        }
        for (int i = 3; i < 6; i++) {
            tile4Knots[i] = knots[i + 14];
        }

        for (int i = 0; i < 3; i++) {
            tile5Knots[i] = knots[i + 9];
        }
        for (int i = 3; i < 6; i++) {
            tile5Knots[i] = knots[i + 16];
        }

        for (int i = 0; i < 3; i++) {
            tile6Knots[i] = knots[i + 11];
        }
        for (int i = 3; i < 6; i++) {
            tile6Knots[i] = knots[i + 18];
        }

        for (int i = 0; i < 3; i++) {
            tile7Knots[i] = knots[i + 13];
        }
        for (int i = 3; i < 6; i++) {
            tile7Knots[i] = knots[i + 20];
        }

        for (int i = 0; i < 3; i++) {
            tile8Knots[i] = knots[i + 16];
        }
        for (int i = 3; i < 6; i++) {
            tile8Knots[i] = knots[i + 24];
        }

        for (int i = 0; i < 3; i++) {
            tile9Knots[i] = knots[i + 18];
        }
        for (int i = 3; i < 6; i++) {
            tile9Knots[i] = knots[i + 26];
        }

        for (int i = 0; i < 3; i++) {
            tile10Knots[i] = knots[i + 20];
        }
        for (int i = 3; i < 6; i++) {
            tile10Knots[i] = knots[i + 28];
        }

        for (int i = 0; i < 3; i++) {
            tile11Knots[i] = knots[i + 22];
        }
        for (int i = 3; i < 6; i++) {
            tile11Knots[i] = knots[i + 30];
        }

        for (int i = 0; i < 3; i++) {
            tile12Knots[i] = knots[i + 24];
        }
        for (int i = 3; i < 6; i++) {
            tile12Knots[i] = knots[i + 32];
        }

        for (int i = 0; i < 3; i++) {
            tile13Knots[i] = knots[i + 28];
        }
        for (int i = 3; i < 6; i++) {
            tile13Knots[i] = knots[i + 35];
        }

        for (int i = 0; i < 3; i++) {
            tile14Knots[i] = knots[i + 30];
        }
        for (int i = 3; i < 6; i++) {
            tile14Knots[i] = knots[i + 36];
        }

        for (int i = 0; i < 3; i++) {
            tile15Knots[i] = knots[i + 32];
        }
        for (int i = 3; i < 6; i++) {
            tile15Knots[i] = knots[i + 39];
        }

        for (int i = 0; i < 3; i++) {
            tile16Knots[i] = knots[i + 34];
        }
        for (int i = 3; i < 6; i++) {
            tile16Knots[i] = knots[i + 41];
        }

        for (int i = 0; i < 3; i++) {
            tile17Knots[i] = knots[i + 39];
        }
        for (int i = 3; i < 6; i++) {
            tile17Knots[i] = knots[i + 44];
        }

        for (int i = 0; i < 3; i++) {
            tile18Knots[i] = knots[i + 41];
        }
        for (int i = 3; i < 6; i++) {
            tile18Knots[i] = knots[i + 46];
        }

        for (int i = 0; i < 3; i++) {
            tile19Knots[i] = knots[i + 43];
        }
        for (int i = 3; i < 6; i++) {
            tile19Knots[i] = knots[i + 48];
        }

        tiles[0].setKnots(tile1Knots);
        tiles[1].setKnots(tile2Knots);
        tiles[2].setKnots(tile3Knots);
        tiles[3].setKnots(tile4Knots);
        tiles[4].setKnots(tile5Knots);
        tiles[5].setKnots(tile6Knots);
        tiles[6].setKnots(tile7Knots);
        tiles[7].setKnots(tile8Knots);
        tiles[8].setKnots(tile9Knots);
        tiles[9].setKnots(tile10Knots);
        tiles[10].setKnots(tile11Knots);
        tiles[11].setKnots(tile12Knots);
        tiles[12].setKnots(tile13Knots);
        tiles[13].setKnots(tile14Knots);
        tiles[14].setKnots(tile15Knots);
        tiles[15].setKnots(tile16Knots);
        tiles[16].setKnots(tile17Knots);
        tiles[17].setKnots(tile18Knots);
        tiles[18].setKnots(tile19Knots);

    }

    private void initEdges() {
        for (int i = 0; i < 6; i++) {
            edges[i] = new Edge(knots[i], knots[i + 1]);
        }
        int d = 0;
        for (int i = 6; i < 10; i++) {
            edges[i] = new Edge(knots[d], knots[d + 8]);
            d = d + 2;
        }
        for (int i = 10; i < 18; i++) {
            edges[i] = new Edge(knots[i - 3], knots[i - 2]);
        }
        int c = 7;
        for (int i = 18; i < 23; i++) {
            edges[i] = new Edge(knots[c], knots[c + 10]);
            c = c + 2;
        }
        for (int i = 23; i < 33; i++) {
            edges[i] = new Edge(knots[i - 7], knots[i - 6]);
        }
        int e = 16;
        for (int i = 33; i < 39; i++) {
            edges[i] = new Edge(knots[e], knots[e + 11]);
            e = e + 2;
        }
        for (int i = 39; i < 49; i++) {
            edges[i] = new Edge(knots[i - 12], knots[i - 11]);
        }
        int f = 28;
        for (int i = 49; i < 54; i++) {
            edges[i] = new Edge(knots[f], knots[f + 10]);
            f = f + 2;
        }
        for (int i = 54; i < 62; i++) {
            edges[i] = new Edge(knots[i - 16], knots[i - 15]);
        }
        int g = 39;
        for (int i = 62; i < 66; i++) {
            edges[i] = new Edge(knots[g], knots[g + 8]);
            g = g + 2;
        }
        for (int i = 66; i < 72; i++) {
            edges[i] = new Edge(knots[i - 19], knots[i - 18]);
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
