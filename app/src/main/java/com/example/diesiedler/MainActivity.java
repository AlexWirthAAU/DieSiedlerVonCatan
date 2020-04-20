package com.example.diesiedler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.model.gameboard.Gameboard;
import com.example.diesiedler.presenter.GameBoardClickListener;
import com.richpath.RichPathView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGameboard();

    }

    private void initGameboard() {
        /*
         *This method is only for testing reasons, to check if the gameBoard-UI works properly
         */
        setContentView(R.layout.gameboardview);
        RichPathView r = findViewById(R.id.ic_gameboardView);
        Gameboard gameboard = new Gameboard(r);
        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(r, this, gameboard);
        gameBoardClickListener.clickBoard();
    }
}
