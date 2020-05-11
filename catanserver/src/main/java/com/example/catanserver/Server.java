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
    public static final List<User> currentUsers = Collections.synchronizedList(new LinkedList<User>());
    private static ServerSocket listenerSocket;
    private static Socket caughtConnection;
    static final List<Socket> currentConnections = Collections.synchronizedList(new LinkedList<Socket>());
    public static final List<GameSession> currentGames = Collections.synchronizedList(new LinkedList<GameSession>());
    public static final Set<Integer> currentlyThreaded = Collections.synchronizedSet(new HashSet<Integer>());
    public static final Set<User> currentlySearching = Collections.synchronizedSet(new HashSet<User>());
    private static boolean flag = true;

    public static void main(String[] args) {
        try {
            listenerSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server running on port: " + listenerSocket.getInetAddress() + SERVER_PORT);

            while (flag) {
                caughtConnection = listenerSocket.accept();
                if (caughtConnection != null) {
                    System.out.println("Remote: " + caughtConnection.getRemoteSocketAddress());
                    System.out.println("Connection to " + caughtConnection.getPort() + "(ListenerThread)");

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
