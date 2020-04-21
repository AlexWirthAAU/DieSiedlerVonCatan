package com.example.diesiedler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.GameBoardClickListener;
import com.richpath.RichPathView;

public class BuildActivity extends AppCompatActivity {

    /**
     * This activity should allow the user to click on the asset he wants to build on
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
        gameBoardClickListener.clickBoard();

    }
}
