package com.example.diesiedler.threads;

import android.os.Message;

import com.example.catangame.GameSession;
import com.example.diesiedler.presenter.ClientData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.diesiedler.presenter.ClientData.*;

/**
 * @author Fabian Schaffenrath
 * Dieser Thread kümmert sich um alle Meldungen, die der Client vom Server bekommt. Dabei verarbeitet er diese
 * je nach Objectart und spricht anschließend den derzeitigen Handler an.
 */
public class ServerCommunicationThread extends Thread {

    private static final Logger logger = Logger.getLogger(ServerCommunicationThread.class.getName());

    /**
     * Wartet auf Input vom Server. Sobald dieser auftritt, werden die Daten abgespeichert und der
     * derzeitige Handler mit einer Message angesprochen.
     * <p>
     * msg.arg1 Codes: TODO: Change to enums
     * -1 StartServerCommunicationThread -> Connection could not be established
     * 0 Action not allowed (mostly ignored)
     * 1 StartServerCommunicationThread -> Connection established
     * 2 UserId return
     * 3 PlayerList returned ({userid},{username},{userid},{username},...)
     * 4 GameSession returned
     * 5 String returned (obj contains string)
     */

    public void run() {
        try {
            Object read;
            while (true) {
                logger.log(Level.INFO, "Waiting");
                read = GET_FROM_SERVER.readObject();
                if (read instanceof GameSession) {  // Neue GameSession State
                    logger.log(Level.INFO, "Got GameSession.");
                    currentGame = (GameSession) read;
                    Message msg = Message.obtain();
                    msg.arg1 = 4;
                    ClientData.currentHandler.sendMessage(msg);
                } else if (read instanceof ArrayList) {  // Userlist
                    // Erstelle Username Set und Id,Username Tupel für die SearchPlayersActivity
                    logger.log(Level.INFO, "Got Userlist.");
                    ArrayList<String> searchingUsers = (ArrayList<String>) read;
                    Map<String, Integer> searchingUsersMap = new HashMap<>();
                    Set<String> searchingUserNames = new HashSet<>();
                    for (int i = 0; i < searchingUsers.size() - 1; i += 2) {
                        int id = Integer.parseInt(searchingUsers.get(i));
                        if (userId != id) {
                            searchingUsersMap.put(searchingUsers.get(i + 1), id);
                            searchingUserNames.add(searchingUsers.get(i + 1));
                        }
                    }
                    ClientData.searchingUserNames = searchingUserNames;
                    ClientData.searchingUsers = searchingUsersMap;
                    Message msg = Message.obtain();
                    msg.arg1 = 3;
                    ClientData.currentHandler.sendMessage(msg);
                } else if (read instanceof String) {  // Spezifische Calls oder Fehlermeldungen
                    logger.log(Level.INFO, "Got String.");
                    Message msg = Message.obtain();
                    msg.arg1 = 5;
                    msg.obj = read;
                    ClientData.currentHandler.sendMessage(msg);
                } else {  // UserId nach erfolgreichem Login
                    logger.log(Level.INFO, "Got Integer.");
                    ClientData.userId = (int) read;
                    Message msg = Message.obtain();
                    msg.arg1 = 2;
                    ClientData.currentHandler.sendMessage(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
