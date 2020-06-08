package com.example.diesiedler.presenter;

import android.os.Handler;

import com.example.catangame.GameSession;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fabian Schaffenrath
 *
 * Holds all Data for Server-Communication and Game-Flow
 */
public class ClientData {

    private static final String HOST = "10.0.0.53";  // Fabian
    //private static final String HOST = "10.0.0.152"; // Christina
    private static final int PORT = 2020;  // Hamachi
    //private static final int PORT = 2020; // Non-Hamachi

    private static final Logger logger = Logger.getLogger(ClientData.class.getName()); // Logger

    public static ObjectInputStream GET_FROM_SERVER;//NOSONAR
    static ObjectOutputStream SEND_TO_SERVER;//NOSONAR

    // this Users ID
    public static int userId = 0;//NOSONAR
    // this Users Name
    public static String userDisplayName;//NOSONAR

    // as Set of all Users, that a currently Searching for Opponents
    public static Set<String> searchingUserNames;//NOSONAR
    // all currently searching Users and their ID
    public static Map<String, Integer> searchingUsers;//NOSONAR

    // the current active Game
    public static GameSession currentGame;//NOSONAR

    // Thread, which is set in WelcomeActivity
    public static Thread SERVER_COMMUNICATION_THREAD;//NOSONAR

    // Handler, which is set for every Activity in their onCreate-Method
    public static Handler currentHandler;//NOSONAR

    public static String cheaterId = "";

    /**
     * Initializes and saves the Server-Connection.
     */
    public static void initializeServerConnection() {
        try {
            // Socket and Streams
            //NOSONAR
            try (Socket SERVER = new Socket(HOST, PORT)) {
                logger.log(Level.INFO, "Socket created.");

                SERVER.setKeepAlive(true);
                logger.log(Level.INFO, "Socket kept alive.");

                SEND_TO_SERVER = new ObjectOutputStream(SERVER.getOutputStream());
                logger.log(Level.INFO, "Object Output Stream created.");

                GET_FROM_SERVER = new ObjectInputStream(SERVER.getInputStream());
                logger.log(Level.INFO, "Object Input Stream created.");

                SERVER.setSoTimeout(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void emptyGameData(){
        currentGame = null;
    }
}
