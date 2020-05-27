package com.example.diesiedler.cheating;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.example.diesiedler.ChooseActionActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;

public class CheatRevealActivity extends AppCompatActivity {

    private String playerId;
    private Handler handler = new CheatRevealHandler(Looper.getMainLooper(),this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat_reveal);
        playerId = getIntent().getStringExtra("playerId");

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
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryReveal(playerId,resource));
        networkThread.start();
    }

    private class CheatRevealHandler extends HandlerOverride {

        public CheatRevealHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg){
            if(msg.arg1 == 5 && ((String)msg.obj).startsWith("CHEAT")){
                String dialogText = "";
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                ClientData.triedReveal = true;
                if(msg.obj.equals("CHEAT REVEALED")){
                    dialogText = "Richtig geraten. Nun kannst du einen Rohstoff zurückstehlen.";
                    builder.setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent intent = new Intent(CheatRevealActivity.this, CheatCounterActivity.class);
                                    intent.putExtra("playerId",playerId);
                                    startActivity(intent);
                                }
                            });
                }
                else if(msg.obj.equals("CHEAT NOT REVEALED")){
                    dialogText = "Falsch geraten. Der Rohstoff wird dir am Ende deines Zuges abgezogen.";
                    builder.setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent intent = new Intent(CheatRevealActivity.this, ChooseActionActivity.class);
                                    startActivity(intent);
                                }
                            });
                }
                builder.setTitle("Ertappen");
                builder.setMessage(dialogText);
                builder.setCancelable(true);
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

}
