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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Knot;
import com.example.diesiedler.ChooseActionActivity;
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateBuildCityView;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.example.diesiedler.threads.NetworkThread;
import com.richpath.RichPathView;

/**
 * @author Alex Wirth
 * <p>
 * This Activity should allow the user to click the knot he wants to build a city on. The knots that are possible to have a city, are highlighted in red.
 * The clicked asset's ID will be sent to the server where the gamesession will be updated.
 * Also, the view of this activity shows the player's resources.
 * If there is no knot where the player can build a city, he will be informed about that and lead to the ChooseAction-Activity.
 */
public class BuildCityActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler = new BuildCityHandler(Looper.getMainLooper(), this); // Handler
    private AlertDialog.Builder alertBuilder; // AlertBuilder

    // TextViews for number of resources
    private TextView woodCount;
    private TextView clayCount;
    private TextView wheatCount;
    private TextView oreCount;
    private TextView woolCount;
    private TextView devCardCount;

    // Buttons to show score and inventory:
    private ImageView devCards;
    private Button scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        devCards = findViewById(R.id.devCard);
        devCards.setOnClickListener(this);
        scoreBoard = findViewById(R.id.scoreBoard);
        scoreBoard.setOnClickListener(this);

        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();
        int status = UpdateBuildCityView.updateView(ClientData.currentGame, richPathView);
        ClientData.currentHandler = handler;

        if (status==0){
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
            gameBoardClickListener.clickBoard("BuildCity");
        }
    }

    /**
     * After having clicked a knot, the knots index will be send to the server by starting a new Network-Thread.
     * The method is called in "GameBoardClickListener".
     *
     * @param s
     */
    public void clicked(String s) {
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
        devCardCount = findViewById(R.id.devCardCount);
        devCardCount.setText(Integer.toString(playerInventory.getCards()));
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
                //TODO: load new activity
                break;
            case R.id.scoreBoard:
                //TODO: load new activity
                break;
        }
    }


    /**
     * Handler is responsible for reacting to the new gamesession object received by the server.
     * If it is not the player's turn, the main activity will be loaded.
     */
    private class BuildCityHandler extends HandlerOverride {

        public BuildCityHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 4) {
                GameSession gameSession = ClientData.currentGame;
                Player currentP = gameSession.getPlayer(gameSession.getCurrPlayer());

                if (currentP.getUserId() != ClientData.userId) {
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}
