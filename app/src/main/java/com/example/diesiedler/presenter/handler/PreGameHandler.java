package com.example.diesiedler.presenter.handler;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

/**
 * @author Fabian Schaffenrath
 *
 * Handler which saves the Looper for Messagehandling and the calling Activity
 * Used for actions pre game start.
 */
public class PreGameHandler extends Handler {

    protected Activity activity;

    /**
     * Constructor
     *
     * @param mainLooper Looper for Messagehandling
     * @param ac         calling Activity
     */
    public PreGameHandler(Looper mainLooper, Activity ac) {
        super(mainLooper);
        activity = ac;
    }
}
