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
 * This Class handles everything that is related to messages being sent to the client.
 */

public class SendToClient {

    public static void sendUserId(User user, int userId) {
        try {
            sendToClient(user, userId);
        }catch(IOException ex){
            System.err.println(ex.getMessage());
            System.err.println("Could not send UserId to Client.");
        }
    }

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

    public static void sendTradeMessageBroadcast(List<Player> toSend, String message) {
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

    public static void sendTradeMessage(User user, String message) {
        try {
            sendToClient(user, message);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Could not send GameSession to Client.");
        }
    }

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

    private static List<String> createSearchingList(Set<User> users) {
        List<String> list = new ArrayList<>();
        for (User user : users) {
            list.add("" + user.getUserId());
            list.add(user.getDisplayName());
        }
        return list;
    }

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
     * Sends a new Object to the Client.
     *
     * @param user User to be sent to.
     * @param obj  Object to be sent to the User.
     * @throws IOException when theres a Problem with the Stream
     */
    private static void sendToClient(User user, Object obj) throws IOException {
        user.getConnectionOutputStream().reset();
        user.getConnectionOutputStream().writeObject(obj);
        user.getConnectionOutputStream().flush();
    }
}
