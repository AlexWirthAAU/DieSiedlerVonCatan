package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.devcards.DevCard;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businesslogic.model.cards.Buy;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * This Thread handles the Buy of a DevCard.
 */
public class BuyCardThread extends GameThread {

    private static Logger logger = Logger.getLogger(BuyCardThread.class.getName()); // Logger
    private List<DevCard> devCardStack;
    private Player player;

    /**
     * Constructor - Gets the DevCard-Stack from the Game
     * and the current Player.
     *
     * {@inheritDoc}
     */
    public BuyCardThread(User user, GameSession game) {
        super(user, game);
        this.devCardStack = game.getDevCards();
        this.player = game.getPlayer(user.getUserId());
    }

    /**
     * If the Player can buy a Card, his Inventory and the
     * GameSession are updated. The updated GameSession is broadcast and the end turn command is
     * sent to the user, containing a displayed message. Additionally the begin turn command is
     * sent to the next user.
     * Otherwise an Error-Thread is started.
     */
    @Override
    public void run() {

        if (Buy.checkStack(player, game)) {

            logger.log(Level.INFO, "checked");
            // the Name of the bought Card
            String cardName = Buy.buyCard(player, devCardStack, game);
            String mess = Buy.buildMessage(cardName);
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
            logger.log(Level.SEVERE, "error");
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um Karten zu kaufen");
            errThread.run();
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
