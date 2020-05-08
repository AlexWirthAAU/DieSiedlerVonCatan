package com.example.diesiedler;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Colors;
import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Gameboard;
import com.example.catangame.gameboard.Knot;
import com.example.diesiedler.presenter.GameBoardClickListener;
import com.example.diesiedler.presenter.PresenterBuild;
import com.example.diesiedler.presenter.UpdateBuildRoadView;
import com.example.diesiedler.presenter.UpdateBuildSettlementView;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.richpath.RichPathView;

import java.util.concurrent.ExecutionException;

public class BuildSettlementActivity extends AppCompatActivity {

    /**
     * This activity should allow the user to click the knot he wants to build on
     * The clicked asset's ID will be sent to the Server that (PresenterBuild) where it is checked whether user is allowed to build or not
     * If yes -> this asset will be colored in user's color
     * If not -> User has to click another asset
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);


        GameSession gs = createGameSession();
        UpdateGameboardView.updateView(gs, richPathView);
        UpdateBuildSettlementView.updateView(gs, richPathView);
        /*int status = UpdateBuildSettlementView.updateView();

        if (statue==0){
            CANT BUILD MESSAGE
        } else {
            CHOOSE ONE OF RED KNOTS & SEND KNOT-INDEX TO SERVER
            THEN LOAD MAIN - ACTIVITY (OVERVIEW)
        }
         */

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard("BuildSettlement");
    }

    public void clicked(String s) throws ExecutionException, InterruptedException {
        //TODO: SEND Knot-Index to Server and load Overview Activity
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
