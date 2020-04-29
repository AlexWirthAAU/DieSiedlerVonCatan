package com.example.catanserver;

import com.example.catangame.GameSession;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Fabian Schaffenrath
 * The User class implements everything that is know of a user. If a game is started, the
 * gameSessions list should be updated.
 */

public class User {

    private static int userCounter = 0;
    private int userId;
    private String displayName;
    private InetAddress connectionAddress;
    private int connectionPort;
    private List<GameSession> gameSessions;
    private int wins = 0;

    public User(String displayName, Socket connection) {
        this.displayName = displayName;
        this.connectionAddress = connection.getInetAddress();
        this.connectionPort = connection.getPort();
        this.userId = userCounter++;

        gameSessions = Collections.synchronizedList(new LinkedList<GameSession>());
    }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public InetAddress getConnectionAddress() {
        return connectionAddress;
    }

    public void setConnectionAddress(InetAddress connectionAddress) {
        this.connectionAddress = connectionAddress;
    }

    public int getConnectionPort() {
        return connectionPort;
    }

    public void setConnectionPort(int connectionPort) {
        this.connectionPort = connectionPort;
    }

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public void addGameSession(GameSession game){
        gameSessions.add(game);
    }

    public void removeGameSession(GameSession game){
        gameSessions.remove(game);
    }

    public void addWin(){
        wins++;
    }

    public int getWins() {
        return wins;
    }
}
