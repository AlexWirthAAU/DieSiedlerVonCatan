package com.example.diesiedler.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.diesiedler.Model.Edge;
import com.example.diesiedler.Model.Gameboard;
import com.example.diesiedler.Model.Knot;
import com.example.diesiedler.Model.Tile;
import com.richpath.RichPath;
import com.richpath.RichPathView;

public class GameBoardClickListener {

    RichPathView richPathView;
    Context c;
    Gameboard gameboard;

    public GameBoardClickListener(RichPathView richPathView, Context c, Gameboard g) {
        this.richPathView = richPathView;
        this.c = c;
        this.gameboard = g;
    }

    public void clickBoard() {
        richPathView.setOnPathClickListener(new RichPath.OnPathClickListener() {
            @Override
            public void onClick(RichPath richPath) {
                String pathType = getPathType(richPath);
                if (pathType.contains("tile")) {
                    //TODO: What should happen if Tile is clicked:
                    int pathID = getTilePathId(richPath);
                    for (Tile t : gameboard.getTiles()) {
                        if (t.getId() == pathID) {
                            Log.d("DEBUG", "Clicked Tile: " + t.getId());
                            Log.d("DEBUG", "Tile: " + t.getId() + " has divevalue: " + t.getDiceValue());
                        }
                    }
                } else if (pathType.contains("settlement")) {
                    //TODO: What should happen if Knot is clicked:
                    int row = getKnotPathId(richPath)[0];
                    int column = getKnotPathId(richPath)[1];
                    for (Knot k : gameboard.getKnots()) {
                        if (k.getRow() == row && k.getColumn() == column) {
                            Log.d("DEBUG", "Clicked Knot: " + k.toString());

                        }
                    }
                } else if (pathType.contains("edge")) {
                    //TODO: What should happen if Edge is clicked:
                    String edgeID = getEdgeId(richPath);
                    for (Edge e : gameboard.getEdges()) {
                        if (e.getId().equals(edgeID)) {
                            Log.d("DEBUG", "Clicked Edge: " + richPath.getName());

                        }
                    }
                } else if (pathType.contains("background")) {
                    Log.d("DEBUG", "Touched background");

                }
            }
        });
    }

    public void scaleBoard() {
        richPathView.setOnTouchListener(new StandardGesture(this.c));
    }

    private int getTilePathId(RichPath richPath) {
        int id = Integer.parseInt(richPath.getName().split("_")[1]);
        return id;
    }

    private int[] getKnotPathId(RichPath richPath) {
        int[] values = new int[2];
        String row = richPath.getName().split("_")[1];
        String column = richPath.getName().split("_")[2];
        values[0] = Integer.parseInt(row);
        values[1] = Integer.parseInt(column);
        return values;
    }

    private String getPathType(RichPath richPath) {
        String type = richPath.getName().split("_")[0];
        return type;
    }

    private String getEdgeId(RichPath richPath) {
        String id = richPath.getName();
        return id;
    }

}
