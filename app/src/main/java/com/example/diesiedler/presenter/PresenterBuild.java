package com.example.diesiedler.presenter;


import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PresenterBuild extends ConnectionData {
    /**
     * This Presenter-Class should communicate the ID of the asset on which the user wants to build, to the server
     */

    private static final Logger log = Logger.getLogger(Presenter.class.getName());
    private static StringBuilder action;
    private static StringBuilder toWrite;
    private static StringBuilder sender;

    public void chooseAssetID(String toSend) {

        action = new StringBuilder(1);
        toWrite = new StringBuilder(1);
        sender = new StringBuilder(1);


        log.log(Level.INFO, "send" + toSend);
        action.replace(0, 0, "#BUILDREQUEST");
        toWrite.replace(0, 0, toSend);

        SendToServer send = new SendToServer();
        send.execute();
    }


    private static class SendToServer extends AsyncTask<Void, Void, Object> {


        @Override
        protected Object doInBackground(Void... voids) {
            try (Socket client = new Socket(HOST, PORT)) {

                log.log(Level.INFO, "intent: " + action);
                log.log(Level.INFO, "background: " + toWrite);

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                SecureObjectStream inFromServer = new SecureObjectStream(client.getInputStream());

                sender.replace(0, 0, String.valueOf(client.getInetAddress()));

                outToServer.writeUTF(action.toString());
                outToServer.writeUTF(toWrite.toString());
                outToServer.writeUTF(sender.toString());
                outToServer.flush();

                Object object = inFromServer.readObject();
                log.log(Level.INFO, "back" + object);

                outToServer.close();
                inFromServer.close();

                return object;

            } catch (ClassNotFoundException | IOException cnfe) {
                log.log(Level.SEVERE, "Exception", cnfe);
            }
            return null;
        }
    }
}
