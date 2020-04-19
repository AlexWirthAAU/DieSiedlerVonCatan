package com.example.catanserver.businessLogic;

import com.example.catanserver.businessLogic.model.Colors;
import com.example.catanserver.businessLogic.model.Game;
import com.example.catanserver.businessLogic.model.GameImpl;
import com.example.catanserver.businessLogic.model.PlayerImpl;
import com.example.catanserver.businessLogic.services.GameServiceImpl;
import com.example.catanserver.businessLogic.services.PlayerServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PlayerOps {

    private PlayerServiceImpl psi = new PlayerServiceImpl();
    private GameServiceImpl gsi = new GameServiceImpl();

    synchronized List<String> startGame(List<String> usersIn) {

        GameImpl game = new GameImpl();
        PlayerImpl player;

        for (String str : usersIn) {
            player = new PlayerImpl(str);
            psi.insert(player);
            game.setPlayer(player);
        }

        gsi.insert(game);

        return gsi.getList(game.getGameId());
    }

    List<String> setColor(Map<String, String> map) {

        PlayerImpl player;
        Integer gameId = null;

        for (Map.Entry<String, String> entry : map.entrySet()) {

            player = psi.findPlayerByUsername(entry.getValue());
            Colors color = Colors.valueOf(entry.getKey().toUpperCase());
            player.setColor(color);

            gameId = player.getGameId();
        }

        return gsi.getList(gameId);
    }

    List<String> checkIfIn(String username) {

        GameImpl game = gsi.findGameByPlayername(username);

        if (game != null) {
            return gsi.getList(game.getGameId());
        }
        return null;
    }

    Map<String, String> checkColors(Integer id) {

        Map<String, String> map = new HashMap<>();

        Game game = gsi.findGameByGameId(id);
        List<PlayerImpl> players = game.getPlayers();

        for (PlayerImpl player : players) {
            map.put(player.getColor().toString().toLowerCase(), player.getDisplayName());
        }

        return map;
    }
}
