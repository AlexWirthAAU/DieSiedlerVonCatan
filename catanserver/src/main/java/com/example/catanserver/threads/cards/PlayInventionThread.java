package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.cards.Invention;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * When playing an Invention Card, the Player get 2 of a Ressource his choice.
 */
public class PlayInventionThread extends GameThread {

    private static Logger logger = Logger.getLogger(PlayInventionThread.class.getName()); // Logger

    private Player player; // current Player

    private String res;

    /**
     * Constructor - The Name of the Resource and the Player is set.
     * <p>
     * {@inheritDoc}
     *
     * @param res Name of the Resource (english, lowercase)
     */
    public PlayInventionThread(User user, GameSession game, String res) {
        super(user, game);
        this.res = res;
        this.player = game.getPlayer(user.getUserId());
    }

    /**
     * If the Player can play the Card, his Inventory and the
     * GameSession are updated. The updated GameSession is broadcast and the
     * end turn command is sent to the user, containing a displayed message.
     * Additionally, the begin turn command is sent to the next user.
     * Otherwise an Error-Thread is started.
     */
    @Override
    public void run() {

        if (Invention.checkCards(player)) {
            logger.log(Level.INFO, "checked");
            // Name of the Resource (german)
            String resName = Invention.playCard(player, res);
            String mess = Invention.buildMessage(resName);
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
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Karte konnte nicht gespielt werden");
            errThread.run();
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
