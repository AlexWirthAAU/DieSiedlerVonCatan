package com.example.diesiedler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortChangeActivity extends AppCompatActivity {

    Button woodGive = findViewById(R.id.woodGive);
    Button woolGive = findViewById(R.id.woolGive);
    Button wheatGive = findViewById(R.id.wheatGive);
    Button oreGive = findViewById(R.id.oreGive);
    Button clayGive = findViewById(R.id.clayGive);

    List<Button> giveBtns = new ArrayList<>();

    Button woodGet = findViewById(R.id.woodGet);
    Button woolGet = findViewById(R.id.woolGet);
    Button wheatGet = findViewById(R.id.wheatGet);
    Button oreGet = findViewById(R.id.oreGet);
    Button clayGet = findViewById(R.id.clayGet);

    List<Button> getBtns = new ArrayList<>();

    StringBuilder res = new StringBuilder();

    private static final Logger logger = Logger.getLogger(PortChangeActivity.class.getName());
    Handler handler = new PortChangeHandler(Looper.getMainLooper(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port_change);

        giveBtns.add(woodGive);
        giveBtns.add(woolGive);
        giveBtns.add(wheatGive);
        giveBtns.add(oreGive);
        giveBtns.add(clayGive);

        getBtns.add(woodGet);
        getBtns.add(woolGet);
        getBtns.add(wheatGet);
        getBtns.add(oreGet);
        getBtns.add(clayGet);

        GameSession game = ClientData.currentGame;
        Player player = game.getCurr();

        if (player.getInventory().isWoodport()) {
            woodGive.setEnabled(false);
        }

        if (player.getInventory().isWoolport()) {
            woolGive.setEnabled(false);
        }

        if (player.getInventory().isWheatport()) {
            wheatGive.setEnabled(false);
        }

        if (player.getInventory().isOreport()) {
            oreGive.setEnabled(false);
        }

        if (player.getInventory().isClayport()) {
            clayGet.setEnabled(false);
        }

        ClientData.currentHandler = handler;
    }

    public void setGive(View view) {

        for (Button btn : giveBtns) {
            btn.setEnabled(false);

            if (btn.getId() == view.getId()) {
                res.append(btn.getText().toString()).append("/");
            }
        }
    }

    public void setGet(View view) {

        for (Button btn : getBtns) {
            btn.setEnabled(false);

            if (btn.getId() == view.getId()) {
                res.append(btn.getText().toString());
            }
        }
    }

    public void change(View view) {
        String toSendTrade = res.toString();
        logger.log(Level.INFO, "CREATE PORT CHANGE");
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryPortChange(toSendTrade));
        networkThread.start();
    }

    private class PortChangeHandler extends HandlerOverride {

        PortChangeHandler(Looper mainLooper, Activity ac) {
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
