package com.example.catanserver.threads.building;

import com.example.catangame.GameSession;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.building.BuildRoad;
import com.example.catanserver.threads.GameThread;
import com.example.catanserver.threads.SendToClient;

public class BuildRoadThread extends GameThread {

    GameSession gameSession;
    int edgeIndex;
    int userID;
    private String card;

    public BuildRoadThread(User user, GameSession game, int edgeIndex, String card) {
        super(user, game);
        this.gameSession = game;
        this.userID = user.getUserId();
        this.edgeIndex = edgeIndex;
        this.card = card;
    }

    public void run() {
        if (card.equals("CARD")) {
            BuildRoad.buildRoadWithCard(gameSession, edgeIndex, userID);
        } else {
            BuildRoad.updateGameSession(gameSession, edgeIndex, userID);
        }
        SendToClient.sendGameSessionBroadcast(gameSession);
    }
}
