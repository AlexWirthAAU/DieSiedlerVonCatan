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
        road.setPlayer(player);
        gameSession.addRoad(road);
        int lastPlayer = gameSession.getPlayers().size() - 1;

        if (player.getInventory().getRoads().size() < 2) {
            updatePlayerInventoryInit(player, road);
        } else {
            updatePlayerInventory(player, road);
        }

        System.out.println("Road built from Player: " + player.getUserId());

        /**
         * If the player has less than two roads, the resources are not affected, as the first two roads can be built for free.
         * If its a regular built, the resources need to be reduced.
         */


        if (player.getInventory().getRoads().size() == 1 && !(player.getUserId() == lastPlayer)) {
            gameSession.nextPlayer();
            System.out.println("Next Player");
        }

        if (player.getInventory().getRoads().size() == 2) {
            gameSession.previosPlayer();
            System.out.println("Spieler in the middle");
        }

        if (player.getInventory().getRoads().size() == 2 && player.equals(gameSession.getPlayer(0))) {
            gameSession.nextPlayer();
            System.out.println("Letzter INIT Spielzug");
        }

        if (player.getInventory().getRoads().size() > 2) {
            gameSession.nextPlayer();
            System.out.println("Regulärer Spielzug");
        }
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

        list.get(0).removeCounter();

        if (list.get(0).getCounter() == 0) {
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
