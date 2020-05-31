package com.example.diesiedler.presenter.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.diesiedler.ChooseActionActivity;
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.RollDiceActivity;
import com.example.diesiedler.beforegame.SearchPlayersActivity;
import com.example.diesiedler.building.BuildRoadActivity;
import com.example.diesiedler.building.BuildSettlementActivity;
import com.example.diesiedler.cheating.CheatCounterActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.trading.AnswerToTradeActivity;

/**
 * @author Fabian Schaffenrath
 *
 * Handler which saves the Looper for Messagehandling and the calling Activity
 */
public class GameHandler extends Handler {

    protected Activity activity;

    /**
     * Constructor
     *
     * @param mainLooper Looper for Messagehandling
     * @param ac         calling Activity
     */
    public GameHandler(Looper mainLooper, Activity ac) {
        super(mainLooper);
        activity = ac;
    }

    /**
     * Handles all Client calls, that need to display a Alert or switch to a different Activity.
     * If the Header starts with another header, the order is important!
     * example: (CHEATED, CHEATEDREVEAL)
     * @param msg the object received from the Server
     */
    @Override
    public void handleMessage(Message msg){
        if(msg.arg1 == 5){
            String message = null;
            if(((String) msg.obj).contains(" ")){
                message = ((String) msg.obj).substring(((String) msg.obj).indexOf(" ") + 1);
            }
            if(((String) msg.obj).startsWith("INIT1")){
                Intent intent = new Intent(activity, BuildSettlementActivity.class);
                activity.startActivity(intent);
            }
            else if(((String) msg.obj).startsWith("INIT2")){
                Intent intent = new Intent(activity, BuildRoadActivity.class);
                activity.startActivity(intent);
            }
            else if(((String) msg.obj).startsWith("BEGINTURN")){
                if(message != null){
                    showAlertWithRollDiceActivityCall("",message);
                }
                else{
                    Intent intent = new Intent(activity,RollDiceActivity.class);
                    activity.startActivity(intent);
                }
            }
            else if(((String) msg.obj).startsWith("DICEROLLED")){
                Intent intent = new Intent(activity, ChooseActionActivity.class);
                activity.startActivity(intent);
            }
            else if(((String) msg.obj).startsWith("ENDTURN")){
                if(message != null){
                    showAlertWithMainActivityCall("",message);
                }
                else{
                    Intent intent = new Intent(activity,MainActivity.class);
                    activity.startActivity(intent);
                }
            }
            else if(((String) msg.obj).startsWith("CHEATERSET")){
                if(ClientData.currentGame.getCurr().getUserId() == ClientData.userId){
                    showAlertWithChooseActionActivityCall("Diebstahl",message);
                }
                else {
                    showAlertWithMainActivityCall("Diebstahl",message);
                }
            }
            else if(((String) msg.obj).startsWith("CHEATER")){
                showAlert("Diebstahl",message);
            }
            else if(((String) msg.obj).startsWith("CHEATEDREVEAL")){  //TODO: putExtra of playerId umgehen
                if(message != null){
                    showAlertWithCheatCounterActivityCall("Diebstahl",message);
                }
                else{
                    Intent intent = new Intent(activity,CheatCounterActivity.class);
                    activity.startActivity(intent);
                }
            }
            else if(((String) msg.obj).startsWith("CHEATED")){
                showAlertWithChooseActionActivityCall("Diebstahl",message);
            }

            else if(((String) msg.obj).startsWith("TRADECOMPLETE")){
                showAlert("Handel",message);
            }
            else if(((String) msg.obj).startsWith("TRADE")){
                Intent intent = new Intent(activity, AnswerToTradeActivity.class);
                intent.putExtra("mess", message);
                activity.startActivity(intent);
            }
            else if(((String) msg.obj).startsWith("ERROR")){
                showAlert("Fehler",message);
            }
            else if(((String) msg.obj).startsWith("WON")){
                showAlertWithSearchPlayersActivityCall("SIEG","Du hast das Spiel gewonnen!");
            }
            else if(((String) msg.obj).startsWith("LOST")){
                showAlertWithSearchPlayersActivityCall("NIEDERLAGE","Du hast das Spiel verloren!");
            }
        }
    }

    private void showAlert(String title, String message){
        AlertDialog.Builder builder = getBuilderBase(title,message);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private void showAlertWithMainActivityCall(String title, String message){
        AlertDialog.Builder builder = getBuilderBase(title,message);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(activity,MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
        builder.create().show();
    }

    private void showAlertWithRollDiceActivityCall(String title, String message){
        AlertDialog.Builder builder = getBuilderBase(title,message);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(activity,RollDiceActivity.class);
                        activity.startActivity(intent);
                    }
                });
        builder.create().show();
    }

    private void showAlertWithChooseActionActivityCall(String title, String message){
        AlertDialog.Builder builder = getBuilderBase(title,message);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(activity, ChooseActionActivity.class);
                        activity.startActivity(intent);
                    }
                });
        builder.create().show();
    }

    private void showAlertWithCheatCounterActivityCall(String title, String message){
        AlertDialog.Builder builder = getBuilderBase(title,message);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(activity, CheatCounterActivity.class);
                        activity.startActivity(intent);
                    }
                });
        builder.create().show();
    }

    private void showAlertWithSearchPlayersActivityCall(String title, String message){
        AlertDialog.Builder builder = getBuilderBase(title,message);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(activity, SearchPlayersActivity.class);
                        activity.startActivity(intent);
                    }
                });
        builder.create().show();
    }

    private AlertDialog.Builder getBuilderBase(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        return builder;
    }

}
