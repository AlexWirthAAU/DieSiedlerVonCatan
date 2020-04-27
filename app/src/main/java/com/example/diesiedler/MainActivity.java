package com.example.diesiedler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.diesiedler.presenter.GameBoardClickListener;
import com.example.diesiedler.model.gameboard.Gameboard;
import com.richpath.RichPathView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpSecoundView();
        //initGameboard();
    }


    private void setUpSecoundView() {
        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked RollerActivity", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, RollDiceActivity.class);
                startActivity(i);
            }
        });
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
