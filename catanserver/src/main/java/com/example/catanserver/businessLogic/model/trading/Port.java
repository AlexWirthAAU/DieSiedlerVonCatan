package com.example.catanserver.businessLogic.model.trading;

import com.example.catangame.Player;

/**
 * @author Christina Senger
 * <p>
 * Class makes logic for Port-Trade
 */
public class Port {

    private static String offered; // Name of the offerd Ressource
    private static String desired; // Name of the desired Ressource

    /**
     * @param currPlayer current Player
     * @param get        Name of the desired Ressource
     * @return true, when the Player <code>canPortTrade</code> and has
     * at least 3 of the offered Ressource, else false.
     */
    public static boolean checkTrade(Player currPlayer, String get) {

        boolean invent = false;

        if (!currPlayer.getInventory().canPortTrade || !currPlayer.getInventory().hasPorts) {
            System.out.println("firstcheck");
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
                System.out.println("portcheck");
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

    /**
     * The Trade-Offer is splitted by /. The first Elements
     * is the offered, the second is the desired Ressource.
     *
     * @param tradeStr Trade-Offer sent from Client
     */
    public static void setTradeData(String tradeStr) {

        String[] trd = tradeStr.split("/");

        offered = trd[0];
        desired = trd[1];
        System.out.println(offered + " give " + desired + " get");
    }

    /**
     * @return Name of the offered Ressources
     */
    public static String getOffered() {
        String ret = offered;
        offered = null;
        return ret;
    }

    /**
     * @return Name of the desired Ressources
     */
    public static String getDesired() {
        String ret = desired;
        desired = null;
        return ret;
    }

    /**
     * Creates a Message, specific to the Name of the Ressources,
     * and appends it to a StringBuilder.
     *
     * @param give Name of the given Ressource
     * @param get  Name of the desired Ressource
     * @return the StringBuilder as a String
     */
    public static String buildMessage(String give, String get) {

        StringBuilder message = new StringBuilder();

        message.append("Du hast erfolgreich 3 ").append(give).append(" gegen 1 ").append(get).append(" getauscht");
        System.out.println(message.toString());
        return message.toString();
    }

    /**
     * Depending on the Name of the Ressources, the desired Ressource
     * is increased by one and the offered Ressource is decreased by 3.
     *
     * @param give       Name of the given Ressource
     * @param get        Name of the desired Ressource
     * @param currPlayer current Player
     */
    public static void exchangeRessources(String give, String get, Player currPlayer) {

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
                currPlayer.getInventory().removeWood(3);
                break;
            case "Wolle":
                currPlayer.getInventory().removeWool(3);
                break;
            case "Weizen":
                currPlayer.getInventory().removeWheat(3);
                break;
            case "Erz":
                currPlayer.getInventory().removeOre(3);
                break;
            case "Lehm":
                currPlayer.getInventory().removeClay(3);
                break;
            default:
                break;
        }

        System.out.println("3 " + give + " gegen 1 " + get);
        System.out.println(currPlayer.getInventory().getAllRessources());
    }
}
