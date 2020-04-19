package com.example.diesiedler.presenters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.diesiedler.SelectColorsActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PresenterStartGame {

    private static List<String> usersIn;
    private static String currUser;

    public void setInGame(List<String> users, String curr, Activity activity) {

        usersIn = users;
        currUser = curr;

        if (!usersIn.get(0).equals(currUser)) {
            usersIn.remove(currUser);
            usersIn.add(0, currUser);
        }

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

            try {

                Socket client;
                System.out.println(currUser + " background");
                client = new Socket("10.0.2.2", 2020); // connect to the server

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inFromServer = new ObjectInputStream(client.getInputStream());

                outToServer.writeUTF("#STARTGAME");
                outToServer.writeObject(usersIn); // write the message to output stream

                gameList = inFromServer.readObject();

                outToServer.close();
                inFromServer.close();
                client.close(); // closing the connection

                return gameList;

            } catch (ClassNotFoundException | IOException cnfe) {
                cnfe.printStackTrace();
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

