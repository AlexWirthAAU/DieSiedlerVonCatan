package com.example.diesiedler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.richpath.RichPathView;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    /**
     * Main - Activity: Overview - View -> Gameboard and Inventory
     */

    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());
    private Handler handler = new MainActivityHandler(Looper.getMainLooper(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);
        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);

        ClientData.currentHandler = handler;

        /*
        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard();

         */
    }

    public void clicked(String s) {
        // not used?
    }

    // TODO: Handler

    private class MainActivityHandler extends HandlerOverride {

        public MainActivityHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 4) {
                if (ClientData.currentGame.getPlayer(ClientData.currentGame.getCurrPlayer()).getUserId() == ClientData.userId) {
                    Intent intent = new Intent(activity, BuildSettlementActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

}
