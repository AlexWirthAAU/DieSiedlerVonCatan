package com.example.catanserver.businessLogic.model.building;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Gameboard;
import com.example.catangame.gameboard.Knot;

public class BuildSettlement {

    private BuildSettlement() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @author Alex Wirth
     * <p>
     * This class' purpose is to update the gamesession object. For this, the gameboard, the gamesession's settlement-list
     * as well as the player's inventory needs to be updated. The queries to check whether a knot is availabe for building a settlement or not,
     * will be handled in the app-programm. There it sends the index of the knot in the knot-array of the gameboard so that
     * the server can update the gamesession object.
     */

    public static void updateGameSession(GameSession gameSession, int knotIndex, int userID) {
        Gameboard gameboard = gameSession.getGameboard();
        Knot toBeSettled = gameboard.getKnots()[knotIndex];
        Player player = gameSession.getPlayer(userID);


        toBeSettled.setSettled(true);
        toBeSettled.setPlayer(player);
        gameSession.addSettlement(toBeSettled);

        /**
         * If the player has less than two settlements, the resources are not affected, as the first two settlements can be built for free.
         * If its a regular built, the resources need to be reduced.
         */
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

        if (k.isHarbourKnot()) {
            updatePorts(p, k);
        }
    }

    private static void updatePlayerInventoryInit(Player p, Knot k) {
        PlayerInventory playerInventory = p.getInventory();

        playerInventory.addSettlement(k);

        if (k.isHarbourKnot()) {
            updatePorts(p, k);
        }
    }

    private static void updatePorts(Player player, Knot knot) {
        PlayerInventory playerInventory = player.getInventory();

        playerInventory.setHasPorts(true);

        if (knot.isWoodPort()) {
            playerInventory.setWoodport(true);
        } else if (knot.isWoolPort()) {
            playerInventory.setWoolport(true);
        } else if (knot.isWheatPort()) {
            playerInventory.setWheatport(true);
        } else if (knot.isOrePort()) {
            playerInventory.setOreport(true);
        } else if (knot.isClayPort()) {
            playerInventory.setClayport(true);
        }

        playerInventory.checkPlayerOptions();
        System.out.println("UPDATE PORTS");
    }
}
