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

    public static Socket SERVER; // Socket and Streams
    public static ObjectInputStream GET_FROM_SERVER;
    public static ObjectOutputStream SEND_TO_SERVER;

    public static int userId = 0; // this Users ID
    public static String userDisplayName; // this Users Name

    public static Set<String> searchingUserNames; // as Set of all Users, that a currently Searching for Opponents
    public static Map<String, Integer> searchingUsers; // all currently searching Users and their ID

    public static GameSession currentGame; // the current active Game

    public static Thread SERVER_COMMUNICATION_THREAD; // Thread, which is set in WelcomeActivity

    public static Handler currentHandler; // Handler, which is set for every Activity in their onCreate-Method

    public static String cheaterId = "";


    /**
     * Initializes and saves the Server-Connection.
     */
    public static void initializeServerConnection() {
        try {
            SERVER = new Socket(HOST, PORT);
            logger.log(Level.INFO, "Socket created.");

            SERVER.setKeepAlive(true);
            logger.log(Level.INFO, "Socket kept alive.");

            SEND_TO_SERVER = new ObjectOutputStream(SERVER.getOutputStream());
            logger.log(Level.INFO, "Object Output Stream created.");

            GET_FROM_SERVER = new ObjectInputStream(SERVER.getInputStream());
            logger.log(Level.INFO, "Object Input Stream created.");

            SERVER.setSoTimeout(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the Socket.
     */
    public static void closeServerConnection() {
        try {
            SERVER.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void emptyGameData(){
        currentGame = null;
    }
}
