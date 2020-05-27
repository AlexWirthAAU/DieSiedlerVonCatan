package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Player;
import com.example.diesiedler.cheating.CheatSelectResourceActivity;
import com.example.diesiedler.presenter.ClientData;

import java.util.List;

public class ScoreBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        ListView scoreList = (ListView) findViewById(R.id.scorelist);

        List<Player> playerList = ClientData.currentGame.getPlayers();

        PlayerListAdapter adapter = new PlayerListAdapter(this, R.layout.adapter_view_layout, playerList);
        scoreList.setAdapter(adapter);
        scoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), CheatSelectResourceActivity.class);
                String chosenPlayer = ((TextView)(view.findViewById(R.id.scPlayer))).getText().toString();
                String chosenPlayerId = "";
                for (Player player:ClientData.currentGame.getPlayers()){
                    if(player.getDisplayName().equals(chosenPlayer)){
                        chosenPlayerId += player.getUserId();
                        break;
                    }
                }
                intent.putExtra("playerId",chosenPlayerId);
                startActivity(intent);
            }
        });
    }
}
