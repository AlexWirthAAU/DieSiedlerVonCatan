package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;

import java.net.Socket;

public class PortThread extends GameThread {

    private Player currPlayer;
    private StringBuilder message = new StringBuilder();
    private String tradeStr;
    private String give;
    private String get;

    public PortThread(Socket connection, User user, GameSession game, String tradeStr) {
        super(connection, user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.tradeStr = tradeStr;
    }

    public void run() {

        setTradeData(tradeStr);

        if (checkTrade()) {

            String mess = buildMessage();
            exchangeRessources();
            SendToClient.sendTradeMessage(connection, mess);
            SendToClient.sendGameSessionBroadcast(connection, game);

        } else {
            SendToClient.sendErrorMessage(connection, "Nicht genug Rohstoffe um zu handeln");
        }
    }

    private boolean checkTrade() {

        boolean invent = false;

        if (currPlayer.getInventory().canPortTrade) {
            return false;
        }

        switch (give) {
            case "Holz":
                invent = currPlayer.getInventory().isWoodport();
                break;
            case "Wolle":
                invent = currPlayer.getInventory().isWoolport();
                break;
            case "Weizen":
                invent = currPlayer.getInventory().isWheatport();
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

    private void setTradeData(String tradeStr) {

        String[] trd = tradeStr.split("/");

        give = trd[0];
        get = trd[1];
    }

    private String buildMessage() {

        message.append("PORTTRADEMESSAGE/");
        message.append("Du hast erfolgreich 3 ").append(give).append(" gegen 1 ").append(get).append(" getauscht");

        return message.toString();
    }

    private void exchangeRessources() {

        switch (give) {
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

        switch (get) {
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
    }
}
