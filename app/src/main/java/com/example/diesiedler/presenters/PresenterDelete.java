package com.example.diesiedler.presenters;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.diesiedler.MyAdapter;
import com.example.diesiedler.presenters.servercon.ConnectionData;
import com.example.diesiedler.presenters.servercon.SecureObjectStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PresenterDelete extends ConnectionData {

    private static String myName;
    private static final Logger log = Logger.getLogger(PresenterDelete.class.getName());

    public void removeMeFromUserList(String username, MyAdapter adapter) {
        myName = username;
        DeleteFromList deleteFromList = new DeleteFromList(adapter);
        deleteFromList.execute();
    }

    private static class DeleteFromList extends AsyncTask<Void, Void, Object> {

        @SuppressLint("StaticFieldLeak")
        private MyAdapter adapter;

        DeleteFromList(MyAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected Object doInBackground(Void... params) {

            try (Socket client = new Socket(HOST, PORT)) {

                System.out.println(myName + " background");

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                SecureObjectStream inFromServer = new SecureObjectStream(client.getInputStream());

                outToServer.writeUTF("#LOGIN");
                outToServer.writeUTF(myName); // write the message to output stream
                Object object = inFromServer.readObject();

                outToServer.close();
                inFromServer.close();
                client.close(); // closing the connection

                return object;

            } catch (ClassNotFoundException | IOException cnfe) {
                log.log(Level.SEVERE, "Exception", cnfe);
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
