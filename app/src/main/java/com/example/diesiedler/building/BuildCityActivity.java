package com.example.diesiedler.building;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Knot;
import com.example.diesiedler.ChooseActionActivity;
import com.example.diesiedler.MainActivity;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateBuildCityView;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.example.diesiedler.threads.NetworkThread;
import com.richpath.RichPathView;

/**
 * @author Alex Wirth
 * <p>
 * This Activity should allow the User to click the Knot he wants to build on.
 * The clicked Asset's ID will be sent to the Server (PresenterBuild) where it is checked whether user is allowed to build or not.
 * If yes, this Asset will be colored in User's Color.
 * If not, User has to click another Asset.
 */
public class BuildCityActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler = new BuildCityHandler(Looper.getMainLooper(), this); // Handler

    private AlertDialog.Builder alertBuilder; // AlertBuilder

    private TextView woodCount; // TextViews for number of Ressources
    private TextView clayCount;
    private TextView wheatCount;
    private TextView oreCount;
    private TextView woolCount;

    private Button devCards; // Buttons to show Score and Inventory
    private Button scoreBoard;

    // TODO: Methoden kommentieren
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        devCards = findViewById(R.id.devCards);
        devCards.setOnClickListener(this);
        scoreBoard = findViewById(R.id.scoreBoard);
        scoreBoard.setOnClickListener(this);

        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();
        int status = UpdateBuildCityView.updateView(ClientData.currentGame, richPathView);
        ClientData.currentHandler = handler;

        if (status==0){
            alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Du kannst nicht bauen");
            alertBuilder.setMessage("Du hast nicht genügend Rohstoffe!");
            alertBuilder.setCancelable(true);
            alertBuilder.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getBaseContext(), ChooseActionActivity.class);
                            startActivity(intent);
                        }
                    });
            alertBuilder.create();
            alertBuilder.show();

        } else {
            GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
            gameBoardClickListener.clickBoard("BuildCity");
        }
    }

    public void clicked(String s) {
        Knot[] knots = ClientData.currentGame.getGameboard().getKnots();
        int knotIndex = 0;
        String[] values = s.split("_");
        int row = Integer.parseInt(values[1]);
        int column = Integer.parseInt(values[2]);
        for (int i = 0; i < knots.length; i++) {
            if (knots[i].getRow() == row && knots[i].getColumn() == column) {
                knotIndex = i;
            }
        }
        String kIString = Integer.toString(knotIndex);

        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryBuildCity(kIString));
        networkThread.start();
    }

    private void updateResources() {
        PlayerInventory playerInventory = ClientData.currentGame.getPlayer(ClientData.userId).getInventory();

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
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.devCards:
                //TODO: load new activity
                break;
            case R.id.scoreBoard:
                //TODO: load new activity
                break;
        }
    }

    private class BuildCityHandler extends HandlerOverride {

        public BuildCityHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 4) {
                GameSession gameSession = ClientData.currentGame;
                Player currentP = gameSession.getPlayer(gameSession.getCurrPlayer());

                if (currentP.getUserId() != ClientData.userId) {
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}
