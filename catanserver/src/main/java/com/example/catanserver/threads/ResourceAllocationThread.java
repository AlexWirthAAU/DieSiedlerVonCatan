package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catanserver.User;
import com.example.catanserver.businessLogic.model.ResourceAllocation;

import java.net.Socket;

public class ResourceAllocationThread extends GameThread {

    /**
     * @author Alex Wirth
     * This Thread handles the allocation of resources, by calling the ResourceAllocation-Service
     */

    GameSession currentGamesession;
    int diceValue;

    public ResourceAllocationThread(Socket socket, User user, GameSession gameSession, String diceValue) {
        super(socket, user, gameSession);
        this.currentGamesession = gameSession;
        this.diceValue = Integer.parseInt(diceValue);
    }


    public void run() {
        ResourceAllocation.updateResources(currentGamesession, diceValue);
    }
}
