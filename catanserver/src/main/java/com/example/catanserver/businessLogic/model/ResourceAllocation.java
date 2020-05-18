package com.example.catanserver.businessLogic.model;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Knot;
import com.example.catangame.gameboard.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Wirth
 * This Service-class will handle the allocation of the resources. Depending on the diced value,
 * the concerned tiles will first be saved in an extra List if the thief is not active on this tile.
 * Afterwards each Knot of the tile is checked. If it is settled, the concerned playery's inventory
 * will be updated (add resources).
 */

public class ResourceAllocation {

    public static void updateResources(GameSession game, int diceValue) {

        List<Tile> affectedTiles = affectedTiles(game, diceValue);

        /**
         * Each tile with the dice value is checked for the resource-type it is.
         */
        for (Tile t : affectedTiles
        ) {
            String resource = t.getResource();
            switch (resource) {
                case "WOOD":
                    updateWood(t);
                    break;
                case "WOOL":
                    updateWool(t);
                    break;
                case "CLAY":
                    updateClay(t);
                    break;
                case "ORE":
                    updateOre(t);
                    break;
                case "WHEAT":
                    updateWheat(t);
                    break;
            }
        }
    }

    /**
     * Adds each tile that has the dice-value on it to a list.
     *
     * @param game
     * @param diceValue
     * @return
     */
    private static List<Tile> affectedTiles(GameSession game, int diceValue) {
        List<Tile> resourceTiles = new ArrayList<>();

        for (Tile t : game.getGameboard().getTiles()
        ) {
            if (t.getDiceValue() == diceValue && t.isThief() == false) {
                resourceTiles.add(t);
                System.out.println("RESOURCE-ALLOCATION: Added Tile: " + t.getId());
            }
        }
        return resourceTiles;
    }


    /**
     * The following methods increase the inventory's resources of each player that has a settlement/city on a tile.
     * @param t
     */

    private static void updateWood(Tile t) {
        Knot[] tileKnots = t.getKnots();
        for (int i = 0; i < tileKnots.length; i++) {
            Player p = tileKnots[i].getPlayer();
            if (p != null) {
                if (tileKnots[i].hasCity() == true) {
                    p.getInventory().addWood(2);
                    System.out.println("Updated Wood +2  for " + p.getDisplayName());
                } else {
                    p.getInventory().addWood(1);
                    System.out.println("Updated Wood +1 for " + p.getDisplayName());
                }
            }
        }
    }

    private static void updateWool(Tile t) {
        Knot[] tileKnots = t.getKnots();
        for (int i = 0; i < tileKnots.length; i++) {
            Player p = tileKnots[i].getPlayer();
            if (p != null) {
                if (tileKnots[i].hasCity() == true) {
                    p.getInventory().addWool(2);
                    System.out.println("Updated Wool +2 for " + p.getDisplayName());
                } else {
                    p.getInventory().addWool(1);
                    System.out.println("Updated Wool +1 for " + p.getDisplayName());
                }
            }
        }
    }

    private static void updateClay(Tile t) {
        Knot[] tileKnots = t.getKnots();
        for (int i = 0; i < tileKnots.length; i++) {
            Player p = tileKnots[i].getPlayer();
            if (p != null) {
                if (tileKnots[i].hasCity() == true) {
                    p.getInventory().addClay(2);
                    System.out.println("Updated Clay +2  for " + p.getDisplayName());
                } else {
                    p.getInventory().addClay(1);
                    System.out.println("Updated Clay +1  for " + p.getDisplayName());
                }
            }
        }

    }

    private static void updateOre(Tile t) {
        Knot[] tileKnots = t.getKnots();
        for (int i = 0; i < tileKnots.length; i++) {
            Player p = tileKnots[i].getPlayer();
            if (p != null) {
                if (tileKnots[i].hasCity() == true) {
                    p.getInventory().addOre(2);
                    System.out.println("Updated Ore +2  for " + p.getDisplayName());
                } else {
                    p.getInventory().addOre(1);
                    System.out.println("Updated Ore +1  for " + p.getDisplayName());
                }
            }
        }
    }

    private static void updateWheat(Tile t) {
        Knot[] tileKnots = t.getKnots();
        for (int i = 0; i < tileKnots.length; i++) {
            Player p = tileKnots[i].getPlayer();
            if (p != null) {
                if (tileKnots[i].hasCity() == true) {
                    p.getInventory().addWheat(2);
                    System.out.println("Updated Wheat +2  for " + p.getDisplayName());
                } else {
                    p.getInventory().addWheat(1);
                    System.out.println("Updated Wheat +1  for " + p.getDisplayName());
                }
            }
        }
    }

}

