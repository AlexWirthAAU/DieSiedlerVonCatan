package com.example.diesiedler.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Die ServerQueries Klasse beinhaltet den direkten Servercall und alle Methoden zur Erstellungen
 * der request Strings
 */
public class ServerQueries {

    private static final Logger logger = Logger.getLogger(ServerQueries.class.getName());

    /**
     * Schickt ein vorgefertigtes Request an den Server.
     *
     * @param query zu sendendes Request
     */
    public static void sendStringQuery(String query) {
        try {
            logger.log(Level.INFO, "MSG: " + query);
            ClientData.SEND_TO_SERVER.writeUTF(query);
            ClientData.SEND_TO_SERVER.flush();
        } catch (IOException ex) {
            logger.log(Level.INFO, "ERROR: Couldn't send query to server.");
        }
    }

    public static String createStringQueryLogin(String name) {
        return "LOGIN " + name.trim();
    }

    public static String createStringQueryApply() {
        return "APPLY";
    }

    public static String createStringQueryStop() {
        return "STOP";
    }

    public static String createStringQueryCreate(ArrayList<Integer> users) {
        String query = "CREATE " + ClientData.userId;
        for (Integer user : users) {
            query += " " + user;
        }
        return query;
    }

    public static String createStringQueryColor(String color) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " COLOR " + color;
    }

    public static String createStringQueryStart() {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " START";
    }

    public static String createStringQueryBuild(String structure) {
        return "" + ClientData.userId + " " + ClientData.currentGame.getGameId() + " BUILD " + structure;
    }
}
