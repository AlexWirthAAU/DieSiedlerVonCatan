package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;

/**
 * @author Fabian Schaffenrath
 * This Thread starts the Game for every User.
 *
 * It sends a static string to every user who is part of the GameSession.
 */

public class StartThread extends GameThread{

    public StartThread(User user, GameSession game) {
        super(user, game);
    }

    public void run(){
        boolean allColorsSet = true;
        for (Player player:game.getPlayers()) {
            if(player.getColor() == null){
                allColorsSet = false;
                break;
            }
        }
        if(allColorsSet){
            SendToClient.sendGameStartBroadcast(game);
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
