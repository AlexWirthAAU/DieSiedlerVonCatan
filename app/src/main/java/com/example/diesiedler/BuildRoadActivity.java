package com.example.diesiedler;

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
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Edge;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateBuildRoadView;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.example.diesiedler.threads.NetworkThread;

import com.richpath.RichPathView;


public class BuildRoadActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * This activity should allow the user to click the edge he wants to build on
     * The clicked asset's ID will be sent to the Server that (PresenterBuild) where it is checked whether user is allowed to build or not
     * If yes -> this asset will be colored in user's color
     * If not -> User has to click another asset
     */

    private Handler handler = new BuildRoadHandler(Looper.getMainLooper(), this);
    private AlertDialog.Builder alertBuilder;
    private TextView woodCount;
    private TextView clayCount;
    private TextView wheatCount;
    private TextView oreCount;
    private TextView woolCount;
    private Button devCards;
    private Button scoreBoard;

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
        ClientData.currentHandler = handler;

        int status = UpdateBuildRoadView.updateView(ClientData.currentGame, richPathView);
        if (status == 0) {
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
            gameBoardClickListener.clickBoard("BuildRoad");
        }
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



    public void clicked(String s) {
        Edge[] edges = ClientData.currentGame.getGameboard().getEdges();
        int edgeIndex = 0;
        for (int i = 0; i < edges.length; i++) {
            if (edges[i].getId().equals(s)) {
                edgeIndex = i;
            }
        }
        String eIString = Integer.toString(edgeIndex);

        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryBuildRoad(eIString));
        networkThread.start();
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

    private class BuildRoadHandler extends HandlerOverride {
        public BuildRoadHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 4) {
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
            }
        }

    }

}
