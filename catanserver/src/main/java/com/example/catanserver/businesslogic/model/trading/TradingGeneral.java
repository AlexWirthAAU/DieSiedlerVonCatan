package com.example.catanserver.businesslogic.model.trading;

import com.example.catangame.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TradingGeneral {

    private static Logger logger = Logger.getLogger(TradingGeneral.class.getName()); // Logger


    private String offered; // Name of the offered Resource
    private String desired; // Name of the desired Resource

    /**
     * The Trade-Offer is splitted by /. The first Elements
     * is the offered, the second is the desired Ressource.
     *
     * @param tradeStr Trade-Offer sent from Client
     */
    public void setTradeData(String tradeStr) {

        String[] trd = tradeStr.split("/");

        offered = trd[0];
        desired = trd[1];
        logger.log(Level.INFO, offered + " give " + desired + " get");
    }

    /**
     * @return Name of the offered Resources
     */
    public String getOffered() {
        String ret = offered;
        offered = null;
        return ret;
    }

    /**
     * @return Name of the desired Resources
     */
    public String getDesired() {
        String ret = desired;
        desired = null;
        return ret;
    }

    /**
     * Creates a Message, specific to the Name of the Resources,
     * and appends it to a StringBuilder.
     *
     * @param give   Name of the given Resource
     * @param get    Name of the desired Resource
     * @param number Number of traded Resources
     * @return the StringBuilder as a String
     */
    public String buildMessage(String give, String get, int number) {

        StringBuilder message = new StringBuilder();

        message.append("Du hast erfolgreich ").append(number).append(" ").append(give).append(" gegen 1 ").append(get).append(" getauscht");

        logger.log(Level.INFO, message.toString());
        return message.toString();
    }

    /**
     * Depending on the Name of the Resources, the desired Resource
     * is increased by one and the offered Resource is decreased by 4.
     *
     * @param give       Name of the given Resource
     * @param get        Name of the desired Resource
     * @param currPlayer current Player
     * @param number     Number of the Resource to remove from current Player
     */
    public void exchangeResources(String give, String get, Player currPlayer, int number) {

        System.out.println(currPlayer.getInventory().getAllResources());

        switch (get) {
            case "Holz":
                currPlayer.getInventory().addWood(1);
                break;
            case "Wolle":
                currPlayer.getInventory().addWool(1);
                break;
            case "Weizen":
                currPlayer.getInventory().addWheat(1);
                break;
            case "Erz":
                currPlayer.getInventory().addOre(1);
                break;
            case "Lehm":
                currPlayer.getInventory().addClay(1);
                break;
            default:
                break;
        }

        switch (give) {
            case "Holz":
                currPlayer.getInventory().removeWood(number);
                break;
            case "Wolle":
                currPlayer.getInventory().removeWool(number);
                break;
            case "Weizen":
                currPlayer.getInventory().removeWheat(number);
                break;
            case "Erz":
                currPlayer.getInventory().removeOre(number);
                break;
            case "Lehm":
                currPlayer.getInventory().removeClay(number);
                break;
            default:
                break;
        }

        logger.log(Level.INFO, number + " " + give + " gegen 1 " + get);
        logger.log(Level.INFO, currPlayer.getInventory().getAllResources());
    }
}
