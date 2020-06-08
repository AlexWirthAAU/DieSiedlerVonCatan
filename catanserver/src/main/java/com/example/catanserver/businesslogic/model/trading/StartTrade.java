package com.example.catanserver.businesslogic.model.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.Trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * <p>
 * Class makes Logic for Trading
 */
public class StartTrade {

    private static Logger logger = Logger.getLogger(StartTrade.class.getName()); // Logger

    private Map<String, Integer> offer = new HashMap<>(); // Map of the offered Ressources
    private Map<String, Integer> want = new HashMap<>(); // Map of the desired Ressources

    /**
     * @param offer Map with the offer Resources
     * @param currPlayer current Player
     * @return true, when the Player <code>canTrade</code> and has
     * at least the number of offered Resources, else false.
     */
    public boolean checkTrade(Map<String, Integer> offer, Player currPlayer) {

        if (!currPlayer.getInventory().isCanTrade()) {
            return false;
        } else return currPlayer.getInventory().getWood() >= offer.get("Holz")
                && currPlayer.getInventory().getWool() >= offer.get("Wolle")
                && currPlayer.getInventory().getWheat() >= offer.get("Weizen")
                && currPlayer.getInventory().getOre() >= offer.get("Erz")
                && currPlayer.getInventory().getClay() >= offer.get("Lehm");
    }

    /**
     * It iterates through all Players in the Game. When the Player is not
     * the current Player and has the desired Resources, he is added to
     * the List of potential Trading-Partners.
     *
     * @param game current Game
     * @param want Map with the desired Ressources
     * @param currPlayer current Player
     */
    public List<Player> checkAndSetTradingPartners(GameSession game, Map<String, Integer> want, Player currPlayer) {

        List<Player> players = game.getPlayers();
        List<Player> potentialTradingPartners = new ArrayList<>();

        for (Player player : players) {

            if (!player.equals(currPlayer) && player.getInventory().getWood() >= want.get("Holz")
                    && player.getInventory().getWool() >= want.get("Wolle")
                    && player.getInventory().getWheat() >= want.get("Weizen")
                    && player.getInventory().getOre() >= want.get("Erz")
                    && player.getInventory().getClay() >= want.get("Lehm")) {

                potentialTradingPartners.add(player);
            }
        }
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
    public void setTradeData(String tradeStr) {

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

        logger.log(Level.INFO, "offer " + offer + " want " + want);
    }

    /**
     * @return Map of the offered Resources
     */
    public Map<String, Integer> getOffered() {
        return offer;
    }

    /**
     * @return Map of the desired Resources
     */
    public Map<String, Integer> getDesired() {
        return want;
    }


    /**
     * Creates a Message, specific to the Name and Amount
     * of the Resources and appends it to a StringBuilder.
     *
     * @param currPlayer current Player
     * @param offer      Map with the offered Ressources
     * @param want       Map wih to desired Ressources
     * @return the StringBuilder as a String
     */
    public String buildMessage(Player currPlayer, Map<String, Integer> offer, Map<String, Integer> want) {

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
                message.append(entry.getValue()).append(" ").append(entry.getKey()).append(", ");
            }
        }

        logger.log(Level.INFO, message.toString());
        return message.toString();
    }

    /**
     * Sets the Data on the Trade and activates a Trade in the Game
     *
     * @param offer                    Map of offered Resources
     * @param want                     Map of desired Resources
     * @param currPlayer               current Player
     * @param potentialTradingPartners List of all potential Trading-Partners
     * @param mess                     Trade-Message
     * @param gs                       current Game
     */
    public void setTrade(Map<String, Integer> offer, Map<String, Integer> want, Player currPlayer, List<Player> potentialTradingPartners, String mess, GameSession gs) {
        Trade trade = new Trade(offer, want, currPlayer, potentialTradingPartners, mess, gs);
        gs.setTrade(trade);
        gs.setIsTradeOn(true);
    }
}
