package com.example.catanserver;

import com.example.catangame.GameSession;
import com.example.catanserver.threads.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Fabian Schaffenrath
 *  This is the Acceptance Thread. It is run only once, parallel to the MainThread(ListenerThread).
 *  Here, the List of connections provided by the ListenerThread is checked and once a connection is found
 *  the input is read and processed. If the connection is allowed, the corresponding Logic Thread is
 *  started.
 *
 *  Normally Connection strings should have the form of "[userid] [gameid] [request] [info](optional)".
 *  Exceptions for these preGame calls:
 *      - Login "LOGIN [Display Name]"
 *      - Find Players "APPLY [userid]"
 *      - List refreshes "REFRESH"
 *      - Game creation "START [userid] [mitspielerid] [mitspielerid](optional) [mitspielerid](optional)"
 */

public class ServerAcceptanceThread extends Thread {

    // TODO: change Strings in messages to enums for faster comparison (ConnectionEnum x = ConnectionEnum.values()[messageSplit[0]])

    public void run(){
        while(true){ // TODO: Add callbacks for blocked comms (enums error code?)
            if(Server.currentConnections.size() > 0){
                Socket connection = Server.currentConnections.get(0);
                String message = null;
                try {
                    ObjectInputStream connectionInputStream = new ObjectInputStream(connection.getInputStream());
                    message = connectionInputStream.readUTF();
                    connectionInputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(message != null) {
                    String[] messageSplit = splitMessage(message);
                    if(messageSplit.length > 0) {
                        // Login
                        if (messageSplit[0].equals("LOGIN")) {
                            if(messageSplit.length > 1) {
                                Thread loginThread = new LoginThread(connection,messageSplit[1]);
                                loginThread.start();
                            }
                        }
                        // Apply for searching
                        else if(messageSplit[0].equals("APPLY")){
                            if(messageSplit.length > 1){
                                int msgUserId = Integer.parseInt(messageSplit[0]);
                                User connectedUser = checkUserExistence(msgUserId,connection);
                                Thread applyThread = new ApplyThread(connection,connectedUser);
                                applyThread.start();
                            }
                        }
                        // Refresh
                        else if(messageSplit[0].equals("REFRESH")){
                            Thread refreshThread = new RefreshThread(connection);
                            refreshThread.start();
                        }
                        // Start a Game
                        else if(messageSplit[0].equals("CREATE")){ // TODO: How to prevent parallel game starts? -> invites?
                            if(messageSplit.length > 2 && messageSplit.length < 6){
                                Thread startThread = new CreateThread(connection, Arrays.copyOfRange(messageSplit,1,messageSplit.length));
                                startThread.start();
                            }
                        }
                        // GameSession Actions
                        else if(messageSplit.length > 1) {
                            int msgUserId = Integer.parseInt(messageSplit[0]);
                            int msgGameId = Integer.parseInt(messageSplit[1]);
                            User connectedUser = checkUserExistence(msgUserId,connection);
                            if (connectedUser != null) {
                                GameSession foundGame = null;
                                for (GameSession game:connectedUser.getGameSessions()) {
                                    if(game.getGameId() == msgGameId){
                                        foundGame = game;
                                        break;
                                    }
                                }
                                if(foundGame != null){
                                    if(messageSplit.length > 2){
                                        if(Server.currentlyThreaded.contains(foundGame.getGameId())) {
                                            if(messageSplit[2].equals("COLOR") && messageSplit.length > 3){
                                                Server.currentlyThreaded.add(foundGame.getGameId());
                                                Thread colorThread = new ColorThread(connection,connectedUser,foundGame,messageSplit[3]);
                                                colorThread.start();
                                            }

                                            if(messageSplit[2].equals("START")){
                                                Server.currentlyThreaded.add(foundGame.getGameId());
                                                Thread startThread = new StartThread(connection,connectedUser,foundGame);
                                                startThread.start();
                                            }

                                            // TODO: Implement all methods post Game creation here

                                            if (messageSplit[2].equals("RESOURCEALLOCATION")) {
                                                String diceValue = messageSplit[3];
                                                Server.currentlyThreaded.add(foundGame.getGameId());
                                                Thread resourceAllocation = new ResourceAllocationThread(connection, connectedUser, foundGame, diceValue);
                                                resourceAllocation.start();
                                                SendToClient.sendGameSessionBroadcast(connection, foundGame);
                                            }

                                            if (messageSplit[2].equals("BUILDSETTLEMENT")) {
                                                int knotIndex = Integer.parseInt(messageSplit[3]);
                                                Server.currentlyThreaded.add(foundGame.getGameId());
                                                Thread buildSettlement = new BuildSettlementThread(connection, connectedUser, foundGame, knotIndex);
                                                buildSettlement.start();
                                                SendToClient.sendGameSessionBroadcast(connection, foundGame);
                                            }

                                            if (messageSplit[2].equals("BUILDCITY")) {
                                                int knotIndex = Integer.parseInt(messageSplit[3]);
                                                Server.currentlyThreaded.add(foundGame.getGameId());
                                                Thread buildCity = new BuildCityThread(connection, foundGame, connectedUser, knotIndex);
                                                buildCity.start();
                                                SendToClient.sendGameSessionBroadcast(connection, foundGame);
                                            }

                                        }

                                        /*
                                         * TODO: This area is for actions,  that do not directly change the GameSession
                                         * Example: Cheating Functions notifier call
                                         */
                                    }
                                }
                                else{
                                    // TODO: GameSession not found
                                }
                            }
                            else{
                                // TODO: User not found / Access blocked
                            }
                        }
                    }
                }
                Server.currentConnections.remove(connection);
            }
        }
    }

    private User checkUserExistence(int id, Socket connection){
        for (User user : Server.currentUsers) {
            if (user.getUserId() == id) {
                if(user.getConnectionAddress() == connection.getInetAddress() && user.getConnectionPort() == connection.getPort()){
                    return user;
                }
                return null;
            }
        }
        return null;
    }

    private String[] splitMessage(String msg){
        ArrayList<String> stringArray = new ArrayList<>();
        while(true){
            int index = msg.indexOf(" ");
            if(index < 0){
                break;
            }
            stringArray.add(msg.substring(0,index-1));
            msg = msg.substring(index+1,msg.length()-1);
        }
        stringArray.add(msg);
        return (String[])stringArray.toArray();
    }

}
