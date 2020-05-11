package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;

import java.net.Socket;

public class BankThread extends GameThread {

    private String give;
    private String get;

    private Player currPlayer;
    private StringBuilder message = new StringBuilder();
    private String tradeStr;

    public BankThread(Socket connection, User user, GameSession game, String tradeStr) {
        super(connection, user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.tradeStr = tradeStr;
    }

    public void run() {

        setTradeData(tradeStr);

        if (checkTrade()) {

            String mess = buildMessage();
            exchangeRessources();
            game.nextPlayer();
            SendToClient.sendTradeMessage(connection, mess);
            SendToClient.sendGameSessionBroadcast(connection, game);

        } else {
            SendToClient.sendErrorMessage(connection, "Nicht genug Rohstoffe um zu handeln");
        }
    }

    private boolean checkTrade() {

        if (currPlayer.getInventory().canBankTrade) {
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
                invent = currPlayer.getInventory().getWool();
                break;
            case "Erz":
                invent = currPlayer.getInventory().getWool();
                break;
            case "Lehm":
                invent = currPlayer.getInventory().getWool();
                break;
            default:
                break;
        }

        return invent >= 4;
    }

    private void setTradeData(String tradeStr) {

        String[] trd = tradeStr.split("/");

        give = trd[0];
        get = trd[1];
    }

    private String buildMessage() {

        message.append("BANKTRADEMESSAGE/");
        message.append("Du hast erfolgreich 4 ").append(give).append(" gegen 1 ").append(get).append(" getauscht");

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
    }
}
