package com.example.diesiedler.presenter.handler;

import android.app.Activity;
import android.os.Looper;
import android.os.Message;

/**
 * @author Fabian Schaffenrath
 * Dieser Handler implementiert eine handleMessage Methode, die nachdem Spielstart von mehreren Aktivitäten
 * aufgerufen werden sollte. (Beispiel: Trade Anfrage; vielleicht auch Entlarven der Schummelfunktion)
 */

public class HandlerGlobalCallOverride extends HandlerOverride {

    public HandlerGlobalCallOverride(Looper mainLooper, Activity ac){
        super(mainLooper,ac);
    }

    /**
     * Waiting for implementation
     * Call with super(msg) in childrens handleMessage method.
     * @param msg msg.arg1 beinhaltet den entsprechenden Parameter zur weiteren Ausführung
     *            Gegebenfalls befindet sich in msg.obj ein zusätzlicher Parameter;
     */
    @Override
    public void handleMessage(Message msg){
    }

}
