package com.example.diesiedler.cheating;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.GameHandler;
import com.example.diesiedler.threads.NetworkThread;

public class CheatCounterActivity extends AppCompatActivity {

    private Handler handler = new CheatCounterHandler(Looper.getMainLooper(),this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat_counter);

        ClientData.currentHandler = handler;
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

    /**
     *
     * @param view Holds the View, but is not needed.
     */
    public void chooseWood(View view){
        send("WOOD");
    }

    /**
     *
     * @param view Holds the View, but is not needed.
     */
    public void chooseWool(View view){
        send("WOOL");
    }

    /**
     *
     * @param view Holds the View, but is not needed.
     */
    public void chooseClay(View view){
        send("CLAY");
    }

    /**
     *
     * @param view Holds the View, but is not needed.
     */
    public void chooseOre(View view){
        send("ORE");
    }

    /**
     *
     * @param view Holds the View, but is not needed.
     */
    public void chooseWheat(View view){
        send("WHEAT");
    }

    public void send(String resource){
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryCounter(ClientData.cheaterId,resource));
        networkThread.start();
    }

    private class CheatCounterHandler extends GameHandler {

        public CheatCounterHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg){
            if(msg.arg1 == 5){
                super.handleMessage(msg);
            }
        }
    }
}
