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
 * This Thread is used to set the chosen color for a player in the GameSession.
 *
 * It sends an updated gameSession object to every player.
 */

public class ColorThread extends GameThread {

    private String colorString;

    public ColorThread(User user, GameSession game, String color) {
        super(user, game);
        this.colorString = color;
    }

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
