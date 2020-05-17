package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.Trade;
import com.example.catanserver.User;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christina Senger
 * <p>
 * This Thread handles the Answers of the Players to the current active Trade.
 */
public class TradeAnswerThread extends GameThread {

    private Trade trade; // Trade which was answered to
    private Player currPlayer; // current Player
    private Player tradingPartner; // Trading-Partner

    private String answerStr;

    /**
     * Constructor - Gets the current Player from the Game und sets the Trade-Offer.
     *
     * {@inheritDoc}
     *
     * @param answerStr Answer --> accept or dismiss
     */
    public TradeAnswerThread(User user, GameSession game, String answerStr) {
        super(user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.answerStr = answerStr;
        this.trade = game.getTrade();
    }

    /**
     * When there is a Answer, the Player is added to the List of the
     * answered Players and his Answer is added as Boolean to the Answers-Map.
     * When all Players have answered, the first one which answered with
     * accepted is set as Trading-Partner.
     */
    public void run() {
        if (answerStr.equals("accepted")) {
            trade.addAnsweredPlayer(currPlayer);
            trade.addAnswers(currPlayer, true);
        } else {
            trade.addAnsweredPlayer(currPlayer);
            trade.addAnswers(currPlayer, false);
        }

        System.out.println(trade.getAnsweredPlayers() + " " + trade.getAnswers());

        if (trade.getAnsweredPlayers().size() == trade.getPotentialTradingPartners().size()) {

            for (Player p : trade.getAnsweredPlayers()) {
                if (trade.getAnswers().get(p)) {
                    this.tradingPartner = p;
                    break;
                }
            }

            setAnswerList();
        }
    }

    /**
     * When no Trading-Partner could be found, the current Player and all potential
     * Trading-Partners are added to the List of Player, to which a Message should be send.
     * The Answer-Message is set and distributed to all Player in the List.
     *
     * Else, the current Player and the Player who first answered with accept are added
     * to the to the List of Player, to which a Message should be send.
     * The Ressources are exchanged, the Answer-Message is set and distributed to all Player in the List.
     */
    private void setAnswerList() {

        List<Player> toSend = new ArrayList<>();

        if (tradingPartner == null) {
            toSend.add(trade.getCurrPlayer());

            trade.setAnswerMessage("TRADEANSWER/Leider keine Handelspartner");
            distribute(toSend, "TRADEANSWER/Leider keine Handelspartner");

        } else {

            toSend.add(trade.getCurrPlayer());

            for (Player p : trade.getAnsweredPlayers()) {
                if (trade.getAnswers().get(p)) {
                    toSend.add(p);
                }
            }

            exchangeRessources();
            trade.setAnswerMessage("TRADEANSWER/Handel zwischen " + trade.getCurrPlayer().getDisplayName() + " und " + trade.getTradingPartner().getDisplayName() + " durchgeführt");
            System.out.println(trade.getAnswerMessage());
            distribute(toSend, "TRADEANSWER/Handel zwischen " + trade.getCurrPlayer().getDisplayName() + " und " + trade.getTradingPartner().getDisplayName() + " durchgeführt");
        }
    }

    /**
     * The offered Ressources are removed from the current Players Inventory
     * and added to the Trading Partners Inventory.
     * The desired Ressources are added to the current Players Inventory
     * and removed from the Trading Partners Inventory.
     */
    private void exchangeRessources() {

        System.out.println(currPlayer.getInventory().getAllRessources() + " curr " + tradingPartner.getInventory().getAllRessources());

        currPlayer.getInventory().addWood(trade.getWoodGet());
        currPlayer.getInventory().addWool(trade.getWoolGet());
        currPlayer.getInventory().addWheat(trade.getWheatGet());
        currPlayer.getInventory().addOre(trade.getOreGet());
        currPlayer.getInventory().addClay(trade.getClayGet());

        currPlayer.getInventory().removeWood(trade.getWoodGive());
        currPlayer.getInventory().removeWool(trade.getWoolGive());
        currPlayer.getInventory().removeWheat(trade.getWheatGive());
        currPlayer.getInventory().removeOre(trade.getOreGive());
        currPlayer.getInventory().removeClay(trade.getClayGive());

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

        System.out.println(currPlayer.getInventory().getAllRessources() + " curr " + tradingPartner.getInventory().getAllRessources());
    }

    /**
     * Send the Trade-Message to all potential Trading-Partner.
     *
     * @param playersToSend List of Player, to which to Message should be sent
     * @param mess Message to send
     */
    private void distribute(List<Player> playersToSend, String mess) {
        game.setTrade(null);
        game.nextPlayer();
        SendToClient.sendTradeMessageBroadcast(playersToSend, mess);
        SendToClient.sendGameSessionBroadcast(game);
    }
}
