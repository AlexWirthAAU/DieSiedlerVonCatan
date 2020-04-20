package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.UserImpl;

import java.util.List;

public interface UserService extends Service<Integer, UserImpl> {

    UserImpl findUserByUsername(String name);

    UserImpl findUserByUserid(int id);

    List<String> getNameList();

}
