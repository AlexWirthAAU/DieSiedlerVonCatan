package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.trading.Bank;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Christina Senger
 */
public class BankThread extends GameThread {

    private String give; // Name of the offerd Ressource
    private String get; // Name of the desired Ressource

    private Player currPlayer; // current Player
    private StringBuilder message = new StringBuilder(); // Message which should be sent to the Player
    private String tradeStr; // Trade-Offer

    /**
     * Constructor - Gets the current Player from the Game und sets the Trade-Offer.
     * <p>
     * {@inheritDoc}
     *
     * @param tradeStr Trade-Offer --> first is offered, second is desired Ressource, splitted by /
     */
    public BankThread(User user, GameSession game, String tradeStr) {
        super(user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.tradeStr = tradeStr;
    }

    /**
     * When the Player can Trade, his Inventory and the
     * GameSession are updated. A Message is built and send to
     * the User. The new GameSession is send broadcast. Else an
     * Error-Thread is started.
     */
    public void run() {

        Bank.setTradeData(tradeStr);
        give = Bank.getOffered();
        get = Bank.getDesired();

        if (Bank.checkTrade(currPlayer, give)) {

            System.out.println("checked");
            String mess = Bank.buildMessage(give, get);
            Bank.exchangeRessources(give, get, currPlayer);
            game.nextPlayer();
            SendToClient.sendTradeMessage(user, mess);
            SendToClient.sendGameSessionBroadcast(game);

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um zu handeln");
            errThread.run();
        }
    }
}
