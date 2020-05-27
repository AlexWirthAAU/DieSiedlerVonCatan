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
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.RollDiceActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;
import com.example.diesiedler.trading.AnswerToTradeActivity;

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

    private class CheatSelectPlayerAndResourceHandler extends HandlerOverride {

        private String mess;

        public CheatSelectPlayerAndResourceHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg){
            if(msg.arg1 == 4 && ClientData.currentGame.getCurr().getUserId() == ClientData.userId && !ClientData.hasRolledDice){
                Intent intent = new Intent(CheatSelectResourceActivity.this, RollDiceActivity.class);
                startActivity(intent);
            }
            else if(msg.arg1 == 4 && ClientData.currentGame.getCurr().getUserId() != ClientData.userId && ClientData.currentGame.isTradeOn()){
                Intent intent = new Intent(activity, AnswerToTradeActivity.class);
                intent.putExtra("mess", mess);
                startActivity(intent);
            }
            else if(msg.arg1 == 5 && ((String)msg.obj).startsWith("CHEAT")){
                String dialogText = "";
                if(msg.obj.equals("CHEAT SET")){
                    dialogText = "Stehlen wird beim Ende des nächsten Spielzuges der zu bestehlenden Person durchgeführt, sofern nicht korrekt entdeckt.";
                }
                else if(msg.obj.equals("CHEAT NOT SET")){
                    dialogText = "Stehlen konnte nicht durchgeführt werden.";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Diebstahl");
                builder.setMessage(dialogText);
                builder.setCancelable(true);
                builder.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent intent;
                                if(ClientData.currentGame.getPlayer(ClientData.userId).equals(ClientData.currentGame.getCurr())){
                                    intent = new Intent(CheatSelectResourceActivity.this, ChooseActionActivity.class);
                                }
                                else{
                                    intent = new Intent(CheatSelectResourceActivity.this, MainActivity.class);
                                }
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else if(msg.arg1 == 5 && ((String)msg.obj).startsWith("XCHEAT")){
                String dialogText = "";
                if(msg.obj.equals("XCHEAT COUNTERED")){
                    dialogText = "Dein Diebstahl wurde entdeckt und du hast einen Rohstoff verloren.";
                }
                else if(msg.obj.equals("XCHEAT BLOCKED")){
                    dialogText = "Dein Diebstahl wurde entdeckt, du hast aber keinen Rohstoff verloren.";
                }
                else if(msg.obj.equals("XCHEAT REVEALED")){
                    dialogText = "Dein Diebstahl wurde erkannt.";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Diebstahl");
                builder.setMessage(dialogText);
                builder.setCancelable(true);
                builder.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else if (msg.arg1 == 5) {  // TODO: Change to enums

                mess = msg.obj.toString();
            }
        }
    }

}
