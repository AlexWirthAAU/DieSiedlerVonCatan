package com.example.catanserver.businessLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServerThread implements Runnable {

    private static UserOps userOps = new UserOps();
    private static PlayerOps playerOps = new PlayerOps();
    private Socket client;
    private List<String> list = new ArrayList<>();

    ServerThread(Socket connection) {
        this.client = connection;
    }

    public void run() {

        try {
            System.out.println("thread");

            ObjectInputStream obInFromClient = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(client.getOutputStream());

            String intent = obInFromClient.readUTF();

            String in;
            String sender;
            int inInt;
            System.out.println(intent);

            switch (intent) {
                case "#LOGIN":
                    System.out.println(intent);
                    in = obInFromClient.readUTF();
                    System.out.println(in);
                    list = userOps.updateUserList(in);
                    break;
                case "#UPDATE":
                    System.out.println(intent);
                    in = obInFromClient.readUTF();
                    inInt = Integer.parseInt(in);
                    System.out.println(inInt);
                    list = userOps.updateUserList(inInt);
                    break;
                case "#STARTGAME":
                    System.out.println(intent);
                    @SuppressWarnings("unchecked")
                    List<String> inList = (List) obInFromClient.readObject();
                    list = playerOps.startGame(inList);
                    break;
                case "#CHECKGAME":
                    System.out.println(intent);
                    in = obInFromClient.readUTF();
                    list = playerOps.checkIfIn(in);
                    break;
                case "#SETCOLOR":
                    System.out.println(intent);
                    @SuppressWarnings("unchecked")
                    Map<String, String> inMap = (Map) obInFromClient.readObject();
                    list = playerOps.setColor(inMap);
                    break;
                case "#CHECKCOLOR":
                    System.out.println(intent);
                    in = obInFromClient.readUTF();
                    inInt = Integer.parseInt(in);
                    Map map = playerOps.checkColors(inInt);
                    outToClient.writeObject(map);
                    break;
                case "#BUILDSETTLEMENT":
                    System.out.println(intent);
                    in = obInFromClient.readUTF();
                    sender = obInFromClient.readUTF();
                    System.out.println("BUILDREQUEST delivered: " + in + " " + sender);
                    /**TODO: Implement build logic: Client will send String with the path-assets id (e.g. "settlement_1_3") + his InetAddress and Server has to check if building at this asset for this player is allowed
                     * in contains the name of the clicked asset
                     * sender contains InetAddress of the client
                     *
                     * If the knot is free to build, a broadcast message is necessary to update each player's gameboard
                     * If the knot is not free to build, only the player that requested to build, should receive a message and be able to select another knot
                     */
                    outToClient.writeObject("SETTLED");
                    break;
                case "#BUILDEDGE":
                    System.out.println(intent);
                    in = obInFromClient.readUTF();
                    sender = obInFromClient.readUTF();
                    System.out.println("BUILDREQUEST delivered: " + in + " " + sender);
                    /**TODO: Implement build logic: Client will send String with the path-assets id (e.g. "edge_1314") + his InetAddress and Server has to check if building at this asset for this player is allowed
                     * in contains the name of the clicked asset
                     * sender contains InetAddress of the client
                     */
                    outToClient.writeObject("BUILD");
                    break;
                default:
                    break;
            }

            System.out.println("after");
            outToClient.writeObject(list);
            outToClient.flush();

            obInFromClient.close();
            outToClient.close();
            client.close();

        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
    }
}
