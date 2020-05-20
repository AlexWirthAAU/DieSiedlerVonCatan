package com.example.diesiedler;

import android.app.Activity;
import android.content.DialogInterface;
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
import com.example.diesiedler.presenter.handler.HandlerOverride;

import com.example.diesiedler.threads.NetworkThread;
import com.example.diesiedler.trading.BankChangeActivity;
import com.example.diesiedler.trading.PortChangeActivity;
import com.example.diesiedler.trading.TradeActivity;
import com.richpath.RichPathView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Alex Wirth
 * <p>
 * Activity in which the Player can choose his Action.
 */
public class ChooseActionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Logger logger = Logger.getLogger(MainActivity.class.getName()); // Logger
    private Button buildRoad;
    private Button loadMain;
    private Button buildCity;
    private Button buyDevCard;
    private Button playdevCard;
    private Button trade;
    private Button exchangeBank;
    private Button exchangePort;
    private Button showCosts;
    private Button ahead;
    Handler handler = new ChooseActionHandler(Looper.getMainLooper(), this); // Hanlder
    private Button buildSettlement; // Action-Buttons
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

        buildSettlement = findViewById(R.id.buildSettlement);
        buildRoad = findViewById(R.id.buildRoad);
        buildCity = findViewById(R.id.buildCity);
        loadMain = findViewById(R.id.loadOverview);
        buyDevCard = findViewById(R.id.buyDevCard);
        playdevCard = findViewById(R.id.setDevCard);
        trade = findViewById(R.id.trade);
        exchangeBank = findViewById(R.id.exchangeBank);
        exchangePort = findViewById(R.id.exchangePort);
        showCosts = findViewById(R.id.showCosts);
        ahead = findViewById(R.id.ahead);

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

        String tradeMessage = getIntent().getStringExtra("mess");

        if (tradeMessage != null) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setCancelable(true);
            builder1.setMessage(tradeMessage);
            AlertDialog alert1 = builder1.create();
            alert1.show();
        }

        ClientData.currentHandler = handler;

        player = ClientData.currentGame.getPlayer(ClientData.userId);
        game = ClientData.currentGame;
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

        switch (view.getId()) {
            case R.id.buildSettlement:
                status = UpdateBuildSettlementView.status(ClientData.currentGame);
                logger.log(Level.INFO, "Status is: " + status);
                if (status == -1) {
                    alert("Du kannst nicht bauen. Keine deiner Straßen führt zu einer bebaubaren Kreuzung!");
                } else if (status == 0) {
                    alert("Du kannst nicht bauen. Du hast nicht genügend Rohstoffe!");
                } else {
                    intent = new Intent(getBaseContext(), BuildSettlementActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.buildRoad:
                status = UpdateBuildRoadView.status(ClientData.currentGame, null);
                if (status == 0) {
                    alert("Du kannst nicht bauen. Du hast nicht genügend Rohstoffe!");
                } else {
                    intent = new Intent(getBaseContext(), BuildRoadActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.buildCity:
                status = UpdateBuildCityView.status(ClientData.currentGame);
                if (status == 0) {
                    alert("Du kannst nicht bauen. Du hast nicht genügend Rohstoffe!");
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
                if (player.getInventory().canBankTrade) {
                    intent = new Intent(getBaseContext(), BankChangeActivity.class);
                    startActivity(intent);
                } else {
                    alert("Nicht genug Rohstoffe um zu tauschen");
                }
                break;
            case R.id.exchangePort:
                if (player.getInventory().canPortTrade && player.getInventory().hasPorts) {
                    intent = new Intent(getBaseContext(), PortChangeActivity.class);
                    startActivity(intent);
                } else if (!player.getInventory().canPortTrade && player.getInventory().hasPorts) {
                    alert("Nicht genug Rohstoffe um zu tauschen");
                } else if (player.getInventory().canPortTrade && !player.getInventory().hasPorts) {
                    alert("Du hast keine Häfen");
                } else {
                    alert("Nicht genug Rohstoffe und keine Häfen um zu tauschen");
                }
                break;
            case R.id.trade:
                if (player.getInventory().canTrade) {
                    intent = new Intent(getBaseContext(), TradeActivity.class);
                    startActivity(intent);
                } else {
                    alert("Nicht genug Rohstoffe um zu handeln");
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
                if (res && game.getDevCards().size() > 0) {
                    logger.log(Level.INFO, "BUY CARD");
                    Thread networkThread = new NetworkThread(ServerQueries.createStringQueryBuyCard());
                    networkThread.start();
                } else if (!res && game.getDevCards().size() > 0) {
                    alert("Nicht genug Rohstoffe um Entwicklungskarten zu kaufen");
                } else if (game.getDevCards().size() == 0 && res) {
                    alert("Es gibt keine Entwicklungskarten mehr");
                } else {
                    alert("Du hast nicht genug Rohstoffe und es gibt auch keine Entwicklungskarten mehr");
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
     *
     * Handler for the ChooseActionActivity
     */
    private class ChooseActionHandler extends HandlerOverride {

        String mess;
        ChooseActionHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Called from ServerCommunicationThread. When a String is send,
         * it is set as Extra of the Intent. If a GameSession was send,
         * a card was bought and the MainActivity is loaded.
         *
         * @param msg msg.arg1 has the Param for further Actions
         *            msg.obj holds an Object send from Server
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
