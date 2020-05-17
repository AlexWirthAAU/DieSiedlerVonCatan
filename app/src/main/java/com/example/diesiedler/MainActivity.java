package com.example.diesiedler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.diesiedler.building.BuildSettlementActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.trading.AnswerToTradeActivity;
import com.richpath.RichPathView;

import java.util.logging.Logger;

/**
 * @author Alex Wirth
 * @author Christina Senger (edit)
 * <p>
 * Overview of Gameboard and Inventory
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Logger logger = Logger.getLogger(MainActivity.class.getName()); // Logger
    private Handler handler = new MainHandler(Looper.getMainLooper(), this); // Handler

    private TextView woodCount; // Number of Ressources
    private TextView clayCount;
    private TextView wheatCount;
    private TextView oreCount;
    private TextView woolCount;

    private TextView currentPlayer; // View of the current Player

    private Button devCards; // Button to show Score and DevCards
    private Button scoreBoard;

    /**
     * Loads actual Gameboard and Ressources. Sets Handler.
     * If the Intent has an Extra, a Alert-Message with its Test is shown.
     *
     * @param savedInstanceState saved State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        devCards = findViewById(R.id.devCards);
        devCards.setOnClickListener(this);
        scoreBoard = findViewById(R.id.scoreBoard);
        scoreBoard.setOnClickListener(this);

        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();

        ClientData.currentHandler = handler;

        String tradeMessage = getIntent().getStringExtra("mess");

        if (tradeMessage != null) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setCancelable(true);
            builder1.setMessage(tradeMessage);
            AlertDialog alert1 = builder1.create();
            alert1.show();
        }
    }

    private void updateResources() {
        PlayerInventory playerInventory = ClientData.currentGame.getPlayer(ClientData.userId).getInventory();
        Player currentP = ClientData.currentGame.getPlayer(ClientData.currentGame.getCurrPlayer());

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
        currentPlayer = findViewById(R.id.currentPlayer);
        currentPlayer.setText(currentP.getDisplayName() + " ist gerade am Zug");
    }

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
     *
     * Handler for the MainActivity
     */
    private class MainHandler extends HandlerOverride {

        MainHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Calles from ServerCommunicationThread. When a String is send, it is set as
         * Extra of the Intent and the AnswerToTradeActivity is started.
         * If a GameSession was send, it is checked, if its your Turn.
         * If not, the MainActivity is started.
         * If yes, to correspondending Activity is started.
         *
         * @param msg msg.arg1 has the Param for further Actions
         *            msg.obj holds a Object send from Server
         */
        @Override
        public void handleMessage(Message msg) {

            if (msg.arg1 == 4) {  // TODO: Change to enums

                GameSession gs = ClientData.currentGame;
                Player currentPlayer = gs.getPlayer(gs.getCurrPlayer());
                PlayerInventory playerInventory = currentPlayer.getInventory();

                if (currentPlayer.getUserId() == ClientData.userId && playerInventory.getRoads().size() < 2) {
                    Intent intent = new Intent(activity, BuildSettlementActivity.class);
                    startActivity(intent);
                } else if (currentPlayer.getUserId() == ClientData.userId && playerInventory.getSettlements().size() > 1 && playerInventory.getRoads().size() > 1) {
                    Intent intent = new Intent(activity, RollDiceActivity.class);
                    startActivity(intent);
                } else if (currentPlayer.getUserId() == ClientData.userId) {
                    Intent intent = new Intent(activity, RollDiceActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                }
            }

            if (msg.arg1 == 5) {  // TODO: Change to enums

                Intent intent = new Intent(activity, AnswerToTradeActivity.class);
                intent.putExtra("mess", msg.obj.toString());
                startActivity(intent);
            }
        }
    }
}
