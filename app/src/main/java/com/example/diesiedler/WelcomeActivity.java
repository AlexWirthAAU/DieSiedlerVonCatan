package com.example.diesiedler;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.ServerCommunicationThread;
import com.example.diesiedler.threads.StartServerCommunicationThread;

import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Startaktivität, die zum Spielen einlädt und die Serververbindung startet.
 */
public class WelcomeActivity extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger(WelcomeActivity.class.getName());
    private Handler handler;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        handler = new WelcomeHandler(Looper.getMainLooper(), this);
    }

    /**
     * Wird auf den Button mit dem Text "Entdecke die Welt von Catan geklickt,
     * wird der Thread zur Serververbindung ausgeführt.
     *
     *  @param view View, um den Button anzusprechen
     */
    public void discover(View view) {
        Thread startServerCommunicationThread = new StartServerCommunicationThread(handler);
        startServerCommunicationThread.start();
    }

    private class WelcomeHandler extends HandlerOverride {

        public WelcomeHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Handler erhält Message vom StartServerCommunicationThread. Bei erfolgreichem
         * Verbindungsaufbau wird der Listener Thread gestartet und die Login Activität aufgerufen.
         *
         * @param msg msg.arg1 beinhaltet den entsprechenden Parameter zur weiteren Ausführung
         */
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1) {  // TODO: Change to enums
                ClientData.SERVER_COMMUNICATION_THREAD = new ServerCommunicationThread();
                ClientData.SERVER_COMMUNICATION_THREAD.start();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}