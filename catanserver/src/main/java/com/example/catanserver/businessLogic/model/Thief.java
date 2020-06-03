package com.example.catanserver.businessLogic.model;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catangame.gameboard.Knot;
import com.example.catangame.gameboard.Tile;
import com.example.catanserver.threads.SendToClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (edit)
 *
 * This Service-class handles the Thief on the Gameboard and the update
 * of Ressources when a Knight-Card was played.
 */

public class Thief {

    public static boolean moveThief(GameSession game, int destinationIndex){
        Tile[] tiles = game.getGameboard().getTiles();
        if(destinationIndex >= 0 && destinationIndex < tiles.length) {
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i].isThief()) {
                    tiles[i].setThief(false);
                    tiles[destinationIndex].setThief(true);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * It gets all Knotes from the Tile where the Thief was moved to and all
     * Players which has Settlements or Citys around this Tile. If there are
     * any Players, a random Player is selected and a random Ressource is
     * stolen from him. The Name of the Player and the Ressources is send
     * to the sendMessage Method.
     *
     * @param game             current Game
     * @param destinationIndex index of the Tile where the Thief was moved to
     * @param curr             current Player
     * @return true when all the Tile-Index was ok, else false
     */
    public static boolean updateRessources(GameSession game, int destinationIndex, Player curr) {

        Tile[] tiles = game.getGameboard().getTiles();

        List<Player> players = new ArrayList<>(3);
        Player playerToStealFrom;
        Random rand = new Random();
        int[] res;

        String resName;
        StringBuilder builder = new StringBuilder();
        String message = "Nichts zu stehlen";

        game.nextPlayer();
        curr.getInventory().removeKnightCard(1);
        KnightPower.checkKnightPowerOnPlay(game, curr.getInventory().getKnightCard(), curr);

        if (game.getKnightPowerOwner() != null) {
            builder.append(" ").append(game.getKnightPowerOwner().getDisplayName()).append(" hat jetzt die größte Rittermacht");
        }

        if (destinationIndex >= 0 && destinationIndex < tiles.length) {

            Tile tile = tiles[destinationIndex];
            Knot[] knotes = tile.getKnots();

            for (int i = 0; i < knotes.length; i++) {

                Player player = knotes[i].getPlayer();

                if (player != null && player.getUserId() != curr.getUserId()) {

                    players.add(player);
                }
            }

            int number = -1;

            if (players.size() > 0) {

                playerToStealFrom = players.get(rand.nextInt(players.size()));
                res = playerToStealFrom.getInventory().getResValues();

                number = selectRes(res);

                switch (number) {
                    case 0:
                        playerToStealFrom.getInventory().removeWood(1);
                        curr.getInventory().addWood(1);
                        resName = "Holz";
                        break;

                    case 1:
                        playerToStealFrom.getInventory().removeWool(1);
                        curr.getInventory().addWool(1);
                        resName = "Wolle";
                        break;

                    case 2:
                        playerToStealFrom.getInventory().removeWheat(1);
                        curr.getInventory().addWheat(1);
                        resName = "Weizen";
                        break;

                    case 3:
                        playerToStealFrom.getInventory().removeOre(1);
                        curr.getInventory().addOre(1);
                        resName = "Erz";
                        break;

                    case 4:
                        playerToStealFrom.getInventory().removeClay(1);
                        curr.getInventory().addClay(1);
                        resName = "Lehm";
                        break;

                    default:
                        SendToClient.sendStringMessageBroadcast(game, message + builder.toString());
                        return true;
                }

                sendMessage(game, resName, curr, playerToStealFrom);
            } else {
                SendToClient.sendStringMessageBroadcast(game, SendToClient.HEADER_KNIGHT + " " + message + builder.toString());
            }

            return true;
        }
        return false;
    }

    /**
     * It selects a random Ressource in the Array. When there is at
     * least one of its, its index is returned, else -1 is returned.
     *
     * @param res Array of the Ressource-Values
     * @return Index of the selected Ressource, when there is 1, else -1
     */
    public static int selectRes(int[] res) {

        int counter = 0;

        for (int i = 0; i < res.length; i++) {
            if (res[i] != 0) {
                counter++;
            }
        }

        if (counter == 0) {
            return -1;
        }

        Random rand = new Random();
        int index = rand.nextInt(res.length);
        int number = res[index];

        while (number == 0) {
            index = rand.nextInt(res.length);
            number = res[index];
        }

        return index;
    }

    /**
     * Builds a Message with the Name of the Players and the stolen Ressource
     * and sends it Broadcast.
     *
     * @param game        current Game
     * @param resName     Name of the stolen Ressource
     * @param curr        current Player
     * @param toStealFrom Player which the Ressource was stolen from
     */
    public static String sendMessage(GameSession game, String resName, Player curr, Player toStealFrom) {

        StringBuilder builder = new StringBuilder();
        builder.append(SendToClient.HEADER_KNIGHT).append(" ");
        builder.append(curr.getDisplayName()).append(" hat 1 ").append(resName);
        builder.append(" von ").append(toStealFrom.getDisplayName()).append(" gestohlen");

        if (game.getKnightPowerOwner() != null) {
            builder.append(" ").append(game.getKnightPowerOwner().getDisplayName()).append(" hat jetzt die größte Rittermacht");
        }

        SendToClient.sendStringMessageBroadcast(game, builder.toString());
        return builder.toString();
    }
}
