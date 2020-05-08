package com.example.diesiedler;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Colors;
import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Gameboard;
import com.example.catangame.gameboard.Knot;
import com.example.catangame.gameboard.Tile;
import com.example.diesiedler.presenter.GameBoardClickListener;
import com.example.diesiedler.presenter.PresenterBuild;
import com.example.diesiedler.presenter.UpdateBuildCityView;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.richpath.RichPath;
import com.richpath.RichPathView;

import java.util.concurrent.ExecutionException;

public class BuildCityActivity extends AppCompatActivity {
    /**
     * This activity should allow the user to click the knot he wants to build on
     * The clicked asset's ID will be sent to the Server (PresenterBuild) where it is checked whether user is allowed to build or not
     * If yes -> this asset will be colored in user's color
     * If not -> User has to click another asset
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        /**
         * Tested - works
         */
        GameSession gameSession = createTestSession();
        int status = UpdateBuildCityView.updateView(gameSession, richPathView);
        Log.d("DEBUG", "Status is: " + status);
        /*
        int status = UpdateBuildCityView.updateView();

        if (status==0){
            CANT BUILD MESSAGE
        } else {
            CHOOSE ONE OF RED KNOTS & SEND KNOT-INDEX TO SERVER
            THEN LOAD MAIN - ACTIVITY (OVERVIEW)
        }
         */

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard("BuildCity");
    }

    public void clicked(String s) throws ExecutionException, InterruptedException {
        //TODO: SEND Knot - Index to the Server and load overview activity
    }


    private static GameSession createTestSession() {
        GameSession gs = new GameSession();
        Player p = new Player("Alex", 1);
        p.setColor(Colors.GREEN);
        gs.getGameboard().getKnots()[10].setPlayer(p);
        p.getInventory().addSettlement(gs.getGameboard().getKnots()[10]);
        p.getInventory().addWheat(2);
        p.getInventory().addOre(3);
        gs.setPlayer(p);
        gs.addSettlement(gs.getGameboard().getKnots()[10]);

        return gs;
    }
}
