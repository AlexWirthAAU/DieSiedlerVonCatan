package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.BufferUnderflowException;


public class ChooseActionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buildSettlement;
    private Button buildRoad;
    private Button loadMain;
    private Button buildCity;
    private Button buyDevCard;
    private Button playdevCard;
    private Button trade;
    private Button exchange;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseaction);

        buildSettlement = findViewById(R.id.buildSettlement);
        buildRoad = findViewById(R.id.buildRoad);
        buildCity = findViewById(R.id.buildCity);
        loadMain = findViewById(R.id.loadOverview);
        buyDevCard = findViewById(R.id.buyDevCard);
        playdevCard = findViewById(R.id.setDevCard);
        trade = findViewById(R.id.trade);
        exchange = findViewById(R.id.exchange);

        buildSettlement.setOnClickListener(this);
        buildRoad.setOnClickListener(this);
        buildCity.setOnClickListener(this);
        loadMain.setOnClickListener(this);
        buyDevCard.setOnClickListener(this);
        playdevCard.setOnClickListener(this);
        trade.setOnClickListener(this);
        exchange.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.buildSettlement:
                intent = new Intent(getBaseContext(), BuildSettlementActivity.class);
                startActivity(intent);
                break;
            case R.id.buildRoad:
                intent = new Intent(getBaseContext(), BuildRoadActivity.class);
                startActivity(intent);
                break;
            case R.id.buildCity:
                intent = new Intent(getBaseContext(), BuildCityActivity.class);
                startActivity(intent);
                break;
            case R.id.loadOverview:
                intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.exchange:
                //TODO: load exhange activity
                break;
            case R.id.trade:
                //TODO: load trade activity
                break;
            case R.id.setDevCard:
                //TODO: load play devCard activity
                break;
            case R.id.buyDevCard:
                //TODO: load buyDevCard activity
                break;
        }
    }
}
