package com.example.diesiedler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.diesiedler.building.BuildCityActivity;
import com.example.diesiedler.building.BuildRoadActivity;
import com.example.diesiedler.building.BuildSettlementActivity;
import com.example.diesiedler.cards.PlayCardActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateBuildCityView;
import com.example.diesiedler.presenter.UpdateBuildRoadView;
import com.example.diesiedler.presenter.UpdateBuildSettlementView;
import com.example.diesiedler.presenter.handler.GameHandler;
import com.example.diesiedler.threads.NetworkThread;
import com.example.diesiedler.trading.BankOrPortChangeActivity;
import com.example.diesiedler.trading.TradeActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Alex Wirth
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Activity in which the Player can choose his Action.
 */
public class ChooseActionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Logger logger = Logger.getLogger(ChooseActionActivity.class.getName()); // Logger
    Handler handler = new ChooseActionHandler(Looper.getMainLooper(), this); // Hanlder
    private Player player; // current Player and Game
    private GameSession game;

    /**
     * Buttons are specified and an OnClickListener is registered.
     * If the Intent has an Extra, a Alert-Message with its Test is shown.
     * The current Handler is set for the current Activity.
     *
     * @param savedInstanceState saved State
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseaction);

        // Action-Buttons
        Button buildSettlement = findViewById(R.id.buildSettlement);
        Button buildRoad = findViewById(R.id.buildRoad);
        Button buildCity = findViewById(R.id.buildCity);
        Button loadMain = findViewById(R.id.loadOverview);
        Button buyDevCard = findViewById(R.id.buyDevCard);
        Button playdevCard = findViewById(R.id.setDevCard);
        Button trade = findViewById(R.id.trade);
        Button exchangeBank = findViewById(R.id.exchangeBank);
        Button exchangePort = findViewById(R.id.exchangePort);
        Button showCosts = findViewById(R.id.showCosts);
        Button ahead = findViewById(R.id.ahead);

        buildSettlement.setOnClickListener(this);
        buildRoad.setOnClickListener(this);
        buildCity.setOnClickListener(this);
        loadMain.setOnClickListener(this);
        buyDevCard.setOnClickListener(this);
        playdevCard.setOnClickListener(this);
        trade.setOnClickListener(this);
        exchangeBank.setOnClickListener(this);
        exchangePort.setOnClickListener(this);
        showCosts.setOnClickListener(this);
        ahead.setOnClickListener(this);

        ClientData.currentHandler = handler;

        player = ClientData.currentGame.getPlayer(ClientData.userId);
        game = ClientData.currentGame;
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
     * Depending on what Button is clicked, the correspondending Activity is loaded.
     * When a Player cannot make a specific Action, an Error-Alert is shown.
     * When the Player wants to buy a DevCard, a NetworkThread is started, which
     * sends a BuyCard-Request to the Server.
     *
     * @param view View of the clicked Button
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        int status;

        String messNoResources = "Nicht genügend Rohstoffe.";

        switch (view.getId()) {
            case R.id.buildSettlement:
                status = UpdateBuildSettlementView.status(ClientData.currentGame);
                logger.log(Level.INFO, "Status is: " + status);
                if (status == -1) {
                    alert("Du kannst nicht bauen. Keine deiner Straßen führt zu einer bebaubaren Kreuzung!");
                } else if (status == 0) {
                    alert(messNoResources);
                } else {
                    intent = new Intent(getBaseContext(), BuildSettlementActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.buildRoad:
                status = UpdateBuildRoadView.status(ClientData.currentGame, null);
                if (status == 0) {
                    alert(messNoResources);
                } else {
                    intent = new Intent(getBaseContext(), BuildRoadActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.buildCity:
                status = UpdateBuildCityView.status(ClientData.currentGame);
                if (status == 0) {
                    alert(messNoResources);
                } else {
                    intent = new Intent(getBaseContext(), BuildCityActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.loadOverview:
                intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.exchangeBank:
                if (player.getInventory().isCanBankTrade()) {
                    intent = new Intent(getBaseContext(), BankOrPortChangeActivity.class);
                    intent.putExtra("kind", "bank");
                    startActivity(intent);
                } else {
                    alert(messNoResources);
                }
                break;
            case R.id.exchangePort:
                if (player.getInventory().isCanPortTrade() && player.getInventory().isHasPorts()) {
                    intent = new Intent(getBaseContext(), BankOrPortChangeActivity.class);
                    intent.putExtra("kind", "port");
                    startActivity(intent);
                } else if (!player.getInventory().isCanPortTrade() && player.getInventory().isHasPorts()) {
                    alert(messNoResources);
                } else if (player.getInventory().isCanPortTrade() && !player.getInventory().isHasPorts()) {
                    alert("Du besitzt keine Häfen");
                } else {
                    alert(messNoResources + " Du besitzt keine Häfen.");
                }
                break;
            case R.id.trade:
                if (player.getInventory().isCanTrade()) {
                    intent = new Intent(getBaseContext(), TradeActivity.class);
                    startActivity(intent);
                } else {
                    alert(messNoResources);
                }
                break;
            case R.id.setDevCard:
                if (player.getInventory().getCards() > 0) {
                    intent = new Intent(getBaseContext(), PlayCardActivity.class);
                    startActivity(intent);
                } else {
                    alert("Du hast keine Entwicklungskarten");
                }
                break;
            case R.id.buyDevCard:
                boolean res = player.getInventory().getWool() > 0 && player.getInventory().getWheat() > 0 && player.getInventory().getOre() > 0;
                if (res && !game.getDevCards().isEmpty()) {
                    logger.log(Level.INFO, "BUY CARD");
                    Thread networkThread = new NetworkThread(ServerQueries.createStringQueryBuyCard());
                    networkThread.start();
                } else if (!res && !game.getDevCards().isEmpty()) {
                    alert(messNoResources);
                } else if (game.getDevCards().isEmpty() && res) {
                    alert("Es gibt keine Entwicklungskarten mehr");
                } else {
                    alert(messNoResources + " Es gibt keine Entwicklungskarten mehr.");
                }
                break;
            case R.id.showCosts:
                intent = new Intent(getBaseContext(), ShowCostsActivity.class);
                startActivity(intent);
                break;
            case R.id.ahead:
                logger.log(Level.INFO, "AHEAD");
                Thread networkThread = new NetworkThread(ServerQueries.createStringQueryNext());
                networkThread.start();
                break;
            default:
                break;
        }
    }

    /**
     * Makes an Alert-Message which the given String
     *
     * @param mes Message which should be shown
     */
    private void alert(String mes) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(true);
        builder1.setMessage(mes);
        AlertDialog alert1 = builder1.create();
        alert1.show();
    }

    /**
     * @author Christina Senger
     * @author Fabian Schaffenrath (edit)
     *
     * Handler for the ChooseActionActivity
     */
    private class ChooseActionHandler extends GameHandler {

        ChooseActionHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Called from ServerCommunicationThread. When a String is sent, it is
         * processed by the super handleMessage method.
         *
         * @param msg msg.arg1 has the Param for further Actions
         *            msg.obj holds an Object sent from Server
         */
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 5){
                super.handleMessage(msg);
            }
        }
    }
}
