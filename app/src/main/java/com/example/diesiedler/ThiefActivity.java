package com.example.diesiedler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.catangame.gameboard.Tile;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.UpdateThiefView;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.example.diesiedler.threads.NetworkThread;
import com.richpath.RichPathView;

/**
 * @author Fabian Schaffenrath
 * This activity is used to select a Tile, to which the Thief should be moved.
 * Once selected, the TileIndex is sent to the Server to execute the movement.
 */

public class ThiefActivity extends AppCompatActivity {

    private Handler handler = new ThiefHandler(Looper.getMainLooper(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);

        ClientData.currentHandler = handler;

        RichPathView richPathView = findViewById(R.id.ic_gameboardView);
        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);

        UpdateThiefView.updateView(ClientData.currentGame, richPathView);

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard("MoveThief");
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

        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryMoveThief(tIString));
        networkThread.start();
    }

    private class ThiefHandler extends HandlerOverride {

        public ThiefHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 4) {
                Intent intent = new Intent(activity, ChooseActionActivity.class);
                startActivity(intent);
            }
        }
    }
}
