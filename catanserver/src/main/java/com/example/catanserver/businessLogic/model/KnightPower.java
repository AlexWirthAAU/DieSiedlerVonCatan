package com.example.catanserver.businessLogic.model;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.threads.SendToClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christina Senger
 * <p>
 * Class is executed, when a Player buys or plays a Knight-Card.
 * It checks for the greatest Knightpower.
 */
public class KnightPower {

    /**
     * When the Player has less or equal Cards than the one with the greatest Knightpower
     * the updated Gamesession is send. Else it now has the greatest Knightpower and the
     * Variables are set. He gets a Victory Point and one Victory Point is removed from
     * the last Owner. A Message is send to the old and the new Owner and a Gamesession
     * is send to all Players.
     *
     * @param game                 current Game
     * @param knightCardCount      Number of Knight Cards the Player has
     * @param knightPowerCandidate Player was has bought a Knight-Card
     */
    public void checkKnightPowerOnBuy(GameSession game, int knightCardCount, Player knightPowerCandidate) {

        int knightPowerCount = game.getKnightPowerCount();
        Player knightCountOwner = game.getKnightPowerOwner();
        int candidateId = knightPowerCandidate.getUserId();
        int ownerId = knightCountOwner.getUserId();

        StringBuilder builder = new StringBuilder();
        builder.append(SendToClient.HEADER_KNIGHT).append(" ");
        builder.append(knightPowerCandidate.getDisplayName()).append(" hat jetzt die größte Rittermacht");

        List<Player> toSend = new ArrayList<>();
        toSend.add(knightPowerCandidate);
        toSend.add(knightCountOwner);

        game.nextPlayer();

        if (knightCardCount <= knightPowerCount || candidateId == ownerId) {
            SendToClient.sendGameSessionBroadcast(game);
        } else {
            game.setKnightPowerCount(knightCardCount);
            game.setKnightPowerOwner(knightPowerCandidate);

            knightPowerCandidate.getInventory().addVictoryPoints(1);
            knightCountOwner.getInventory().removeVictoryPoints(1);

            SendToClient.sendGameSessionBroadcast(game);
            SendToClient.sendStringMessage(toSend, builder.toString());
        }
    }

    /**
     * When the Player is not the one with the greatest Knightpower the updated Gamesession is send.
     * Else it is iterated trough all Players and the first one wih the most Knight-Cards is stored.
     * If no one has more Knight-Cards than the Player, it still has the greatest Knightpower and the
     * updated Gamesession is send. Else the Player found now has the greatest Knightpower and the
     * Variables are set. He gets a Victory Point and one Victory Point is removed from the last Owner.
     * A Message is send to the old and the new Owner and a Gamesession is send to all Players.
     *
     * @param game                 current Game
     * @param knightCardCount      Number of Knight Cards the Player has
     * @param knightPowerCandidate Player was has bought a Knight-Card
     */
    public void checkKnightPowerOnPlay(GameSession game, int knightCardCount, Player knightPowerCandidate) {

        Player knightCountOwner = game.getKnightPowerOwner();
        int candidateId = knightPowerCandidate.getUserId();
        int ownerId = knightCountOwner.getUserId();

        int greatest = 0;
        Player player = knightCountOwner;

        StringBuilder builder = new StringBuilder();
        builder.append(SendToClient.HEADER_KNIGHT).append(" ");
        builder.append(knightPowerCandidate.getDisplayName()).append(" hat jetzt die größte Rittermacht");

        List<Player> players = game.getPlayers();
        List<Player> toSend = new ArrayList<>();

        game.nextPlayer();

        if (candidateId != ownerId) {
            SendToClient.sendGameSessionBroadcast(game);
        } else {

            for (Player p : players) {
                if (p.getInventory().getKnightCard() > knightCardCount && p.getInventory().getKnightCard() > greatest) {
                    greatest = p.getInventory().getKnightCard();
                    player = p;
                }
            }

            if (greatest == 0) {
                SendToClient.sendGameSessionBroadcast(game);
            } else {
                game.setKnightPowerCount(greatest);
                game.setKnightPowerOwner(player);

                knightPowerCandidate.getInventory().removeVictoryPoints(1);
                player.getInventory().addVictoryPoints(1);

                toSend.add(knightPowerCandidate);
                toSend.add(player);

                SendToClient.sendGameSessionBroadcast(game);
                SendToClient.sendStringMessage(toSend, builder.toString());
            }
        }
    }
}
