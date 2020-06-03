package com.example.catanserver.businessLogic.model.trading;

import com.example.catangame.Player;
import com.example.catangame.Trade;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christina Senger
 * <p>
 * Class makes Logic for answer to a Trade
 */
public class Answer {

    /**
     * Adds the Player which has answered the the List of answered Players
     * and adds his Answer to the Map.
     *
     * @param answerStr  Answer, accepted or dismiss
     * @param trade      current Trade
     * @param currPlayer current Player
     */
    public static void addAnsweredPlayer(String answerStr, Trade trade, Player currPlayer) {

        if (answerStr.equals("accepted")) {
            trade.addAnsweredPlayer(currPlayer);
            trade.addAnswers(currPlayer, true);
        } else {
            trade.addAnsweredPlayer(currPlayer);
            trade.addAnswers(currPlayer, false);
        }

        System.out.println(trade.getAnsweredPlayers().size() + " " + trade.getAnswers());
    }

    /**
     * Iterates through the List of answered Players and returnes the first
     * which has accepted as Trading-Partner. Else null is returned.
     *
     * @param trade current Trade
     * @return the Trading-Partner or null, when no Partner is found
     */
    public static Player setPartner(Trade trade) {

        Player partner = null;

        for (Player p : trade.getAnsweredPlayers()) {
            if (trade.getAnswers().get(p)) {
                partner = p;
                break;
            }
        }

        return partner;
    }

    /**
     * When no Trading-Partner could be found, the current Player and all potential
     * Trading-Partners are added to the List of Player, to which a Message should be send.
     * The Answer-Message is set and distributed to all Player in the List.
     * <p>
     * Else, the current Player and the Player who first answered with accept are added
     * to the to the List of Player, to which a Message should be send.
     * The Ressources are exchanged, the Answer-Message is set and distributed to all Player in the List.
     *
     * @param tradingOfferer Player which offered the Trade
     * @param trade          current Trade
     * @return List of Players a Message should be send to
     */
    public static boolean trade(Player tradingOfferer, Trade trade) {

        Player tradingPartner = setPartner(trade);
        trade.setTradingPartner(tradingPartner);

        if (tradingPartner != null) {
            System.out.println(tradingOfferer + " offer " + tradingPartner + " partner");

            System.out.println(tradingOfferer.getInventory().getAllSupplies() + " curr ");
            System.out.println(tradingPartner.getInventory().getAllSupplies());
            exchangeRessources(tradingPartner, tradingOfferer, trade);

            System.out.println("Handel durchgefuehrt");
            System.out.println("Handel zwischen " + tradingOfferer.getDisplayName() + " und " + tradingPartner.getDisplayName() + " durchgefuehrt");
            return true;
        }
        return false;
    }

    /**
     * The offered Ressources are removed from the current Players Inventory
     * and added to the Trading Partners Inventory.
     * The desired Ressources are added to the current Players Inventory
     * and removed from the Trading Partners Inventory.
     *
     * @param tradingPartner Player which answered first with accepted
     * @param tradingOfferer Player which offered the Trade
     * @param trade          current Trade
     */
    public static void exchangeRessources(Player tradingPartner, Player tradingOfferer, Trade trade) {

        tradingOfferer.getInventory().addWood(trade.getWoodGet());
        tradingOfferer.getInventory().addWool(trade.getWoolGet());
        tradingOfferer.getInventory().addWheat(trade.getWheatGet());
        tradingOfferer.getInventory().addOre(trade.getOreGet());
        tradingOfferer.getInventory().addClay(trade.getClayGet());

        tradingOfferer.getInventory().removeWood(trade.getWoodGive());
        tradingOfferer.getInventory().removeWool(trade.getWoolGive());
        tradingOfferer.getInventory().removeWheat(trade.getWheatGive());
        tradingOfferer.getInventory().removeOre(trade.getOreGive());
        tradingOfferer.getInventory().removeClay(trade.getClayGive());

        tradingPartner.getInventory().addWood(trade.getWoodGive());
        tradingPartner.getInventory().addWool(trade.getWoolGive());
        tradingPartner.getInventory().addWheat(trade.getWheatGive());
        tradingPartner.getInventory().addOre(trade.getOreGive());
        tradingPartner.getInventory().addClay(trade.getClayGive());

        tradingPartner.getInventory().removeWood(trade.getWoodGet());
        tradingPartner.getInventory().removeWool(trade.getWoolGet());
        tradingPartner.getInventory().removeWheat(trade.getWheatGet());
        tradingPartner.getInventory().removeOre(trade.getOreGet());
        tradingPartner.getInventory().removeClay(trade.getClayGet());

        System.out.println(tradingOfferer.getInventory().getAllSupplies() + " curr ");
        System.out.println(tradingPartner.getInventory().getAllSupplies());
    }
}
