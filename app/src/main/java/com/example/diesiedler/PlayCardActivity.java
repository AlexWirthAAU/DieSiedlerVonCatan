package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.diesiedler.presenter.ClientData;

public class PlayCardActivity extends AppCompatActivity {

    Button knightBtn = findViewById(R.id.knight);
    Button monopolBtn = findViewById(R.id.monopol);
    Button inventionBtn = findViewById(R.id.invention);
    Button buildStreetBtn = findViewById(R.id.buildStreet);

    private GameSession game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_card);

        Player player = game.getCurr();

        if (player.getInventory().getKnightCard() < 1) {
            knightBtn.setEnabled(false);
        }

        if (player.getInventory().getMonopolCard() < 1) {
            monopolBtn.setEnabled(false);
        }

        if (player.getInventory().getInventionCard() < 1) {
            inventionBtn.setEnabled(false);
        }

        if (player.getInventory().getBuildStreetCard() < 1) {
            buildStreetBtn.setEnabled(false);
        }

        game = ClientData.currentGame;
    }

    public void playKnight(View view) {
        Intent intent = new Intent(this, PlayKnightActivity.class);
        startActivity(intent);
    }

    public void playMonopol(View view) {
        Intent intent = new Intent(this, PlayMonopolActivity.class);
        startActivity(intent);
    }

    public void playInvention(View view) {
        Intent intent = new Intent(this, PlayInventionActivity.class);
        startActivity(intent);
    }

    public void playBuildStreet(View view) {
        Intent intent = new Intent(this, PlayBuildStreetActivity.class);
        startActivity(intent);
    }
}
