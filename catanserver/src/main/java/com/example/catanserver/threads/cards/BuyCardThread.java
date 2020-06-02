package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.devcards.DevCard;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.cards.Buy;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

import java.util.List;

/**
 * @author Christina Senger
 * <p>
 * This Thread handles the Buy of a DevCard.
 */
public class BuyCardThread extends GameThread {

    private List<DevCard> devCardStack;
    private Player player;

    private StringBuilder message = new StringBuilder(); // Message which is send to the User
    private String cardName; // the Name of the bought Card

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
     * When the Player can buy a Card, his Inventory and the
     * GameSession are updated. A Message is built and send to
     * the User. The new GameSession is send broadcast. Else an
     * Error-Thread is started.
     */
    public void run() {

        if (Buy.checkStack(player, game)) {

            System.out.println("checked");
            cardName = Buy.buyCard(player, devCardStack, game);
            String mess = Buy.buildMessage(cardName);
            game.nextPlayer();
            SendToClient.sendTradeMessage(user, mess);
            SendToClient.sendGameSessionBroadcast(game);

        } else {
            System.out.println("error");
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um Karten zu kaufen");
            errThread.run();
        }
    }
}
