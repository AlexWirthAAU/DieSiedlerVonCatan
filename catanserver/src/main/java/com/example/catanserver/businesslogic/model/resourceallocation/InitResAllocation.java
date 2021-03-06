package com.example.catanserver.businesslogic.model.resourceallocation;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.PlayerInventory;
import com.example.catangame.gameboard.Gameboard;
import com.example.catangame.gameboard.Knot;
import com.example.catangame.gameboard.Tile;

public class InitResAllocation {

    private InitResAllocation() {
        throw new IllegalStateException("Utility class");
    }


    public static void allocateInit(GameSession g) {
        Gameboard gameboard = g.getGameboard();
        Tile[] tiles = gameboard.getTiles();
        String tResource;

        for (Tile t : tiles) {
            tResource = t.getResource();
            Knot[] tileKnots = t.getKnots();

            for (Knot k : tileKnots) {
                Player p = k.getPlayer();
                if (p != null) {
                    PlayerInventory playerInventory = p.getInventory();
                    switch (tResource) {
                        case "WOOD":
                            playerInventory.addWood(1);
                            printResourceAllocation(p.getDisplayName(),t.getResource());
                            break;
                        case "WOOL":
                            playerInventory.addWool(1);
                            printResourceAllocation(p.getDisplayName(),t.getResource());
                            break;
                        case "CLAY":
                            playerInventory.addClay(1);
                            printResourceAllocation(p.getDisplayName(),t.getResource());
                            break;
                        case "WHEAT":
                            playerInventory.addWheat(1);
                            printResourceAllocation(p.getDisplayName(),t.getResource());
                            break;
                        case "ORE":
                            playerInventory.addOre(1);
                            printResourceAllocation(p.getDisplayName(),t.getResource());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private static void printResourceAllocation(String playerName, String resource){
        System.out.println(playerName + " received 1 " + resource);
    }
}
