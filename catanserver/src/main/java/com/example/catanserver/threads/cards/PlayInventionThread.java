package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.User;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

public class PlayInventionThread extends GameThread {

    private Player player;
    private StringBuilder message = new StringBuilder();
    private String res;
    private String resName;

    public PlayInventionThread(User user, GameSession game, String answerStr) {
        super(user, game);
        this.res = answerStr;
        this.player = game.getPlayer(user.getUserId());
    }

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

    private boolean checkCards() {

        return player.getInventory().getInventionCard() > 0;
    }

    private void playCard() {

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
    }

    private String buildMessage() {

        message.append("CARDPLAYMESSAGE/");
        message.append("Du hast eine Erfindungskarte gespielt und zwei ");
        message.append(resName).append(" erhalten");

        return message.toString();
    }
}
