package com.example.diesiedler.cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Player;
import com.example.diesiedler.R;
import com.example.diesiedler.building.BuildRoadActivity;
import com.example.diesiedler.presenter.ClientData;

/**
 * @author Christina Senger
 * <p>
 * This Activity is to select which DevCard the Player wants to play.
 */
public class PlayCardActivity extends AppCompatActivity {

    /**
     * When the Players has none of same Cards, the Buttons are set disabled.
     *
     * @param savedInstanceState saved State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_card);

        Button knightBtn = findViewById(R.id.knight); // Card-Buttons
        Button monopolBtn = findViewById(R.id.monopol);
        Button inventionBtn = findViewById(R.id.invention);
        Button buildStreetBtn = findViewById(R.id.buildStreet);

        Player player = ClientData.currentGame.getPlayer(ClientData.userId);

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
    }

    /**
     * If the Button with the Text "Ritterkarte" is clicked, ...
     *
     * @param view View to acces the Button
     */
    public void playKnight(View view) {
        Intent intent = new Intent(this, PlayKnightActivity.class);
        startActivity(intent);
    }

    /**
     * If the Button with the Text "Monopolkarte" is clicked, the PlayMonopolActivity is started.
     *
     * @param view View to acces the Button
     */
    public void playMonopol(View view) {
        Intent intent = new Intent(this, PlayMonopolActivity.class);
        startActivity(intent);
    }

    /**
     * If the Button with the Text "Erfindungskarte" is clicked, the PlayInventionActivity is started.
     *
     * @param view View to acces the Button
     */
    public void playInvention(View view) {
        Intent intent = new Intent(this, PlayInventionActivity.class);
        startActivity(intent);
    }

    /**
     * If the Button with the Text "Straßenbaukarte" is clicked, "CARD" is set as
     * extra to the Intent and the BuildRoadActivity is started.
     *
     * @param view View to acces the Button
     */
    public void playBuildStreet(View view) {
        Intent intent = new Intent(this, BuildRoadActivity.class);
        intent.putExtra("card", "CARD");
        startActivity(intent);
    }
}
