package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.ResourceAllocation;

// TODO: kommentieren
public class ResourceAllocationThread extends GameThread {

    GameSession gameSession;
    int diceValue;


    public ResourceAllocationThread(User user, GameSession game, int diceValue) {
        super(user, game);
        this.gameSession = game;
        this.diceValue = diceValue;
    }

    public void run() {
        ResourceAllocation.updateResources(gameSession, diceValue);
        Player player = gameSession.getPlayer(user.getUserId());
        if(player.hasToSkip()){
            player.skip();
            endTurn();
            gameSession.nextPlayer();
        }
        SendToClient.sendGameSessionBroadcast(gameSession);
        Server.currentlyThreaded.remove(gameSession.getGameId());
    }
}
