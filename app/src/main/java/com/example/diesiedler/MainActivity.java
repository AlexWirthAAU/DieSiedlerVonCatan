package com.example.diesiedler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Grab;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.diesiedler.cheating.CheatRevealActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.GameHandler;
import com.richpath.RichPathView;

import java.util.logging.Logger;

/**
 * @author Alex Wirth
 * @author Christina Senger (edit)
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Overview of Gameboard and Inventory
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Logger logger = Logger.getLogger(MainActivity.class.getName()); // Logger
    private Handler handler = new MainHandler(Looper.getMainLooper(), this); // Handler
    private RichPathView richPathView;

    private TextView woodCount; // Number of Ressources
    private TextView clayCount;
    private TextView wheatCount;
    private TextView oreCount;
    private TextView woolCount;
    private TextView devCardCount;

    private TextView currentPlayer; // View of the current Player

    private ImageView devCards; // Button to show Score and DevCards
    private Button scoreBoard;

    private ImageView grabView;
    private String grabberId;

    /**
     * Loads actual Gameboard and Ressources. Sets Handler.
     * If the Intent has an Extra, a Alert-Message with its Test is shown.
     *
     * @param savedInstanceState saved State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        richPathView = findViewById(R.id.ic_gameboardView);

        devCards = findViewById(R.id.devCard);
        devCards.setOnClickListener(this);
        scoreBoard = findViewById(R.id.scoreBoard);
        scoreBoard.setOnClickListener(this);

        // Cheating
        grabView = findViewById(R.id.grabActive);
        grabView.setVisibility(View.GONE);
        checkForGrab();

        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();

        ClientData.currentHandler = handler;

        String intentMess = getIntent().getStringExtra("mess");

        if (intentMess != null) {
            System.out.println(intentMess + " inentmessinMainin");
            alert(intentMess);
        }

        System.out.println(intentMess + " inentmessinMainout");
    }

    @Override
    public void onResume() {
        super.onResume();
        ClientData.currentHandler = handler;
        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();
        checkForGrab();
    }

    public void onRestart() {
        super.onRestart();
        ClientData.currentHandler = handler;
        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();
        checkForGrab();
    }

    /**
     * Going back is not possible here.
     * TODO: When choosing action i call this activity to give an overview -> i have to go back then to choose what todo
    @Override
    public void onBackPressed() {
    }
     */
    private void alert(String tradeMessage) {
        if (tradeMessage != null) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setCancelable(true);
            builder1.setMessage(tradeMessage);
            AlertDialog alert1 = builder1.create();
            alert1.show();
        }
    }

    private void updateResources() {
        PlayerInventory playerInventory = ClientData.currentGame.getPlayer(ClientData.userId).getInventory();
        Player currentP = ClientData.currentGame.getCurr();

        woodCount = findViewById(R.id.woodCount);
        woodCount.setText(Integer.toString(playerInventory.getWood()));
        clayCount = findViewById(R.id.clayCount);
        clayCount.setText(Integer.toString(playerInventory.getClay()));
        wheatCount = findViewById(R.id.wheatCount);
        wheatCount.setText(Integer.toString(playerInventory.getWheat()));
        oreCount = findViewById(R.id.oreCount);
        oreCount.setText(Integer.toString(playerInventory.getOre()));
        woolCount = findViewById(R.id.woolCount);
        woolCount.setText(Integer.toString(playerInventory.getWool()));
        currentPlayer = findViewById(R.id.currentPlayer);
        currentPlayer.setText(currentP.getDisplayName() + " ist gerade am Zug");
        devCardCount = findViewById(R.id.devCardCount);
        devCardCount.setText(Integer.toString(playerInventory.getCards()));

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.devCard:
                //TODO: load new activity
                break;
            case R.id.scoreBoard:
                intent = new Intent(getBaseContext(), ScoreBoardActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * If the user is the current player and someone is trying to steal from him, show the
     * hand grabbing image.
     */
    private void checkForGrab(){
        if(ClientData.currentGame.getCurr().getUserId() == ClientData.userId){
            for (Grab grab:ClientData.currentGame.getGrabs()) {
                if(grab.getGrabbed().getUserId() == ClientData.userId && (grab.getRevealed() == null || grab.getRevealed())){
                    grabberId = "" + grab.getGrabber().getUserId();
                    grabView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), CheatRevealActivity.class);
                            intent.putExtra("playerId","" + grabberId);
                            startActivity(intent);
                        }
                    });
                    grabView.setClickable(true);
                    grabView.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

    /**
     * @author Alex Wirth
     * @author Christina Senger (edit)
     * @author Fabian Schaffenrath (edit)
     *
     * Handler for the MainActivity
     */
    private class MainHandler extends GameHandler {

        MainHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Calls from ServerCommunicationThread. If a String is sent, it is processed by the
         * super handleMessage method. If a game session is sent, the view gets updated.
         *
         * @param msg msg.arg1 has the Param for further Actions
         *            msg.obj holds a Object sent from Server
         */
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 5){
                super.handleMessage(msg);
            }
            else if(msg.arg1 == 4){
                UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
                updateResources();
                checkForGrab();
            }
        }
    }
}
