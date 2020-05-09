package com.example.diesiedler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diesiedler.presenter.GameBoardClickListener;
import com.example.diesiedler.presenter.PresenterBuild;
import com.richpath.RichPathView;

public class BuildRoadActivity extends AppCompatActivity {

    /**
     * This activity should allow the user to click the edge he wants to build on
     * The clicked asset's ID will be sent to the Server that (PresenterBuild) where it is checked whether user is allowed to build or not
     * If yes -> this asset will be colored in user's color
     * If not -> User has to click another asset
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboardview);
        RichPathView richPathView = findViewById(R.id.ic_gameboardView);


        for (Edge e : g.getEdges()
        ) {
            if (p.getInventory().getRoadKnots().contains(e.getOne()) || p.getInventory().getRoadKnots().contains(e.getTwo())) {
                if (e.getPlayer() == null) {
                    richPath = richPathView.findRichPathByName(e.getId());
                    richPath.setFillColor(Color.BLACK);
                }
            }
        }

    public void clicked(String s) {
        Thread networkThread = new NetworkThread(ServerQueries.createStringQueryBuild(s));
        networkThread.start();
    }
}
