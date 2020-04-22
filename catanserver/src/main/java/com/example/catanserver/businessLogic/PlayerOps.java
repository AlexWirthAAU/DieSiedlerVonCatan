package com.example.catanserver.businessLogic;

import com.example.catanserver.businessLogic.model.Colors;
import com.example.catanserver.businessLogic.model.Game;
import com.example.catanserver.businessLogic.model.GameImpl;
import com.example.catanserver.businessLogic.model.PlayerImpl;
import com.example.catanserver.businessLogic.model.User;
import com.example.catanserver.businessLogic.services.GameServiceImpl;
import com.example.catanserver.businessLogic.services.PlayerServiceImpl;
import com.example.catanserver.businessLogic.services.UserServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PlayerOps {

    private static UserServiceImpl usi;
    private static PlayerServiceImpl psi;
    private static GameServiceImpl gsi;

    public PlayerOps(UserServiceImpl u, PlayerServiceImpl p, GameServiceImpl g) {
        usi = u;
        psi = p;
        gsi = g;

    }

    synchronized ArrayList<String> startGame(List<String> usersIn) {

        GameImpl game = new GameImpl();
        PlayerImpl player;
        int id;

        for (String str : usersIn) {

            User user = usi.findUserByUsername(str);
            player = new PlayerImpl(str, user.getHost(), user.getUserId());
            psi.insert(player);
            usi.delete(user.getUserId());
            game.setPlayer(player);
        }

        id = game.getGameId();
        System.out.println(id + " gameId in service");
        gsi.insert(game);

        return gsi.getList(game.getGameId());
    }

    synchronized ArrayList<String> setColor(Map<String, String> map) {

        PlayerImpl player;
        Integer gameId = null;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getValue() + " userInMap");
            Colors color = Colors.valueOf(entry.getKey().toUpperCase());
            System.out.println(color + " colorServer");
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {

            player = psi.findPlayerByUsername(entry.getValue());
            Colors color = Colors.valueOf(entry.getKey().toUpperCase());
            player.setColor(color);

            gameId = player.getGameId();
        }

        return gsi.getList(gameId);
    }

    ArrayList<String> checkIfIn(String username) {

        List<GameImpl> list = gsi.fetchAll();

        for (GameImpl game : list) {
            List<PlayerImpl> p = game.getPlayers();
            for (PlayerImpl player : p) {
                if (player.getDisplayName().equals(username)) {
                    return gsi.getList(game.getGameId());
                }
            }
        }
        return null;
    }

    Map<String, String> checkColors(Integer id) {

        Map<String, String> map = new HashMap<>();

        Game game = gsi.findObject(id);
        List<PlayerImpl> players = game.getPlayers();

        for (PlayerImpl player : players) {
            System.out.println(player.getPlayerId() + " playerinCheckColors");
            if (player.getColor() != null) {
                map.put(player.getColor().toString().toLowerCase(), player.getDisplayName());
            }
        }

        return map;
    }
}
