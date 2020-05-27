package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (edit, Documentation)
 * @author Alex Wirt (edit)
 *
 * This Class handles everything that is related to Messages being sent to the Client.
 */
public class SendToClient {

    /**
     * Send his own Id to a User.
     *
     * @param user   User to Id should be sent to
     * @param userId this Users Id
     */
    public static void sendUserId(User user, int userId) {
        try {
            sendToClient(user, userId);
        }catch(IOException ex){
            System.err.println(ex.getMessage());
            System.err.println("Could not send UserId to Client.");
        }
    }

    /**
     * Creates a Userlist from a Userset and sends this
     * List to every User in this List.
     *
     * @param set Set of all Users currently searching
     */
    public static void sendSearchingListBroadcast(Set<User> set) {
        List<String> searchingList = createSearchingList(set);
        for (User user : set) {
            try {
                sendToClient(user, searchingList);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                System.err.println("Could not send List to Client" + user.getDisplayName() + ".");
            }
        }
    }

    /**
     * Creates a Userlist from the GameSession and sends the
     * identifier "STARTGAME" to every User in this List.
     *
     * @param game GameSessionObject to send
     */
    public static void sendGameStartBroadcast(GameSession game) {
        List<User> userList = createGameUserList(game);
        if (userList.size() == game.getPlayers().size()) {
            for (User user : userList) {
                try {
                    sendToClient(user, "STARTGAME");
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                    System.err.println("Could not start GameSession at Client " + user.getDisplayName() + ".");
                }
            }
        } else {
            // TODO: What happens if a user cant be found? (should not happen)
        }
    }

    /**
     * Creates a Userlist from the GameSession and sends the
     * GameSession Object to every User in this List.
     *
     * @param game GameSessionObject to send
     */
    public static void sendGameSessionBroadcast(GameSession game) {
        List<User> userList = createGameUserList(game);
        if (userList.size() == game.getPlayers().size()) {
            for (User user : userList) {
                try {
                    sendToClient(user, game);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                    System.err.println("Could not send GameSession to Client " + user.getDisplayName() + ".");
                }
            }
        } else {
            // TODO: What happens if a user cant be found? (should not happen)
        }
    }

    /**
     * Creates a Userlist from the Playerlist and sends the Message to
     * every User in this List.
     *
     * @param toSend List of all Player to which the Trade-Message should be sent
     * @param message specific Trade-Message
     */
    public static void sendTradeMessageBroadcast(List<Player> toSend, String message, GameSession game) {
        List<User> userList = createTradeUserList(toSend);
        for (User user : userList) {
            try {
                sendToClient(user, message);
                sendToClient(user, game);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                System.err.println("Could not send Message to Client" + user.getDisplayName() + ".");
            }
        }
    }

    /**
     * Sends an Trade-Message to a specific User.
     *
     * @param user User to which the Trade-Message should be sent
     * @param message specific Trade-Message
     */
    public static void sendTradeMessage(User user, String message) {
        try {
            sendToClient(user, message);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Could not send GameSession to Client.");
        }
    }

    /**
     * Creates a Userlist from the Playerlist and sends the Message to
     * every User in this List.
     *
     * @param toSend  List of all Player to which the Knight-Message should be sent
     * @param message specific Knight-Message
     */
    public static void sendKnightMessage(List<Player> toSend, String message) {
        List<User> userList = createTradeUserList(toSend);
        for (User user : userList) {
            try {
                sendToClient(user, message);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                System.err.println("Could not send Message to Client" + user.getDisplayName() + ".");
            }
        }
    }

    /**
     * Creates a Userlist from the Game and sends the Message to
     * every User in this List.
     *
     * @param game    current Game
     * @param message specific Knight-Message
     */
    public static void sendKnightMessageBroadcast(GameSession game, String message) {
        List<User> userList = createGameUserList(game);
        for (User user : userList) {
            try {
                sendToClient(user, message);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                System.err.println("Could not send Message to Client" + user.getDisplayName() + ".");
            }
        }
    }

    /**
     * Sends an Error-Message on a specific Stream and flushes the Stream.
     *
     * @param connectionOutputStream Stream on which the Error-Message should be sent on
     * @param message specific Error-Message
     */
    public static void sendErrorMessage(ObjectOutputStream connectionOutputStream, String message) {
        try{
            connectionOutputStream.writeObject(message);
            connectionOutputStream.flush();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Could not send error message to Client.");
            System.err.println("Message: " + message);
        }
    }

    /**
     * Creates a List of all Users that are in the given Game.
     *
     * @param game GameSession
     * @return List of Users in the <code>game</code>
     */
    private static List<User> createGameUserList(GameSession game) {
        List<User> list = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            User foundUser = null;
            for (User user : Server.currentUsers) {
                if (user.getUserId() == player.getUserId()) {
                    foundUser = user;
                    break;
                }
            }
            if (foundUser != null) {
                list.add(foundUser);
            }
        }
        return list;
    }

    /**
     * Creates a List of UserIds, Usernames from a Set of Users.
     *
     * @param users Set of of all User currently Searching
     * @return List of UserId and Usernames of all User currently Searching
     */
    public static List<String> createSearchingList(Set<User> users) {
        List<String> list = new ArrayList<>();
        for (User user : users) {
            list.add("" + user.getUserId());
            list.add(user.getDisplayName());
        }
        return list;
    }

    /**
     * Creates a List of Users from a List of Players.
     *
     * @param players List of all Player which the Trade Message should be send to
     * @return List of all User which the Trade Message should be send to
     */
    private static List<User> createTradeUserList(List<Player> players) {
        List<User> list = new ArrayList<>();
        for (Player player : players) {
            User foundUser = null;
            for (User user : Server.currentUsers) {
                if (user.getUserId() == player.getUserId()) {
                    foundUser = user;
                    break;
                }
            }
            if (foundUser != null) {
                list.add(foundUser);
            }
        }
        return list;
    }

    /**
     * Sends a new Object to the Client and flushes the Stream.
     *
     * @param user User to be sent to
     * @param obj  Object to be sent to the User
     * @throws IOException when theres a Problem with the Stream
     */
    public static void sendToClient(User user, Object obj) throws IOException {
        user.getConnectionOutputStream().reset();
        user.getConnectionOutputStream().writeObject(obj);
        user.getConnectionOutputStream().flush();
    }
}
