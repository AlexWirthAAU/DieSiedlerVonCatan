package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.Colors;
import com.example.catanserver.businessLogic.model.PlayerImpl;

import java.util.List;


public interface PlayerService extends Service<Integer, PlayerImpl> {

    PlayerImpl findPlayerByUsername(String name);

    PlayerImpl findPlayerByUserid(int id);

    List<PlayerImpl> findPlayersByGameid(int id);

    PlayerImpl findPlayerByColor(Colors color);

    List<String> getNameList();
}
