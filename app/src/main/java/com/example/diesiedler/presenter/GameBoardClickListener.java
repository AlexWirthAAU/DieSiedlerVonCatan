package com.example.diesiedler.presenter;

import android.content.Context;
import android.util.Log;

import com.example.diesiedler.building.BuildRoadActivity;
import com.example.diesiedler.building.BuildSettlementActivity;
import com.example.diesiedler.presenter.interaction.StandardGesture;
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
        final String[] toSend = {""};
        richPathView.setOnPathClickListener(new RichPath.OnPathClickListener() {
            @Override
            public void onClick(RichPath richPath) {
                String pathType = getPathType(richPath);
                if (pathType.contains("tile")) {
                    //TODO: What should happen if Tile is clicked:
                    toSend[0] = richPath.getName();

                } else if (pathType.contains("settlement")) {
                    //TODO: What should happen if Knot is clicked:
                    toSend[0] = richPath.getName();
                    new BuildSettlementActivity().clicked(toSend[0]);

                } else if (pathType.contains("edge")) {
                    //TODO: What should happen if Edge is clicked:
                    toSend[0] = richPath.getName();
                    new BuildRoadActivity().clicked(toSend[0]);

                } else if (pathType.contains("background") || pathType.contains("harbour")) {
                    Log.d("DEBUG", "Touched background");
                }
            }
        });
        Log.d("DEBUG", toSend[0]);
    }

    public void scaleBoard() {
        richPathView.setOnTouchListener(new StandardGesture(this.context));
    }

    private String getPathType(RichPath richPath) {
        String type = richPath.getName().split("_")[0];
        return type;
    }
}
