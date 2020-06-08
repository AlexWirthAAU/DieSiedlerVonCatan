package com.example.diesiedler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Player;
import com.example.diesiedler.cheating.CheatSelectResourceActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.handler.GameHandler;

import java.util.List;

public class ScoreBoardActivity extends AppCompatActivity {

    TextView knightPowerText;
    ListView scoreList;
    Handler handler = new ScoreBoardHandler(Looper.getMainLooper(),this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        ClientData.currentHandler = handler;

        scoreList = (ListView) findViewById(R.id.scorelist);
        knightPowerText = findViewById(R.id.knightpower);

        initializeList();
        updateKnightPower();
    }

    @Override
    public void onResume() {
        super.onResume();
        ClientData.currentHandler = handler;
        initializeList();
        updateKnightPower();
    }

    public void onRestart() {
        super.onRestart();
        ClientData.currentHandler = handler;
        initializeList();
        updateKnightPower();
    }

    private void initializeList(){
        List<Player> playerList = ClientData.currentGame.getPlayers();

        PlayerListAdapter adapter = new PlayerListAdapter(this, R.layout.adapter_view_layout_scoreboard, playerList);
        scoreList.setAdapter(adapter);
        if(ClientData.currentGame.isInitialized()) {
            scoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), CheatSelectResourceActivity.class);
                    String chosenPlayer = ((TextView) (view.findViewById(R.id.scPlayer))).getText().toString();
                    String chosenPlayerId = "";
                    for (Player player : ClientData.currentGame.getPlayers()) {
                        if (player.getDisplayName().equals(chosenPlayer)) {
                            chosenPlayerId += player.getUserId();
                            break;
                        }
                    }
                    intent.putExtra("playerId", chosenPlayerId);
                    startActivity(intent);
                }
            });
        }
    }

    private void updateKnightPower(){
        if (ClientData.currentGame.getKnightPowerOwner() != null) {
            knightPowerText.setText("Größte Rittermacht hat " + ClientData.currentGame.getKnightPowerOwner().getDisplayName());
        }
    }

    /**
     * @author Alex Wirth
     * @author Christina Senger (edit)
     * @author Fabian Schaffenrath (edit)
     *
     * Handler for the ScoreBoardActivity
     */
    private class ScoreBoardHandler extends GameHandler {

        ScoreBoardHandler(Looper mainLooper, Activity ac) {
            super(mainLooper, ac);
        }

        /**
         * Calls from ServerCommunicationThread. If a String is sent, it is processed by the
         * super handleMessage method. If a game session is sent, the view gets updated.
         *
         * @param msg msg.arg1 has the Param for further Actions
         *            msg.obj holds a Object sent from Server
         */
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 5){
                super.handleMessage(msg);
            }
            else if(msg.arg1 == 4){
                initializeList();
                updateKnightPower();
            }
        }
    }
}
