package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.GameSession;
import com.example.catangame.Player;

//Geladen, wenn Spieler am Zug ist
public class SelectActionActivity extends AppCompatActivity {

    Button tradeBtn = findViewById(R.id.trade);
    Button bankBtn = findViewById(R.id.bank);
    Button portBtn = findViewById(R.id.port);
    private GameSession game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);

        game = (GameSession) getIntent().getSerializableExtra("game");
        assert game != null;
        Player player = game.getCurr();

        if (!player.getInventory().canTrade) {
            tradeBtn.setEnabled(false);
        }

        if (!player.getInventory().canBankTrade) {
            bankBtn.setEnabled(false);
        }

        if (player.getInventory().canPortTrade) {
            portBtn.setEnabled(false);
        }
    }

    public void trade(View view) {
        Intent intent = new Intent(this, TradeActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    public void bankChange(View view) {
        Intent intent = new Intent(this, BankChangeActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    public void portChange(View view) {
        Intent intent = new Intent(this, PortChangeActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    public void showCosts(View view) {
        Intent intent = new Intent(this, ShowCostsActivity.class);
        startActivity(intent);
    }

    public void ahead(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }
}
