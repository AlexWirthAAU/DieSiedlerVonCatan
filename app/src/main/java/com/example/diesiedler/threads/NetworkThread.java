package com.example.diesiedler.threads;

import com.example.diesiedler.presenter.ServerQueries;

/**
 * @author Fabian Schaffenrath
 *
 * The NetworkThread manages all Server-Requests. Dabei bekommt er
 * Therefore, he gets a precast String and sends it to the Server.
 */
public class NetworkThread extends Thread {

    private String query;

    /**
     * Constructor - Sets the given precast Query as Quer to send.
     *
     * @param query precast Query
     */
    public NetworkThread(String query) {
        this.query = query;
    }

    /**
     * Executes sendStringQuery-Method of ServerQueries-Class with the Query.
     */
    @Override
    public void run() {
        ServerQueries.sendStringQuery(query);
    }
}
