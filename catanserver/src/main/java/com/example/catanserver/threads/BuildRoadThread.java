package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.building.BuildRoad;

public class BuildRoadThread extends GameThread {

    GameSession gameSession;
    int edgeIndex;
    int userID;

    public BuildRoadThread(User user, GameSession game, int edgeIndex) {
        super(user, game);
        this.gameSession = game;
        this.userID = user.getUserId();
        this.edgeIndex = edgeIndex;
    }

    public void run() {
        BuildRoad.updateGameSession(gameSession, edgeIndex, userID);
        SendToClient.sendGameSessionBroadcast(gameSession);
    }
}
