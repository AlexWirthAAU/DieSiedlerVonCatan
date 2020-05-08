package com.example.diesiedler;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.ServerQueries;
import com.example.diesiedler.presenter.interaction.GameBoardClickListener;
import com.example.diesiedler.threads.NetworkThread;
import com.richpath.RichPathView;

import java.util.logging.Logger;

public class BuildSettlementActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(BuildSettlementActivity.class.getName());

    /**
     * This activity should allow the user to click the knot he wants to build on
     * The clicked asset's ID will be sent to the Server that (PresenterBuild) where it is checked whether user is allowed to build or not
     * If yes -> this asset will be colored in user's color
     * If not -> User has to click another asset
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);

        GameBoardClickListener gameBoardClickListener = new GameBoardClickListener(richPathView, this);
        gameBoardClickListener.clickBoard();
    }

    public void clicked(String s) {
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryBuild(s));
        networkThread.start();
    }

    // TODO: Handler
}
