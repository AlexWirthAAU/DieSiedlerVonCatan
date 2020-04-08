package com.example.diesiedler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.diesiedler.Presenter.GameBoardClickListener;
import com.example.diesiedler.Model.Gameboard;
import com.richpath.RichPathView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);

        RichPathView r = findViewById(R.id.ic_gameboardView);
        Gameboard gameboard = new Gameboard(this, r);
        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(r, this, gameboard);
        gameBoardClickListener.clickBoard();

    }
}
