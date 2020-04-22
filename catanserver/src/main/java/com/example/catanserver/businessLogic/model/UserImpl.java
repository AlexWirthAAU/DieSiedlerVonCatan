package com.example.catanserver.businessLogic.model;

import java.io.Serializable;

/**
 * @author Christina Senger
 * <p>
 * Ein User kennt seine UserId, seinen
 * DisplayName und seinen Host und
 * hat entsprechende Getter und Setter.
 */
public class UserImpl implements User, Serializable {

    private int userId;
    private String displayName;
    private String host;

    public UserImpl(String displayName, String host) {

        this.displayName = displayName;
        this.host = host;
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

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
