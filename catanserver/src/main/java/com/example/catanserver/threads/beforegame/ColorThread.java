package com.example.catanserver.threads.beforegame;

import com.example.catangame.Colors;
import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (Documentation)
 *
 * This Thread is used to set the chosen color for a player in the GameSession.
 */
public class ColorThread extends GameThread {

    private String colorString;

    /**
     * Constructor - Sets User and Game (super) and the Color.
     *
     * @param user  the User which want to set his Color
     * @param game  the current GameId
     * @param color the desired Color
     */
    public ColorThread(User user, GameSession game, String color) {
        super(user, game);
        this.colorString = color;
    }

    /**
     * If the Color is not taken by another Player, it is set
     * as the Players Color. The Game is removed from currentlyThreaded.
     * It sends an updated GameSession object to every player.
     */
    public void run(){
        try {
            Colors color = Colors.valueOf(this.colorString);
            boolean taken = false;
            for (Player player : game.getPlayers()) {
                if (player.getColor() == color) {
                    taken = true;
                    break;
                }
            }
            if (!taken) {
                game.getPlayer(user.getUserId()).setColor(color);
                System.out.println(game.getPlayer(user.getUserId()).getColor());
                SendToClient.sendGameSessionBroadcast(game);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
