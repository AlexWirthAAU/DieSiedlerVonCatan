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
 * Die ClientData Klasse enthält alle Daten, die sowohl zur Spielabwicklung als auch zur Serverkommunikation
 * benötigt werden.
 */
public class ClientData {

    private static final String HOST ="25.64.65.87";  // Fabian
    // static final String HOST = "192.168.0.23"; // Christina
    private static final int PORT = 10;  // Hamachi
    // static final int PORT = 2020; // Non-Hamachi

    public static Socket SERVER;
    public static ObjectInputStream GET_FROM_SERVER;
    public static ObjectOutputStream SEND_TO_SERVER;

    public static int userId = 0;
    public static String userDisplayName;
    public static Set<String> searchingUserNames;
    public static Map<String,Integer> searchingUsers;
    public static GameSession currentGame;

    public static Thread SERVER_COMMUNICATION_THREAD;

    public static Handler currentHandler;

    private static final Logger logger = Logger.getLogger(ClientData.class.getName());

    /**
     * Initializiert und speichert die Serververbindung.
     */
    public static void initializeServerConnection(){
        try {
            SERVER = new Socket(HOST, PORT);
            logger.log(Level.INFO,"Socket created.");

            SERVER.setKeepAlive(true);
            logger.log(Level.INFO,"Socket kept alive.");

            SEND_TO_SERVER = new ObjectOutputStream(SERVER.getOutputStream());
            logger.log(Level.INFO,"Object Output Stream created.");

            GET_FROM_SERVER = new ObjectInputStream(SERVER.getInputStream());
            logger.log(Level.INFO,"Object Input Stream created.");

            SERVER.setSoTimeout(0);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void closeServerConnection(){
        try{
            SERVER.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
