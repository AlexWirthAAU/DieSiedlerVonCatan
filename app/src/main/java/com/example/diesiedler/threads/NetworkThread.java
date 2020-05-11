package com.example.diesiedler.threads;

import com.example.diesiedler.presenter.ServerQueries;

/**
 * @author Fabian Schaffenrath
 * Der NetworkThread übernimmt alle Anfragen, die an den Server geschickt werden. Dabei bekommt er
 * bereits einen fertigen request String und schickt diesen nur an den Server.
 */
public class NetworkThread extends Thread {

    private String query;

    public NetworkThread(String query) {
        this.query = query;
    }

    public void run() {
        ServerQueries.sendStringQuery(query);
    }
}
