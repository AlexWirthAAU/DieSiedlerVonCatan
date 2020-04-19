package com.example.catanserver.businessLogic;

import com.example.catanserver.businessLogic.model.UserImpl;
import com.example.catanserver.businessLogic.services.UserServiceImpl;

import java.util.List;

class UserOps {

    private UserServiceImpl usi = new UserServiceImpl();

    synchronized List<String> updateUserList(String username) {

        UserImpl user = usi.findUserByUsername(username);

        if (user == null) {
            UserImpl newUser = new UserImpl(username);
            usi.insert(newUser);
        } else {
            int id = user.getUserId();
            usi.delete(id);
        }

        for (int i = 0; i < usi.fetchAll().size(); i++) {
            System.out.println(usi.fetchAll().get(i).getDisplayName());
        }

        for (int i = 0; i < usi.getNameList().size(); i++) {
            System.out.println(usi.getNameList().get(i));
        }
        return usi.getNameList();
    }

    List<String> updateUserList(int listsize) {

        if (listsize == usi.fetchAll().size()) {
            return null;
        } else {
            return usi.getNameList();
        }
    }
}
