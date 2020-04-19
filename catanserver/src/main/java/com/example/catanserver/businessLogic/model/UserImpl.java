package com.example.catanserver.businessLogic.model;

import java.io.Serializable;

public class UserImpl implements User, Serializable {

    private int userId;
    private String displayName;

    public UserImpl(String displayName) {
        this.displayName = displayName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
