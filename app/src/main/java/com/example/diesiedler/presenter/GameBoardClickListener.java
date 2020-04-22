package com.example.diesiedler.presenter;

import android.content.Context;
import android.util.Log;

import com.richpath.RichPath;
import com.richpath.RichPathView;

public class GameBoardClickListener {

    RichPathView richPathView;
    Context context;

    public GameBoardClickListener(RichPathView richPathView, Context c) {
        this.richPathView = richPathView;
        this.context = c;
    }

    public void clickBoard() {
        richPathView.setOnPathClickListener(new RichPath.OnPathClickListener() {
            @Override
            public void onClick(RichPath richPath) {
                String pathType = getPathType(richPath);
                if (pathType.contains("tile")) {
                    //TODO: What should happen if Tile is clicked:
                    String pathID = richPath.getName();
                    new PresenterBuild().chooseAssetID(pathID);

                } else if (pathType.contains("settlement")) {
                    //TODO: What should happen if Knot is clicked:
                    String pathID = richPath.getName();
                    new PresenterBuild().chooseAssetID(pathID);

                } else if (pathType.contains("edge")) {
                    //TODO: What should happen if Edge is clicked:
                    String pathID = richPath.getName();
                    new PresenterBuild().chooseAssetID(pathID);

                } else if (pathType.contains("background") || pathType.contains("harbour")) {
                    Log.d("DEBUG", "Touched background");
                }
            }
        });
    }

    public void scaleBoard() {
        richPathView.setOnTouchListener(new StandardGesture(this.context));
    }

    private String getPathType(RichPath richPath) {
        String type = richPath.getName().split("_")[0];
        return type;
    }


}
