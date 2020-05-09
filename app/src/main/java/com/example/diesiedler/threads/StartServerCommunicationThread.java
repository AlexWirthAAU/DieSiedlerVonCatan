package com.example.diesiedler.threads;

import android.os.Handler;
import android.os.Message;

import com.example.diesiedler.presenter.ClientData;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Fabian Schaffenrath
 * Der StartServerCommunicationThread wird nur einmal in der Welcome Activity aufgerufen, um die Server
 * Verbindung herzustellen. Dabei initialisiert er bereits sowohl ObjectIn- als auch ObjectOutputStream.
 */
public class StartServerCommunicationThread extends Thread {

    private Handler handler;

    private static final Logger logger = Logger.getLogger(StartServerCommunicationThread.class.getName());

    public StartServerCommunicationThread(Handler handler){
        this.handler = handler;
    }

    /**
     * Initialisiert die Server Verbindung und schickt seinem Handler anschließend eine Meldung, falls
     * erfolgreich.
     */
    public void run(){
        ClientData.initializeServerConnection();
        logger.log(Level.INFO,"Initialization done.");
        Message msg = Message.obtain();
        msg.arg1 = 1;
        handler.sendMessage(msg);
    }
}
