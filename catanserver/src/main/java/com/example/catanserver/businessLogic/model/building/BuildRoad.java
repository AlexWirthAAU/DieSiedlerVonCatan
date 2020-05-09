package com.example.catanserver.businessLogic.model.building;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Gameboard;

public class BuildRoad {

    public static void updateGameSession(GameSession gameSession, int roadIndex, int userID) {
        Gameboard gameboard = gameSession.getGameboard();
        Edge road = gameboard.getEdges()[roadIndex];
        Player player = gameSession.getPlayer(userID);


        if (player.getInventory().getRoads().size() < 2) {
            updatePlayerInventoryInit(player, road);
        } else {
            updatePlayerInventory(player, road);
        }

        road.setPlayer(player);
        gameSession.addRoad(road);
        gameSession.nextPlayer();
    }

    private static void updatePlayerInventory(Player p, Edge e) {
        PlayerInventory playerInventory = p.getInventory();


        playerInventory.addRoadKnots(e.getOne());
        playerInventory.addRoadKnots(e.getTwo());
        playerInventory.addRoad(e);
        playerInventory.removeWood(1);
        playerInventory.removeClay(1);
    }

    private static void updatePlayerInventoryInit(Player p, Edge e) {
        PlayerInventory playerInventory = p.getInventory();

        playerInventory.addRoadKnots(e.getOne());
        playerInventory.addRoadKnots(e.getTwo());
        playerInventory.addRoad(e);
    }

}
