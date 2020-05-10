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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseaction);

        buildSettlement = findViewById(R.id.buildSettlement);
        buildRoad = findViewById(R.id.buildRoad);
        loadMain = findViewById(R.id.loadOverview);

        buildSettlement.setOnClickListener(this);
        buildRoad.setOnClickListener(this);
        loadMain.setOnClickListener(this);

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
            case R.id.loadOverview:
                intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
