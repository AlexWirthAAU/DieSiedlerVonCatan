package com.example.catanserver.businesslogic.model.trading;

import com.example.catangame.Player;

/**
 * @author Christina Senger
 * <p>
 * Class makes logic for Port-Trade
 */
public class Port {

    /**
     * @param currPlayer current Player
     * @param get Name of the desired Resource
     * @return true, when the Player <code>canPortTrade</code> and has
     * at least 3 of the offered Resource, else false.
     */
    public static boolean checkTrade(Player currPlayer, String get) {

        boolean invent = false;

        if (!currPlayer.getInventory().isCanPortTrade() || !currPlayer.getInventory().isHasPorts()) {
            return false;
        }

        switch (get) {
            case "Holz":
                invent = currPlayer.getInventory().isWoodport();
                break;
            case "Wolle":
                invent = currPlayer.getInventory().isWoolport();
                break;
            case "Weizen":
                invent = currPlayer.getInventory().isWheatport();
                break;
            case "Erz":
                invent = currPlayer.getInventory().isOreport();
                break;
            case "Lehm":
                invent = currPlayer.getInventory().isClayport();
                break;
            default:
                break;
        }

        return invent;
    }
}
