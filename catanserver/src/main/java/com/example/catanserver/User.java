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
 * @author Christina Senger (Documentation)
 *
 * The User class implements everything that is know of a user, including the socket connection and
 * object streams. If a game is started, the gameSessions list should be updated.
 */
public class User {

    private static int userCounter = 0; // consecutive Id

    private int userId; // the Users/Players Id
    private String displayName;

    private Socket connection;
    private ObjectOutputStream connectionOutputStream;
    private ObjectInputStream connectionInputStream;

    private List<GameSession> gameSessions; // the Users GameSessions
    private int wins = 0; // the Users Wins

    /**
     * It set the displayName with the given Name and the Connection Data with the
     * give Data from Thread. The UserId is set as the next of userCounter.
     * The User gets an empty List, where all his Games can be stored.
     *
     * @param displayName the Users/Players Name
     * @param connection  the Users Connection Socket
     * @param ois         the Sockets ObjectInputStream
     * @param oos         the Socket ObjectOutputStream
     */
    public User(String displayName, Socket connection, ObjectInputStream ois, ObjectOutputStream oos) {
        this.displayName = displayName;
        this.userId = userCounter++;
        this.connection = connection;
        this.connectionInputStream = ois;
        this.connectionOutputStream = oos;

        gameSessions = Collections.synchronizedList(new LinkedList<>());
    }

    // Getter
    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ObjectOutputStream getConnectionOutputStream() {
        return connectionOutputStream;
    }

    List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public void addGameSession(GameSession game) {
        gameSessions.add(game);
    }
}
