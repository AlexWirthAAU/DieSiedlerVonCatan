package com.example.catanserver.threads;

import com.example.catangame.GameSession;
import com.example.catangame.Player;
import com.example.catanserver.Server;
import com.example.catanserver.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Fabian Schaffenrath
 * This Class handles everything that is related to messages being sent to the client. It also is
 * responsible for closing connections.
 */

public class SendToClient {

    public static void sendUserId(Socket connection, int userId){
        try {
            sendToClient(connection,userId);
            connection.close();
        }catch(IOException ex){
            System.err.println(ex.getMessage());
            System.err.println("Could not send UserId to Client.");
        }
    }

    public static void sendSearchingList(Socket connection, Set<User> set){
        List<String> searchingList = createSearchingList(set);
        try{
            sendToClient(connection,searchingList);
            connection.close();
        }catch (IOException ex){
            System.err.println(ex.getMessage());
            System.err.println("Could not send List to Client.");
        }
    }

    public static void sendSearchingListBroadcast(Socket connection, Set<User> set){ // TODO: Discuss -> Broadcast or timed refreshes? (or both?)
        List<String> searchingList = createSearchingList(set);
        for (User user: set) {
            try{
                sendToClient(user,searchingList);
            }catch(IOException ex){
                System.err.println(ex.getMessage());
                System.err.println("Could not send List to Client" + user.getDisplayName() + ".");
            }
        }
        try{
            connection.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sendTradeMessageBroadcast(Socket connection, List<Player> toSend, String message) { // TODO: Discuss -> Broadcast or timed refreshes? (or both?)
        List<User> userList = createTradeUserList(toSend);
        for (User user : userList) {
            try {
                sendToClient(user, message);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                System.err.println("Could not send Message to Client" + user.getDisplayName() + ".");
            }
        }
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendGameStartBroadcast(Socket connection, GameSession game){
        List<User> userList = createGameUserList(game);
        if(userList.size() == game.getPlayers().size()){
            for (User user:userList) {
                try{
                    sendToClient(user,"STARTGAME");  // TODO: GameId necessary?
                }catch(IOException ex){
                    System.err.println(ex.getMessage());
                    System.err.println("Could not start GameSession at Client " + user.getDisplayName() + ".");
                }
            }
        }
        else{
            // TODO: What happens if user cant be found? (should not happen)
        }
        try{
            connection.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sendGameSession(Socket connection, GameSession game){
        try{
            sendToClient(connection,game);
            connection.close();
        }catch(IOException ex){
            System.err.println(ex.getMessage());
            System.err.println("Could not send GameSession to Client.");
        }
    }

    public static void sendGameSessionBroadcast(Socket connection, GameSession game){
        List<User> userList = createGameUserList(game);
        if(userList.size() == game.getPlayers().size()) {
            for (User user:userList){
                try {
                    sendToClient(user,game);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                    System.err.println("Could not send GameSession to Client " + user.getDisplayName() + ".");
                }
            }
        }
        else{
            // TODO: What happens if user cant be found? (should not happen)
        }
        try{
            connection.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sendErrorMessage(Socket connection,String message){
        try{
            sendToClient(connection,message);
        }catch (IOException ex){
            System.err.println(ex.getMessage());
            System.err.println("Could not send error message to Client.");
            System.err.println("Message: " + message);
        }
    }

    private static List<User> createGameUserList(GameSession game){
        List<User> list = new ArrayList<>();
        for (Player player:game.getPlayers()) {
            User foundUser = null;
            for (User user:Server.currentUsers) {
                if(user.getUserId() == player.getUserId()){
                    foundUser = user;
                    break;
                }
            }
            if(foundUser != null){
                list.add(foundUser);
            }
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

    private static List<String> createSearchingList(Set<User> users){
        List<String> list = new ArrayList<>();
        for (User user:users) {
            list.add(""+user.getUserId());
            list.add(user.getDisplayName());
        }
        return list;
    }

    private static void sendToClient(User user, Object obj) throws IOException{
        Socket connection = new Socket(user.getConnectionAddress(),user.getConnectionPort());
        ObjectOutputStream connectionOutputStream = new ObjectOutputStream(connection.getOutputStream());
        connectionOutputStream.writeObject(obj);
        connectionOutputStream.close();
        connection.close();
    }

    private static void sendToClient(Socket connection, Object obj) throws IOException{
        ObjectOutputStream connectionOutputStream = new ObjectOutputStream(connection.getOutputStream());
        connectionOutputStream.writeObject(obj);
        connectionOutputStream.close();
    }
}
