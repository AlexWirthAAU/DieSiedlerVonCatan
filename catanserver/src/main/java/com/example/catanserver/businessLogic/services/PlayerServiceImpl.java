package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.PlayerImpl;

import java.util.ArrayList;

/**
 * @author Christina Senger
 * <p>
 * Der PlayerService verfügt zusätzlich übe Methoden, um einen Player
 * über seinen Namen oder seinen Host zu finden. Außerdem
 * hat er eine Methode, um die Namen aller Spieler als Liste von
 * Stringelementen zurückzugeben.
 */
public class PlayerServiceImpl extends ServiceImpl<Integer, PlayerImpl> implements PlayerService {

    /**
     * Durchsucht die Liste mit allen Spielern. Ist eine
     * playerId gleich der gegebenen Id, wird der player zurückgegeben.
     *
     * @param id Playerid
     * @return den Player mit der angegebenen Id, sonst null
     */
    public PlayerImpl findObject(Integer id) {
        for (PlayerImpl player : this.list) {
            if (player.getPlayerId() == id) {
                return player;
            }
        }
        return null;
    }

    /**
     * @return Liste aller Player
     */
    public ArrayList<PlayerImpl> fetchAll() {
        return this.list;
    }

    /**
     * Der Player erhält die seine UserId als PlayerId
     * und wird in die Liste aller Spieler eingefügt.
     *
     * @param player einzufügender Spieler
     * @return eingefügter Spieler
     */
    public PlayerImpl insert(PlayerImpl player) {
        player.setPlayerId(player.getUserId());
        this.list.add(player);
        return player;
    }

    /**
     * Gibt es unter allen Spielern einen Spieler mit der
     * gesuchten Id, wird dieser aus der Liste entfernt.
     *
     * @param id PlayerId des zu löschenden Spielers
     */
    public void delete(Integer id) {
        PlayerImpl p = null;
        for (PlayerImpl player : this.list) {
            if (player.getPlayerId() == id) {
                p = player;
                break;
            }
        }
        if (p != null) {
            this.list.remove(p);
        }
    }

    /**
     * Gibt es unter allen Spielern einen Spieler mit der
     * gegebenen Id, wird dieser aus der Liste entfernt
     * und der neue Spieler an der Stelle eingesetzt.
     *
     * @param player upzudatender Spieler
     * @return den neu eingefügen Spieler
     */
    public PlayerImpl update(PlayerImpl player) {
        PlayerImpl p = null;
        for (PlayerImpl play : this.list) {
            if (play.getPlayerId() == player.getPlayerId()) {
                p = play;
                break;
            }
        }
        if (p != null) {
            this.list.remove(p);
        }
        this.list.add(player);
        return p;
    }

    /**
     * Durchsucht die Liste mit allen Spielern. Ist ein
     * displayName gleich dem gegebenen Namen, wird der player zurückgegeben.
     *
     * @param username Username des gesuchten Spielers
     * @return den gesuchten Spieler, sonst null
     */
    public PlayerImpl findPlayerByUsername(String username) {
        for (PlayerImpl player : this.list) {
            if (username.equals(player.getDisplayName())) {
                return player;
            }
        }
        return null;
    }

    /**
     * Durchsucht die Liste mit allen Spielern. Ist ein
     * Host gleich dem gegebenen Host, wird der playe zurückgegeben.
     *
     * @param host Host des gesuchten Spielers
     * @return den gesuchten Spieler, sonst null
     */
    public PlayerImpl findPlayerByHost(String host) {
        for (PlayerImpl player : this.list) {
            if (host.equals(player.getHost())) {
                return player;
            }
        }
        return null;
    }

    /**
     * @return eine Liste aller DisplayNames
     */
    public ArrayList<String> getNameList() {
        ArrayList<String> names = new ArrayList<>();

        for (PlayerImpl player : this.list) {
            names.add(player.getDisplayName());
        }
        return names;
    }
}
