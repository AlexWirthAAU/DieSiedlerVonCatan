package com.example.diesiedler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.catangame.gameboard.Tile;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.UpdateThiefView;
import com.example.diesiedler.presenter.handler.GameHandler;
import com.example.diesiedler.threads.NetworkThread;

/**
 * @author Fabian Schaffenrath
 * This activity is used to select a Tile, to which the Thief should be moved.
 * Once selected, the TileIndex is sent to the Server to execute the movement.
 */

public class ThiefActivity extends GameBoardOverviewActivity {

    private Handler handler = new ThiefHandler(Looper.getMainLooper(), this);
    public String card; // "CARD" when to Activity is started from the PlayCardActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClientData.currentHandler = handler;

        card = getIntent().getStringExtra("card");

        UpdateThiefView.updateView(ClientData.currentGame, richPathView);


        if(card != null && card.equals("CARD")) {
            gameBoardClickListener.clickBoard("MoveThiefCARD");
        }
        else{
            gameBoardClickListener.clickBoard("MoveThief");
        }
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

    @Override
    public void onBackPressed() {
        // Back button should not return to the previous Activity here.
    }

    public void clicked(String clicked){
        Tile[] tiles = ClientData.currentGame.getGameboard().getTiles();

        int tileIndex = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getId() == Integer.parseInt(clicked.substring(5))) {
                tileIndex = i;
                break;
            }
        }
        String tIString = Integer.toString(tileIndex);

        Thread networkThread;

        if (card != null) {
            networkThread = new NetworkThread(ServerQueries.createStringQueryPlayKnightCard(tIString));
        } else {
            networkThread = new NetworkThread(ServerQueries.createStringQueryMoveThief(tIString));
        }
        networkThread.start();
    }

    private class ThiefHandler extends GameHandler {

        ThiefHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 5){
                super.handleMessage(msg);
            }
            else if(msg.arg1 == 4){
                UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
                UpdateThiefView.updateView(ClientData.currentGame, richPathView);
            }
        }
    }
}
