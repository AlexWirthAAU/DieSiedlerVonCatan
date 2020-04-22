package com.example.catanserver.businessLogic.model;

/**
 * @author Christina Senger
 * <p>
 * Ein User kennt seine UserId, seinen
 * DisplayName und seinen Host und
 * hat entsprechende Getter und Setter.
 */
public interface User {

    int getUserId();

    void setUserId(int userId);

    String getDisplayName();

    void setDisplayName(String displayName);

    String getHost();

    void setHost(String host);

}
