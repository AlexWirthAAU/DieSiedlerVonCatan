package com.example.diesiedler.presenters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Button;

import com.example.diesiedler.MyAdapter;
import com.example.diesiedler.R;
import com.example.diesiedler.SearchPlayersActivity;
import com.example.diesiedler.SelectColorsActivity;
import com.example.diesiedler.presenters.servercon.ConnectionData;
import com.example.diesiedler.presenters.servercon.SecureObjectStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Presenter extends ConnectionData {

    private static final Logger log = Logger.getLogger(Presenter.class.getName());
    private static StringBuilder action = new StringBuilder(1);
    private static StringBuilder toWrite = new StringBuilder(1);
    private static ArrayList usersIn;
    private static String currUser;

    public static void checkColors(Integer id, Activity ac) {

        action.replace(0, 0, "#CHECKCOLOR");
        toWrite.replace(0, 0, id + "");

        SendToServer checkColors = new SendToServer(ac, "Buttons");
        checkColors.execute();
    }

    public static void removeMeFromUserList(String username, MyAdapter adapter) {

        log.log(Level.INFO, "checkIfIn", username);
        action.replace(0, 0, "#DELETE");
        toWrite.replace(0, 0, username);

        SendToServer deleteFromList = new SendToServer(adapter);
        deleteFromList.execute();
    }

    public static void setInGame(ArrayList<String> users, String curr, Activity activity) {

        if (!users.get(0).equals(curr)) {
            users.remove(curr);
            users.add(0, curr);
        }

        log.log(Level.INFO, "setInGame", curr);
        action.replace(0, 0, "#STARTGAME");
        usersIn = users;
        currUser = curr;

        SendToServer startGame = new SendToServer(activity);
        startGame.execute();
    }

    public static void checkForChanges(int size, MyAdapter adapter) {

        log.log(Level.INFO, "sizesend", size);
        action.replace(0, 0, "#UPDATE");
        toWrite.replace(0, 0, size + "");

        SendToServer updateList = new SendToServer(adapter);
        updateList.execute();
    }

    public void addUserAndGetUserList(Activity ac, String toSend) {

        log.log(Level.INFO, "send", toSend);
        action.replace(0, 0, "#LOGIN");
        toWrite.replace(0, 0, toSend);

        SendToServer send = new SendToServer(ac);
        send.execute();
    }

    public void checkIfIn(String username, Activity ac) {

        log.log(Level.INFO, "checkIfIn", username);
        action.replace(0, 0, "#CHECKGAME");
        toWrite.replace(0, 0, username);

        SendToServer checkGame = new SendToServer(ac);
        checkGame.execute();
    }

    private static class SendToServer extends AsyncTask<Void, Void, Object> {

        @SuppressLint("StaticFieldLeak")
        private Activity ac;
        @SuppressLint("StaticFieldLeak")
        private Button green, orange, violett, lightblue;
        @SuppressLint("StaticFieldLeak")
        private MyAdapter adapter;

        SendToServer(Activity ac) {
            this.ac = ac;
        }

        SendToServer(Activity ac, String extra) {

            this.ac = ac;
            log.log(Level.INFO, extra);

            this.green = ac.findViewById(R.id.green);
            this.orange = ac.findViewById(R.id.orange);
            this.violett = ac.findViewById(R.id.violett);
            this.lightblue = ac.findViewById(R.id.lightblue);
        }

        SendToServer(MyAdapter adapter) {

            this.adapter = adapter;

        }

        @Override
        protected Object doInBackground(Void... params) {

            try (Socket client = new Socket(HOST, PORT)) {

                log.log(Level.INFO, "intent", action);
                log.log(Level.INFO, "background", toWrite);

                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                SecureObjectStream inFromServer = new SecureObjectStream(client.getInputStream());

                outToServer.writeUTF(action.toString());

                if (!action.toString().equals("#STARTGAME")) {
                    outToServer.writeUTF(toWrite.toString()); // write the message to output stream
                } else {
                    outToServer.writeObject(usersIn); // write the message to output stream
                }
                outToServer.flush();

                Object object = inFromServer.readObject();
                log.log(Level.INFO, "back", object);

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

            String action2 = action.toString();
            System.out.println(action2);

            switch (action2) {

                case "#LOGIN":
                    @SuppressWarnings("unchecked")
                    ArrayList<String> list = (ArrayList) result;

                    for (int i = 0; i < list.size(); i++) {
                        log.log(Level.INFO, "post", list.get(i));
                    }

                    Intent intent = new Intent(ac, SearchPlayersActivity.class);
                    intent.putExtra("name", list);
                    intent.putExtra("myName", toWrite.toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ac.getApplicationContext().startActivity(intent);
                    break;

                case "CHECKCOLOR":
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
                    break;

                case "CHECKGAME":
                    if (result != null) {
                        log.log(Level.INFO, "neuesSpiel");
                        Intent intent2 = new Intent(ac, SelectColorsActivity.class);

                        @SuppressWarnings("unchecked")
                        ArrayList<String> gameList = (ArrayList<String>) result;

                        intent2.putStringArrayListExtra("gameList", gameList);
                        intent2.putExtra("myName", toWrite.toString());
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ac.getApplicationContext().startActivity(intent2);
                    }
                    break;

                case "#DELETE":

                case "#UPDATE":
                    @SuppressWarnings("unchecked")
                    ArrayList<String> newlist = (ArrayList) result;

                    if (newlist != null) {

                        adapter.update(newlist);
                    }
                    break;

                case "#STARTGAME":
                    Intent intent3 = new Intent(ac, SelectColorsActivity.class);

                    @SuppressWarnings("unchecked")
                    ArrayList<String> gameList = (ArrayList<String>) result;

                    intent3.putStringArrayListExtra("gameList", gameList);
                    intent3.putExtra("myName", currUser);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ac.getApplicationContext().startActivity(intent3);
                    break;

                default:
                    break;
            }
        }
    }
}
