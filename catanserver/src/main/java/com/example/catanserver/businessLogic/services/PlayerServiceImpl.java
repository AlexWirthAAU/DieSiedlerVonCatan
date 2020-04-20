package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.Colors;
import com.example.catanserver.businessLogic.model.PlayerImpl;

import java.util.ArrayList;
import java.util.List;

public class PlayerServiceImpl extends ServiceImpl<Integer, PlayerImpl> implements PlayerService {

    public PlayerServiceImpl() {
        this.currentId = 1;
    }

    public List<PlayerImpl> fetchAll() {
        return this.list;
    }


    public PlayerImpl findElement(Integer id) {
        for (PlayerImpl player : this.list) {
            if (player.getPlayerId() == id) {
                return player;
            }
        }
        return null;
    }

    public PlayerImpl insert(PlayerImpl player) {
        player.setPlayerId(currentId++);
        this.list.add(player);
        return player;
    }

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

    public PlayerImpl findPlayerByUsername(String name) {
        for (PlayerImpl player : this.list) {
            if (name.equals(player.getDisplayName())) {
                return player;
            }
        }
        return null;
    }


    public PlayerImpl findPlayerByUserid(int id) {
        for (PlayerImpl player : this.list) {
            if (id == player.getUserId()) {
                return player;
            }
        }
        return null;
    }

    public List<PlayerImpl> findPlayersByGameid(int id) {

        List<PlayerImpl> l = new ArrayList<>();

        for (PlayerImpl player : this.list) {
            if (id == player.getGameId()) {
                l.add(player);
            }
        }
        return l;
    }

    public PlayerImpl findPlayerByColor(Colors color) {
        for (PlayerImpl player : this.list) {
            if (color == player.getColor()) {
                return player;
            }
        }
        return null;
    }

    public List<String> getNameList() {
        List<String> names = new ArrayList<>();

        for (PlayerImpl player : this.list) {
            names.add(player.getDisplayName());
        }
        return names;
    }
}
