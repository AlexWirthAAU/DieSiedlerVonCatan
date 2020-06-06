package com.example.diesiedler.building;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.catangame.gameboard.Knot;
import com.example.diesiedler.GameBoardOverviewActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateBuildCityView;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.GameHandler;
import com.example.diesiedler.threads.NetworkThread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alex Wirth
 * @author Fabian Schaffenrath (edit)
 * <p>
 * This Activity should allow the user to click the knot he wants to build a city on. The knots that are possible to have a city, are highlighted in red.
 * The clicked asset's ID will be sent to the server where the gamesession will be updated.
 * Also, the view of this activity shows the player's resources.
 * If there is no knot where the player can build a city, he will be informed about that and lead to the ChooseAction-Activity.
 */
public class BuildCityActivity extends GameBoardOverviewActivity {

    private static final Logger logger = Logger.getLogger(BuildCityActivity.class.getName()); // Logger
    private Handler handler = new BuildCityHandler(Looper.getMainLooper(), this); // Handler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClientData.currentHandler = handler;

        UpdateBuildCityView.updateView(ClientData.currentGame, richPathView);
        gameBoardClickListener.clickBoard("BuildCity");
    }

    @Override
    public void onResume() {
        super.onResume();
        ClientData.currentHandler = handler;
        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();
    }

    public void onRestart() {
        super.onRestart();
        ClientData.currentHandler = handler;
        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();
    }

    /**
     * After having clicked a knot, the knots index will be send to the server by starting a new Network-Thread.
     * The method is called in "GameBoardClickListener".
     *
     * @param s
     */
    public void clicked(String s) {
        logger.log(Level.INFO, "Clicked: " + s);
        Knot[] knots = ClientData.currentGame.getGameboard().getKnots();
        int knotIndex = 0;
        String[] values = s.split("_");
        int row = Integer.parseInt(values[1]);
        int column = Integer.parseInt(values[2]);
        for (int i = 0; i < knots.length; i++) {
            if (knots[i].getRow() == row && knots[i].getColumn() == column) {
                knotIndex = i;
            }
        }
        String kIString = Integer.toString(knotIndex);

        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryBuildCity(kIString));
        networkThread.start();
    }


    /**
     * Handler is responsible for reacting to the new gamesession object received by the server.
     */
    private class BuildCityHandler extends GameHandler {

        public BuildCityHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 5) {
                super.handleMessage(msg);
            } else if (msg.arg1 == 4) {
                UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
                updateResources();
                UpdateBuildCityView.updateView(ClientData.currentGame, richPathView);
            }
        }
    }
}
