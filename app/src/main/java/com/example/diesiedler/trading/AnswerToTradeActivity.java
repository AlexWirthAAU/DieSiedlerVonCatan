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

import com.example.catangame.GameSession;
import com.example.diesiedler.ChooseActionActivity;
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;

import java.util.logging.Level;
import java.util.logging.Logger;

//Geladen, wenn eine Trade Message vom Server kommt

/**
 * TODO: Implement server answer
 */
public class AnswerToTradeActivity extends AppCompatActivity {

    private GameSession game;
    private static final Logger logger = Logger.getLogger(AnswerToTradeActivity.class.getName());
    Handler handler = new AnswerToTradeHandler(Looper.getMainLooper(), this);
    private String toSendAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_to_trade);

        game = ClientData.currentGame;
        String tradeMessage = getIntent().getStringExtra("mess");
        TextView tradeMes = findViewById(R.id.tradeMes);
        tradeMes.setText(tradeMessage);

        ClientData.currentHandler = handler;
    }

    public void dismiss(View view) {

        toSendAnswer = "dismissed";
        logger.log(Level.INFO, "CREATE ANSWER NO");
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryTrade(toSendAnswer));
        networkThread.start();
    }

    public void accept(View view) {

        toSendAnswer = "accepted";
        logger.log(Level.INFO, "CREATE ANSWER YES");
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryTrade(toSendAnswer));
        networkThread.start();
    }

    private class AnswerToTradeHandler extends HandlerOverride {

        AnswerToTradeHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Wird vom ServerCommunicationThread aufgerufen. Im Falle einer Stringübertragung wird
         * die Nachricht als String dem Intent übergeben, sollte eine GameSession übertragen
         * werden, so ist der Handel abgeschlossen. Ist man an der Reihe wird die ChooseActionActivity
         * aufgerufen. Ansonsten wird die MainActivity aufgerufen.
         *
         * @param msg msg.arg1 beinhaltet den entsprechenden Parameter zur weiteren Ausführung
         */
        @Override
        public void handleMessage(Message msg) {

            Intent intentMain = new Intent(activity, MainActivity.class);
            Intent intentSelect = new Intent(activity, ChooseActionActivity.class);

            if (msg.arg1 == 4) {  // TODO: Change to enums

                ClientData.currentGame = (GameSession) msg.obj;

                if (ClientData.currentGame.getCurr().getUserId() == ClientData.userId) {
                    startActivity(intentSelect);
                } else {
                    startActivity(intentMain);
                }
            }
            if (msg.arg1 == 5) {  // TODO: Change to enums

                intentMain.putExtra("mess", msg.obj.toString());
                intentSelect.putExtra("mess", msg.obj.toString());
            }
        }
    }
}
