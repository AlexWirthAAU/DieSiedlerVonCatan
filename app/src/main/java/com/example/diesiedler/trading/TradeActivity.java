package com.example.diesiedler.trading;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Player;
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Activity where the Player can set his Trade-Offer for the Opponents.
 */
public class TradeActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(TradeActivity.class.getName()); // Logger
    private final int STARTVALUE = 0; // Startvalue for desired and offered Ressources

    TextView countWoodGive;
    TextView countWoodGet;
    private int woolGive = STARTVALUE;
    private int woolGet = STARTVALUE;
    private int wheatGive = STARTVALUE;
    private int wheatGet = STARTVALUE;
    private int oreGive = STARTVALUE;
    private int oreGet = STARTVALUE;
    private int clayGive = STARTVALUE;
    private int clayGet = STARTVALUE;

    Handler handler = new TradeHandler(Looper.getMainLooper(), this); // Handler
    private int woodGet = STARTVALUE; // Values of desired and offered Ressources
    private int woodGive = STARTVALUE;
    TextView countWoolGive;
    TextView countWoolGet;
    TextView countWheatGive;
    TextView countWheatGet;
    TextView countOreGive;
    TextView countOreGet;
    TextView countClayGive;
    TextView countClayGet;

    private StringBuilder tradeMap = new StringBuilder(); // StringBuilder in which the Trade-Offer is stored
    private Player player; // current Player

    /**
     * Specifies the Handler in ClientData for the current Activity.
     *
     * @param savedInstanceState saved State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        countWoodGive = findViewById(R.id.countWoodGive);
        countWoodGet = findViewById(R.id.countWoodGet);
        countWoolGive = findViewById(R.id.countWoolGive);
        countWoolGet = findViewById(R.id.countWoolGet);
        countWheatGive = findViewById(R.id.countWheatGive);
        countWheatGet = findViewById(R.id.countWheatGet);
        countOreGive = findViewById(R.id.countOreGive);
        countOreGet = findViewById(R.id.countOreGet);
        countClayGive = findViewById(R.id.countClayGive);
        countClayGet = findViewById(R.id.countClayGet);

        player = ClientData.currentGame.getPlayer(ClientData.userId);

        ClientData.currentHandler = handler;
    }

    /**
     * Checks of the Player has more of one Ressource than the actual
     * Value of that to give, so that the Value can be increased.
     *
     * @param value current Value of a Ressource to give
     * @param res Name of the Ressource
     * @return true, when the Player has more of this Ressource, else false
     */
    private boolean checkRessources(int value, String res) {

        int availableRes = -1;

        switch (res) {
            case "Wood":
                availableRes = player.getInventory().getWood();
                break;
            case "Wool":
                availableRes = player.getInventory().getWool();
                break;
            case "Wheat":
                availableRes = player.getInventory().getWheat();
                break;
            case "Ore":
                availableRes = player.getInventory().getOre();
                break;
            case "Clay":
                availableRes = player.getInventory().getClay();
                break;
            default:
                break;
        }

        return availableRes > value;
    }

    /**
     * Shows an Alert-Message with the Info, that the Player
     * has not enough of a specific Ressource.
     *
     * @param res The Ressource the Player has not enough of as String
     */
    private void alert(String res) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(true);
        builder1.setMessage("Du hast nicht genug " + res);
        AlertDialog alert1 = builder1.create();
        alert1.show();
    }

    /**
     * When the Trade-Button is clicked, all Values are added
     * to the Trade-StringBuilder with their Names+Dividers.
     * The NetworkThread is started, which send the Trade as a
     * String to the Server.
     *
     * @param view View to access the Trade-Button
     */
    public void trade(View view) {

        tradeMap.append("Holz/").append(woodGive);
        tradeMap.append("/Wolle/").append(woolGive);
        tradeMap.append("/Weizen/").append(wheatGive);
        tradeMap.append("/Erz/").append(oreGive);
        tradeMap.append("/Lehm/").append(clayGive);
        tradeMap.append("/splitter/").append(-1);
        tradeMap.append("/Holz/").append(woodGet);
        tradeMap.append("/Wolle/").append(woolGet);
        tradeMap.append("/Weizen/").append(wheatGet);
        tradeMap.append("/Erz/").append(oreGet);
        tradeMap.append("/Lehm/").append(clayGet);

        String toSendTrade = tradeMap.toString();
        logger.log(Level.INFO, "CREATE TRADE");
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryTrade(toSendTrade));
        networkThread.start();
    }

    /**
     * When a Plus-Button is clicked, and the Check was successful,
     * the Ressource-Value is increased and the TextView is updated.
     * Else, an Alert-Message is shown, that the Player doesnt have enough Ressources.
     *
     * @param view View to access the Plus-Buttons
     */
    public void plus(View view) {

        switch (view.getId()) {

            case R.id.plusWoodGive:
                if (checkRessources(woodGive, "Wood")) {
                    woodGive += 1;
                    countWoodGive.setText(String.valueOf(woodGive));
                } else {
                    alert("Holz");
                }
                break;

            case R.id.plusWoolGive:
                if (checkRessources(woolGive, "Wool")) {
                    woolGive += 1;
                    countWoolGive.setText(String.valueOf(woolGive));
                } else {
                    alert("Wolle");
                }
                break;

            case R.id.plusWheatGive:
                if (checkRessources(wheatGive, "Wheat")) {
                    wheatGive += 1;
                    countWheatGive.setText(String.valueOf(wheatGive));
                } else {
                    alert("Weizen");
                }
                break;

            case R.id.plusOreGive:
                if (checkRessources(oreGive, "Ore")) {
                    oreGive += 1;
                    countOreGive.setText(String.valueOf(oreGive));
                } else {
                    alert("Erz");
                }
                break;

            case R.id.plusClayGive:
                if (checkRessources(clayGive, "Clay")) {
                    clayGive += 1;
                    countClayGive.setText(String.valueOf(clayGive));
                } else {
                    alert("Lehm");
                }
                break;
            default:
                break;
        }

        switch (view.getId()) {

            case R.id.plusWoodGet:
                woodGet += 1;
                countWoodGet.setText(String.valueOf(woodGet));
                break;

            case R.id.plusWoolGet:
                woolGet += 1;
                countWoolGet.setText(String.valueOf(woolGet));
                break;

            case R.id.plusWheatGet:
                wheatGet += 1;
                countWheatGet.setText(String.valueOf(wheatGet));
                break;

            case R.id.plusOreGet:
                oreGet += 1;
                countOreGet.setText(String.valueOf(oreGet));
                break;

            case R.id.plusClayGet:
                clayGet += 1;
                countClayGet.setText(String.valueOf(clayGet));
                break;

            default:
                break;
        }
    }

    /**
     * When a Minus-Button is clicked, the Ressource-Value is reduced
     * and the TextView is updated. When the Value already is 0, nothing happens.
     *
     * @param view View to access the Minus-Buttons
     */
    public void minus(View view) {

        switch (view.getId()) {

            case R.id.minusWoodGive:
                if (woodGive >= 1) {
                    woodGive -= 1;
                    countWoodGive.setText(String.valueOf(woodGive));
                }
                break;

            case R.id.minusWoolGive:
                if (woolGive >= 1) {
                    woolGive -= 1;
                    countWoolGive.setText(String.valueOf(woolGive));
                }
                break;

            case R.id.minusWheatGive:
                if (wheatGive >= 1) {
                    wheatGive -= 1;
                    countWheatGive.setText(String.valueOf(wheatGive));
                }
                break;

            case R.id.minusOreGive:
                if (oreGive >= 1) {
                    oreGive -= 1;
                    countOreGive.setText(String.valueOf(oreGive));
                }
                break;

            case R.id.minusClayGive:
                if (clayGive >= 1) {
                    clayGive -= 1;
                    countClayGive.setText(String.valueOf(clayGive));
                }
                break;

            default:
                break;
        }

        switch (view.getId()) {

            case R.id.minusWoodGet:
                if (woodGet >= 1) {
                    woodGet -= 1;
                    countWoodGet.setText(String.valueOf(woodGet));
                }
                break;

            case R.id.minusWoolGet:
                if (woolGet >= 1) {
                    woolGet -= 1;
                    countWoolGet.setText(String.valueOf(woolGet));
                }
                break;

            case R.id.minusWheatGet:
                if (wheatGet >= 1) {
                    wheatGet -= 1;
                    countWheatGet.setText(String.valueOf(wheatGet));
                }
                break;

            case R.id.minusOreGet:
                if (oreGet >= 1) {
                    oreGet -= 1;
                    countOreGet.setText(String.valueOf(oreGet));
                }
                break;

            case R.id.minusClayGet:
                if (clayGet >= 1) {
                    clayGet -= 1;
                    countClayGet.setText(String.valueOf(clayGet));
                }
                break;

            default:
                break;
        }
    }

    /**
     * @author Christina Senger
     *
     * Handler for the TradeActivity
     */
    private class TradeHandler extends HandlerOverride {

        private String mess;

        TradeHandler(Looper mainLooper, Activity ac) {
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

                intent.putExtra("mess", mess);
                System.out.println(mess + " objstart");
                startActivity(intent);
            }
            if (msg.arg1 == 5) {  // TODO: Change to enums

                mess = msg.obj.toString();
            }
        }
    }
}
