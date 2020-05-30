package com.example.catanserver.threads.building;

import com.example.catangame.GameSession;
import com.example.catanserver.Server;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.building.BuildCity;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

// TODO: kommentieren
public class BuildCityThread extends GameThread {

    GameSession gameSession;
    int knotIndex;
    int userID;

    public BuildCityThread(GameSession g, User user, int k) {
        super(user, g);
        this.gameSession = g;
        this.knotIndex = k;
        this.userID = user.getUserId();
    }

    public void run() {
        BuildCity.updateGameSession(gameSession, knotIndex, userID);
        endTurn();
        SendToClient.sendGameSessionBroadcast(gameSession);
        Server.currentlyThreaded.remove(gameSession.getGameId());
    }

}
