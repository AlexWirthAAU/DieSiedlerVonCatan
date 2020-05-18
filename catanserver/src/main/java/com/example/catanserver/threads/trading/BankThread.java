package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Christina Senger
 */
public class BankThread extends GameThread {

    private String give; // Name of the offerd Ressource
    private String get; // Name of the desired Ressource

    private Player currPlayer; // current Player
    private StringBuilder message = new StringBuilder(); // Message which should be sent to the Player
    private String tradeStr; // Trade-Offer

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
     * GameSession are updated. A Message is built and send to
     * the User. The new GameSession is send broadcast. Else an
     * Error-Thread is started.
     */
    public void run() {

        setTradeData(tradeStr);

        if (checkTrade()) {

            System.out.println("checked");
            String mess = buildMessage();
            exchangeRessources();
            game.nextPlayer();
            SendToClient.sendTradeMessage(user, mess);
            SendToClient.sendGameSessionBroadcast(game);

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um zu handeln");
            errThread.run();
        }
    }

    /**
     * @return true, when the Player <code>canBankTrade</code> and has
     * at least 4 of the offered Ressource, else false.
     */
    private boolean checkTrade() {

        if (!currPlayer.getInventory().canBankTrade) {
            return false;
        }

        int invent = -1;

        switch (give) {
            case "Holz":
                invent = currPlayer.getInventory().getWood();
                break;
            case "Wolle":
                invent = currPlayer.getInventory().getWool();
                break;
            case "Weizen":
                invent = currPlayer.getInventory().getWheat();
                break;
            case "Erz":
                invent = currPlayer.getInventory().getOre();
                break;
            case "Lehm":
                invent = currPlayer.getInventory().getClay();
                break;
            default:
                break;
        }

        return invent >= 4;
    }

    /**
     * The Trade-Offer is splitted by /. The first Elements
     * is the offered, the second is the desired Ressource.
     *
     * @param tradeStr Trade-Offer sent from Client
     */
    private void setTradeData(String tradeStr) {

        String[] trd = tradeStr.split("/");

        give = trd[0];
        get = trd[1];
        System.out.println(give + " give " + get + " get");
    }

    /**
     * Creates a Message, specific to the Name of the Ressources,
     * and appends it to a StringBuilder.
     *
     * @return the StringBuilder as a String
     */
    private String buildMessage() {

        message.append("BANKTRADEMESSAGE/");
        message.append("Du hast erfolgreich 4 ").append(give).append(" gegen 1 ").append(get).append(" getauscht");

        System.out.println(message.toString());
        return message.toString();
    }

    /**
     * Depending on the Name of the Ressources, the desired Ressource
     * is increased by one and the offered Ressource is decreased by 4.
     */
    private void exchangeRessources() {

        switch (get) {
            case "Holz":
                currPlayer.getInventory().addWood(1);
                break;
            case "Wolle":
                currPlayer.getInventory().addWool(1);
                break;
            case "Weizen":
                currPlayer.getInventory().addWheat(1);
                break;
            case "Erz":
                currPlayer.getInventory().addOre(1);
                break;
            case "Lehm":
                currPlayer.getInventory().addClay(1);
                break;
            default:
                break;
        }

        switch (give) {
            case "Holz":
                currPlayer.getInventory().removeWood(4);
                break;
            case "Wolle":
                currPlayer.getInventory().removeWool(4);
                break;
            case "Weizen":
                currPlayer.getInventory().removeWheat(4);
                break;
            case "Erz":
                currPlayer.getInventory().removeOre(4);
                break;
            case "Lehm":
                currPlayer.getInventory().removeClay(4);
                break;
            default:
                break;
        }

        System.out.println("4 " + give + " gegen 1 " + get);
        System.out.println(currPlayer.getInventory().getAllRessources());
    }
}
