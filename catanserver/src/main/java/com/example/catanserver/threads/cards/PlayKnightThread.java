package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.devcards.BuildStreetCard;
import com.example.catangame.devcards.DevCard;
import com.example.catangame.devcards.InventionCard;
import com.example.catangame.devcards.KnightCard;
import com.example.catangame.devcards.MonopolCard;
import com.example.catanserver.User;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

public class PlayKnightThread extends GameThread {

    private Player player;
    private StringBuilder message = new StringBuilder();
    private String cardName;
    private DevCard card;

    public PlayKnightThread(User user, GameSession game, String answerStr) {
        super(user, game);
        this.cardName = answerStr;
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

        boolean ok = false;

        switch (cardName) {
            case "knight":
                if (player.getInventory().getKnightCard() >= 1) {
                    ok = true;
                    card = new KnightCard();
                }
                break;

            case "monopol":
                if (player.getInventory().getMonopolCard() >= 1) {
                    ok = true;
                    card = new MonopolCard();
                }
                break;

            case "invention":
                if (player.getInventory().getInventionCard() >= 1) {
                    ok = true;
                    card = new InventionCard();
                }
                break;

            case "buildStreet":
                if (player.getInventory().getBuildStreetCard() >= 1) {
                    ok = true;
                    card = new BuildStreetCard();
                }
                break;

            default:
                break;
        }

        return ok;
    }

    private void playCard() {

        if (card instanceof BuildStreetCard) {
            player.getInventory().removeBuildStreetCard(1);
            cardName = "Straßenbaukarte";

        } else if (card instanceof KnightCard) {
            player.getInventory().removeKnightCard(1);
            cardName = "Ritterkarte";

        } else if (card instanceof InventionCard) {
            player.getInventory().removeInventianCard(1);
            cardName = "Erfindungskarte";

        } else if (card instanceof MonopolCard) {
            player.getInventory().removeMonopolCard(1);
            cardName = "Monopolkarte";

        }
    }

    private String buildMessage() {

        message.append("CARDPLAYMESSAGE/");
        message.append("Du hast eine ").append(cardName).append(" gespielt");

        return message.toString();
    }
}
