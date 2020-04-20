package com.example.diesiedler.presenters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.diesiedler.SelectColorsActivity;
import com.example.diesiedler.presenters.servercon.ConnectionData;
import com.example.diesiedler.presenters.servercon.SecureObjectStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PresenterStartGame extends ConnectionData {

    private static List<String> usersIn;
    private static String currUser;
    private static final Logger log = Logger.getLogger(PresenterStartGame.class.getName());

    public void setInGame(List<String> users, String curr, Activity activity) {

        usersIn = users;
        currUser = curr;

        if (!usersIn.get(0).equals(currUser)) {
            usersIn.remove(currUser);
            usersIn.add(0, currUser);
        }

        System.out.println(usersIn.get(0) + " " + usersIn.get(1) + " setinGame");
        StartGame startGame = new StartGame(activity);
        startGame.execute();
    }

    private static class StartGame extends AsyncTask<Void, Void, Object> {

        @SuppressLint("StaticFieldLeak")
        private Activity activity;

        StartGame(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Object doInBackground(Void... params) {

            Object gameList;

            try (Socket client = new Socket(HOST, PORT)) {

                System.out.println(currUser + " background");

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                SecureObjectStream inFromServer = new SecureObjectStream(client.getInputStream());

                outToServer.writeUTF("#STARTGAME");
                outToServer.writeObject(usersIn); // write the message to output stream
                outToServer.flush();

                gameList = inFromServer.readObject();

                outToServer.close();
                inFromServer.close();
                client.close(); // closing the connection

                return gameList;

            } catch (ClassNotFoundException | IOException cnfe) {
                log.log(Level.SEVERE, "Exception", cnfe);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {

            Intent intent = new Intent(activity, SelectColorsActivity.class);

            @SuppressWarnings("unchecked")
            ArrayList<String> gameList = (ArrayList<String>) result;

            intent.putStringArrayListExtra("gameList", gameList);
            intent.putExtra("myName", currUser);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.getApplicationContext().startActivity(intent);
        }
    }
}

