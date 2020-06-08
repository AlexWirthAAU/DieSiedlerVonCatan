package com.example.diesiedler.trading;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.PlayerInventory;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.GameHandler;
import com.example.diesiedler.threads.NetworkThread;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Activity to setup a 4:1 Trade with the Bank.
 */
public class BankOrPortChangeActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(BankOrPortChangeActivity.class.getName()); // Logger
    List<Button> giveBtns = new ArrayList<>(); // List of all Give-Buttons
    List<Button> getBtns = new ArrayList<>(); // List of all Get-Buttons
    StringBuilder res = new StringBuilder(); // StringBuilder for the Trade-Offer
    Handler handler = new BankOrPortChangeHandler(Looper.getMainLooper(), this); // Handler

    String kind; // bank or port, depending on the chosen Action

    PlayerInventory playerInventory; // current Players Inventory

    Button woolGive = findViewById(R.id.woolGive); // Resource-Buttons
    Button wheatGive = findViewById(R.id.wheatGive);
    Button oreGive = findViewById(R.id.oreGive);
    Button clayGive = findViewById(R.id.clayGive);
    Button woodGive = findViewById(R.id.woodGive);

    Button woolGet = findViewById(R.id.woolGet);
    Button wheatGet = findViewById(R.id.wheatGet);
    Button oreGet = findViewById(R.id.oreGet);
    Button clayGet = findViewById(R.id.clayGet);
    Button woodGet = findViewById(R.id.woodGet);

    /**
     * Adds all Buttons to their List.
     * Specifies the Handler in ClientData for the current Activity.
     *
     * @param savedInstanceState saved State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_change);

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

        kind = getIntent().getStringExtra("kind");

        playerInventory = ClientData.currentGame.getPlayer(ClientData.userId).getInventory();

        if (kind.equals("bank")) {
            setBankButtons();
        }

        if (kind.equals("port")) {
            setPortButtons();
        }

        ClientData.currentHandler = handler;
    }

    /**
     * Enables the Resource-Buttons, when the
     * Player has less than 4 of the corresponding Resource.
     */
    private void setBankButtons() {
        if (playerInventory.getWood() < 4) {
            woodGive.setEnabled(false);
        }

        if (playerInventory.getWool() < 4) {
            woolGive.setEnabled(false);
        }

        if (playerInventory.getWheat() < 4) {
            wheatGive.setEnabled(false);
        }

        if (playerInventory.getOre() < 4) {
            oreGive.setEnabled(false);
        }

        if (playerInventory.getClay() < 4) {
            clayGive.setEnabled(false);
        }
    }

    /**
     * Enables the Resource-Buttons, when the
     * Player has less than 3 of the corresponding Resource or no Port.
     */
    private void setPortButtons() {

        if (playerInventory.getWood() < 3) {
            woodGive.setEnabled(false);
        }

        if (!playerInventory.isWoodport()) {
            woodGet.setEnabled(false);
        }

        if (playerInventory.getWool() < 3) {
            woolGive.setEnabled(false);
        }

        if (!playerInventory.isWoolport()) {
            woolGet.setEnabled(false);
        }

        if (playerInventory.getWheat() < 3) {
            wheatGive.setEnabled(false);
        }

        if (!playerInventory.isWheatport()) {
            wheatGet.setEnabled(false);
        }

        if (playerInventory.getOre() < 3) {
            oreGive.setEnabled(false);
        }

        if (!playerInventory.isOreport()) {
            oreGet.setEnabled(false);
        }

        if (playerInventory.getClay() < 3) {
            clayGive.setEnabled(false);
        }

        if (!playerInventory.isClayport()) {
            clayGet.setEnabled(false);
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

    /**
     * When a Give-Button was clicked, all other Get-Buttons are enabled
     * and the Ressource is appended to the StringBuilder (Text).
     *
     * @param view View to access the Buttons
     */
    public void setGive(View view) {

        for (Button btn : giveBtns) {
            btn.setEnabled(false);

            if (btn.getId() == view.getId()) {
                if (res.toString().length() > 0) {
                    res.append("/");
                }
                res.append(btn.getText().toString());
            }
        }
    }

    /**
     * When a Get-Button was clicked, all other Give-Buttons are enabled
     * and the desired Ressource is appended to the StringBuilder (Text).
     *
     * @param view View to access the Buttons
     */
    public void setGet(View view) {

        for (Button btn : getBtns) {
            btn.setEnabled(false);

            if (btn.getId() == view.getId()) {
                if (res.toString().length() > 0) {
                    res.append("/");
                }
                res.append(btn.getText().toString());
            }
        }
    }

    /**
     * When the Trade-Button was clicked, a NetworkThread is started
     * which sends the Trade-Offer as a String with a Divider to the Server.
     *
     * @param view View to access the Trade-Button
     */
    public void change(View view) {
        String toSendTrade = res.toString();
        Thread networkThread;

        if (kind.equals("bank")) {
            networkThread = new NetworkThread(ServerQueries.createStringQueryBankChange(toSendTrade));
            logger.log(Level.INFO, "CREATE BANK CHANGE");
        } else {
            networkThread = new NetworkThread(ServerQueries.createStringQueryPortChange(toSendTrade));
            logger.log(Level.INFO, "CREATE PORT CHANGE");
        }
        networkThread.start();
    }

    /**
     * @author Christina Senger
     * @author Fabian Schaffenrath (edit)
     *
     * Handler for the BankChangeActivity
     */
    private class BankOrPortChangeHandler extends GameHandler {

        BankOrPortChangeHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Called from ServerCommunicationThread. When a String was sent, it is processed
         * by the super handleMessage method.
         *
         * @param msg msg.arg1 has the Param for further Actions
         *            msg.obj holds the Object sent from the Server
         */
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 5){
                super.handleMessage(msg);
            }
        }
    }
}
