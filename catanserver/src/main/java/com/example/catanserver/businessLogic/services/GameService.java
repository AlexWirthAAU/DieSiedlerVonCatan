package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.GameImpl;

import java.util.List;

public interface GameService extends Service<Integer, GameImpl> {

    GameImpl findGameByPlayers(List<String> players);

    GameImpl findGameByPlayername(String name);

    GameImpl findGameByGameId(Integer id);

    List<String> getList(Integer id);
}
