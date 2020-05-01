package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catanserver.businessLogic.model.ResourceAllocation;

public class ResourceAllocationThread extends Thread {

    /**
     * @author Alex Wirth
     * This Thread handles the allocation of resources, by calling the ResourceAllocation-Service
     */

    GameSession currentGamesession;
    int diceValue;

    public ResourceAllocationThread(GameSession gameSession, String diceValue) {
        this.currentGamesession = gameSession;
        this.diceValue = Integer.parseInt(diceValue);
    }

    @Override
    public void run() {
        ResourceAllocation.updateResources(currentGamesession, diceValue);
    }
}
