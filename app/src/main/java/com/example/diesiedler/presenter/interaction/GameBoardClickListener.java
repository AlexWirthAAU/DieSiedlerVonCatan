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

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameBoardClickListener {

    private RichPathView richPathView;
    private static final Logger logger = Logger.getLogger(GameBoardClickListener.class.getName()); // Logger

    public GameBoardClickListener(RichPathView richPathView, Context c) {
        this.richPathView = richPathView;
    }

    public void clickBoard(String a) {
        final String[] toSend = {""};
        final String activity = a;
        richPathView.setOnPathClickListener(new RichPath.OnPathClickListener() {
            @Override
            public void onClick(RichPath richPath) {
                String pathType = getPathType(richPath);
                if (pathType.contains("tile")) {
                    if(activity.equals("MoveThief") || activity.equals("MoveThiefCARD")){
                        toSend[0] = richPath.getName();
                        ThiefActivity thiefActivity = new ThiefActivity();
                        if(activity.equals("MoveThiefCARD")){
                            thiefActivity.card = "CARD";
                        }
                        thiefActivity.clicked(toSend[0]);
                    }
                    toSend[0] = richPath.getName();

                } else if (pathType.contains("settlement")) {
                    if (activity.equals("BuildSettlement") && richPath.getFillColor() == Color.RED) {
                        toSend[0] = richPath.getName();
                        new BuildSettlementActivity().clicked(toSend[0]);
                    } else if (activity.equals("BuildCity") && richPath.getFillColor() == Color.RED) {
                        toSend[0] = richPath.getName();
                        new BuildCityActivity().clicked(toSend[0]);
                    }
                } else if (pathType.contains("edge")) {
                    if (activity.equals("BuildRoad") && richPath.getFillColor() == Color.RED) {
                        toSend[0] = richPath.getName();
                        new BuildRoadActivity().clicked(toSend[0]);
                    }
                } else if (pathType.contains("background") || pathType.contains("harbour")) {
                    logger.log(Level.INFO, "Clicked background");
                }
            }
        });
        logger.log(Level.INFO, toSend[0] + "clicked");
    }

    private String getPathType(RichPath richPath) {
        return richPath.getName().split("_")[0];
    }
}
