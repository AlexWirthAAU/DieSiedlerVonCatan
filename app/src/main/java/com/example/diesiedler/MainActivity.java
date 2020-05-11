package com.example.diesiedler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.richpath.RichPathView;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private GameSession game;
    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());
    Handler handler = new MainHandler(Looper.getMainLooper(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard();

        // Nach einem Bank- oder Hafentausch oder Entwicklungskartenkauf wird die Erfolgsmeldung angezeigt
        String tradeMessage = getIntent().getStringExtra("mess");

        if (tradeMessage != null) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setCancelable(true);
            builder1.setMessage(tradeMessage);
            AlertDialog alert1 = builder1.create();
            alert1.show();
        }

        game = ClientData.currentGame;
        ClientData.currentHandler = handler;
    }

    public void clicked(String s) {

    }

    private class MainHandler extends HandlerOverride {

        MainHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Wird vom ServerCommunicationThread aufgerufen. Im Falle einer Stringübertragung wird
         * die Nachricht dem Intent als Extra übergeben und die AnswerToTradeActivity wird gestartet
         * sollte eine GameSession übertragen werden, so wird überprüft, ob man an der Reihe ist
         * und ggf. die SelectActionActivity wird aufgerufen.
         *
         * @param msg msg.arg1 beinhaltet den entsprechenden Parameter zur weiteren Ausführung
         */
        @Override
        public void handleMessage(Message msg) {

            if (msg.arg1 == 4) {  // TODO: Change to enums

                ClientData.currentGame = (GameSession) msg.obj;
                // TODO: Gameboard Update

                if (game.getCurr().getUserId() == ClientData.userId) {
                    Intent intent = new Intent(activity, SelectActionActivity.class);
                    startActivity(intent);
                }
            }
            if (msg.arg1 == 5) {  // TODO: Change to enums

                Intent intent = new Intent(activity, AnswerToTradeActivity.class);
                intent.putExtra("mess", msg.obj.toString());
                startActivity(intent);
            }
        }
    }
}
