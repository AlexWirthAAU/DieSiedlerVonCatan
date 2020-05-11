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

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.richpath.RichPathView;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Main - Activity: Overview - View -> Gameboard and Inventory
     */

    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());
    private Handler handler = new MainActivityHandler(Looper.getMainLooper(), this);
    private TextView woodCount;
    private TextView clayCount;
    private TextView wheatCount;
    private TextView oreCount;
    private TextView woolCount;
    private TextView currentPlayer;
    private Button devCards;
    private Button scoreBoard;


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


    private class MainActivityHandler extends HandlerOverride {

        public MainActivityHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 4) {
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
        }
    }

}
