package com.example.diesiedler.presenter.handler;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

/**
 * Hanlder, der den Looper zum Messagehandling und die aufrufende Aktivität speichert.
 */
public class HandlerOverride extends Handler {

    protected Activity activity;

    public HandlerOverride(Looper mainLooper, Activity ac) {
        super(mainLooper);
        activity = ac;
    }
}
