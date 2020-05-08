package com.example.catangame;

import java.io.Serializable;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Ein Player kennt seine UserId, seinen displayName,
 * seine Farbe und sein Inventory und besitzt entsprechende
 * Getter und Setter.
 */
public class Player implements Serializable {

    private String displayName;
    private int userId;
    private Colors color;
    private PlayerInventory inventory;

    public Player(String displayName, int userId) {
        this.displayName = displayName;
        this.userId = userId;
        inventory = new PlayerInventory();
    }

    public int getUserId() {
        return this.userId;
    }

    public Colors getColor() {
        return this.color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
