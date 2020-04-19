package com.example.diesiedler.presenters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.diesiedler.SearchPlayersActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

public class Presenter {

    private static String username;


    public void addUserAndGetUserList(Activity ac, String toSend) {
        System.out.println(toSend + " send");
        username = toSend;
        SendStringAndGetUsers send = new SendStringAndGetUsers(ac);
        send.execute();
    }

    private static class SendStringAndGetUsers extends AsyncTask<Void, Void, List> {

        @SuppressLint("StaticFieldLeak")
        private Activity activity;

        SendStringAndGetUsers(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected List doInBackground(Void... params) {

            try {

                Socket client;
                System.out.println(username + " background");
                client = new Socket("10.0.2.2", 2020); // connect to the server

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inFromServer = new ObjectInputStream(client.getInputStream());

                outToServer.writeUTF("#LOGIN");
                outToServer.writeUTF(username); // write the message to output stream
                outToServer.flush();

                List l = (List) inFromServer.readObject();
                System.out.println(l.get(0) + " back");

                outToServer.close();
                inFromServer.close();
                client.close(); // closing the connection

                return l;

            } catch (ClassNotFoundException | IOException cnfe) {
                cnfe.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List list) {

            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i) + " post");
            }

            Intent intent = new Intent(activity, SearchPlayersActivity.class);
            intent.putExtra("name", (Serializable) list);
            intent.putExtra("myName", username);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.getApplicationContext().startActivity(intent);

        }
    }
}
