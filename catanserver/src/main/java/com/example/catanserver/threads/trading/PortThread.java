package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 */
public class PortThread extends GameThread {

    private String give; // Name of the offered Ressource
    private String get; // Name of the desired Ressource

    private Player currPlayer; // current Player
    private StringBuilder message = new StringBuilder(); // Message which should be sent to the Player
    private String tradeStr;

    /**
     * Constructor - Gets the current Player from the Game und sets the Trade-Offer.
     * <p>
     * {@inheritDoc}
     *
     * @param tradeStr Trade-Offer --> first is offered, second is desired Ressource, splitted by /
     */
    public PortThread(User user, GameSession game, String tradeStr) {
        super(user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.tradeStr = tradeStr;
    }

    /**
     * When the Player can Trade, his Inventory and the
     * GameSession are updated.The updated GameSession is broadcast and the
     * end turn command is sent to the user, containing a displayed message.
     * Additionally, the begin turn command is sent to the next user.
     * Otherwise an Error-Thread is started.
     */
    public void run() {

        setTradeData(tradeStr);

        if (checkTrade()) {

            System.out.println("checked");
            String mess = buildMessage();
            exchangeRessources();
            game.nextPlayer();
            endTurn();
            SendToClient.sendGameSessionBroadcast(game);
            SendToClient.sendStringMessage(user, SendToClient.HEADER_ENDTURN + " " + mess);
            User nextUser = Server.findUser(game.getCurr().getUserId());
            if(nextUser != null) {
                SendToClient.sendStringMessage(nextUser, SendToClient.HEADER_BEGINTURN);
            }

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um zu handeln");
            errThread.run();
        }
    }

    /**
     * @return true, when the Player <code>canPortTrade</code> and has
     * at least 3 of the offered Ressource, else false.
     */
    private boolean checkTrade() {

        boolean invent = false;

        if (!currPlayer.getInventory().canPortTrade || !currPlayer.getInventory().hasPorts) {
            System.out.println("firstcheck");
            return false;
        }

        switch (get) {
            case "Holz":
                invent = currPlayer.getInventory().isWoodport();
                break;
            case "Wolle":
                invent = currPlayer.getInventory().isWoolport();
                break;
            case "Weizen":
                invent = currPlayer.getInventory().isWheatport();
                System.out.println("portcheck");
                break;
            case "Erz":
                invent = currPlayer.getInventory().isOreport();
                break;
            case "Lehm":
                invent = currPlayer.getInventory().isClayport();
                break;
            default:
                break;
        }

        return invent;
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

        message.append("Du hast erfolgreich 3 ").append(give).append(" gegen 1 ").append(get).append(" getauscht");
        System.out.println(message.toString());
        return message.toString();
    }

    /**
     * Depending on the Name of the Ressources, the desired Ressource
     * is increased by one and the offered Ressource is decreased by 3.
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
                currPlayer.getInventory().removeWood(3);
                break;
            case "Wolle":
                currPlayer.getInventory().removeWool(3);
                break;
            case "Weizen":
                currPlayer.getInventory().removeWheat(3);
                break;
            case "Erz":
                currPlayer.getInventory().removeOre(3);
                break;
            case "Lehm":
                currPlayer.getInventory().removeClay(3);
                break;
            default:
                break;
        }

        System.out.println("3 " + give + " gegen 1 " + get);
        System.out.println(currPlayer.getInventory().getAllRessources());
    }
}
