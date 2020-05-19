package com.example.diesiedler.presenter;

import com.example.catangame.GameSession;
import com.example.catangame.gameboard.Tile;
import com.richpath.RichPath;
import com.richpath.RichPathView;

/**
 * @author Fabian Schaffenrath
 * This Class is used, to prepare a RichPathView for Tile selection.
 */

public class UpdateThiefView {

    public static void updateView(GameSession gs, RichPathView rpv){
        Tile[] tiles = gs.getGameboard().getTiles();

        for (int i = 0; i < tiles.length; i++) {
            RichPath tileThief = rpv.findRichPathByName("thief_" + tiles[i].getId());
            tileThief.setFillAlpha(1);
        }
    }
}
