package com.example.catanserver;

import com.example.catangame.GameSession;
import com.example.catanserver.threads.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Fabian Schaffenrath
 *  This is the Client Listener Thread. It is run once per user (or rather socket).
 *  The user specific in and output Streams are created here and every network call of the client
 *  is validated. If valid, a second thread is started, implementing the purpose of the network call.
 *
 *  Normally Connection strings should have the form of "[userid] [gameid] [request] [info](optional)*".
 *  Exceptions for these preGame calls:
 *      - Login "LOGIN [Display Name]"
 *      - Search for Players "APPLY"
 *      - Stop searching for Players "STOP"
 *      - Game creation "CREATE [userid] [mitspielerid] [mitspielerid](optional) [mitspielerid](optional)"
 */

public class ClientListenerThread extends Thread {

    private User user;

    // TODO: change Strings in messages to enums for faster comparison (ConnectionEnum x = ConnectionEnum.values()[messageSplit[0]])

    private Socket connection;
    private ObjectInputStream connectionInputStream;
    private ObjectOutputStream connectionOutputStream;

    public ClientListenerThread(Socket connection){
        this.connection = connection;
        Server.currentConnections.add(connection);
    }

    public void run() {
        try {
            connectionInputStream = new ObjectInputStream(connection.getInputStream());
            System.out.println("ClientReader: InputStream created.");
            connectionOutputStream = new ObjectOutputStream(connection.getOutputStream());
            System.out.println("ClientReader: OutputStream created.");
            connection.setKeepAlive(true);
            connection.setSoTimeout(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO: Add callbacks for blocked comms (enums error code?)
        System.out.println("Processing Connection.");
        String message = null;
        while (true) {
            try {
                message = connectionInputStream.readUTF();
            } catch(EOFException e){
                try {
                    connection.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (message != null) {
                message = message.trim();
                System.out.println("MSG: \"" + message + "\"");
                String[] messageSplit = splitMessage(message);
                if (messageSplit.length > 0) {
                    // Login
                    if (messageSplit[0].equals("LOGIN")) {
                        if (messageSplit.length == 2) {
                            if(user == null) {
                                boolean nameTaken = false;
                                for (User otherUser:Server.currentUsers) {
                                    if(otherUser.getDisplayName().equals(messageSplit[1])){
                                        nameTaken = true;
                                        break;
                                    }
                                }
                                if(!nameTaken) {
                                    user = new User(messageSplit[1], connection, connectionInputStream, connectionOutputStream);
                                    Server.currentUsers.add(user);
                                }
                            }
                            if(user != null) {
                                System.out.println("Starting LOGINThread.");
                                Thread loginThread = new LoginThread(user);
                                loginThread.start();
                            }
                            else{
                                System.out.println("Starting ErrorThread [LOGIN]");
                                Thread errorThread = new ErrorThread(connectionOutputStream, "This name is taken!");
                                errorThread.start();
                            }
                        }
                    }
                    else if(user != null) {
                        // Apply for searching
                        if (messageSplit[0].equals("APPLY")) {
                            System.out.println("Starting APPLYThread.");
                            Thread applyThread = new ApplyThread(user);
                            applyThread.start();
                        }
                        // Stop Searching
                        else if (messageSplit[0].equals("STOP")) {
                            System.out.println("Starting STOPThread.");
                            Thread stopThread = new StopThread(user);
                            stopThread.start();
                        }
                        // Start a Game
                        else if (messageSplit[0].equals("CREATE")) {
                            if (messageSplit.length > 2 && messageSplit.length < 6) {
                                System.out.println("Starting CREATEThread.");
                                Thread startThread = new CreateThread(Arrays.copyOfRange(messageSplit, 1, messageSplit.length));
                                startThread.start();
                            }
                        }
                        // GameSession Actions
                        else if (messageSplit.length > 1) {
                            try {
                                int msgUserId = Integer.parseInt(messageSplit[0]);
                                int msgGameId = Integer.parseInt(messageSplit[1]);
                                if (user.getUserId() == msgUserId) {
                                    GameSession foundGame = null;
                                    for (GameSession game : user.getGameSessions()) {
                                        if (game.getGameId() == msgGameId) {
                                            foundGame = game;
                                            break;
                                        }
                                    }
                                    if (foundGame != null) {
                                        if (messageSplit.length > 2) {
                                            if (!Server.currentlyThreaded.contains(foundGame.getGameId())) {


                                                if (messageSplit[2].equals("COLOR") && messageSplit.length > 3) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting COLORThread.");
                                                    Thread colorThread = new ColorThread(user, foundGame, messageSplit[3]);
                                                    colorThread.start();
                                                }

                                                if (messageSplit[2].equals("START")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting STARTThread.");
                                                    Thread startThread = new StartThread(user, foundGame);
                                                    startThread.start();
                                                }


                                                // TODO: Implement all methods post Game creation here


                                                if (messageSplit[2].equals("BUILDSETTLEMENT")) {
                                                    int knotIndex = Integer.parseInt(messageSplit[3]);
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting BUILDSETTLEMENTThread.");
                                                    Thread bsThread = new BuildSettlementThread(user, foundGame, knotIndex);
                                                    bsThread.start();
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());
                                                }

                                                if (messageSplit[2].equals("BUILDROAD")) {
                                                    int edgeIndex = Integer.parseInt(messageSplit[3]);
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting BUILDROADThread.");
                                                    Thread brThread = new BuildRoadThread(user, foundGame, edgeIndex);
                                                    brThread.start();
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());
                                                }

                                                if (messageSplit[2].equals("DICEVALUE")) {
                                                    int diceValue = Integer.parseInt(messageSplit[3]);
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting DICEVALUEThread");
                                                    Thread rAThread = new ResourceAllocationThread(user, foundGame, diceValue);
                                                    rAThread.start();
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());
                                                }

                                            }

                                            /*
                                             * TODO: This area is for actions,  that do not directly change the GameSession
                                             * Example: Cheating Functions notifier call
                                             */
                                        }
                                    } else {
                                        // Falsche GameID
                                        Thread errorThread = new ErrorThread(connectionOutputStream,"Spiel konnte nicht gefunden werden.");
                                        errorThread.start();
                                    }
                                } else {
                                    // Falsche UserID
                                    Thread errorThread = new ErrorThread(connectionOutputStream,"Fehler bei der Benutzeridentifikation.");
                                    errorThread.start();
                                }
                            }catch(NumberFormatException e){
                                System.out.println("Integer parsing failed.");
                            }
                        }
                    }
                }
            }
            message = null;
        }
    }

    private String[] splitMessage(String msg){
        // TODO: optimize
        ArrayList<String> stringList = new ArrayList<>();
        while(true){
            int index = msg.indexOf(" ");
            if(index < 0){
                break;
            }
            stringList.add(msg.substring(0,index));
            msg = msg.substring(index+1);
        }
        stringList.add(msg);
        String[] stringArray = new String[stringList.size()];
        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = stringList.get(i);
        }
        return stringArray;
    }

}
