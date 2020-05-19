package com.example.diesiedler.presenter.handler;

import android.app.Activity;
import android.os.Looper;
import android.os.Message;

/**
 * @author Fabian Schaffenrath
 *
 * The Handler has a handleMessage Method, which is calles from different Activities
 * after a Game has been started.
 */
public class HandlerGlobalCallOverride extends HandlerOverride {

    public HandlerGlobalCallOverride(Looper mainLooper, Activity ac) {
        super(mainLooper, ac);
    }

    /**
     * Waiting for implementation --> Call with super(msg) in childrens handleMessage method.
     *
     * @param msg msg.arg1 has the Param for further Actions
     *            msg.obj can hold an Object, which has been send
     */
    @Override
    public void handleMessage(Message msg) {
    }
}
