package com.example.catanserver;

import com.example.catangame.GameSession;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Fabian Schaffenrath
 * The User class implements everything that is know of a user, including the socket connection and
 * object streams. If a game is started, the gameSessions list should be updated.
 */

public class User {

    private static int userCounter = 0;
    private int userId;
    private String displayName;
    private Socket connection;
    private ObjectOutputStream connectionOutputStream;
    private ObjectInputStream connectionInputStream;
    private List<GameSession> gameSessions;
    private int wins = 0;

    public User(String displayName, Socket connection, ObjectInputStream ois, ObjectOutputStream oos) {
        this.displayName = displayName;
        this.userId = userCounter++;
        this.connection = connection;
        this.connectionInputStream = ois;
        this.connectionOutputStream = oos;

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

    public Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public ObjectOutputStream getConnectionOutputStream() {
        return connectionOutputStream;
    }

    public void setConnectionOutputStream(ObjectOutputStream connectionOutputStream) {
        this.connectionOutputStream = connectionOutputStream;
    }

    public ObjectInputStream getConnectionInputStream() {
        return connectionInputStream;
    }

    public void setConnectionInputStream(ObjectInputStream connectionInputStream) {
        this.connectionInputStream = connectionInputStream;
    }
}
