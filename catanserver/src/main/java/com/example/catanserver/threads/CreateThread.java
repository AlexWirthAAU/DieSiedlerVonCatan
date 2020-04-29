package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;

import java.net.Socket;

/**
 * @author Fabian Schaffenrath
 * This Thread is used to create a game, once all players have been selected.
 *
 * It sends a gameSession object to every client.
 */

public class CreateThread extends Thread {

    private String[] userIds;
    private Socket connection;

    public CreateThread(Socket connection, String[] userIds){
        this.connection = connection;
        this.userIds = userIds;
    }

    public void run(){

        GameSession game = new GameSession();
        for (String userId: userIds) {
            int parsedId = Integer.parseInt(userId);
            for (User user: Server.currentUsers) {
                if(user.getUserId() == parsedId){
                    Player player = new Player(user.getDisplayName(),parsedId);
                    user.addGameSession(game);
                    game.setPlayer(player);
                    Server.currentlySearching.remove(user);
                }
            }
        }
        Server.currentGames.add(game);
        SendToClient.sendGameSessionBroadcast(connection,game);
    }
}
