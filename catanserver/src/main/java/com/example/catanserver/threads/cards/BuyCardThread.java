package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.devcards.DevCard;
import com.example.catangame.devcards.InventionCard;
import com.example.catangame.devcards.KnightCard;
import com.example.catangame.devcards.MonopolCard;
import com.example.catangame.devcards.VictoryPointCard;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.cards.Buy;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

import java.util.List;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
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
     * If the Player can buy a Card, his Inventory and the
     * GameSession are updated. The updated GameSession is broadcast and the end turn command is
     * sent to the user, containing a displayed message. Additionally the begin turn command is
     * sent to the next user.
     * Otherwise an Error-Thread is started.
     */
    public void run() {

        if (Buy.checkStack(player, game)) {

            System.out.println("checked");
            cardName = Buy.buyCard(player, devCardStack, game);
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
            System.out.println("error");
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um Karten zu kaufen");
            errThread.run();
        }
    }
}
