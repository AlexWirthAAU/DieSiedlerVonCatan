package com.example.catanserver.businessLogic.model.building;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.devcards.BuildStreetCard;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Gameboard;

import java.util.LinkedList;


/**
 * @author Alex Wirth
 * @author Christina Senger (edit)
 */
public class BuildRoad {

    public static void updateGameSession(GameSession gameSession, int roadIndex, int userID) {
        Gameboard gameboard = gameSession.getGameboard();
        Edge road = gameboard.getEdges()[roadIndex];
        Player player = gameSession.getPlayer(userID);


        /**
         * If the player has less than two roads, the resources are not affected, as the first two roads can be built for free.
         * If its a regular built, the resources need to be reduced.
         */
        if (player.getInventory().getRoads().size() < 2) {
            updatePlayerInventoryInit(player, road);
        } else {
            updatePlayerInventory(player, road);
        }

        road.setPlayer(player);
        gameSession.addRoad(road);
        gameSession.nextPlayer();
    }

    /**
     * Gameboard, Edges, the Player and his List of BuildStreet Cards are fetched.
     * The Players Inventory is updated. The current Player is set as Player for the
     * Road with the <code>roadIndex</code> and the Road is added to the GameSession.
     * The Counter is decreased. When the Counter is 0, the BuildStreet Card is removed
     * from the Players Inventory and the next Player is on turn.
     *
     * @param gameSession current Game
     * @param roadIndex   Index of the Road the Player wants to build on
     * @param userID      the Players Id
     */
    public static void buildRoadWithCard(GameSession gameSession, int roadIndex, int userID) {

        Gameboard gameboard = gameSession.getGameboard();
        Edge road = gameboard.getEdges()[roadIndex];
        Player player = gameSession.getPlayer(userID);
        LinkedList<BuildStreetCard> list = player.getInventory().getBuildStreetCardLinkedList();

        updatePlayerInventoryInit(player, road);

        road.setPlayer(player);
        gameSession.addRoad(road);

        list.get(list.size()).removeCounter();

        if (list.get(list.size()).getCounter() == 0) {
            list.remove(list.size());
            player.getInventory().removeBuildStreetCard(1);
            gameSession.nextPlayer();
        }
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
