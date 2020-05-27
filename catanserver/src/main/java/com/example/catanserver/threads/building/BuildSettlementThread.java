package com.example.catanserver.threads.building;

import com.example.catangame.GameSession;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.building.BuildSettlement;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

// TODO: kommentieren
public class BuildSettlementThread extends GameThread {

    GameSession gameSession;
    int knotIndex;
    int userID;

    public BuildSettlementThread(User user, GameSession g, int k) {
        super(user, g);
        this.gameSession = g;
        this.knotIndex = k;
        this.userID = user.getUserId();
    }


    public void run() {
        BuildSettlement.updateGameSession(gameSession, knotIndex, userID);
        endTurn();
        System.out.println("UPDATED GAMESESSION");
        SendToClient.sendGameSessionBroadcast(gameSession);
        System.out.println("BROADCASTED GAMESESSION");
        Server.currentlyThreaded.remove(gameSession.getGameId());
    }

}
