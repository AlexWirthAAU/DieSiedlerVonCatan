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

/**
 * @author Fabian Schaffenrath
 *
 * This is the Main Thread of the Server. It is listening to all Requests and starts
 * a personal listener Thread for every user.
 * It implements all used Lists/Sets needed as static, reachable from every class.
 */
public class Server {

    // List of all Users that are currently active
    public static final List<User> currentUsers = Collections.synchronizedList(new LinkedList<User>());//NOSONAR
    // List of all Connections that are currently active
    public static final List<Socket> currentConnections = Collections.synchronizedList(new LinkedList<Socket>());//NOSONAR
    // List of all Games that are currently active
    public static final List<GameSession> currentGames = Collections.synchronizedList(new LinkedList<GameSession>());//NOSONAR
    // Set of all Games (GameId) in which Players are currently making a Network Call
    public static final Set<Integer> currentlyThreaded = Collections.synchronizedSet(new HashSet<Integer>());//NOSONAR
    // Set of all Users that are currently searching for Opponents
    public static final Set<User> currentlySearching = Collections.synchronizedSet(new HashSet<User>());//NOSONAR
    private final static int SERVER_PORT = 10; // Port the Server listens to
    private static ServerSocket listenerSocket; // ServerSocket
    private static Socket caughtConnection; // Client Socket

    /**
     * The ServerSocket is started and waits for Clients to accept.
     * When a Client is connected, a ClientListenerThread for this Connection is started.
     */
    public static void main(String[] args) {
        try {
            listenerSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server running on: " + listenerSocket.getInetAddress() + SERVER_PORT);

            while(true){//NOSONAR
                caughtConnection = listenerSocket.accept();
                if(caughtConnection != null){
                    System.out.println("Connection to " + caughtConnection.getInetAddress() + ":" + caughtConnection.getPort() + "(ListenerThread)");
                    System.out.println("Remote: " + caughtConnection.getRemoteSocketAddress());
                    Thread serverReaderThread = new ClientListenerThread(caughtConnection);
                    serverReaderThread.start();
                }
            }
        }catch(IOException ex){
            System.err.println(ex.getMessage());
            System.err.println("Server could not be started!");
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
