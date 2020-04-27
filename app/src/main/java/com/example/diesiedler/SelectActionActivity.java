package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SelectActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);
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
