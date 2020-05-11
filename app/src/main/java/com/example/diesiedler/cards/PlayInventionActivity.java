package com.example.diesiedler.cards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;
import com.example.diesiedler.trading.BankChangeActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayInventionActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(BankChangeActivity.class.getName());
    Handler handler = new PlayInventionHandler(Looper.getMainLooper(), this);

    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_invention);

        ClientData.currentHandler = handler;
    }

    public void wood(View view) {
        logger.log(Level.INFO, "GET WOOD");
        res = "wood";
    }

    public void wool(View view) {
        logger.log(Level.INFO, "GET WOOL");
        res = "wool";
    }

    public void wheat(View view) {
        logger.log(Level.INFO, "GET WHEAT");
        res = "wheat";
    }

    public void ore(View view) {
        logger.log(Level.INFO, "GET ORE");
        res = "ore";
    }

    public void clay(View view) {
        logger.log(Level.INFO, "GET CLAY");
        res = "clay";
    }

    public void start(View view) {
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryPlayInventionCard(res));
        networkThread.start();
    }

    private class PlayInventionHandler extends HandlerOverride {

        PlayInventionHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Wird vom ServerCommunicationThread aufgerufen. Im Falle einer Stringübertragung wird
         * die Nachricht als String dem Intent übergeben, sollte eine GameSession übertragen
         * werden, so wurde der Handel durchgeführt und die MainActivity wird aufgerufen.
         *
         * @param msg msg.arg1 beinhaltet den entsprechenden Parameter zur weiteren Ausführung
         */
        @Override
        public void handleMessage(Message msg) {

            Intent intent = new Intent(activity, MainActivity.class);

            if (msg.arg1 == 4) {  // TODO: Change to enums

                ClientData.currentGame = (GameSession) msg.obj;
                startActivity(intent);
            }
            if (msg.arg1 == 5) {  // TODO: Change to enums

                intent.putExtra("mess", msg.obj.toString());
            }
        }
    }
}
