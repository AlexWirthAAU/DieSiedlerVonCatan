package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.building.BuildCity;

import java.net.Socket;

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
    }

}
