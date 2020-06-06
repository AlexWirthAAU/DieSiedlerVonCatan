package com.example.catanserver.businessLogic.model.building;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Gameboard;
import com.example.catangame.gameboard.Knot;

public class BuildCity {

    private BuildCity() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @author Alex Wirth
     * <p>
     * This class' purpose is to update the gamesession object. For this, the gameboard, the gamesession's settlement-list
     * as well as the player's inventory needs to be updated. The queries to check whether a knot is availabe for building a city or not,
     * will be handled in the app-programm. There it sends the index of the knot in the knot-array of the gameboard so that
     * the server can update the gamesession object.
     */

    public static void updateGameSession(GameSession gameSession, int knotIndex, int userID) {
        Gameboard gameboard = gameSession.getGameboard();
        Knot possibleCity = gameboard.getKnots()[knotIndex];
        Player player = gameSession.getPlayer(userID);

        possibleCity.setHasCity(true);
        possibleCity.setPlayer(player);
        gameSession.addCity(possibleCity);
        updatePlayerInventory(player, possibleCity);
        gameSession.nextPlayer();
    }

    /**
     * Reduces the players amount of resources and adds a city to the inventory
     *
     * @param p
     * @param k
     */
    private static void updatePlayerInventory(Player p, Knot k) {
        PlayerInventory playerInventory = p.getInventory();

        playerInventory.addCity(k);
        playerInventory.removeWheat(2);
        playerInventory.removeOre(3);
    }

}
