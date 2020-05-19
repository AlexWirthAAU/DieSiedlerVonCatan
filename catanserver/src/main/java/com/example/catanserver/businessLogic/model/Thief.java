package com.example.catanserver.businessLogic.model;

import com.example.catangame.GameSession;
import com.example.catangame.gameboard.Tile;

/**
 * @author Fabian Schaffenrath
 * This Service-class handles the Thief on the Gameboard.
 */

public class Thief {

    public static boolean moveThief(GameSession game, int destinationIndex){
        Tile[] tiles = game.getGameboard().getTiles();
        if(destinationIndex >= 0 && destinationIndex < tiles.length) {
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i].isThief()) {
                    tiles[i].setThief(false);
                    tiles[destinationIndex].setThief(true);
                    return true;
                }
            }
        }
        return false;
    }
}
