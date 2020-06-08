package com.example.catanserver;

import com.example.catangame.GameSession;
import com.example.catanserver.threads.ErrorThread;
import com.example.catanserver.threads.NextThread;
import com.example.catanserver.threads.ResourceAllocationThread;
import com.example.catanserver.threads.beforegame.StartThread;
import com.example.catanserver.threads.beforegame.StopThread;
import com.example.catanserver.threads.ThiefThread;
import com.example.catanserver.threads.beforegame.ApplyThread;
import com.example.catanserver.threads.beforegame.ColorThread;
import com.example.catanserver.threads.beforegame.CreateThread;
import com.example.catanserver.threads.beforegame.LoginThread;
import com.example.catanserver.threads.building.BuildCityThread;
import com.example.catanserver.threads.building.BuildRoadThread;
import com.example.catanserver.threads.building.BuildSettlementThread;
import com.example.catanserver.threads.cards.BuyCardThread;
import com.example.catanserver.threads.cards.PlayInventionThread;
import com.example.catanserver.threads.cards.PlayMonopolThread;
import com.example.catanserver.threads.cheating.CheatThread;
import com.example.catanserver.threads.cheating.CounterThread;
import com.example.catanserver.threads.cheating.RevealThread;
import com.example.catanserver.threads.trading.BankThread;
import com.example.catanserver.threads.trading.PortThread;
import com.example.catanserver.threads.trading.TradeAnswerThread;
import com.example.catanserver.threads.trading.TradeThread;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (Documentation)
 *
 *  This is the Client Listener Thread. It is run once per user (or rather socket).
 *  The user specific in and output Streams are created here and every Network Call of the client
 *  is validated.
 */
public class ClientListenerThread extends Thread {

    private User user;

    private Socket connection;
    private ObjectInputStream connectionInputStream;
    private ObjectOutputStream connectionOutputStream;

    /**
     * Constructor - stores the Connection and adds it to
     * the List of currently active Connections.
     *
     * @param connection the Clients Connection (Socket)
     */
    public ClientListenerThread(Socket connection){
        this.connection = connection;
        Server.currentConnections.add(connection);
    }

    /**
     * The user specific in and output Streams are created here and every Network Call of the client
     * is validated. If valid, a second Thread is started, implementing the Purpose of the Network Call.
     * <p>
     * Normally Connection strings should have the form of "[userid] [gameid] [request] [info](optional)*".
     * Exceptions for these preGame calls:
     * - Login "LOGIN [Display Name]"
     * - Search for Players "APPLY"
     * - Stop searching for Players "STOP"
     * - Game creation "CREATE [userid] [mitspielerid] [mitspielerid](optional) [mitspielerid](optional)"
     */
    public void run() {
        // Getting Streams from the Connection.
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

        System.out.println("Start Listening.");

        // Reading UTF from InputStream and storing it into <code>message</code>.
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

            // When there is a Message, the <code>message</code> is splitted.
            if (message != null) {
                message = message.trim();
                System.out.println("MSG: \"" + message + "\"");
                String[] messageSplit = splitMessage(message);

                if (messageSplit.length > 0) {

                    // Login
                    // When the Name is not taken, a new User with the Name and this Connection Data
                    // is created and added to the List of current Users. A LoginThread is started with
                    // the User as Data. Else the ErrorThread is started.
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
                            } else{
                                System.out.println("Starting ErrorThread [LOGIN]");
                                Thread errorThread = new ErrorThread(connectionOutputStream, "This name is taken!");
                                errorThread.start();
                            }
                        }
                    } else if(user != null) {

                        // Apply for searching
                        // A ApplyThread is started with the User as Data.
                        if (messageSplit[0].equals("APPLY")) {
                            System.out.println("Starting APPLYThread.");
                            Thread applyThread = new ApplyThread(user);
                            applyThread.start();
                        }

                        // Stop Searching
                        // A StopThread is started with the User as Data.
                        else if (messageSplit[0].equals("STOP")) {
                            System.out.println("Starting STOPThread.");
                            Thread stopThread = new StopThread(user);
                            stopThread.start();
                        }

                        // Start a Game
                        // When there is a Request for starting a Game with between 3 and 4 Players,
                        // a CreateThread is started with all Users as Data.
                        else if (messageSplit[0].equals("CREATE")) {
                            if (messageSplit.length > 2 && messageSplit.length < 6) {
                                System.out.println("Starting CREATEThread.");
                                Thread startThread = new CreateThread(Arrays.copyOfRange(messageSplit, 1, messageSplit.length));
                                startThread.start();
                            }
                        }

                        // GameSession Actions
                        // The first send Params are the UserId and the GameId.
                        // When a Player makes a Network Call, his Game is added
                        // to the List of currently threaded Games. There cannot
                        // more than one Request per Game the same Time.
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
                                            boolean threadCreated = false;
                                            if (!Server.currentlyThreaded.contains(foundGame.getGameId())) {

                                                // Color-Selection
                                                // A new ColorThread is started with the User, the GameId and the Color as Data.
                                                if (messageSplit[2].equals("COLOR") && messageSplit.length > 3) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting COLORThread.");
                                                    Thread colorThread = new ColorThread(user, foundGame, messageSplit[3]);
                                                    colorThread.start();
                                                    threadCreated = true;
                                                }

                                                // Starting a Game
                                                // A new StartThread is started with the User and the GameId as Data.
                                                else if (messageSplit[2].equals("START")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting STARTThread.");
                                                    Thread startThread = new StartThread(user, foundGame);
                                                    startThread.start();
                                                    threadCreated = true;
                                                }

                                                // Trading with Opponents
                                                // A new TradeThread is started with the User, the GameId and the Trade-Message as Data.
                                                else if (messageSplit[2].equals("TRADE")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    Thread tradeThread = new TradeThread(user, foundGame, messageSplit[3]);
                                                    tradeThread.start();
                                                    threadCreated = true;
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());  // TODO: has to be called inside the created thread.
                                                }

                                                // Answering to a Trade
                                                // A new TradeAnswerThread is started with the User, the GameId and the Answer as Data.
                                                else if (messageSplit[2].equals("TRADEANSWER")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    Thread tradeAnswerThread = new TradeAnswerThread(user, foundGame, messageSplit[3]);
                                                    tradeAnswerThread.start();
                                                    threadCreated = true;
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());  // TODO: has to be called inside the created thread.
                                                }

                                                // Trading with the Bank
                                                // A new BankThread is started with the User, the GameId and the Trade-Message as Data.
                                                else if (messageSplit[2].equals("BANK")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    Thread bankThread = new BankThread(user, foundGame, messageSplit[3]);
                                                    bankThread.start();
                                                    threadCreated = true;
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());  // TODO: has to be called inside the created thread.
                                                }

                                                // Trading over a Port
                                                // A new PortThread is started with the User, the GameId and the Trade-Message as Data.
                                                else if (messageSplit[2].equals("PORT")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    Thread portThread = new PortThread(user, foundGame, messageSplit[3]);
                                                    portThread.start();
                                                    threadCreated = true;
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());  // TODO: has to be called inside the created thread.
                                                }

                                                // Answer for a Trade
                                                // A new TradeAnswerThread is started with the User, the GameId and the Trade-Answer as Data.
                                                else if (messageSplit[2].equals("ANSWER")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    Thread tradeanswerThread = new TradeAnswerThread(user, foundGame, messageSplit[3]);
                                                    tradeanswerThread.start();
                                                    threadCreated = true;
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());  // TODO: has to be called inside the created thread.
                                                }

                                                // Buying a DevCard
                                                // A new BuyCardThread is started with the User and the GameId as Data.
                                                else if (messageSplit[2].equals("BUYCARD")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting BUYCARDThread.");
                                                    Thread buyCadThread = new BuyCardThread(user, foundGame);
                                                    buyCadThread.start();
                                                    threadCreated = true;
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());  // TODO: has to be called inside the created thread.
                                                }

                                                // Playing a Knight Card
                                                // A new PlayKnightThread is started with the User, the GameId and the id of the Tile as Data.
                                                else if (messageSplit[2].equals("PLAYKNIGHT")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting ThiefThread");
                                                    Thread thiefThread = new ThiefThread(user, foundGame, messageSplit[3], "CARD");
                                                    thiefThread.start();
                                                    threadCreated = true;
                                                }

                                                // Playing a Monopol Card
                                                // A new PlayMonopolThread is started with the User, the GameId and the desired Ressource as Data.
                                                else if (messageSplit[2].equals("PLAYMONOPOL")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    Thread playMonopolThread = new PlayMonopolThread(user, foundGame, messageSplit[3]);
                                                    playMonopolThread.start();
                                                    threadCreated = true;
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());  // TODO: has to be called inside the created thread.
                                                }

                                                // Playing a Invention Card
                                                // A new PlayInventionThread is started with the User, the GameId and the desired Ressource as Data.
                                                else if (messageSplit[2].equals("PLAYINVENTION")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    Thread playInventionThread = new PlayInventionThread(user, foundGame, messageSplit[3]);
                                                    playInventionThread.start();
                                                    threadCreated = true;
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());  // TODO: has to be called inside the created thread.
                                                }

                                                // Playing a BuildStreet Card
                                                // A new BuildRoadThread is started with the User, the GameId, the Index of the Edge,
                                                // where the Player wants to build a Road and the identifier CARD as Data.
                                                else if (messageSplit[2].equals("PLAYBUILDSTREET")) {
                                                    int edgeIndex = Integer.parseInt(messageSplit[3]);
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting BUILDROADThread.");
                                                    Thread brThread = new BuildRoadThread(user, foundGame, edgeIndex, "CARD");
                                                    brThread.start();
                                                    threadCreated = true;
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());  // TODO: has to be called inside the created thread.
                                                }

                                                // Going on to the next Player
                                                // A new NextThread is started with the User and the GameId as Data.
                                                else if (messageSplit[2].equals("NEXT")) {
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    Thread nextThread = new NextThread(user, foundGame);
                                                    nextThread.start();
                                                    threadCreated = true;
                                                    Server.currentlyThreaded.remove(foundGame.getGameId());  // TODO: has to be called inside the created thread.
                                                }

                                                // Building a Settlement
                                                // A new BuildSettlementThread is started with the User, the GameId, the Index of the Knot,
                                                // where the Player wants to build a settlement as Data.
                                                else if (messageSplit[2].equals("BUILDSETTLEMENT")) {
                                                    int knotIndex = Integer.parseInt(messageSplit[3]);
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting BUILDSETTLEMENTThread.");
                                                    Thread bsThread = new BuildSettlementThread(user, foundGame, knotIndex);
                                                    bsThread.start();
                                                    threadCreated = true;
                                                }

                                                // Building a Road
                                                // A new BuildRoadThread is started with the User, the GameId, the Index of the Edge,
                                                // where the Player wants to build a road as Data.
                                                else if (messageSplit[2].equals("BUILDROAD")) {
                                                    int edgeIndex = Integer.parseInt(messageSplit[3]);
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting BUILDROADThread.");
                                                    Thread brThread = new BuildRoadThread(user, foundGame, edgeIndex, " ");
                                                    brThread.start();
                                                    threadCreated = true;
                                                }

                                                // Building a City
                                                // A new BuildCityThread is started with the User, the GameId, the Index of the Knot,
                                                // where the Player wants to build a city as Data.
                                                else if (messageSplit[2].equals("BUILDCITY")) {
                                                    int knotIndex = Integer.parseInt(messageSplit[3]);
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting BUILDCITYThread.");
                                                    Thread bcThread = new BuildCityThread(foundGame, user, knotIndex);
                                                    bcThread.start();
                                                    threadCreated = true;
                                                }

                                                // Dicing
                                                // A new ResourceAllocationThread is started with the User, the GameId
                                                // and the diced Value as Data.
                                                else if (messageSplit[2].equals("DICEVALUE")) {
                                                    int diceValue = Integer.parseInt(messageSplit[3]);
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting DICEVALUEThread");
                                                    Thread rAThread = new ResourceAllocationThread(user, foundGame, diceValue);
                                                    rAThread.start();
                                                    threadCreated = true;
                                                }
                                                // Moving the Thief
                                                // A new ThiefThread is started wirh the user, the game, the TileId and an empty cardstring
                                                else if(messageSplit[2].equals("THIEF") && messageSplit.length > 3){
                                                    Server.currentlyThreaded.add(foundGame.getGameId());
                                                    System.out.println("Starting ThiefThread");
                                                    Thread thiefThread = new ThiefThread(user, foundGame, messageSplit[3], " ");
                                                    thiefThread.start();
                                                    threadCreated = true;
                                                }


                                            }
                                            /*
                                             * This area is for actions,  that do not directly change the GameSession
                                             */
                                            // Creating Cheat Request
                                            // A new CheatThread is started with the user, the game, the user to be stolen from
                                            // and the resource to be stolen
                                            if(messageSplit[2].equals("CHEAT") && messageSplit.length > 4){
                                                System.out.println("Starting CheatThread.");
                                                Thread cheatThread = new CheatThread(user,foundGame,messageSplit[3],messageSplit[4]);
                                                cheatThread.start();
                                                threadCreated = true;
                                            }
                                            // Cheat Reveal
                                            // A new RevealThread is started with the user, the game, the cheating userId
                                            // and the guessed stolen resource
                                            else if(messageSplit[2].equals("REVEAL") && messageSplit.length > 4){
                                                System.out.println("Starting RevealThread.");
                                                Thread revealThread = new RevealThread(user,foundGame,messageSplit[3],messageSplit[4]);
                                                revealThread.start();
                                                threadCreated = true;
                                            }
                                            // Counter Cheat
                                            // A new CounterThread is started with the user, the game, the cheating userId
                                            // and the to be stolen resource
                                            else if(messageSplit[2].equals("COUNTER") && messageSplit.length > 4){
                                                System.out.println("Starting CounterThread.");
                                                Thread counterThread = new CounterThread(user,foundGame,messageSplit[3],messageSplit[4]);
                                                counterThread.start();
                                                threadCreated = true;
                                            }

                                            if(!threadCreated){
                                                Thread errorThread = new ErrorThread(user.getConnectionOutputStream(),"Aktion konnte nicht durchgeführt werden.");
                                                errorThread.start();
                                            }
                                        }
                                    } else {
                                        // When the GameId is wrong, a new ErrorThread is started
                                        Thread errorThread = new ErrorThread(connectionOutputStream,"Spiel konnte nicht gefunden werden.");
                                        errorThread.start();
                                    }
                                } else {
                                    // When the UserId is wrong, a new ErrorThread is started
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

    /**
     * Get the Message from Client and splits it into userId (later),
     * gameId (later), identifier (request) and Data (info).
     *
     * @param msg Message from Client to split
     * @return Array with Message-Parts
     */
    private String[] splitMessage(String msg){
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
