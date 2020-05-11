package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catanserver.User;

/**
 * @author Fabian Schaffenrath
 * This class implements the typical inputs every normal connection string should contain.
 * Practically every Thread responsible for actions after the game start should inherit from this.
 */

abstract class GameThread extends Thread {

    User user;
    GameSession game;

    public GameThread(User user, GameSession game) {
        this.game = game;
        this.user = user;
    }
}
