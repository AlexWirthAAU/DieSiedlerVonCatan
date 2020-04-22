package com.example.diesiedler.presenter;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Christina Senger
 * <p>
 * Zentrale Datenklasse für die Netzwerkverbindung
 */
class ConnectionData {

    static final String HOST = "10.0.0.138";
    static final int PORT = 2020;
    static Socket client;
    static ObjectOutputStream outToServer;
    static SecureObjectStream inFromServer;

    private static class Send extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {

                client = new Socket(HOST, PORT);
                outToServer = new ObjectOutputStream(client.getOutputStream());
                inFromServer = new SecureObjectStream(client.getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
