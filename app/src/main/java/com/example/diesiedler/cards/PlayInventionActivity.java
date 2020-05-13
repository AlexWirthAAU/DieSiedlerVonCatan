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

/**
 * @author Christina Senger
 * <p>
 * Activity to select the Ressource of which to Player wants to get 2,
 * through playing a Invention Card.
 */
public class PlayInventionActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(BankChangeActivity.class.getName()); // Logger
    Handler handler = new PlayInventionHandler(Looper.getMainLooper(), this); // Handler

    String res; // String to save the Ressource of the clicked Button

    /**
     * The Handler in ClientData is set for the current Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_invention);

        ClientData.currentHandler = handler;
    }

    /**
     * When the Player wants to get 2 Wood, res is set as wood.
     *
     * @param view View to access the Button
     */
    public void wood(View view) {
        logger.log(Level.INFO, "GET WOOD");
        res = "wood";
    }

    /**
     * When the Player wants to get 2 Wool, res is set as wool.
     *
     * @param view View to access the Button
     */
    public void wool(View view) {
        logger.log(Level.INFO, "GET WOOL");
        res = "wool";
    }

    /**
     * When the Player wants to get 2 Wheat, res is set as wheat.
     *
     * @param view View to access the Button
     */
    public void wheat(View view) {
        logger.log(Level.INFO, "GET WHEAT");
        res = "wheat";
    }

    /**
     * When the Player wants to get 2 Ore, res is set as ore.
     *
     * @param view View to access the Button
     */
    public void ore(View view) {
        logger.log(Level.INFO, "GET ORE");
        res = "ore";
    }

    /**
     * When the Player wants to get 2 Clay, res is set as clay.
     *
     * @param view View to access the Button
     */
    public void clay(View view) {
        logger.log(Level.INFO, "GET CLAY");
        res = "clay";
    }

    /**
     * The NetworkThread is started, which sends the selected Ressource to the Server.
     *
     * @param view View to access the Button
     */
    public void start(View view) {
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryPlayInventionCard(res));
        networkThread.start();
    }

    /**
     * @author Christina Senger
     *
     * Handler for the PlayInvenionActivity
     */
    private class PlayInventionHandler extends HandlerOverride {

        PlayInventionHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Called from ServerCommunicationThread. When a String is send, it is set as Extra
         * of the Intent. When a GameSession is send, the Card was played.
         * The GameSession gets updated and the MainActivity is called.
         *
         * @param msg msg.arg1 has the Param for further actions
         *            msg.obj has the updated GameSession
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
