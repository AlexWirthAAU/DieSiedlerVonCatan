package com.example.catanserver;

import com.example.catangame.GameSession;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fabian Schaffenrath
 *
 * This is the Main Thread of the Server. It is listening to all Requests and starts
 * a personal listener Thread for every user.
 * It implements all used Lists/Sets needed as static, reachable from every class.
 */
public class Server {

    // List of all Users that are currently active
    public static final List<User> currentUsers = Collections.synchronizedList(new LinkedList<>());//NOSONAR
    // List of all Games that are currently active
    public static final List<GameSession> currentGames = Collections.synchronizedList(new LinkedList<>());//NOSONAR
    // Set of all Games (GameId) in which Players are currently making a Network Call
    public static final Set<Integer> currentlyThreaded = Collections.synchronizedSet(new HashSet<>());//NOSONAR
    // Set of all Users that are currently searching for Opponents
    public static final Set<User> currentlySearching = Collections.synchronizedSet(new HashSet<>());//NOSONAR
    // List of all Connections that are currently active
    static final List<Socket> currentConnections = Collections.synchronizedList(new LinkedList<>());//NOSONAR
    private static final int SERVER_PORT = 10; // Port the Server listens to
    private static Logger logger = Logger.getLogger(Server.class.getName()); // Logger

    /**
     * The ServerSocket is started and waits for Clients to accept.
     * When a Client is connected, a ClientListenerThread for this Connection is started.
     */
    public static void main(String[] args) {
        try {
            // ServerSocket
            try (ServerSocket listenerSocket = new ServerSocket(SERVER_PORT)) {
                logger.log(Level.INFO, "Server running on: " + listenerSocket.getInetAddress() + SERVER_PORT);

                while (true) {//NOSONAR
                    // Client Socket
                    Socket caughtConnection = listenerSocket.accept();
                    if (caughtConnection != null) {
                        logger.log(Level.INFO, "Connection to " + caughtConnection.getInetAddress() + ":" + caughtConnection.getPort() + "(ListenerThread)");
                        logger.log(Level.INFO, "Remote: " + caughtConnection.getRemoteSocketAddress());
                        Thread serverReaderThread = new ClientListenerThread(caughtConnection);
                        serverReaderThread.start();
                    }
                }
            }
        }catch(IOException ex){
            logger.log(Level.SEVERE, ex.getMessage());
            logger.log(Level.SEVERE, "Server could not be started!");
        }
    }

    public static User findUser(int userId){
        for (User user:currentUsers) {
            if(user.getUserId() == userId){
                return user;
            }
        }
        return null;
    }
}
