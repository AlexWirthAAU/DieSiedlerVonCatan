package com.example.catanserver.businessLogic.model.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Christina Senger
 * <p>
 * Class makes Logic for Trading
 */
public class StartTrade {

    private static Map<String, Integer> offer = new HashMap<>(); // Map of the offered Ressources
    private static Map<String, Integer> want = new HashMap<>(); // Map of the desired Ressources

    /**
     * @param offer      Map with the offer Ressources
     * @param currPlayer current Player
     * @return true, when the Player <code>canTrade</code> and has
     * at least the number of offered Ressources, else false.
     */
    public static boolean checkTrade(Map<String, Integer> offer, Player currPlayer) {

        if (!currPlayer.getInventory().canTrade) {
            return false;
        } else return currPlayer.getInventory().getWood() >= offer.get("Holz")
                && currPlayer.getInventory().getWool() >= offer.get("Wolle")
                && currPlayer.getInventory().getWheat() >= offer.get("Weizen")
                && currPlayer.getInventory().getOre() >= offer.get("Erz")
                && currPlayer.getInventory().getClay() >= offer.get("Lehm");
    }

    /**
     * It iterates through all Players in the Game. When the Player is not
     * the current Player and has the desired Ressources, he is added to
     * the List of potential Trading-Partners.
     *
     * @param game       current Game
     * @param want       Map with the desired Ressources
     * @param currPlayer current Player
     */
    public static List<Player> checkAndSetTradingPartners(GameSession game, Map<String, Integer> want, Player currPlayer) {

        List<Player> players = game.getPlayers();
        List<Player> potentialTradingPartners = new ArrayList<>();

        for (Player player : players) {

            if (!player.equals(currPlayer)) {

                if (player.getInventory().getWood() >= want.get("Holz")
                        && player.getInventory().getWool() >= want.get("Wolle")
                        && player.getInventory().getWheat() >= want.get("Weizen")
                        && player.getInventory().getOre() >= want.get("Erz")
                        && player.getInventory().getClay() >= want.get("Lehm")) {

                    potentialTradingPartners.add(player);
                }
            }
        }
        System.out.println("trading partners" + potentialTradingPartners);
        return potentialTradingPartners;
    }

    /**
     * The Trade-Offer is splitted by /. The first Elements
     * are the offered, the second are the desired Ressources.
     * It adds those to Maps with the Name as Key and the Number
     * as Value.
     *
     * @param tradeStr Trade-Offer sent from Client
     */
    public static void setTradeData(String tradeStr) {

        String[] trd = tradeStr.split("/");

        int i = 0;

        while (i < trd.length - 1) {
            int num = Integer.parseInt(trd[i + 1]);
            if (num == -1) {
                i += 2;
                break;
            }
            offer.put(trd[i], num);
            i += 2;
        }

        while (i < trd.length - 1) {
            int num = Integer.parseInt(trd[i + 1]);
            want.put(trd[i], num);
            i += 2;
        }

        System.out.println("offer " + offer + " want " + want);
    }

    /**
     * @return Map of the offered Ressources
     */
    public static Map<String, Integer> getOffered() {
        Map<String, Integer> ret = offer;
        offer = null;
        return ret;
    }

    /**
     * @return Map of the desired Ressources
     */
    public static Map<String, Integer> getDesired() {
        Map<String, Integer> ret = want;
        want = null;
        return ret;
    }


    /**
     * Creates a Message, specific to the Name and Amount
     * of the Ressources and appends it to a StringBuilder.
     *
     * @param currPlayer current Player
     * @param offer      Map with the offered Ressources
     * @param want       Map wih to desired Ressources
     * @return the StringBuilder as a String
     */
    public static String buildMessage(Player currPlayer, Map<String, Integer> offer, Map<String, Integer> want) {

        StringBuilder message = new StringBuilder();

        message.append(currPlayer.getDisplayName()).append(" bietet ");

        for (Map.Entry<String, Integer> entry : offer.entrySet()) {
            if (entry.getValue() > 0) {
                message.append(entry.getValue()).append(" ").append(entry.getKey()).append(" und ");
            }
        }

        message.append("moechte dafuer ");

        for (Map.Entry<String, Integer> entry : want.entrySet()) {
            if (entry.getValue() > 0) {
                message.append(entry.getValue()).append(" ").append(entry.getKey()).append(" ");
            }
        }

        System.out.println(message.toString());
        return message.toString();
    }
}
