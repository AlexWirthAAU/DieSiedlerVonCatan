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
 * This is the Main Thread of the Server. It is listening to all Requests and saves the
 * connections inside a list for the Acceptance Thread.
 * It implements all used Lists/Sets needed as static, reachable from every class.
 */

public class Server {

    private final static int SERVER_PORT = 2020;
    private static ServerSocket listenerSocket;
    private static Socket caughtConnection;
    public static List<Socket> currentConnections = Collections.synchronizedList(new LinkedList<Socket>());
    public static List<User> currentUsers = Collections.synchronizedList(new LinkedList<User>());
    public static List<GameSession> currentGames = Collections.synchronizedList(new LinkedList<GameSession>());
    public static Set<Integer> currentlyThreaded = Collections.synchronizedSet(new HashSet<Integer>());
    public static Set<User> currentlySearching = Collections.synchronizedSet(new HashSet<User>());

    public static void main(String[] args) {
        try {
            listenerSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server running on port: " + SERVER_PORT);

            Thread serverAcceptanceThread = new ServerAcceptanceThread();
            serverAcceptanceThread.start();

            while(true){
                caughtConnection = listenerSocket.accept();
                if(caughtConnection != null){
                    System.out.println("Connection to " + caughtConnection.getPort() + "(ListenerThread)");
                    currentConnections.add(caughtConnection);
                }
            }

        }catch(IOException ex){
            System.err.println(ex.getMessage());
            System.err.println("Server could not be started!");
        }
    }

}
