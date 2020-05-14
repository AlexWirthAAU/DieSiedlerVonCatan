package com.example.diesiedler.trading;

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
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Activity to setup a 3:1 Trade over a Port.
 */
public class PortChangeActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(BankChangeActivity.class.getName()); // Logger
    Button woolGive = findViewById(R.id.woolGive);
    Button wheatGive = findViewById(R.id.wheatGive);
    Button oreGive = findViewById(R.id.oreGive);
    Button clayGive = findViewById(R.id.clayGive);
    Button woodGive = findViewById(R.id.woodGive); // Buttons for Ressources, the Player wants to give
    List<Button> giveBtns = new ArrayList<>(); // List of all Give-Buttons
    Button woolGet = findViewById(R.id.woolGet);
    Button wheatGet = findViewById(R.id.wheatGet);
    Button oreGet = findViewById(R.id.oreGet);
    Button clayGet = findViewById(R.id.clayGet);
    Button woodGet = findViewById(R.id.woodGet); // Buttons for Ressources, the Player wants to get
    List<Button> getBtns = new ArrayList<>(); // List of all Get-Buttons
    StringBuilder res = new StringBuilder(); // StringBuilder for the Trade-Offer
    Handler handler = new PortChangeHandler(Looper.getMainLooper(), this); // Handler

    /**
     * Adds all Buttons to their List and enables them, when the
     * Player has less than 3 of the correspondending Ressource or no Port.
     * Specifies the Handler in ClientData for the current Activity.
     *
     * @param savedInstanceState saved State
     */
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

        if (player.getInventory().isWoodport() || player.getInventory().getWood() < 3) {
            woodGive.setEnabled(false);
        }

        if (player.getInventory().isWoolport() || player.getInventory().getWool() < 3) {
            woolGive.setEnabled(false);
        }

        if (player.getInventory().isWheatport() || player.getInventory().getWheat() < 3) {
            wheatGive.setEnabled(false);
        }

        if (player.getInventory().isOreport() || player.getInventory().getOre() < 3) {
            oreGive.setEnabled(false);
        }

        if (player.getInventory().isClayport() || player.getInventory().getClay() < 3) {
            clayGet.setEnabled(false);
        }

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
                res.append(btn.getText().toString()).append("/");
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
        logger.log(Level.INFO, "CREATE PORT CHANGE");
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryPortChange(toSendTrade));
        networkThread.start();
    }

    /**
     * @author Christina Senger
     *
     * Handler for the PortChangeActivity
     */
    private class PortChangeHandler extends HandlerOverride {

        PortChangeHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Called from ServerCommunicationThread. When a String was send, it is set
         * as Extra of the Intent. When a GameSession was send, the Trade was
         * carried out and the MainActivity is started.
         *
         * @param msg msg.arg1 has the Param for further Actions
         *            msg.obj holds the Object send from the Server
         */
        @Override
        public void handleMessage(Message msg) {

            Intent intent = new Intent(activity, MainActivity.class);

            if (msg.arg1 == 4) {  // TODO: Change to enums

                startActivity(intent);
            }
            if (msg.arg1 == 5) {  // TODO: Change to enums

                intent.putExtra("mess", msg.obj.toString());
            }
        }
    }

}
