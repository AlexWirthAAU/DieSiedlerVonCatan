package com.example.catangame;

import java.io.Serializable;

/**
 * @author Christina Senger
 * @author Fabian Schaffenrath (edit)
 * <p>
 * Represantation of a Player
 */
public class Player implements Serializable {

    private String displayName;
    private int userId;

    private Colors color; // the Players Color
    private PlayerInventory inventory; // the Player Inventory
    private int toSkip;

    /**
     * Constructor - set the Values with the Data from Thread
     * and give the Player a new (empty) Inventory.
     *
     * @param displayName the Players Name
     * @param userId      the Players ID
     */
    public Player(String displayName, int userId) {
        this.displayName = displayName;
        this.userId = userId;
        inventory = new PlayerInventory();
        toSkip = 0;
    }

    // Getter
    public int getUserId() {
        return this.userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Colors getColor() {
        return this.color;
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    // Set Color
    public void setColor(Colors color) {
        this.color = color;
    }

    public void addSkip(){
        toSkip++;
    }

    public void skip(){
        if(toSkip>0) {
            toSkip--;
        }
    }

    public boolean hasToSkip(){
        if(toSkip>0){
            return true;
        }
        return false;
    }

}
