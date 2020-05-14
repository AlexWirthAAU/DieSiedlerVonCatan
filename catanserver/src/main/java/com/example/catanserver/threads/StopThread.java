package com.example.catanserver.threads;

import com.example.catanserver.Server;
import com.example.catanserver.User;

/**
 * @author Fabian Schaffenrath
 * @author Christina Senger (Documentation edit)
 *
 * This Thread removes a User from the searching list.
 */
public class StopThread extends Thread {

    private User user;

    /**
     * Constructor
     *
     * @param user the User trying to stop searching
     */
    public StopThread(User user) {
        this.user = user;
    }

    /**
     * Removes a User from the searching list and sends an updated
     * List of searching Users to everyone still present in the List.
     */
    public void run() {
        if (Server.currentlySearching.remove(user)) {
            SendToClient.sendSearchingListBroadcast(Server.currentlySearching);
        }
    }
}
