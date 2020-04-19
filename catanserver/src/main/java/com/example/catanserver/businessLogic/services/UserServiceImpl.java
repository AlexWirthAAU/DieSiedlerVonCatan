package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.UserImpl;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends ServiceImpl<Integer, UserImpl> implements UserService {

    public UserServiceImpl() {
        this.currentId = 1;
    }

    public List<UserImpl> fetchAll() {
        return this.list;
    }

    public UserImpl findElement(Integer id) {
        for (UserImpl user : this.list) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        return null;
    }

    public UserImpl insert(UserImpl user) {
        user.setUserId(currentId++);
        this.list.add(user);
        return user;
    }

    public void delete(Integer id) {
        UserImpl u = null;
        for (UserImpl user : this.list) {
            if (user.getUserId() == id) {
                u = user;
                break;
            }
        }
        if (u != null) {
            this.list.remove(u);
        }
    }

    public UserImpl update(UserImpl user) {
        UserImpl u = null;
        for (UserImpl use : this.list) {
            if (use.getUserId() == user.getUserId()) {
                u = use;
                break;
            }
        }
        if (u != null) {
            this.list.remove(u);
        }
        this.list.add(user);
        return u;
    }

    public UserImpl findUserByUsername(String name) {
        for (UserImpl user : this.list) {
            if (name.equals(user.getDisplayName())) {
                return user;
            }
        }
        return null;
    }

    public UserImpl findUserByUserid(int id) {
        for (UserImpl user : this.list) {
            if (id == user.getUserId()) {
                return user;
            }
        }
        return null;
    }

    public List<String> getNameList() {
        List<String> names = new ArrayList<>();

        for (UserImpl user : this.list) {
            names.add(user.getDisplayName());
        }
        return names;
    }
}
