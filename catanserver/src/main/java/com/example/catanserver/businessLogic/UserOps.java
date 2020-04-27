package com.example.catanserver.businessLogic;

import com.example.catanserver.businessLogic.model.player.UserImpl;
import com.example.catanserver.businessLogic.services.UserServiceImpl;

import java.util.ArrayList;

class UserOps {

    private static UserServiceImpl usi;

    UserOps(UserServiceImpl u) {
        usi = u;
    }

    synchronized ArrayList<String> updateUserList(String username, String ip) {

        UserImpl user = usi.findUserByUsername(username);

        if (user == null) {
            UserImpl newUser = new UserImpl(username, ip);
            usi.insert(newUser);
        } else {
            int id = user.getUserId();
            usi.delete(id);
        }

        return usi.getNameList();
    }

    ArrayList<String> updateUserList(int listsize) {

        if (listsize == usi.fetchAll().size()) {
            System.out.println(usi.fetchAll().size() + " realsize");
            return null;
        } else {
            return usi.getNameList();
        }
    }
}
