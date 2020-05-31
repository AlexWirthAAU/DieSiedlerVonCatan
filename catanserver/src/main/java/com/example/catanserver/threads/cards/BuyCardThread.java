package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.devcards.BuildStreetCard;
import com.example.catangame.devcards.DevCard;
import com.example.catangame.devcards.InventionCard;
import com.example.catangame.devcards.KnightCard;
import com.example.catangame.devcards.MonopolCard;
import com.example.catangame.devcards.VictoryPointCard;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

import java.util.List;
import java.util.Random;

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

        if (checkStack()) {

            System.out.println("checked");
            buyCard();
            String mess = buildMessage();
            game.nextPlayer();
            endTurn();
            SendToClient.sendGameSessionBroadcast(game);
            SendToClient.sendStringMessage(user, SendToClient.HEADER_ENDTURN + " " + mess);
            User nextUser = Server.findUser(game.getCurr().getUserId());
            if(nextUser != null) {
                SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
            }

        } else {
            System.out.println("error");
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um Karten zu kaufen");
            errThread.run();
        }
    }

    /**
     * @return true, when there are DevCards left and the Player has enough Ressources to buy on, else false
     */
    private boolean checkStack() {
        System.out.println(player.getDisplayName() + " has" + player.getInventory().getWool()
                + " Wolle " + player.getInventory().getOre() + " Erz " + player.getInventory().getWheat() + " Weizen "
                + " und es gibt " + game.getDevCards().size() + " Karten");

        return (player.getInventory().getWool() != 0
                && player.getInventory().getOre() != 0
                && player.getInventory().getWheat() != 0
                && game.getDevCards().size() != 0);
    }

    /**
     * The Ressources are from the Players Inventory.
     * When the Stack is greate than 23 (first 3 Cards), the first
     * Card is bought, else a random Card is bought. The Card is
     * removed from the Stack, added to the Players Inventory and its Name is stored.
     */
    private void buyCard() {

        DevCard card;
        System.out.println(player.getInventory().getAllSupplies());

        player.getInventory().removeWool(1);
        player.getInventory().removeWheat(1);
        player.getInventory().removeOre(1);

        System.out.println(devCardStack.size());

        if (devCardStack.size() > 23) {
            card = devCardStack.remove(0);
            System.out.println("first");
        } else {
            Random rand = new Random();
            card = devCardStack.remove(rand.nextInt(devCardStack.size()));
            System.out.println("random");
        }

        if (card instanceof BuildStreetCard) {
            player.getInventory().addBuildStreetCard(1);
            cardName = "Strassenbaukarte";
            System.out.println(cardName);

        } else if (card instanceof KnightCard) {
            player.getInventory().addKnightCard(1);
            cardName = "Ritterkarte";
            System.out.println(cardName);

        } else if (card instanceof InventionCard) {
            player.getInventory().addInventianCard(1);
            cardName = "Erfindungskarte";
            System.out.println(cardName);

        } else if (card instanceof MonopolCard) {
            player.getInventory().addMonopolCard(1);
            cardName = "Monopolkarte";
            System.out.println(cardName);

        } else if (card instanceof VictoryPointCard) {
            player.getInventory().addVictoryCard();
            cardName = "Siegpunktkarte";
            System.out.println(cardName);
        }
        System.out.println(player.getInventory().getAllSupplies());
    }

    /**
     * Creates a Message, specific to the Type of the bought Card,
     * and appends it to a StringBuilder.
     *
     * @return the StringBuilder as a String
     */
    private String buildMessage() {

        message.append("Du hast eine ").append(cardName).append(" gekauft");

        if (cardName.equals("Siegpunktkarte")) {
            message.append(" und einen Siegpunkt erhalten");
        }
        System.out.println(message.toString());
        return message.toString();
    }
}
