package com.example.diesiedler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.diesiedler.presenter.GameBoardClickListener;
import com.example.diesiedler.presenter.PresenterBuild;
import com.richpath.RichPathView;

public class MainActivity extends AppCompatActivity {

    private GameSession game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard();

        game = (GameSession) getIntent().getSerializableExtra("game");
    }

    public void clicked(String s) {
        new PresenterBuild().chooseAssetID(s);
    }

}
