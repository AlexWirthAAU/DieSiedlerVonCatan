package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.devcards.DevCard;
import com.example.catanserver.User;

public class PlayMonopolThread extends GameThread {
    private Player player;
    private StringBuilder message = new StringBuilder();
    private String cardName;
    private DevCard card;
    private String res;
    private String resName;
    private int number;

    public PlayMonopolThread(User user, GameSession game, String answerStr) {
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

        return player.getInventory().getMonopolCard() > 0;
    }

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

    private String buildMessage() {

        message.append("CARDPLAYMESSAGE/");
        message.append("Du hast eine Monopolkarte gespielt und ");
        message.append(number).append(" ").append(resName).append(" erhalten");

        return message.toString();
    }
}
