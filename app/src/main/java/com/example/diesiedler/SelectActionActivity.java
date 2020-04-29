package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//Geladen, wenn Spieler am Zug ist
public class SelectActionActivity extends AppCompatActivity {

    Button tradeBtn = findViewById(R.id.trade);
    Button bankBtn = findViewById(R.id.bank);
    Button portBtn = findViewById(R.id.port);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);

        /**TODO: Implement checks against game session**/
        if (false) {
            tradeBtn.setEnabled(false);
        }

        if (false) {
            bankBtn.setEnabled(false);
        }

        if (false) {
            portBtn.setEnabled(false);
        }
    }

    public void trade(View view) {
        Intent intent = new Intent(this, TradeActivity.class);
        startActivity(intent);
    }

    public void bankChange(View view) {
        Intent intent = new Intent(this, BankChangeActivity.class);
        startActivity(intent);
    }

    public void portChange(View view) {
        Intent intent = new Intent(this, PortChangeActivity.class);
        startActivity(intent);
    }

    public void ahead(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
