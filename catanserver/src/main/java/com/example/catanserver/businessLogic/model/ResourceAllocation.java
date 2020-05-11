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


    private static void updateWood(Tile t) {
        Knot[] tileKnots = t.getKnots();
        for (int i = 0; i < tileKnots.length; i++) {
            Player p = tileKnots[i].getPlayer();
            if (p != null) {
                if (tileKnots[i].hasCity() == true) {
                    p.getInventory().addWood(2);
                    System.out.println("Updated Wood +2");
                } else {
                    p.getInventory().addWood(1);
                    System.out.println("Updated Wood +1");
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
                    System.out.println("Updated Wool +2");
                } else {
                    p.getInventory().addWool(1);
                    System.out.println("Updated Wool +1");
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
                    System.out.println("Updated Clay +2");
                } else {
                    p.getInventory().addClay(1);
                    System.out.println("Updated Clay +1");
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
                    System.out.println("Updated Ore +2");
                } else {
                    p.getInventory().addOre(1);
                    System.out.println("Updated Ore +1");
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
                    System.out.println("Updated Wheat +2");
                } else {
                    p.getInventory().addWheat(1);
                    System.out.println("Updated Wheat +1");
                }
            }
        }
    }

}

