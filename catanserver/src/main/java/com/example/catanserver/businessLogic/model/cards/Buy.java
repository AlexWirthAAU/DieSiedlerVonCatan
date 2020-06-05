package com.example.catanserver.businessLogic.model.cards;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.devcards.BuildStreetCard;
import com.example.catangame.devcards.DevCard;
import com.example.catangame.devcards.InventionCard;
import com.example.catangame.devcards.KnightCard;
import com.example.catangame.devcards.MonopolCard;
import com.example.catangame.devcards.VictoryPointCard;
import com.example.catanserver.businessLogic.model.KnightPower;

import java.util.List;
import java.util.Random;

/**
 * @author Christina Senger
 * <p>
 * Class makes logic for buying a Card
 */
public class Buy {

    private static Random rand = new Random();

    /**
     * @param player current Player
     * @param game   current Game
     * @return true, when there are DevCards left and the Player has enough Ressources to buy on, else false
     */
    public static boolean checkStack(Player player, GameSession game) {

        return (player.getInventory().getWool() != 0
                && player.getInventory().getOre() != 0
                && player.getInventory().getWheat() != 0
                && game.getDevCards().size() != 0);
    }

    /**
     * The Ressources are from the Players Inventory.
     * When the Stack is greate than 23 (first 3 Cards), the first
     * Card is bought, else a random Card is bought. The Card is
     * removed from the Stack, added to the Players Inventory and its Name is stored.
     *
     * @param player       current Player
     * @param devCardStack current Stack of Cards
     * @return cardName Name of the bought card
     */
    public static String buyCard(Player player, List<DevCard> devCardStack, GameSession game) {

        String cardName = null;

        DevCard card;
        System.out.println(player.getInventory().getAllSupplies());

        player.getInventory().removeWool(1);
        player.getInventory().removeWheat(1);
        player.getInventory().removeOre(1);

        System.out.println(devCardStack.size());

        if (devCardStack.size() > 23) {
            card = devCardStack.remove(0);
            System.out.println("first");
        } else {
            card = devCardStack.remove(rand.nextInt(devCardStack.size()));
            System.out.println("random");
        }

        if (card instanceof BuildStreetCard) {
            player.getInventory().addBuildStreetCard(1);
            cardName = "Strassenbaukarte";
            System.out.println(cardName);

        } else if (card instanceof KnightCard) {
            player.getInventory().addKnightCard(1);
            cardName = "Ritterkarte";
            KnightPower.checkKnightPowerOnBuy(game, player.getInventory().getKnightCard(), player);
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
            cardName = "Siegpunktkarte";
            System.out.println(cardName);
        }
        System.out.println(player.getInventory().getAllSupplies());
        return cardName;
    }

    /**
     * Creates a Message, specific to the Type of the bought Card,
     * and appends it to a StringBuilder.
     *
     * @param cardName Name of the bought Card
     * @return the StringBuilder as a String
     */
    public static String buildMessage(String cardName) {

        StringBuilder message = new StringBuilder();

        message.append("Du hast eine ").append(cardName).append(" gekauft");

        if (cardName.equals("Siegpunktkarte")) {
            message.append(" und einen Siegpunkt erhalten");
        }

        System.out.println(message.toString());
        return message.toString();
    }
}
