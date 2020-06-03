package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.Trade;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.trading.StartTrade;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * This Thread handles the starting of a new Trade.
 */
public class TradeThread extends GameThread {

    private List<Player> potentialTradingPartners = new ArrayList<>(); // List of all potential Trading-Partner (have enough Ressources)

    private Map<String, Integer> offer = new HashMap<>(); // Map of the offered Ressources
    private Map<String, Integer> want = new HashMap<>(); // Map of the desired Ressources

    private Trade trade; // Representation of a Trade
    private GameSession gs; // current Game

    private Player currPlayer; // current Player
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
        this.gs = game;
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

        StartTrade startTrade = new StartTrade();

        startTrade.setTradeData(tradeStr);
        offer = startTrade.getOffered();
        want = startTrade.getDesired();

        if (startTrade.checkTrade(offer, currPlayer)) {

            System.out.println("checked");
            potentialTradingPartners = startTrade.checkAndSetTradingPartners(game, want,currPlayer);

            if (potentialTradingPartners.size() == 0) {
                game.nextPlayer();
                SendToClient.sendGameSessionBroadcast(game);
                SendToClient.sendStringMessage(user, SendToClient.HEADER_ENDTURN + " Keine Handelspartner");
                User nextUser = Server.findUser(game.getCurr().getUserId());
                if (nextUser != null) {
                    SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
                }
            } else {
                String mess = startTrade.buildMessage(currPlayer, offer, want);
                startTrade.setTrade(offer, want, currPlayer, potentialTradingPartners, mess, gs);

                SendToClient.sendGameSessionBroadcast(game);
                SendToClient.sendStringMessage(potentialTradingPartners,SendToClient.HEADER_TRADE + " " + mess);
            }

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(),"Nicht genug Rohstoffe um zu handeln");
            errThread.run();
        }
    }
}
