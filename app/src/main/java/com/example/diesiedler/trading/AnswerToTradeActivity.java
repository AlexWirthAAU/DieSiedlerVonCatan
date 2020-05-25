package com.example.diesiedler.trading;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Player;
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.RollDiceActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 *
 * This Activity is loaded, when the MainActivity is on and the
 * Server sends a Trade-Offer. The Player can here accept or
 * dismiss the offer.
 */
public class AnswerToTradeActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(AnswerToTradeActivity.class.getName()); // Loogger

    Handler handler = new AnswerToTradeHandler(Looper.getMainLooper(), this); // Handler

    private String toSendAnswer; // Holder for Answer-String

    /**
     * Gets the Trade-Offer from the Intent and show it in the Textview.
     * Specifies the Handler in the Client Data for the current Activity.
     *
     * @param savedInstanceState saved State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_to_trade);

        String tradeMessage = getIntent().getStringExtra("mess");
        TextView tradeMes = findViewById(R.id.tradeMes);
        tradeMes.setText(tradeMessage);

        ClientData.currentHandler = handler;
    }

    /**
     * When the Player dismisses the Offer, the NetworkThread is started,
     * which sends the Answer-String to the Server.
     *
     * @param view
     */
    public void dismiss(View view) {

        toSendAnswer = "dismissed";
        logger.log(Level.INFO, "CREATE ANSWER NO");
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryTradeAnswer(toSendAnswer));
        networkThread.start();
    }

    /**
     * When the Player accepts the Offer, the NetworkThread is started,
     * which sends the Answer-String to the Server.
     *
     * @param view
     */
    public void accept(View view) {

        toSendAnswer = "accepted";
        logger.log(Level.INFO, "CREATE ANSWER YES");
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryTradeAnswer(toSendAnswer));
        networkThread.start();
    }

    /**
     * @author Christina Senger
     * <p>
     * Handler for the AnswerToTradeActivity
     */
    private class AnswerToTradeHandler extends HandlerOverride {

        private String mess;

        AnswerToTradeHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Called from ServerCommunicationThread. When a String was send, it is set
         * as Extra of the Intent. When a GameSession was send, the Trade was
         * carried out. It is checked, whether it is the Players Turn.
         * If yes, the ChooseActionActivity is started.
         * If not, the MainActivity is started.
         *
         * @param msg msg.arg1 has the Param for further Actions
         *            msg.obj holds the Object send from the Server
         */
        @Override
        public void handleMessage(Message msg) {

            Intent intentMain = new Intent(activity, MainActivity.class);
            Intent intentSelect = new Intent(activity, RollDiceActivity.class);

            if (msg.arg1 == 4) {  // TODO: Change to enums

                intentSelect.putExtra("mess", mess);
                intentMain.putExtra("mess", mess);
                System.out.println(mess + " objstart");

                Player currentPlayer = ClientData.currentGame.getPlayer(ClientData.currentGame.getCurrPlayer());

                if (currentPlayer.getUserId() == ClientData.userId) {
                    startActivity(intentSelect);
                } else {
                    startActivity(intentMain);
                }
            }
            if (msg.arg1 == 5) {  // TODO: Change to enums

                mess = msg.obj.toString();
                mess = msg.obj.toString();
            }
        }
    }
}
