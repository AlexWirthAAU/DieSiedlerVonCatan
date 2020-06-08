package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businesslogic.model.trading.Bank;
import com.example.catanserver.businesslogic.model.trading.TradingGeneral;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 */
public class BankThread extends GameThread {

    private static Logger logger = Logger.getLogger(BankThread.class.getName()); // Logger

    private Player currPlayer; // current Player
    private String tradeStr; // Trade-Offer
    private TradingGeneral tradingGeneral = new TradingGeneral();

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
     * GameSession are updated. The updated GameSession is broadcast and the
     * end turn command is sent to the user, containing a displayed message.
     * Additionally, the begin turn command is sent to the next user.
     * Otherwise an Error-Thread is started.
     */
    @Override
    public void run() {

        tradingGeneral.setTradeData(tradeStr);
        // Name of the offered Resource
        String give = tradingGeneral.getOffered();
        // Name of the desired Resource
        String get = tradingGeneral.getDesired();

        if (Bank.checkTrade(currPlayer, give)) {

            logger.log(Level.INFO, "checked");
            String mess = tradingGeneral.buildMessage(give, get, 4);
            tradingGeneral.exchangeResources(give, get, currPlayer, 4);
            game.nextPlayer();
            if(!endTurn()) {
                SendToClient.sendGameSessionBroadcast(game);
                SendToClient.sendStringMessage(user, SendToClient.HEADER_ENDTURN + " " + mess);
                User nextUser = Server.findUser(game.getCurr().getUserId());
                if (nextUser != null) {
                    SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
                }
            }

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um zu handeln");
            errThread.run();
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
