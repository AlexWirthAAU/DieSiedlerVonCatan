package com.example.diesiedler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Colors;
import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Knot;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.UpdateBuildSettlementView;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.handler.HandlerOverride;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.example.diesiedler.threads.NetworkThread;
import com.richpath.RichPathView;

public class BuildSettlementActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * This activity should allow the user to click the knot he wants to build on
     * The clicked asset's ID will be sent to the Server that (PresenterBuild) where it is checked whether user is allowed to build or not
     * If yes -> this asset will be colored in user's color
     * If not -> User has to click another asset
     */

    private Handler handler = new BuildSettlementHandler(Looper.getMainLooper(), this);
    private TextView woodCount;
    private TextView clayCount;
    private TextView wheatCount;
    private TextView oreCount;
    private TextView woolCount;
    private Button devCards;
    private Button scoreBoard;
    private AlertDialog.Builder alertBuilder;


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
        int status = UpdateBuildSettlementView.updateView(ClientData.currentGame, richPathView);
        ClientData.currentHandler = handler;

        if (status == -1) {
            alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Du kannst nicht bauen");
            alertBuilder.setMessage("Keine deiner Straßen führt zu einer bebaubaren Kreuzung!");
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
        } else if (status == 0) {
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

        } else if (status == 1) {
            GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
            gameBoardClickListener.clickBoard("BuildSettlement");
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

        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryBuildSettlement(kIString));
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


    private class BuildSettlementHandler extends HandlerOverride {

        public BuildSettlementHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 4) {
                GameSession gameSession = ClientData.currentGame;
                Player currentP = gameSession.getPlayer(gameSession.getCurrPlayer());

                if (currentP.getUserId() == ClientData.userId && gameSession.getPlayer(ClientData.userId).getInventory().getRoads().size() < 2) {
                    Intent intent = new Intent(activity, BuildRoadActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }























    private GameSession createGameSession() {
        GameSession gameSession = new GameSession();
        Player p = new Player("Alex", 1);
        //Player p2 = new Player("Hans", 2);
        //p2.setColor(Colors.LIGHTBLUE);
        p.getInventory().addWood(5);
        p.getInventory().addClay(5);
        p.getInventory().addWheat(5);
        p.getInventory().addWool(5);
        p.setColor(Colors.GREEN);
        gameSession.setPlayer(p);

        Knot k1 = gameSession.getGameboard().getKnots()[10];
        Knot k2 = gameSession.getGameboard().getKnots()[22];
        Knot k3 = gameSession.getGameboard().getKnots()[1];

        Edge e1 = gameSession.getGameboard().getEdges()[7];
        Edge e2 = gameSession.getGameboard().getEdges()[1];

        k1.setPlayer(p);
        k2.setPlayer(p);
        //k3.setPlayer(p2);
        e1.setPlayer(p);
        e2.setPlayer(p);
        p.getInventory().addRoad(e1);
        p.getInventory().addRoadKnots(e1.getOne());
        p.getInventory().addRoadKnots(e1.getTwo());
        p.getInventory().addRoad(e2);
        p.getInventory().addRoadKnots(e2.getOne());
        p.getInventory().addRoadKnots(e2.getTwo());
        p.getInventory().addSettlement(k1);
        p.getInventory().addSettlement(k2);

        gameSession.addSettlement(k1);
        gameSession.addSettlement(k2);
        gameSession.addRoad(e1);
        gameSession.addRoad(e2);
        //gameSession.addSettlement(k3);

        return gameSession;
    }
}
