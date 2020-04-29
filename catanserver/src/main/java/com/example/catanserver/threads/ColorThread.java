package com.example.catanserver.threads;

import com.example.catangame.Colors;
import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;

import java.net.Socket;

/**
 * @author Fabian Schaffenrath
 * This Thread is used to set the chosen color for a player in the gameSession
 *
 * It sends an updated gameSession object to every client.
 */

public class ColorThread extends GameThread{

    private String color;

    public ColorThread(Socket connection, User user, GameSession game, String color) {
        super(connection, user, game);
        this.color = color;
    }

    public void run(){
        Colors color = Colors.valueOf(this.color);
        boolean taken = false;
        for (Player player:game.getPlayers()) {
            if(player.getColor() == color) {
                taken = true;
                break;
            }
        }
        if(!taken) {
            game.getPlayer(user.getUserId()).setColor(color);
            SendToClient.sendGameSessionBroadcast(connection,game);
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
