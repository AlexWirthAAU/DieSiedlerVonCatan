package com.example.catanserver.businessLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

    private static final int SERVER_PORT = 2020;
    static Vector<ServerThread> ar = new Vector<>();

    public static void main(String[] args) {

        Socket con;

        try {

            ServerSocket serverSocket = new ServerSocket(Server.SERVER_PORT);
            System.out.println("Server running on port: " + Server.SERVER_PORT);

            while (true) {
                con = serverSocket.accept();
                if (con != null) {
                    System.out.println("Server");

                    ObjectInputStream obInFromClient = new ObjectInputStream(con.getInputStream());
                    ObjectOutputStream outToClient = new ObjectOutputStream(con.getOutputStream());
                    ServerThread svrThread = new ServerThread(con, obInFromClient, outToClient);

                    Thread thread = new Thread(svrThread);
                    ar.add(svrThread);

                    thread.start();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}