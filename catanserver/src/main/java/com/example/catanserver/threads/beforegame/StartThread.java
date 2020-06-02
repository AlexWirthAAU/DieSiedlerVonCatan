package com.example.catanserver.threads.beforegame;

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
 * This Thread starts the Game for every User.
 */
public class StartThread extends GameThread {

    /**
     * {@inheritDoc}
     */
    public StartThread(User user, GameSession game) {
        super(user, game);
    }

    /**
     * When all Players have selected their Colors, a command string is sent
     * to every user indicating a game start.
     * Game is removed from currentlyThreaded.
     */
    public void run(){
        boolean allColorsSet = true;
        for (Player player:game.getPlayers()) {
            if(player.getColor() == null){
                allColorsSet = false;
                break;
            }
        }
        if(allColorsSet){
            SendToClient.sendStringMessageBroadcast(game,SendToClient.HEADER_START);
        }
        Server.currentlyThreaded.remove(game.getGameId());
    }
}
