package com.example.diesiedler.presenters;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class PresenterSetColor {

    private static Map<String, String> playermMap;

    public void setColor(Map<String, String> map) {

        playermMap = map;
        SetColor setColor = new SetColor();
        setColor.execute();
    }

    private static class SetColor extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Socket client;
                client = new Socket("192.168.0.23", 2020); // connect to the server

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inFromServer = new ObjectInputStream(client.getInputStream());

                outToServer.writeUTF("#SETCOLOR");
                outToServer.writeObject(playermMap); // write the message to output stream
                outToServer.flush();

                inFromServer.readObject();

                outToServer.close();
                inFromServer.close();
                client.close(); // closing the connection

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
