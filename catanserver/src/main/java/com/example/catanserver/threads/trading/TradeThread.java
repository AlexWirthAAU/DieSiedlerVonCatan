package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.Trade;
import com.example.catanserver.User;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeThread extends GameThread {

    private List<Player> potentialTradingPartners = new ArrayList<>(); // List of all potential Trading-Partner (have enough Ressources)

    private Map<String, Integer> offer = new HashMap<>(); // Map of the offered Ressources
    private Map<String, Integer> want = new HashMap<>(); // Map of the desired Ressources

    private Trade trade; // Representation of a Trade
    private GameSession game; // current Game

    private Player currPlayer; // current Player
    private StringBuilder message = new StringBuilder(); // Message which should be sent to the Player
    private String tradeStr; // Trade-Offer

    /**
     * Constructor - Gets the current Player from the Game und sets the Trade-Offer.
     * <p>
     * {@inheritDoc}
     *
     * @param tradeStr Trade-Offer --> first are offered, second are desired Ressources and their Amount, splitted by /
     */
    public TradeThread(User user, GameSession game, String tradeStr) {
        super(user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.game = game;
        this.tradeStr = tradeStr;
    }

    /**
     * Sets the Trade. When the Player can trade, the Opponents are checked
     * for potential Trading-Partners. A Message is built. A new Trade is
     * created with all those Data and set as Trade of the Game. The Message
     * is distributed to all potential Trading-Partners. Else, an Error-Thread
     * is created.
     */
    public void run() {

        setTradeData(tradeStr);

        if (checkTrade(offer)) {

            System.out.println("checked");
            checkAndSetTradingPartners(game, want);
            String mess = buildMessage();
            this.trade = new Trade(offer, want, currPlayer, potentialTradingPartners, mess, game);
            this.game.setTrade(this.trade);
            this.game.setIsTradeOn(true);

            distribute(potentialTradingPartners, mess);

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um zu handeln");
            errThread.run();
        }
    }

    /**
     * Send the Trade-Message and to the to all potential Trading-Partner.
     *
     * @param playersToSend List of Player, to which to Message should be sent
     * @param mess Message to send
     */
    private void distribute(List<Player> playersToSend, String mess) {
        SendToClient.sendTradeMessageBroadcast(playersToSend, mess, this.game);
    }

    /**
     * @param offer Map with the offer Ressources
     * @return true, when the Player <code>canTrade</code> and has
     * at least the number of offered Ressources, else false.
     */
    private boolean checkTrade(Map<String, Integer> offer) {

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
     * @param game current Game
     * @param want Map with the desired Ressources
     */
    private void checkAndSetTradingPartners(GameSession game, Map<String, Integer> want) {

        List<Player> players = game.getPlayers();

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
    }

    /**
     * The Trade-Offer is splitted by /. The first Elements
     * are the offered, the second are the desired Ressources.
     * It adds those to Maps with the Name as Key and the Number
     * as Value.
     *
     * @param tradeStr Trade-Offer sent from Client
     */
    private void setTradeData(String tradeStr) {

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
     * Creates a Message, specific to the Name and Amount
     * of the Ressources and appends it to a StringBuilder.
     *
     * @return the StringBuilder as a String
     */
    private String buildMessage () {

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
