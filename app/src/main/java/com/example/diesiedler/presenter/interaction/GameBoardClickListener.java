package com.example.diesiedler.presenter.interaction;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.diesiedler.ThiefActivity;
import com.example.diesiedler.building.BuildCityActivity;
import com.example.diesiedler.building.BuildRoadActivity;
import com.example.diesiedler.building.BuildSettlementActivity;
import com.richpath.RichPath;
import com.richpath.RichPathView;

public class GameBoardClickListener {

    private RichPathView richPathView;
    private Context context;

    public GameBoardClickListener(RichPathView richPathView, Context c) {
        this.richPathView = richPathView;
        this.context = c;
    }

    public void clickBoard(String a) {
        final String[] toSend = {""};
        final String activity = a;
        richPathView.setOnPathClickListener(new RichPath.OnPathClickListener() {
            @Override
            public void onClick(RichPath richPath) {
                String pathType = getPathType(richPath);
                if (pathType.contains("tile")) {
                    //TODO: What should happen if Tile is clicked:
                    if(activity.equals("MoveThief")){
                        toSend[0] = richPath.getName();
                        new ThiefActivity().clicked(toSend[0]);
                    }
                    toSend[0] = richPath.getName();

                } else if (pathType.contains("settlement")) {
                    //TODO: What should happen if Knot is clicked:
                    if (activity.equals("BuildSettlement") && richPath.getFillColor() == Color.RED) {
                        toSend[0] = richPath.getName();
                        new BuildSettlementActivity().clicked(toSend[0]);
                    } else if (activity.equals("BuildCity") && richPath.getFillColor() == Color.RED) {
                        toSend[0] = richPath.getName();
                        new BuildCityActivity().clicked(toSend[0]);
                    }
                } else if (pathType.contains("edge")) {
                    //TODO: What should happen if Edge is clicked:

                    if (activity.equals("BuildRoad") && richPath.getFillColor() == Color.RED) {
                        toSend[0] = richPath.getName();
                        new BuildRoadActivity().clicked(toSend[0]);
                    }
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
