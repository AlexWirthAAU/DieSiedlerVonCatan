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

public class CheatSelectResourceActivity extends AppCompatActivity {

    private String playerId;
    private Handler handler = new CheatSelectPlayerAndResourceHandler(Looper.getMainLooper(),this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat_select_player_and_resource);
        playerId = getIntent().getStringExtra("playerId");

        ClientData.currentHandler = handler;
        if(playerId == null || playerId.equals(""+ClientData.userId)){
            onBackPressed();
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

    public void chooseWood(View view){
        send("WOOD");
    }

    public void chooseWool(View view){
        send("WOOL");
    }

    public void chooseClay(View view){
        send("CLAY");
    }

    public void chooseOre(View view){
        send("ORE");
    }

    public void chooseWheat(View view){
        send("WHEAT");
    }

    public void send(String resource){
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryCheat(playerId,resource));
        networkThread.start();
    }

    private class CheatSelectPlayerAndResourceHandler extends GameHandler {

        private String mess;

        public CheatSelectPlayerAndResourceHandler(Looper mainLooper, Activity ac) {
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
