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

public class PlayBuildStreetThread extends GameThread {

    private Player player;
    private StringBuilder message = new StringBuilder();
    private String cardName;
    private DevCard card;

    public PlayBuildStreetThread(User user, GameSession game) {
        super(user, game);
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

        return player.getInventory().getBuildStreetCard() >= 1;
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
        message.append("Du hast eine Ritterkarte gespielt").append(cardName).append(" gespielt");

        return message.toString();
    }
}
