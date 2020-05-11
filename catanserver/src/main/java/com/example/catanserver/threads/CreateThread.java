package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;

import java.util.HashSet;
import java.util.Set;


/**
 * @author Fabian Schaffenrath
 * This Thread is used to create a game, once all players have been selected.
 * These players are removed from the currently Searching list.
 *
 * It sends a gameSession object to every player and a new list to every user currently searching.
 */

public class CreateThread extends Thread {

    private String[] userIds;

    public CreateThread(String[] userIds) {
        this.userIds = userIds;
    }

    public void run(){
        GameSession game = new GameSession();
        Set<User> usersToRemove = new HashSet<>();
        int foundPlayers = 0;
        for (String userId: userIds) {
            int parsedId = Integer.parseInt(userId);
            for (User user : Server.currentlySearching) {
                if(user.getUserId() == parsedId){
                    usersToRemove.add(user);
                    foundPlayers++;
                }
            }
        }

        if (foundPlayers > 1) { // TODO: Update later to 2
            for (User userToRemove : usersToRemove) {
                Player player = new Player(userToRemove.getDisplayName(), userToRemove.getUserId());
                userToRemove.addGameSession(game);
                game.setPlayer(player);
                Server.currentlySearching.remove(userToRemove);
            }
            Server.currentGames.add(game);
            SendToClient.sendGameSessionBroadcast(game);
            SendToClient.sendSearchingListBroadcast(Server.currentlySearching);
        }
    }
}
