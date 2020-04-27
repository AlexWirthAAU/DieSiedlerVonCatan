package com.example.diesiedler;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.GameBoardClickListener;
import com.example.diesiedler.presenter.PresenterBuild;
import com.richpath.RichPathView;

import java.util.concurrent.ExecutionException;

public class BuildRoadActivity extends AppCompatActivity {

    /**
     * This activity should allow the user to click the edge he wants to build on
     * The clicked asset's ID will be sent to the Server that (PresenterBuild) where it is checked whether user is allowed to build or not
     * If yes -> this asset will be colored in user's color
     * If not -> User has to click another asset
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard("BuildRoad");
    }

    public void clicked(String s, Context context) throws ExecutionException, InterruptedException {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setCancelable(true);

        PresenterBuild presenterBuild = new PresenterBuild();
        String o = (String) presenterBuild.chooseAssetID(s);

        if (o.equals("SETTLED")) {
            builder1.setMessage("Bauen erfolgreich");
            AlertDialog alert1 = builder1.create();
            alert1.show();
        } else {
            builder1.setMessage("Hier kannst du nicht bauen!");
            AlertDialog alert1 = builder1.create();
            alert1.show();
        }
    }
}
