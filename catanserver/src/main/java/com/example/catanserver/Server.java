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
 * This is the Main Thread of the Server. It is listening to all Requests and starts
 * a personal listener Thread for every user.
 * It implements all used Lists/Sets needed as static, reachable from every class.
 */

public class Server {

    private final static int SERVER_PORT = 2020;  // Hamachi
    // private final static int SERVER_PORT = 2020;  // Non-Hamachi
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
            System.out.println("Server running on: " + listenerSocket.getInetAddress() + SERVER_PORT);

            while(true){
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

}
