package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.building.BuildSettlement;

import java.net.Socket;

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
        System.out.println("UPDATED GAMESESSION");
        SendToClient.sendGameSessionBroadcast(gameSession);
        System.out.println("BROADCASTED GAMESESSION");
    }

}
