package com.example.diesiedler.presenters;

import android.os.AsyncTask;

import com.example.diesiedler.presenters.servercon.ConnectionData;
import com.example.diesiedler.presenters.servercon.SecureObjectStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PresenterSetColor extends ConnectionData {

    private static Map<String, String> playermMap;
    private static final Logger log = Logger.getLogger(PresenterSetColor.class.getName());

    public static void setColor(Map<String, String> map) {

        playermMap = map;
        SetColor setColor = new SetColor();
        setColor.execute();
    }

    private static class SetColor extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try (Socket client = new Socket(HOST, PORT)) {

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                SecureObjectStream inFromServer = new SecureObjectStream(client.getInputStream());

                outToServer.writeUTF("#SETCOLOR");
                outToServer.writeObject(playermMap); // write the message to output stream
                outToServer.flush();

                outToServer.close();
                inFromServer.close();
                client.close();

            } catch (IOException ioe) {
                log.log(Level.SEVERE, "Exception", ioe);
            }

            return null;
        }
    }
}
