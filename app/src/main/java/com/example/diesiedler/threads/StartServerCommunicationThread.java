package com.example.diesiedler.threads;

import android.os.Handler;
import android.os.Message;

import com.example.diesiedler.presenter.ClientData;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fabian Schaffenrath
 *
 * The StartServerCommunicationThread is only started once in the Welcome Activity
 * to create the Server-Connection and initialise the ObjectIn- and ObjectOutputStream.
 */
public class StartServerCommunicationThread extends Thread {

    private static final Logger logger = Logger.getLogger(StartServerCommunicationThread.class.getName()); // Logger

    private Handler handler;

    /**
     * Constructor - sets the Handler
     *
     * @param handler for the StartServerCommunicationThread
     */
    public StartServerCommunicationThread(Handler handler) {
        this.handler = handler;
    }

    /**
     * Initialises the Server-Connection and send a Message
     * to the Handler when it was successful.
     */
    public void run() {
        ClientData.initializeServerConnection();
        logger.log(Level.INFO, "Initialization done.");
        Message msg = Message.obtain();
        msg.arg1 = 1;
        handler.sendMessage(msg);
    }
}
