package com.example.diesiedler.presenters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Button;

import com.example.diesiedler.R;
import com.example.diesiedler.presenters.servercon.ConnectionData;
import com.example.diesiedler.presenters.servercon.SecureObjectStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PresenterCheckColors extends ConnectionData {

    private static Integer gameId;
    private static final Logger log = Logger.getLogger(PresenterCheckColors.class.getName());

    public void checkColors(Integer id, Activity activity) {

        gameId = id;
        CheckColors checkColors = new CheckColors(activity);
        checkColors.execute();
    }

    private static class CheckColors extends AsyncTask<Void, Void, Object> {

        @SuppressLint("StaticFieldLeak")
        private Activity ac;
        @SuppressLint("StaticFieldLeak")
        private Button green, orange, violett, lightblue;

        CheckColors(Activity activity) {
            this.ac = activity;
            this.green = ac.findViewById(R.id.green);
            this.orange = ac.findViewById(R.id.orange);
            this.violett = ac.findViewById(R.id.violett);
            this.lightblue = ac.findViewById(R.id.lightblue);
        }

        @Override
        protected Object doInBackground(Void... params) {

            try (Socket client = new Socket(HOST, PORT)) {

                System.out.println(gameId + " background");

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                SecureObjectStream inFromServer = new SecureObjectStream(client.getInputStream());

                outToServer.writeUTF("#CHECKCOLOR");
                outToServer.writeUTF(gameId + ""); // write the message to output stream
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
            Map<String, String> gameMap = (Map) result;

            if (gameMap != null) {
                System.out.println("colorBack");

                if (gameMap.containsKey("green")) {
                    ac.findViewById(R.id.green).setEnabled(false);
                    green.setText(gameMap.get("green"));
                }
                if (gameMap.containsKey("orange")) {
                    ac.findViewById(R.id.orange).setEnabled(false);
                    orange.setText(gameMap.get("orange"));
                }
                if (gameMap.containsKey("violett")) {
                    ac.findViewById(R.id.violett).setEnabled(false);
                    violett.setText(gameMap.get("violett"));
                }
                if (gameMap.containsKey("lightblue")) {
                    ac.findViewById(R.id.lightblue).setEnabled(false);
                    lightblue.setText(gameMap.get("lightblue"));
                }

            }
        }
    }
}
