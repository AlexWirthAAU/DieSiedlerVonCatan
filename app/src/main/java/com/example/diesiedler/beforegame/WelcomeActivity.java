package com.example.diesiedler.beforegame;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.threads.ServerCommunicationThread;
import com.example.diesiedler.threads.StartServerCommunicationThread;

import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Startactivity, which invites to play and startes the Server-Connection.
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(WelcomeActivity.class.getName()); // Logger
    private Handler handler; // Handler

    /**
     * The Handler in ClientData is set for the current Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        handler = new WelcomeHandler(Looper.getMainLooper(), this);
    }

    /**
     * Going back is not possible here.
     */
    @Override
    public void onBackPressed() {
    }

    /**
     * When the Button with the Text "Entdecke die Welt von Catan" is clicked,
     * the Thread with the Server-Connection is started.
     *
     *  @param view View, to access the Button
     */
    public void discover(View view) {
        Thread startServerCommunicationThread = new StartServerCommunicationThread(handler);
        startServerCommunicationThread.start();
    }

    /**
     * @author Fabian Schaffenrath (edit)
     * <p>
     * Handler for the WelcomeActivity
     */
    private class WelcomeHandler extends HandlerOverride {

        WelcomeHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Handler gets a Message from StartServerCommunicationThread. On successful Connection,
         * the ListenerThread and the LoginActivity is started.
         *
         * @param msg msg.arg1 has the Param for further actions
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