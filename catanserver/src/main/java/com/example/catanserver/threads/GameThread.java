package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catanserver.User;

import java.net.Socket;

/**
 * @author Fabian Schaffenrath
 * This class implements the typical inputs every normal connection string should contain.
 * Practically every Thread responsible for actions after the game start should inherit from this.
 */

abstract class GameThread extends Thread {

    Socket connection;
    User user;
    GameSession game;

    GameThread(Socket connection, User user, GameSession game) {
        this.connection = connection;
        this.game = game;
        this.user = user;
    }

    public abstract void run();
}
