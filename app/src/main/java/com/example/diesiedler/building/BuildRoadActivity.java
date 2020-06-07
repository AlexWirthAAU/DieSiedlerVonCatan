package com.example.diesiedler.building;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.example.catangame.gameboard.Edge;
import com.example.diesiedler.GameBoardOverviewActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateBuildRoadView;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.GameHandler;
import com.example.diesiedler.threads.NetworkThread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Alex Wirth
 * @author Christina Senger (edit)
 * <p>
 * This Activity should allow the user to click the edge he wants to build a road on. The edges that he can possibly build a road on, are highlighted in red.
 * The clicked asset's ID will be sent to the server where the gamesession will be updated.
 * Also, the view of this activity shows the player's resources.
 * If there is no edge where the player can build a city, he will be informed about that and lead to the ChooseAction-Activity.
 */
public class BuildRoadActivity extends GameBoardOverviewActivity {

    private static final Logger logger = Logger.getLogger(BuildRoadActivity.class.getName()); // Logger
    private Handler handler = new BuildRoadHandler(Looper.getMainLooper(), this); // Handler
    private static String card = ""; // "CARD" when to Activity is started from the PlayCardActivity
    private String cardIn, cardString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClientData.currentHandler = handler;

        card = getIntent().getStringExtra("card");
        cardIn = "CardIn: " + card;
        cardString = "Card: " + card;
        if (card != null) {
            logger.log(Level.INFO, cardIn);
        }
        logger.log(Level.INFO, cardString);


        UpdateBuildRoadView.updateView(ClientData.currentGame, richPathView, card);
        gameBoardClickListener.clickBoard("BuildRoad");
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
     * Going back is not allowed in the init phase.
     */
    @Override
    public void onBackPressed() {
        if(ClientData.currentGame.isInitialized()){
            super.onBackPressed();
        }
    }


    /**
     * After having clicked an edge, the edge' index will be send to the server by starting a new Network-Thread.
     * The method is called in "GameBoardClickListener".
     *
     * @param s
     */
    public void clicked(String s) {
        String logging = "Clicked: " + s;
        logger.log(Level.INFO, logging);
        Edge[] edges = ClientData.currentGame.getGameboard().getEdges();
        int edgeIndex = 0;
        for (int i = 0; i < edges.length; i++) {
            if (edges[i].getId().equals(s)) {
                edgeIndex = i;
            }
        }
        String eIString = Integer.toString(edgeIndex);

        Thread networkThread;

        if (card != null) {
            logger.log(Level.INFO, cardIn);
            networkThread = new NetworkThread(ServerQueries.createStringQueryPlayBuildStreetCard(eIString));
        } else {
            networkThread = new NetworkThread(ServerQueries.createStringQueryBuildRoad(eIString));
        }
        networkThread.start();
    }


    /**
     * @author Alex Wirth
     * @author Christina Senger (edit)
     * @author Fabian Schaffenrath (edit)
     * <p>
     * Handler for the BuildRoadActivity
     */
    private class BuildRoadHandler extends GameHandler {
        public BuildRoadHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * When a new game session is available, the view is updated.
         *
         * @param msg msg.arg1 has the Param for further Actions
         *            msg.obj has the received object
         */
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 5){
                super.handleMessage(msg);
            }
            else if (msg.arg1 == 4) {
                UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
                updateResources();
            }
        }

    }

}
