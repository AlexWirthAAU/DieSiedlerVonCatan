package com.example.diesiedler.Presenter;

import android.content.Context;
import android.util.Log;

import com.richpath.RichPath;
import com.richpath.RichPathView;

public class GameBoardClickListener {

    RichPathView richPathView;
    Context c;

    public GameBoardClickListener(RichPathView richPathView, Context c) {
        this.richPathView = richPathView;
        this.c = c;
    }

    public void clickBoard() {
        richPathView.setOnPathClickListener(new RichPath.OnPathClickListener() {
            @Override
            public void onClick(RichPath richPath) {
                String pathType = getPathType(richPath);
                if (pathType.contains("tile")) {
                    //TODO: What should happen if Tile is clicked:
                    int pathID = getTilePathId(richPath);
                    Log.d("DEBUG", pathType + "   " + pathID);
                } else if (pathType.contains("settlement")) {
                    //TODO: What should happen if Knot is clicked
                    int row = getKnotPathId(richPath)[0];
                    int column = getKnotPathId(richPath)[1];
                    Log.d("DEBUG", pathType + " " + row + " " + column);
                } else if (pathType.contains("road")) {
                    //TODO: What should happen if road is clicked
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

}
