package com.example.diesiedler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.NetworkThread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Aktivität in welcher der User seinen Username eingeben kann.
 */
public class LoginActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(LoginActivity.class.getName());

    private EditText displayName; // Eingabetextfeld für den Username
    public Handler loginHandler = new LoginHandler(Looper.getMainLooper(), this);

    /**
     * Der Handler wird in der ClientData für die jetzige Aktivität angepasst.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        displayName = findViewById(R.id.displayName);
        ClientData.currentHandler = loginHandler;
    }

    /**
     * Klickt der User den Button, so wird der NetworkThread gestartet,
     * der den eingegebenen Namen an den Server schickt.
     *
     * @param view View, um aus Textfeld zu lesen
     */
    public void setName(View view) {

        String username = displayName.getText().toString();
        logger.log(Level.INFO, username);
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryLogin(username));
        networkThread.start();
    }

    private class LoginHandler extends HandlerOverride {

        public LoginHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Übernimmt das starten der SearchPlayers Aktivität bei erfolgreicher Ausführung.
         *
         * @param msg msg.arg1 beinhaltet den entsprechenden Parameter zur weiteren Ausführung
         */
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 2) {  // TODO: Change to enums
                ClientData.userDisplayName = ((LoginActivity) activity).displayName.getText().toString();
                Intent intent = new Intent(activity, SearchPlayersActivity.class);
                startActivity(intent);
            }
        }
    }
}
