package com.example.catanserver.businessLogic.model.cards;

import com.example.catangame.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Class makes logic for playing an Invention-Card
 */
public class Invention {

    private static Logger logger = Logger.getLogger(Invention.class.getName()); // Logger

    /**
     * @param player current Player
     * @return true, when the Player has an Invention Card, else false
     */
    public static boolean checkCards(Player player) {

        return player.getInventory().getInventionCard() != 0;
    }

    /**
     * Depending on the Name of the desired Resource, the Value of
     * the Ressource is increases by 2. The Invention Card is
     * removed from the Players Inventory.
     *
     * @param player current Player
     * @param res    english Name of the selected Resource
     */
    public static String playCard(Player player, String res) {

        String resName = null;

        logger.log(Level.INFO, player.getInventory().getAllSupplies());

        switch (res) {
            case "wood":
                player.getInventory().addWood(2);
                resName = "Holz";
                break;

            case "wool":
                resName = "Wolle";
                player.getInventory().addWool(2);
                break;

            case "wheat":
                player.getInventory().addWheat(2);
                resName = "Weizen";
                break;

            case "ore":
                player.getInventory().addOre(2);
                resName = "Erz";
                break;

            case "clay":
                player.getInventory().addClay(2);
                resName = "Lehm";
                break;

            default:
                break;
        }

        player.getInventory().removeInventianCard(1);
        logger.log(Level.INFO, player.getInventory().getAllSupplies());
        return resName;
    }

    /**
     * Creates a Message, specific to the Name of the desired Ressource,
     * and appends it to a StringBuilder.
     *
     * @param resName Name of the selected Ressource
     * @return the StringBuilder as a String
     */
    public static String buildMessage(String resName) {

        StringBuilder message = new StringBuilder();

        message.append("Du hast eine Erfindungskarte gespielt und zwei ");
        message.append(resName).append(" erhalten");

        logger.log(Level.INFO, message.toString());
        return message.toString();
    }
}
