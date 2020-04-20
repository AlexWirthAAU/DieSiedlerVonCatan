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

public class PresenterUpdate extends ConnectionData {

    private static String listsize;
    private static final Logger log = Logger.getLogger(PresenterUpdate.class.getName());

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

            try (Socket client = new Socket(HOST, PORT)) {

                System.out.println(listsize + " background");

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                SecureObjectStream inFromServer = new SecureObjectStream(client.getInputStream());

                outToServer.writeUTF("#UPDATE");
                outToServer.writeUTF(listsize); // write the message to output stream
                outToServer.flush();
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
