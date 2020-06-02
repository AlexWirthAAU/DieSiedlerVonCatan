package com.example.diesiedler;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Player;
import com.example.diesiedler.presenter.ClientData;

import java.util.List;

public class ScoreBoardActivity extends AppCompatActivity {

    TextView text = findViewById(R.id.knightpower);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        ListView scoreList = (ListView) findViewById(R.id.scorelist);

        List<Player> playerList = ClientData.currentGame.getPlayers();

        PlayerListAdapter adapter = new PlayerListAdapter(this, R.layout.adapter_view_layout, playerList);
        scoreList.setAdapter(adapter);

        if (ClientData.currentGame.getKnightPowerOwner() != null) {
            text.setText("Größte Rittermacht hat " + ClientData.currentGame.getKnightPowerOwner());
        }
    }
}
