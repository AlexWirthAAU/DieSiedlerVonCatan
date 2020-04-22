package com.example.catanserver.businessLogic.model;

/**
 * @author Christina Senger
 * <p>
 * Ein Player kennt seine PlayerId, die GameId
 * des Spiel in dem er ist und seine Farbe und
 * hat entsprechende Getter und Setter.
 * <p>
 * Vom User erbt er dessen DisplayName, die UserId,
 * und den Host des Geräts.
 */
public class PlayerImpl extends UserImpl implements Player {

    private int gameId;
    private int playerId;
    private Colors color;
    private PlayerInventory inventory;

    public PlayerImpl(String displayName, String host, int playerId) {
        super(displayName, host);
        this.playerId = playerId;
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
