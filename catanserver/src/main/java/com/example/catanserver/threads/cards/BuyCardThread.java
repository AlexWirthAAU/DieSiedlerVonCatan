package com.example.catanserver.threads.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.devcards.BuildStreetCard;
import com.example.catangame.devcards.DevCard;
import com.example.catangame.devcards.InventionCard;
import com.example.catangame.devcards.KnightCard;
import com.example.catangame.devcards.MonopolCard;
import com.example.catangame.devcards.VictoryPointCard;
import com.example.catanserver.User;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

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

            System.out.println("checked");
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

        System.out.println(devCardStack.size());

        if (devCardStack.size() > 23) {
            card = devCardStack.remove(0);
            System.out.println("first");
        } else {
            Random rand = new Random();
            card = devCardStack.remove(rand.nextInt(devCardStack.size()));
            System.out.println("random");
        }

        if (card instanceof BuildStreetCard) {
            player.getInventory().addBuildStreetCard(1);
            cardName = "Straßenbaukarte";
            System.out.println(cardName);

        } else if (card instanceof KnightCard) {
            player.getInventory().addKnightCard(1);
            cardName = "Ritterkarte";
            System.out.println(cardName);

        } else if (card instanceof InventionCard) {
            player.getInventory().addInventianCard(1);
            cardName = "Erfindungskarte";
            System.out.println(cardName);

        } else if (card instanceof MonopolCard) {
            player.getInventory().addMonopolCard(1);
            cardName = "Monopolkarte";
            System.out.println(cardName);

        } else if (card instanceof VictoryPointCard) {
            player.getInventory().addVictoryCard();
            player.getInventory().addVictoryPoints(1);
            cardName = "Siegpunktkarte";
            System.out.println(cardName);
        }
    }

    private String buildMessage() {

        message.append("CARDBUYMESSAGE/");
        message.append("Du hast eine ").append(cardName).append(" gekauft");

        if (cardName.equals("Siegpunktkarte")) {
            message.append(" und einen Siegpunkt erhalten");
        }
        System.out.println(message.toString());
        return message.toString();
    }
}
