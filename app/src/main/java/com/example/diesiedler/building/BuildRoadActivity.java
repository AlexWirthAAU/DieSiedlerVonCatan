package com.example.diesiedler.building;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Edge;
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.ScoreBoardActivity;
import com.example.diesiedler.cards.DevCardInventoryActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateBuildRoadView;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.GameHandler;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.example.diesiedler.threads.NetworkThread;
import com.richpath.RichPathView;

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
public class BuildRoadActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Logger logger = Logger.getLogger(BuildRoadActivity.class.getName()); // Logger
    private Handler handler = new BuildRoadHandler(Looper.getMainLooper(), this); // Handler
    private RichPathView richPathView;
    private static String card = ""; // "CARD" when to Activity is started from the PlayCardActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        richPathView = findViewById(R.id.ic_gameboardView);

        // Buttons to show score and inventory:
        ImageView devCards = findViewById(R.id.devCard);
        devCards.setOnClickListener(this);
        Button scoreBoard = findViewById(R.id.scoreBoard);
        scoreBoard.setOnClickListener(this);

        card = getIntent().getStringExtra("card");
        if (card != null) {
            logger.log(Level.INFO, card + " cardin");
        }

        logger.log(Level.INFO, card + " card");

        ClientData.currentHandler = handler;

        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();
        UpdateBuildRoadView.updateView(ClientData.currentGame, richPathView, card);

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard("BuildRoad");
    }

    @Override
    public void onResume() {
        super.onResume();
        ClientData.currentHandler = handler;
    }
    public void onRestart() {
        super.onRestart();
        ClientData.currentHandler = handler;
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
     * This method is responsible for refreshing the player's resources.
     */
    private void updateResources() {
        PlayerInventory playerInventory = ClientData.currentGame.getCurr().getInventory();
        Player currentP = ClientData.currentGame.getCurr();

        //TextViews for number of players resources
        TextView woodCount = findViewById(R.id.woodCount);
        woodCount.setText(String.format(Integer.toString(playerInventory.getWood())));
        TextView clayCount = findViewById(R.id.clayCount);
        clayCount.setText(String.format(Integer.toString(playerInventory.getClay())));
        TextView wheatCount = findViewById(R.id.wheatCount);
        wheatCount.setText(String.format(Integer.toString(playerInventory.getWheat())));
        TextView oreCount = findViewById(R.id.oreCount);
        oreCount.setText(String.format(Integer.toString(playerInventory.getOre())));
        TextView woolCount = findViewById(R.id.woolCount);
        woolCount.setText(String.format(Integer.toString(playerInventory.getWool())));

        TextView currentPlayer = findViewById(R.id.currentPlayer);
        if (currentP.getUserId() == ClientData.userId) {
            currentPlayer.setText(String.format("Du bist gerade am Zug!"));
        } else {
            currentPlayer.setText(String.format(currentP.getDisplayName() + " ist gerade am Zug"));
        }
        TextView devCardCount = findViewById(R.id.devCardCount);
        devCardCount.setText(String.format(Integer.toString(playerInventory.getCards())));
    }


    /**
     * After having clicked an edge, the edge' index will be send to the server by starting a new Network-Thread.
     * The method is called in "GameBoardClickListener".
     *
     * @param s
     */
    public void clicked(String s) {
        logger.log(Level.INFO, s + " clicked");
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
            logger.log(Level.INFO, card + " cardin");
            networkThread = new NetworkThread(ServerQueries.createStringQueryPlayBuildStreetCard(eIString));
        } else {
            networkThread = new NetworkThread(ServerQueries.createStringQueryBuildRoad(eIString));
        }
        networkThread.start();
    }

    /**
     * The View has to buttons that can be clicked.
     * "devCards" will lead the player to an overview of his card-inventory where he can see all his dev-cards
     * "scoreBoard" will load an overview of the current victory points of each player. This activity will also be used for cheating.
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.devCard:
                intent = new Intent(getBaseContext(), DevCardInventoryActivity.class);
                startActivity(intent);
                break;
            case R.id.scoreBoard:
                intent = new Intent(getBaseContext(), ScoreBoardActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
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
