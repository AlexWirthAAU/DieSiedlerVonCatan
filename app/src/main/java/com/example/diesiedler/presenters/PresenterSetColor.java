package com.example.diesiedler.presenters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.diesiedler.StartGameActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Map;

public class PresenterSetColor {

    private static Map<String, String> playermMap;
    private static Object newMap;

    public void setColor(Map<String, String> map, Activity activity) {

        playermMap = map;
        SetColor setColor = new SetColor(activity);
        setColor.execute();
    }

    private static class SetColor extends AsyncTask<Void, Void, Void> {

        @SuppressLint("StaticFieldLeak")
        private Activity activity;

        SetColor(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Socket client;
                client = new Socket("10.0.2.2", 2020); // connect to the server

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inFromServer = new ObjectInputStream(client.getInputStream());

                outToServer.writeUTF("#SETCOLOR");
                outToServer.writeObject(playermMap); // write the message to output stream

                newMap = inFromServer.readObject();

                outToServer.close();
                inFromServer.close();
                client.close(); // closing the connection

                //return newMap;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = new Intent(activity, StartGameActivity.class);
            intent.putExtra("players", (Serializable) newMap);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.getApplicationContext().startActivity(intent);
        }
    }
}
