package com.example.catanserver.businesslogic.model.trading;

import com.example.catangame.Player;

/**
 * @author Christina Senger
 * <p>
 * Class makes logic for Bank-Trade
 */
public class Bank {

    /**
     * @param currPlayer current Player
     * @param give Name of the given Resource
     * @return true, when the Player <code>canBankTrade</code> and has
     * at least 4 of the offered Resource, else false.
     */
    public static boolean checkTrade(Player currPlayer, String give) {

        if (!currPlayer.getInventory().isCanBankTrade()) {
            return false;
        }

        int invent = -1;

        switch (give) {
            case "Holz":
                invent = currPlayer.getInventory().getWood();
                break;
            case "Wolle":
                invent = currPlayer.getInventory().getWool();
                break;
            case "Weizen":
                invent = currPlayer.getInventory().getWheat();
                break;
            case "Erz":
                invent = currPlayer.getInventory().getOre();
                break;
            case "Lehm":
                invent = currPlayer.getInventory().getClay();
                break;
            default:
                break;
        }

        return invent >= 4;
    }
}
