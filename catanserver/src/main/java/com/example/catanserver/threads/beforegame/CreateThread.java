package com.example.catanserver.threads.beforegame;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.threads.SendToClient;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (Documentation)
 *
 * This Thread is used to create a game, once all players have been selected.
 */
public class CreateThread extends Thread {

    private String[] userIds;

    /**
     * @param userIds Ids of all Users in the Game
     */
    public CreateThread(String[] userIds) {
        this.userIds = userIds;
    }

    /**
     * It makes from all Users in the List Players and adds them to new Game.
     * These Players are removed from the currently Searching list.
     * It sends a GameSession to every player and a new List to every User currently searching.
     */
    @Override
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
