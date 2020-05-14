package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;
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

        if (checkCards()) {

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
     * @return true, when the Player has an Monopol Card, else false
     */
    private boolean checkCards() {

        return player.getInventory().getMonopolCard() > 0;
    }

    /**
     * Depending on the Name of the desired Ressource, all of this Ressource
     * is removed from the other Players Inventory and added to the
     * current Players Inventory. The Monopol Card is removed.
     */
    private void playCard() {

        switch (res) {
            case "wood":
                for (Player p : game.getPlayers()) {
                    number += p.getInventory().removeAllWood();
                }
                player.getInventory().addWood(number);
                resName = "Holz";
                break;

            case "wool":
                for (Player p : game.getPlayers()) {
                    number += p.getInventory().removeAllWool();
                }
                resName = "Wolle";
                player.getInventory().addWool(number);
                break;

            case "wheat":
                for (Player p : game.getPlayers()) {
                    number += p.getInventory().removeAllWheat();
                }
                player.getInventory().addWheat(number);
                resName = "Weizen";
                break;

            case "ore":
                for (Player p : game.getPlayers()) {
                    number += p.getInventory().removeAllOre();
                }
                player.getInventory().addOre(number);
                resName = "Erz";
                break;

            case "clay":
                for (Player p : game.getPlayers()) {
                    number += p.getInventory().removeAllClay();
                }
                player.getInventory().addClay(number);
                resName = "Lehm";
                break;

            default:
                break;
        }

        player.getInventory().removeMonopolCard(1);
    }

    /**
     * Creates a Message, specific to the Name of the desired Ressource,
     * and appends it to a StringBuilder.
     *
     * @return the StringBuilder as a String
     */
    private String buildMessage() {

        message.append("CARDPLAYMESSAGE/");
        message.append("Du hast eine Monopolkarte gespielt und ");
        message.append(number).append(" ").append(resName).append(" erhalten");

        return message.toString();
    }
}
