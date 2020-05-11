package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.devcards.BuildStreetCard;
import com.example.catangame.devcards.DevCard;
import com.example.catangame.devcards.InventionCard;
import com.example.catangame.devcards.KnightCard;
import com.example.catangame.devcards.MonopolCard;
import com.example.catangame.devcards.VictoryPointCard;
import com.example.catanserver.User;

import java.util.List;
import java.util.Random;

public class BuyCardThread extends GameThread {

    private List<DevCard> devCardStack;
    private Player player;
    private StringBuilder message = new StringBuilder();
    private String cardName;

    public BuyCardThread(User user, GameSession game) {
        super(user, game);
        this.devCardStack = game.getDevCards();
        this.player = game.getPlayer(user.getUserId());
    }

    public void run() {

        if (checkStack()) {

            buyCard();
            String mess = buildMessage();
            game.nextPlayer();
            SendToClient.sendTradeMessage(user, mess);
            SendToClient.sendGameSessionBroadcast(game);

        } else {
            ErrorThread errThread = new ErrorThread(user.getConnectionOutputStream(), "Nicht genug Rohstoffe um Karten zu kaufen");
            errThread.run();
        }
    }

    private boolean checkStack() {

        return (player.getInventory().getWool() < 1
                || player.getInventory().getOre() < 1
                || player.getInventory().getWheat() < 1
                || game.getDevCards().size() == 0);
    }

    private void buyCard() {

        DevCard card;

        player.getInventory().removeWool(1);
        player.getInventory().removeWheat(1);
        player.getInventory().removeOre(1);

        if (devCardStack.size() > 23) {
            card = devCardStack.remove(0);
        } else {
            Random rand = new Random();
            card = devCardStack.remove(rand.nextInt(devCardStack.size()));
        }

        if (card instanceof BuildStreetCard) {
            player.getInventory().addBuildStreetCard(1);
            cardName = "Straßenbaukarte";

        } else if (card instanceof KnightCard) {
            player.getInventory().addKnightCard(1);
            cardName = "Ritterkarte";

        } else if (card instanceof InventionCard) {
            player.getInventory().addInventianCard(1);
            cardName = "Erfindungskarte";

        } else if (card instanceof MonopolCard) {
            player.getInventory().addMonopolCard(1);
            cardName = "Monopolkarte";

        } else if (card instanceof VictoryPointCard) {
            player.getInventory().addVictoryCard();
            player.getInventory().addVictoryPoints(1);
            cardName = "Siegpunktkarte";
        }
    }

    private String buildMessage() {

        message.append("CARDBUYMESSAGE/");
        message.append("Du hast eine ").append(cardName).append(" gekauft");

        if (cardName.equals("Siegpunktkarte")) {
            message.append(" und einen Siegpunkt erhalten");
        }

        return message.toString();
    }
}
