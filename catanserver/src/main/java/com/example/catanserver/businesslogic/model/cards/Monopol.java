package com.example.catanserver.businesslogic.model.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Class makes logic for playing a Monopol-Card
 */
public class Monopol {

    private static Logger logger = Logger.getLogger(Monopol.class.getName()); // Logger
    private static int number = 0;

    /**
     * @param player current Player
     * @return true, when the Player has an Monopol Card, else false
     */
    public static boolean checkCards(Player player) {

        return player.getInventory().getMonopolCard() != 0;
    }

    /**
     * Depending on the Name of the desired Ressource, all of this Ressource
     * is removed from the other Players Inventory and added to the
     * current Players Inventory. The Monopol Card is removed.
     *
     * @param player current Player
     * @param res    english Name of the selected Ressource
     * @param game   current Game
     */
    public static String playCard(Player player, String res, GameSession game) {

        String resName = null;
        logger.log(Level.INFO, player.getInventory().getAllSupplies());

        switch (res) {
            case "wood":
                for (Player p : game.getPlayers()) {
                    if (p.getUserId() != player.getUserId()) {
                        number += p.getInventory().removeAllWood();
                    }
                }
                player.getInventory().addWood(number);
                resName = "Holz";
                break;

            case "wool":
                for (Player p : game.getPlayers()) {
                    if (p.getUserId() != player.getUserId()) {
                        number += p.getInventory().removeAllWool();
                    }
                }
                resName = "Wolle";
                player.getInventory().addWool(number);
                break;

            case "wheat":
                for (Player p : game.getPlayers()) {
                    if (p.getUserId() != player.getUserId()) {
                        number += p.getInventory().removeAllWheat();
                    }
                }
                player.getInventory().addWheat(number);
                resName = "Weizen";
                break;

            case "ore":
                for (Player p : game.getPlayers()) {
                    if (p.getUserId() != player.getUserId()) {
                        number += p.getInventory().removeAllOre();
                    }
                }
                player.getInventory().addOre(number);
                resName = "Erz";
                break;

            case "clay":
                for (Player p : game.getPlayers()) {
                    if (p.getUserId() != player.getUserId()) {
                        number += p.getInventory().removeAllClay();
                    }
                }
                player.getInventory().addClay(number);
                resName = "Lehm";
                break;

            default:
                break;
        }

        player.getInventory().removeMonopolCard(1);
        logger.log(Level.INFO, player.getInventory().getAllSupplies());

        return resName;
    }

    public static int getNumber() {
        int ret = number;
        number = 0;
        return ret;
    }

    /**
     * Creates a Message, specific to the Name of the desired Ressource,
     * and appends it to a StringBuilder.
     *
     * @param resName Name of the selected Ressource
     * @param number  Number of stolen Ressources
     * @return the StringBuilder as a String
     */
    public static String buildMessage(String resName, int number) {

        StringBuilder message = new StringBuilder();

        message.append("Du hast eine Monopolkarte gespielt und ");
        message.append(number).append(" ").append(resName).append(" erhalten");
        logger.log(Level.INFO, message.toString());
        return message.toString();
    }
}
