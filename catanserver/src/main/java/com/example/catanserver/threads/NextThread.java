package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catanserver.User;

public class NextThread extends GameThread {

    public NextThread(User user, GameSession game) {
        super(user, game);
    }

    public void run() {
        game.nextPlayer();
        SendToClient.sendGameSessionBroadcast(game);
    }
}
