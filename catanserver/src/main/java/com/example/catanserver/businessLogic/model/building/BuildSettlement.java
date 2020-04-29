package com.example.catanserver.businessLogic.model.building;

import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Edge;
import com.example.catangame.gameboard.Gameboard;
import com.example.catangame.gameboard.Knot;

public class BuildSettlement implements BuildStructure {
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



    @Override
    public void updateGameboard() {
        knot.setPlayer(player);
    }

    @Override
    public void updatePlayerInventory() {
        //todo implementation missing
    }
}
