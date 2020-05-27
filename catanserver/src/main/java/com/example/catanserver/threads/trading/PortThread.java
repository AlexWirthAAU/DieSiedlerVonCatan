package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.trading.Port;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

public class PortThread extends GameThread {

    private String give; // Name of the offered Ressource
    private String get; // Name of the desired Ressource

    private Player currPlayer; // current Player
    private String tradeStr;

    /**
     * Constructor - Gets the current Player from the Game und sets the Trade-Offer.
     * <p>
     * {@inheritDoc}
     *
     * @param tradeStr Trade-Offer --> first is offered, second is desired Ressource, splitted by /
     */
    public PortThread(User user, GameSession game, String tradeStr) {
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

        Port.setTradeData(tradeStr);
        give = Port.getOffered();
        get = Port.getDesired();

        if (Port.checkTrade(currPlayer, get)) {

            System.out.println("checked");
            String mess = Port.buildMessage(give, get);
            Port.exchangeRessources(give, get, currPlayer);
            game.nextPlayer();
            SendToClient.sendTradeMessage(user, mess);
            SendToClient.sendGameSessionBroadcast(game);

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um zu handeln");
            errThread.run();
        }
    }
}
