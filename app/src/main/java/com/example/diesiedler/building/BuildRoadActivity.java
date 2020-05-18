package com.example.diesiedler.building;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Edge;
import com.example.diesiedler.ChooseActionActivity;
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateBuildRoadView;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.example.diesiedler.threads.NetworkThread;
import com.richpath.RichPathView;

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

    private Handler handler = new BuildRoadHandler(Looper.getMainLooper(), this); // Handler
    private AlertDialog.Builder alertBuilder; // AlertBuilder

    // TextViews for number of resources
    private TextView woodCount;
    private TextView clayCount;
    private TextView wheatCount;
    private TextView oreCount;
    private TextView woolCount;

    // Buttons to show score and inventory
    private Button devCards;
    private Button scoreBoard;

    //"CARD" when to Activity is started from the PlayCardActivity
    private String card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        devCards = findViewById(R.id.devCards);
        devCards.setOnClickListener(this);
        scoreBoard = findViewById(R.id.scoreBoard);
        scoreBoard.setOnClickListener(this);

        card = getIntent().getStringExtra("card");

        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();
        ClientData.currentHandler = handler;

        int status = UpdateBuildRoadView.updateView(ClientData.currentGame, richPathView, card);
        if (status == 0) {
            alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Du kannst nicht bauen");
            alertBuilder.setMessage("Du hast nicht genügend Rohstoffe!");
            alertBuilder.setCancelable(true);
            alertBuilder.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getBaseContext(), ChooseActionActivity.class);
                            startActivity(intent);
                        }
                    });
            alertBuilder.create();
            alertBuilder.show();
        } else {
            GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
            gameBoardClickListener.clickBoard("BuildRoad");
        }
    }

    /**
     * This method is responsible for refreshing the player's resources.
     */
    private void updateResources() {
        PlayerInventory playerInventory = ClientData.currentGame.getPlayer(ClientData.userId).getInventory();

        woodCount = findViewById(R.id.woodCount);
        woodCount.setText(Integer.toString(playerInventory.getWood()));
        clayCount = findViewById(R.id.clayCount);
        clayCount.setText(Integer.toString(playerInventory.getClay()));
        wheatCount = findViewById(R.id.wheatCount);
        wheatCount.setText(Integer.toString(playerInventory.getWheat()));
        oreCount = findViewById(R.id.oreCount);
        oreCount.setText(Integer.toString(playerInventory.getOre()));
        woolCount = findViewById(R.id.woolCount);
        woolCount.setText(Integer.toString(playerInventory.getWool()));
    }


    /**
     * After having clicked an edge, the edge' index will be send to the server by starting a new Network-Thread.
     * The method is called in "GameBoardClickListener".
     *
     * @param s
     */
    public void clicked(String s) {
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
            case R.id.devCards:
                //TODO: load new activity
                break;
            case R.id.scoreBoard:
                //TODO: load new activity
                break;
        }
    }

    /**
     * @author Alex Wirth
     * @author Christina Senger (edit)
     * <p>
     * Handler for the BuildRoadActivity
     */
    private class BuildRoadHandler extends HandlerOverride {
        public BuildRoadHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * When a GameSession is send, it is checked if it is the Players Turn.
         * If yes, the Activity get loaded once more with card as Extra of  the Intent.
         * If no, the MainActivit is started.
         *
         * @param msg msg.arg1 has the Param for further Actions
         *            msg.obj has the updated GameSession
         */
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 4) {

                if (ClientData.currentGame.getCurrPlayer() == ClientData.userId) {

                    Intent intent = new Intent(activity, BuildRoadActivity.class);
                    intent.putExtra("card", "CARD");
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                }
            }
        }

    }

}
