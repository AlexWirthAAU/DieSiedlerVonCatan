package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//Geladen, wenn Spieler am Zug ist
public class SelectActionActivity extends AppCompatActivity {

    Button tradeBtn = findViewById(R.id.trade);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);

        /**TODO: Implement check against game session**/
        if (false) {
            tradeBtn.setEnabled(false);
        }
    }

    public void trade(View view) {
        Intent intent = new Intent(this, TradeActivity.class);
        startActivity(intent);
    }

    public void ahead(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
