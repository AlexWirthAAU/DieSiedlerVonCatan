package com.example.catanserver.businessLogic.model;

public class PlayerImpl extends UserImpl implements Player {

    private int gameId;
    private int playerId;
    private Colors color;
    private PlayerInventory inventory;

    public PlayerImpl(String displayName) {
        super(displayName);
        inventory = new PlayerInventory();
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getGameId() {
        return this.gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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

    public void setInventory(PlayerInventory inventory) {
        this.inventory = inventory;
    }
}