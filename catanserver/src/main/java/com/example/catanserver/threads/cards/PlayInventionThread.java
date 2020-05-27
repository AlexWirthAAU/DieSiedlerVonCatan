package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.cards.Invention;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Christina Senger
 * <p>
 * When playing an Invention Card, the Player get 2 of a Ressource his choice.
 */
public class PlayInventionThread extends GameThread {

    private Player player; // current Player
    private StringBuilder message = new StringBuilder(); // Message send to the User

    private String res;
    private String resName; // Name of the Ressource (german)

    /**
     * Contructor - The Name of the Ressource and the Player is set.
     * <p>
     * {@inheritDoc}
     *
     * @param res Name of the Ressource (english, lowercase)
     */
    public PlayInventionThread(User user, GameSession game, String res) {
        super(user, game);
        this.res = res;
        this.player = game.getPlayer(user.getUserId());
    }

    /**
     * When the Player can play the Card, his Inventory and the
     * GameSession are updated. A Message is built and send to
     * the User. The new GameSession is send broadcast. Else an
     * Error-Thread is started.
     */
    public void run() {

        if (Invention.checkCards(player)) {
            System.out.println("checked");
            resName = Invention.playCard(player, res);
            String mess = Invention.buildMessage(resName);
            game.nextPlayer();
            SendToClient.sendTradeMessage(user, mess);
            SendToClient.sendGameSessionBroadcast(game);

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Karte konnte nicht gespielt werden");
            errThread.run();
        }
    }
}
