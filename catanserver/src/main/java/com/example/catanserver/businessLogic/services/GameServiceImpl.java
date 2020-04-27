package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.Colors;
import com.example.catanserver.businessLogic.model.GameImpl;
import com.example.catanserver.businessLogic.model.player.PlayerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christina Senger
 * <p>
 * Der GameService verfügt zusätzlich über Methoden, um eine Spiel
 * über seine Id oder seine Spieler zu finden. Außerdem kann er
 * Spieler und deren Farben einander zuordnen und er hat eine Methode,
 * um die Id, die Namen aller Nutzer und deren Farben als Liste von
 * Stringelementen zurückzugeben.
 */
public class GameServiceImpl extends ServiceImpl<Integer, GameImpl> implements GameService {

    /**
     * Konstruktor - setzt die aktuelle Id auf 1
     */
    public GameServiceImpl() {
        this.currentId = 1;
    }

    /**
     * Durchsucht die Liste mit allen Spielen. Ist eine
     * gameId gleich der gegebenen Id, wird das Spiel zurückgegeben.
     *
     * @param id Gameid
     * @return das Spiel mit der angegebenen Id, sonst null
     */
    public GameImpl findObject(Integer id) {
        for (GameImpl game : this.list) {
            if (game.getGameId() == id) {
                return game;
            }
        }
        return null;
    }

    /**
     * @return Liste aller User
     */
    public ArrayList<GameImpl> fetchAll() {
        return this.list;
    }

    /**
     * Das Spiel bekommt die nächste freie Id
     * und wird in die Liste aller Spiele eingefügt.
     *
     * @param game einzufügendes Spiel
     * @return eingefügtes Spiel
     */
    public GameImpl insert(GameImpl game) {
        game.setGameId(currentId++);
        this.list.add(game);
        return game;
    }

    /**
     * Gibt es unter allen Spielen eine Spiel mit der
     * gesuchten Id, wird dieses aus der Liste entfernt.
     *
     * @param id GameId des zu löschenden Spiels
     */
    public void delete(Integer id) {
        GameImpl g = null;
        for (GameImpl game : this.list) {
            if (game.getGameId() == id) {
                g = game;
                break;
            }
        }
        if (g != null) {
            this.list.remove(g);
        }
    }

    /**
     * Gibt es unter allen Spielen eine Spiel mit der
     * gegebenen Id, wird dieses aus der Liste entfernt
     * und das neue Spiel an der Stelle eingesetzt.
     *
     * @param game upzudatendes Spiel
     * @return das neu eingefügte Spiel
     */
    public GameImpl update(GameImpl game) {
        GameImpl g = null;
        for (GameImpl gi : this.list) {
            if (gi.getGameId() == game.getGameId()) {
                g = gi;
                break;
            }
        }
        if (g != null) {
            this.list.remove(g);
        }
        this.list.add(game);
        return g;
    }

    /**
     * Durchsucht die Liste mit allen Spielen. Hat ein Spiel die
     * übergebenen Spieler, wird das Spiel zurückgegeben.
     *
     * @param players Liste den Spielern
     * @return das gesuchte Spiel, sonst null
     */
    public GameImpl findGameByPlayers(ArrayList<String> players) {

        for (GameImpl game : this.list) {
            List<PlayerImpl> toCheck = game.getPlayers();

            for (int i = 0; i < players.size() && i < toCheck.size(); i++) {
                String strToCheck = toCheck.get(i).getDisplayName();

                if ((players.get(i)).equals(strToCheck)) {
                    return game;
                }
            }
        }
        return null;
    }

    /**
     * Durchsucht die Spielerliste des gegebenen Spieles.
     * Hat ein Spieler die gesuchte Farbe, wird das Spiel zurückgegeben.
     *
     * @param color Farbe des Spielers
     * @param game  Spiel des Spielers
     * @return den gesuchten Spieler, sonst null
     */
    public PlayerImpl findPlayerInGameByColor(Colors color, GameImpl game) {

        List<PlayerImpl> toCheck = game.getPlayers();

        for (PlayerImpl player : toCheck) {

            if (player.getColor().equals(color)) {
                return player;
            }
        }
        return null;
    }

    /**
     * @param id Id des Spiels
     * @return Liste mit GameId, Spieler und deren Farben als String
     */
    public ArrayList<String> getList(Integer id) {

        GameImpl game = findObject(id);
        ArrayList<String> gameList = new ArrayList<>();

        gameList.add(id.toString());
        gameList.add(game.getPlayers().size() + "");

        for (PlayerImpl player : game.getPlayers()) {
            gameList.add((player.getDisplayName()));
        }

        for (PlayerImpl player : game.getPlayers()) {
            gameList.add((player.getDisplayName()));
            if (player.getColor() != null) {
                gameList.add(player.getColor().toString());
            }
        }

        return gameList;
    }
}
