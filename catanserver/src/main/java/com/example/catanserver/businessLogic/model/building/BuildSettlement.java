package com.example.catanserver.businessLogic.model.building;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Gameboard;
import com.example.catangame.gameboard.Knot;

public class BuildSettlement {

    /**
     * @author Alex Wirth
     * <p>
     * This class' purpose is to update the gamesession object. For this, the gameboard, the gamesession's settlement-list
     * as well as the player's inventory needs to be updated. The queries to check whether a knot is availabe for building a settlement or not,
     * will be handled in the app-programm. There it sends the index of the knot in the knot-array of the gameboard so that
     * the server can update the gamesession object.
     */

    /*
    private boolean enoughResources = false;
    private Player player;
    PlayerInventory playerInventory;
    //Gameboard gameboard = new Gameboard();
    private Knot knot;

    private static final String TAG = "BuildSettlement"; //for logging

    @Override
    public boolean checkResources() {
        // cost for settlement: 1 wood, 1 clay, 1 wheat, 1 wool
        int wood = playerInventory.getWood();
        int clay = playerInventory.getClay();
        int wheat = playerInventory.getWheat();
        int wool = playerInventory.getWool();
        if (wood >= 1 && clay >= 1 && wheat >= 1 && wool >= 1) {
            enoughResources = true;
        }
        return enoughResources;
    }

    @Override
    public void selectBuildingSite() {
        /* settlement can only be built:
         - if there is a road of the player leading to the chosen place and
         - there is no other settlement or city only one distance (road or possible road) away
         */

    /*
        if (enoughResources && adjacentRoadOfPlayer() && notTooCloseToSettlementOrCity()) {
            updateGameboard();
            updatePlayerInventory();
        }
    }

    private boolean adjacentRoadOfPlayer() {
        boolean isAdjacentRoad = false;
        Gameboard gameboard = new Gameboard();
        Edge[] edges = gameboard.getEdges();
        for(Edge edge : edges) {
            if (edge.getOne().equals(knot) || edge.getTwo().equals(knot)){
                if (edge.getPlayer().equals(knot.getPlayer())) { //todo: getPlayer() instead of getUser() ?
                    isAdjacentRoad = true;
                    //Log.d(TAG, "Edge: " + edge + ", Knot: " + knot); //todo: why doesn't Log.d work?
                    break;
                }
            }
        }
        return isAdjacentRoad;
    }

    private boolean notTooCloseToSettlementOrCity() {
        //todo implementation missing
        return true;
    }

    */
    public static void updateGameSession(GameSession gameSession, int knotIndex, int userID) {
        Gameboard gameboard = gameSession.getGameboard();
        Knot toBeSettled = gameboard.getKnots()[knotIndex];
        Player player = gameSession.getPlayer(userID);


        toBeSettled.setSettled(true);
        toBeSettled.setPlayer(player);
        gameSession.addSettlement(toBeSettled);

        if (player.getInventory().getSettlements().size() < 2) {
            updatePlayerInventoryInit(player, toBeSettled);
        } else {
            updatePlayerInventoryRegular(player, toBeSettled);
            gameSession.nextPlayer();
        }
    }

    private static void updatePlayerInventoryRegular(Player p, Knot k) {
        PlayerInventory playerInventory = p.getInventory();

        playerInventory.addSettlement(k);
        playerInventory.removeWood(1);
        playerInventory.removeClay(1);
        playerInventory.removeWheat(1);
        playerInventory.removeWool(1);
        playerInventory.addVictoryPoints(1);
    }

    private static void updatePlayerInventoryInit(Player p, Knot k) {
        PlayerInventory playerInventory = p.getInventory();

        playerInventory.addSettlement(k);
        playerInventory.addVictoryPoints(1);
    }
}
