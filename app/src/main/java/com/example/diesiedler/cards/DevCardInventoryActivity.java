package com.example.diesiedler.cards;

import android.os.Bundle;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.PlayerInventory;
import com.example.diesiedler.R;
import com.example.diesiedler.presenter.ClientData;

import java.util.LinkedList;
import java.util.List;

public class DevCardInventoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devcard_inventory);

        ListView scoreList = (ListView) findViewById(R.id.devCardsList);

        List<String> devCards = new LinkedList<>();
        PlayerInventory playerInventory = ClientData.currentGame.getPlayer(ClientData.userId).getInventory();

        if (playerInventory.getMonopolCard() > 0) {
            devCards.add("Monopol");
        }
        if (playerInventory.getKnightCard() > 0) {
            devCards.add("Ritterkarte");
        }
        if (playerInventory.getBuildStreetCard() > 0) {
            devCards.add("Straßenbau");
        }
        if (playerInventory.getInventionCard() > 0) {
            devCards.add("Erfindung");
        }

        DevCardListAdapter devCardListAdapter = new DevCardListAdapter(this, R.layout.adapter_view_layout_devcard_inventory, devCards);
        scoreList.setAdapter(devCardListAdapter);

    }
}
