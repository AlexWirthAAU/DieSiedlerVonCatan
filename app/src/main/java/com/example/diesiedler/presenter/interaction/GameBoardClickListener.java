package com.example.diesiedler.presenter.interaction;

import android.graphics.Color;
import com.example.diesiedler.ThiefActivity;
import com.example.diesiedler.building.BuildCityActivity;
import com.example.diesiedler.building.BuildRoadActivity;
import com.example.diesiedler.building.BuildSettlementActivity;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alex Wirth
 * @author Christina Senger (edit)
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Depending on which action the player chooses to do (BuildSettlement, BuildCity, BuildRoad, MoveThief) the affected assets are
 * open to be clicked on (The ones that are marked red). The clicked asset's ID will the be sent back to the activity where it was called from.
 * The clickBoard method is called in activities concerning buildings and moving the thief. There the server communication for requesting to change
 * the gameboard is handled.
 */
public class GameBoardClickListener {

    private RichPathView richPathView;
    private static final Logger logger = Logger.getLogger(GameBoardClickListener.class.getName()); // Logger

    public GameBoardClickListener(RichPathView richPathView) {
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
                    //What should happen if Tile is clicked
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
                    //What should happen if Knot is clicked
                    if (activity.equals("BuildSettlement") && richPath.getFillColor() == Color.RED) {
                        toSend[0] = richPath.getName();
                        new BuildSettlementActivity().clicked(toSend[0]);
                    } else if (activity.equals("BuildCity") && richPath.getFillColor() == Color.RED) {
                        toSend[0] = richPath.getName();
                        new BuildCityActivity().clicked(toSend[0]);
                    }
                } else if (pathType.contains("edge")) {
                    //What should happen if Edge is clicked
                    if (activity.equals("BuildRoad") && richPath.getFillColor() == Color.RED) {
                        toSend[0] = richPath.getName();
                        new BuildRoadActivity().clicked(toSend[0]);
                    }
                } else if (pathType.contains("background") || pathType.contains("harbour")) {
                    logger.log(Level.INFO, "Clicked background");
                }
            }
        });
        logger.log(Level.INFO, toSend[0], "clicked");
    }

    private String getPathType(RichPath richPath) {
        return richPath.getName().split("_")[0];
    }
}
