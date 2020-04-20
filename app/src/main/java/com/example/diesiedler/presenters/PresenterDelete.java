package com.example.diesiedler.presenters;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.diesiedler.MyAdapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class PresenterDelete {

    private static String myName;

    public void removeMeFromUserList(String username, MyAdapter adapter) {
        myName = username;
        DeleteFromList deleteFromList = new DeleteFromList(adapter);
        deleteFromList.execute();
    }

    private static class DeleteFromList extends AsyncTask<Void, Void, Object> implements ConnectionData {

        @SuppressLint("StaticFieldLeak")
        private MyAdapter adapter;

        DeleteFromList(MyAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected Object doInBackground(Void... params) {
            try {

                Socket client;
                System.out.println(myName + " background");
                client = new Socket(host, PORT); // connect to the server

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inFromServer = new ObjectInputStream(client.getInputStream());

                outToServer.writeUTF("#LOGIN");
                outToServer.writeUTF(myName); // write the message to output stream
                Object object = inFromServer.readObject();

                outToServer.close();
                inFromServer.close();
                client.close(); // closing the connection

                return object;

            } catch (ClassNotFoundException | IOException cnfe) {
                cnfe.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {

            @SuppressWarnings("unchecked")
            List<String> newlist = (List) result;

            if (newlist != null) {

                adapter.update(newlist);
            }
        }
    }
}
