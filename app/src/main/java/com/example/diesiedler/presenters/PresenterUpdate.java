package com.example.diesiedler.presenters;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.diesiedler.MyAdapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class PresenterUpdate {

    private static String listsize;

    public void checkForChanges(int size, MyAdapter adapter) {
        System.out.println(size + " send");
        listsize = size + "";
        UpdateList updateList = new UpdateList(adapter);
        updateList.execute();
    }

    private static class UpdateList extends AsyncTask<Void, Void, Object> {

        @SuppressLint("StaticFieldLeak")
        private MyAdapter adapter;

        UpdateList(MyAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected Object doInBackground(Void... params) {
            try {

                Socket client;
                System.out.println(listsize + " background");
                client = new Socket("10.0.2.2", 2020); // connect to the server

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream inFromServer = new ObjectInputStream(client.getInputStream());

                outToServer.writeUTF("UPDATE");
                outToServer.writeUTF(listsize); // write the message to output stream
                outToServer.flush();
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
