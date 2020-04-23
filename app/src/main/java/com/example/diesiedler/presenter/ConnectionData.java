package com.example.diesiedler.presenter;

class ConnectionData {

    static final String HOST = "193.81.249.102";
    static final int PORT = 2020;

    public ConnectionData() {
        //added empty constructor so the implicit one is not used because sonarcloud said that this is an issue
        //sonarcloud message: "Utility classes should not have public constructors"
    }
}
