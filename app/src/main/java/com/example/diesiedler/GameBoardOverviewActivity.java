package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.diesiedler.cards.DevCardInventoryActivity;
import com.example.diesiedler.presenter.ClientData;
import com.example.diesiedler.presenter.UpdateGameboardView;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.richpath.RichPathView;


/**
 * @author Alex Wirth
 * <p>
 * This class is the main definition of what the gameboard overview looks like. All Views and Buttons are updated. Though, this activity will
 * never be called. it is only used for inheritance. All Actions that are processed on the gameboard extend from this class.
 */
public class GameBoardOverviewActivity extends AppCompatActivity implements View.OnClickListener {

    protected RichPathView richPathView;
    protected GameBoardClickListener gameBoardClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        richPathView = findViewById(R.id.ic_gameboardView);

        // Buttons to show score and inventory:
        ImageView devCards = findViewById(R.id.devCard);
        devCards.setOnClickListener(this);
        Button scoreBoard = findViewById(R.id.scoreBoard);
        scoreBoard.setOnClickListener(this);


        UpdateGameboardView.updateView(ClientData.currentGame, richPathView);
        updateResources();

        gameBoardClickListener = new GameBoardClickListener(richPathView);
    }


    /**
     * This method is responsible for refreshing the player's resources.
     */
    protected void updateResources() {
        PlayerInventory playerInventory = ClientData.currentGame.getPlayer(ClientData.userId).getInventory();
        Player currentP = ClientData.currentGame.getCurr();

        //TextViews for number of players resources
        TextView woodCount = findViewById(R.id.woodCount);
        woodCount.setText(String.format(Integer.toString(playerInventory.getWood())));
        TextView clayCount = findViewById(R.id.clayCount);
        clayCount.setText(String.format(Integer.toString(playerInventory.getClay())));
        TextView wheatCount = findViewById(R.id.wheatCount);
        wheatCount.setText(String.format(Integer.toString(playerInventory.getWheat())));
        TextView oreCount = findViewById(R.id.oreCount);
        oreCount.setText(String.format(Integer.toString(playerInventory.getOre())));
        TextView woolCount = findViewById(R.id.woolCount);
        woolCount.setText(String.format(Integer.toString(playerInventory.getWool())));

        TextView currentPlayer = findViewById(R.id.currentPlayer);
        if (currentP.getUserId() == ClientData.userId) {
            currentPlayer.setText(("Du bist gerade am Zug!"));
        } else {
            currentPlayer.setText((currentP.getDisplayName() + " ist gerade am Zug"));
        }
        TextView devCardCount = findViewById(R.id.devCardCount);
        devCardCount.setText(String.format(Integer.toString(playerInventory.getCards())));
    }


    /**
     * The View has to buttons that can be clicked.
     * "devCards" will lead the player to an overview of his card-inventory where he can see all his dev-cards
     * "scoreBoard" will load an overview of the current victory points of each player. This activity will also be used for cheating.
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.devCard:
                intent = new Intent(getBaseContext(), DevCardInventoryActivity.class);
                startActivity(intent);
                break;
            case R.id.scoreBoard:
                intent = new Intent(getBaseContext(), ScoreBoardActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
