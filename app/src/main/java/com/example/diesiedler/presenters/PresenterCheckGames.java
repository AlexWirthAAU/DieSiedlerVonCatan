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
import java.util.logging.Level;
import java.util.logging.Logger;

public class PresenterCheckGames extends ConnectionData {

    private static String user;
    private static final Logger log = Logger.getLogger(PresenterCheckGames.class.getName());

    public void checkIfIn(String username, Activity activity) {

        user = username;
        System.out.println(user + " checkIfIn");
        CheckGame checkGame = new CheckGame(activity);
        checkGame.execute();
    }

    private static class CheckGame extends AsyncTask<Void, Void, Object> {

        @SuppressLint("StaticFieldLeak")
        private Activity activity;

        CheckGame(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Object doInBackground(Void... params) {

            Object gameList;

            try (Socket client = new Socket(HOST, PORT)) {

                System.out.println(user + " background");

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                SecureObjectStream inFromServer = new SecureObjectStream(client.getInputStream());

                outToServer.writeUTF("#CHECKGAME");
                outToServer.writeUTF(user); // write the message to output stream
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

            if (result != null) {
                System.out.println("neuesSpiel");
                Intent intent = new Intent(activity, SelectColorsActivity.class);

                @SuppressWarnings("unchecked")
                ArrayList<String> gameList = (ArrayList<String>) result;

                intent.putStringArrayListExtra("gameList", gameList);
                intent.putExtra("myName", user);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getApplicationContext().startActivity(intent);
            }
        }
    }
}
