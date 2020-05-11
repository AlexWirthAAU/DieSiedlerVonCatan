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
import com.example.diesiedler.cards.PlayCardActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;
import com.example.diesiedler.trading.BankChangeActivity;
import com.example.diesiedler.trading.PortChangeActivity;
import com.example.diesiedler.trading.TradeActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

//Geladen, wenn Spieler am Zug ist
public class SelectActionActivity extends AppCompatActivity {

    Button tradeBtn = findViewById(R.id.trade);
    Button bankBtn = findViewById(R.id.bank);
    Button portBtn = findViewById(R.id.port);
    Button buyCardBtn = findViewById(R.id.buyCard);

    private GameSession game;
    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());
    Handler handler = new SelectActionHandler(Looper.getMainLooper(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);

        game = ClientData.currentGame;

        Player player = game.getCurr();

        if (!player.getInventory().canTrade) {
            tradeBtn.setEnabled(false);
        }

        if (!player.getInventory().canBankTrade) {
            bankBtn.setEnabled(false);
        }

        if (player.getInventory().canPortTrade) {
            portBtn.setEnabled(false);
        }

        if (player.getInventory().getWool() < 1
                || player.getInventory().getOre() < 1
                || player.getInventory().getWheat() < 1
                || game.getDevCards().size() == 0) {
            buyCardBtn.setEnabled(false);
        }

        // Nach einem Handel wird die Erfolgsmeldung angezeigt
        String tradeMessage = getIntent().getStringExtra("mess");

        if (tradeMessage != null) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setCancelable(true);
            builder1.setMessage(tradeMessage);
            AlertDialog alert1 = builder1.create();
            alert1.show();
        }

        ClientData.currentHandler = handler;
    }

    public void trade(View view) {
        Intent intent = new Intent(this, TradeActivity.class);
        startActivity(intent);
    }

    public void bankChange(View view) {
        Intent intent = new Intent(this, BankChangeActivity.class);
        startActivity(intent);
    }

    public void portChange(View view) {
        Intent intent = new Intent(this, PortChangeActivity.class);
        startActivity(intent);
    }

    public void showCosts(View view) {
        Intent intent = new Intent(this, ShowCostsActivity.class);
        startActivity(intent);
    }

    public void buyCard(View view) {
        logger.log(Level.INFO, "BUY CARD");
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryBuyCard());
        networkThread.start();
    }

    public void playCard(View view) {
        Intent intent = new Intent(this, PlayCardActivity.class);
        startActivity(intent);
    }

    public void ahead(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    private class SelectActionHandler extends HandlerOverride {

        SelectActionHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Wird vom ServerCommunicationThread aufgerufen. Im Falle einer Stringübertragung wird
         * die Nachricht als String dem Intent übergeben, sollte eine GameSession übertragen
         * werden, so wurde der Kauf der Karte durchgeführt und die MainActivity wird aufgerufen.
         *
         * @param msg msg.arg1 beinhaltet den entsprechenden Parameter zur weiteren Ausführung
         */
        @Override
        public void handleMessage(Message msg) {

            Intent intent = new Intent(activity, MainActivity.class);

            if (msg.arg1 == 4) {  // TODO: Change to enums

                ClientData.currentGame = (GameSession) msg.obj;
                startActivity(intent);
            }

            if (msg.arg1 == 5) {  // TODO: Change to enums

                intent.putExtra("mess", msg.obj.toString());
            }
        }
    }
}
