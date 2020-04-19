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
            int inInt;
            //System.out.println(inFromClient.readLine());

            switch (intent) {
                case "#LOGIN":
                    System.out.println(intent);
                    in = obInFromClient.readUTF();
                    list = userOps.updateUserList(in);
                case "#UPDATE":
                    System.out.println(intent);
                    in = obInFromClient.readUTF();
                    inInt = Integer.parseInt(in);
                    list = userOps.updateUserList(inInt);
                case "#STARTGAME":
                    System.out.println(intent);
                    @SuppressWarnings("unchecked")
                    List<String> inList = (List) obInFromClient.readObject();
                    list = playerOps.startGame(inList);
                case "#CHECKGAME":
                    System.out.println(intent);
                    in = obInFromClient.readUTF();
                    list = playerOps.checkIfIn(in);
                case "#SETCOLOR":
                    System.out.println(intent);
                    @SuppressWarnings("unchecked")
                    Map<String, String> inMap = (Map) obInFromClient.readObject();
                    list = playerOps.setColor(inMap);
                case "#CHECKCOLOR":
                    System.out.println(intent);
                    in = obInFromClient.readUTF();
                    inInt = Integer.parseInt(in);
                    Map map = playerOps.checkColors(inInt);
                    outToClient.writeObject(map);
            }

            /*
            try {
                object=obInFromClient.readObject();
                if (object instanceof List) {
                    System.out.println("if");
                    inList = (List) object;
                    list = playerOps.startGame(inList);
                } else {
                    System.out.println("else");
                    inMap = (Map) object;
                    playerOps.setColor(inMap);
                }
            } catch (OptionalDataException ode) {
                in=obInFromClient.readUTF();
                System.out.println("catchob" + in);
                try {
                    System.out.println("try");
                    inInt = Integer.parseInt(in);
                    list = userOps.updateUserList(inInt);
                } catch (NumberFormatException nfe) {
                    System.out.println("catch");
                    list = userOps.updateUserList(in);
                } finally {
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i) + " thread");
                        }
                    }
                }
            }*/

            outToClient.writeObject(list);

            obInFromClient.close();
            outToClient.close();
            client.close();

        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
    }
}
