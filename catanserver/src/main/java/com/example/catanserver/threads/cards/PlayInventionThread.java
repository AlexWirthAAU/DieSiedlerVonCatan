package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;
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

        if (checkCards()) {
            System.out.println("checked");
            playCard();
            String mess = buildMessage();
            game.nextPlayer();
            SendToClient.sendTradeMessage(user, mess);
            SendToClient.sendGameSessionBroadcast(game);

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Karte konnte nicht gespielt werden");
            errThread.run();
        }
    }

    /**
     * @return true, when the Player has an Invention Card, else false
     */
    private boolean checkCards() {

        return player.getInventory().getInventionCard() != 0;
    }

    /**
     * Depending on the Name of the desired Ressource, the Value of
     * the Ressource is increases by 2. The Invention Card is
     * removed from the Players Inventory.
     */
    private void playCard() {

        System.out.println(player.getInventory().getAllSupplies());
        switch (res) {
            case "wood":
                player.getInventory().addWood(2);
                resName = "Holz";
                break;

            case "wool":
                resName = "Wolle";
                player.getInventory().addWool(2);
                break;

            case "wheat":
                player.getInventory().addWheat(2);
                resName = "Weizen";
                break;

            case "ore":
                player.getInventory().addOre(2);
                resName = "Erz";
                break;

            case "clay":
                player.getInventory().addClay(2);
                resName = "Lehm";
                break;

            default:
                break;
        }

        player.getInventory().removeInventianCard(1);
        System.out.println(player.getInventory().getAllSupplies() + " after");
    }

    /**
     * Creates a Message, specific to the Name of the desired Ressource,
     * and appends it to a StringBuilder.
     *
     * @return the StringBuilder as a String
     */
    private String buildMessage() {

        message.append("CARDPLAYMESSAGE/");
        message.append("Du hast eine Erfindungskarte gespielt und zwei ");
        message.append(resName).append(" erhalten");

        System.out.println(message.toString());
        return message.toString();
    }
}
