package com.example.catanserver.businessLogic;

import com.example.catanserver.businessLogic.services.GameServiceImpl;
import com.example.catanserver.businessLogic.services.PlayerServiceImpl;
import com.example.catanserver.businessLogic.services.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ServerThread implements Runnable {

    private static UserServiceImpl usi = new UserServiceImpl();
    private static PlayerServiceImpl psi = new PlayerServiceImpl();
    private static GameServiceImpl gsi = new GameServiceImpl();
    private UserOps userOps = new UserOps(usi);
    private PlayerOps playerOps = new PlayerOps(usi, psi, gsi);
    private Socket client;
    private ObjectOutputStream outToClient;
    private ObjectInputStream obInFromClient;
    private String name;

    private List<String> list = new ArrayList<>();

    ServerThread(Socket connection, ObjectInputStream obInFromClient, ObjectOutputStream outToClient) {
        this.client = connection;
        this.name = connection.getInetAddress().getHostAddress();
        this.outToClient = outToClient;
        this.obInFromClient = obInFromClient;
    }

    public void run() {

        try {
            System.out.println("thread");

            //ObjectInputStream obInFromClient = new ObjectInputStream(client.getInputStream());
            //ObjectOutputStream outToClient = new ObjectOutputStream(client.getOutputStream());

            String intent = this.obInFromClient.readUTF();

            String ip = client.getInetAddress().getHostAddress();

            String in;
            int inInt;
            System.out.println(intent);

            switch (intent) {
                case "#LOGIN":
                    System.out.println(intent);
                    in = this.obInFromClient.readUTF();
                    System.out.println(in);
                    list = userOps.updateUserList(in, ip);
                    break;
                case "#UPDATE":
                    System.out.println(intent);
                    in = this.obInFromClient.readUTF();
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
                    in = this.obInFromClient.readUTF();
                    list = playerOps.checkIfIn(in);
                    break;
                case "#SETCOLOR":
                    System.out.println(intent);
                    @SuppressWarnings("unchecked")
                    Map<String, String> inMap = (Map) this.obInFromClient.readObject();
                    list = playerOps.setColor(inMap);
                    break;
                case "#CHECKCOLOR":
                    System.out.println(intent);
                    in = this.obInFromClient.readUTF();
                    inInt = Integer.parseInt(in);
                    Map map = playerOps.checkColors(inInt);
                    this.outToClient.writeObject(map);
                    break;
                case "#BUILDREQUEST":
                    System.out.println(intent);
                    //in = this.obInFromClient.readUTF();
                    /**TODO: Implement build logic: Client will send String with the path-assets id (e.g. "knot_1314") and Server has to check if building at this asset for this player is allowed
                     * Client can't yet send to the Server (not implemented)
                     */
                    break;
                default:
                    break;
            }

            System.out.println("after");
            System.out.println(list);
            this.outToClient.writeObject(list);
            this.outToClient.flush();

            this.obInFromClient.close();
            this.outToClient.close();
            this.client.close();

        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
    }
}
