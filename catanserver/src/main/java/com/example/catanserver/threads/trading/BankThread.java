package com.example.catanserver.threads.trading;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

public class BankThread extends GameThread {

    private String give;
    private String get;

    private Player currPlayer;
    private StringBuilder message = new StringBuilder();
    private String tradeStr;

    public BankThread(User user, GameSession game, String tradeStr) {
        super(user, game);
        this.currPlayer = game.getPlayer(user.getUserId());
        this.tradeStr = tradeStr;
    }

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

    private void setTradeData(String tradeStr) {

        String[] trd = tradeStr.split("/");

        give = trd[0];
        get = trd[1];
        System.out.println(give + " give " + get + " get");
    }

    private String buildMessage() {

        message.append("BANKTRADEMESSAGE/");
        message.append("Du hast erfolgreich 4 ").append(give).append(" gegen 1 ").append(get).append(" getauscht");

        System.out.println(message.toString());
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

        System.out.println("4 " + give + " gegen 1 " + get);
        System.out.println(currPlayer.getInventory().getAllRessources());
    }
}
