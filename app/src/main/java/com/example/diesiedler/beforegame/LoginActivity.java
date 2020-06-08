package com.example.diesiedler.beforegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.handler.PreGameHandler;
import com.example.diesiedler.threads.NetworkThread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Activity where the User can put in his Username
 */
public class LoginActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(LoginActivity.class.getName());

    Handler loginHandler = new LoginHandler(Looper.getMainLooper(), this); // Handler
    private EditText displayName; // Input Field for the Username

    /**
     * The Handler in ClientData is set for the current Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        displayName = findViewById(R.id.displayName);
        ClientData.currentHandler = loginHandler;
    }

    /**
     * When the User clicks the Button, the NetworkThread is started,
     * which sends the Name to the Server.
     *
     * @param view View, to read from TextField
     */
    public void setName(View view) {

        String username = displayName.getText().toString();
        logger.log(Level.INFO, username);
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryLogin(username));
        networkThread.start();
    }

    @Override
    public void onBackPressed() {
        // Back button should not return to the previous Activity here.
    }

    /**
     * @author Fabian Schaffenrath (edit)
     * <p>
     * Handler for the LoginActivity
     */
    private class LoginHandler extends PreGameHandler {

        LoginHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * When the Login was succesfull the SearchPlayersActivity is started
         *
         * @param msg msg.arg1 has the Param for further Actions
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
