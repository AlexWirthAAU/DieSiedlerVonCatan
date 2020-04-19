package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.GameImpl;
import com.example.catanserver.businessLogic.model.PlayerImpl;

import java.util.ArrayList;
import java.util.List;

public class GameServiceImpl extends ServiceImpl<Integer, GameImpl> implements GameService {

    public GameServiceImpl() {
        this.currentId = 1;
    }

    public List<GameImpl> fetchAll() {
        return this.list;
    }

    public GameImpl findElement(Integer id) {
        for (GameImpl game : this.list) {
            if (game.getGameId() == id) {
                return game;
            }
        }
        return null;
    }

    public GameImpl insert(GameImpl game) {
        game.setGameId(currentId++);
        this.list.add(game);
        return game;
    }

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

    public GameImpl findGameByPlayername(String name) {
        for (GameImpl game : this.list) {
            for (PlayerImpl player : game.getPlayers()) {
                if (name.equals(player.getDisplayName())) {
                    return game;
                }
            }
        }
        return null;
    }

    public GameImpl findGameByGameId(Integer id) {
        for (GameImpl game : this.list) {
            if (id.equals(game.getGameId())) {
                return game;
            }
        }
        return null;
    }

    public GameImpl findGameByPlayers(List<String> players) {
        for (GameImpl game : this.list) {
            for (int i = 0; i < players.size() && i < game.getPlayers().size(); i++) {
                if ((players.get(i)).equals((game.getPlayers()).get(i).toString())) {
                    return game;
                }
            }
        }
        return null;
    }

    public List<String> getList(Integer id) {

        GameImpl game = findGameByGameId(id);
        List<String> gameList = new ArrayList<>();

        gameList.add(id.toString());

        for (PlayerImpl player : game.getPlayers()) {
            gameList.add((player.getDisplayName()));
            if (player.getColor() != null) {
                gameList.add(player.getColor().toString());
            }
        }

        return gameList;
    }
}
