package com.example.diesiedler.presenter;

import android.content.Context;
import android.util.Log;

import com.example.diesiedler.BuildCityActivity;
import com.example.diesiedler.BuildRoadActivity;
import com.example.diesiedler.BuildSettlementActivity;
import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.concurrent.ExecutionException;

public class GameBoardClickListener {

    RichPathView richPathView;
    Context context;

    public GameBoardClickListener(RichPathView richPathView, Context c) {
        this.richPathView = richPathView;
        this.context = c;
    }

    public void clickBoard(String activity) {
        final String[] toSend = {""};
        final String a = activity;
        richPathView.setOnPathClickListener(new RichPath.OnPathClickListener() {
            @Override
            public void onClick(RichPath richPath) {
                String pathType = getPathType(richPath);
                if (pathType.contains("tile")) {
                    //TODO: What should happen if Tile is clicked:
                    toSend[0] = richPath.getName();

                } else if (pathType.contains("settlement")) {
                    //TODO: What should happen if Knot is clicked:
                    if (a.equals("BuildSettlement")) {
                        toSend[0] = richPath.getName() + " BuildSettlement";
                        try {
                            new BuildSettlementActivity().clicked(toSend[0], context);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (a.equals("BuildCity")) {
                        toSend[0] = richPath.getName() + " BuildCity";
                        try {
                            new BuildCityActivity().clicked(toSend[0], context);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (pathType.contains("edge")) {
                    //TODO: What should happen if Edge is clicked:
                    if (a.equals("BuildRoad")) {
                        toSend[0] = richPath.getName() + " BuildRoad";
                        try {
                            new BuildRoadActivity().clicked(toSend[0], context);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
