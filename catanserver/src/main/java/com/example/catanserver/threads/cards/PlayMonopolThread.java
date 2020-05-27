package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.cards.Monopol;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

public class PlayMonopolThread extends GameThread {

    private Player player; // current Player
    private StringBuilder message = new StringBuilder(); // Message send to the User

    private String res;
    private String resName; // Name of the Ressource (german)

    private int number; // Number of how many of a Ressource to Player gets

    /**
     * Contructor - The Name of the Ressource and the Player is set.
     * <p>
     * {@inheritDoc}
     *
     * @param res Name of the Ressource (english, lowercase)
     */
    public PlayMonopolThread(User user, GameSession game, String res) {
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

        if (Monopol.checkCards(player)) {
            System.out.println("checked");
            resName = Monopol.playCard(player, res, game);
            number = Monopol.getNumber();
            String mess = Monopol.buildMessage(resName, number);
            game.nextPlayer();
            SendToClient.sendTradeMessage(user, mess);
            SendToClient.sendGameSessionBroadcast(game);

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Karte konnte nicht gespielt werden");
            errThread.run();
        }
    }
}
