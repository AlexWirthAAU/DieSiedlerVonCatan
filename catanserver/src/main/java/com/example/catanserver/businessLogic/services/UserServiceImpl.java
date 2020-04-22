package com.example.catanserver.businessLogic.services;

import com.example.catanserver.businessLogic.model.UserImpl;

import java.util.ArrayList;

/**
 * @author Christina Senger
 * <p>
 * Der UserService verfügt zusätzlich übe Methoden, um einen User
 * über seinen Namen oder seinen Host zu finden. Außerdem
 * hat er eine Methode, um die Namen aller Nutzer als Liste von
 * Stringelementen zurückzugeben.
 */
public class UserServiceImpl extends ServiceImpl<Integer, UserImpl> implements UserService {

    /**
     * Konstruktor - setzt die aktuelle Id auf 1
     */
    public UserServiceImpl() {
        this.currentId = 1;
    }

    /**
     * Durchsucht die Liste mit allen Usern. Ist eine
     * userId gleich der gegebenen Id, wird der user zurückgegeben.
     *
     * @param id Userid
     * @return den User mit der angegebenen Id, sonst null
     */
    public UserImpl findObject(Integer id) {
        for (UserImpl user : this.list) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * @return Liste aller User
     */
    public ArrayList<UserImpl> fetchAll() {
        return this.list;
    }

    /**
     * Der User bekommt die nächste frei Id
     * und wird in die Liste aller User eingefügt.
     *
     * @param user einzufügender User
     * @return eingefügten User
     */
    public UserImpl insert(UserImpl user) {
        user.setUserId(currentId++);
        this.list.add(user);
        return user;
    }

    /**
     * Gibt es unter allen User einen User mit der
     * gesuchten Id, wird dieser aus der Liste entfernt.
     *
     * @param id UserId des zu löschenden Users
     */
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

    /**
     * Gibt es unter allen User einen User mit der
     * gegebenen Id, wird dieser aus der Liste entfernt
     * und der neue User an der Stelle eingesetzt.
     *
     * @param user upzudatender User
     * @return den neu eingefügen User
     */
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

    /**
     * Durchsucht die Liste mit allen Usern. Ist ein
     * displayName gleich dem gegebenen Namen, wird der user zurückgegeben.
     *
     * @param name Username des gesuchten Users
     * @return den gesuchten User, sonst null
     */
    public UserImpl findUserByUsername(String name) {
        for (UserImpl user : this.list) {
            if (name.equals(user.getDisplayName())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Durchsucht die Liste mit allen Usern. Ist ein
     * Host gleich dem gegebenen Host, wird der user zurückgegeben.
     *
     * @param host Host des gesuchten Users
     * @return den gesuchten User, sonst null
     */
    public UserImpl findUserByHost(String host) {
        for (UserImpl user : this.list) {
            if (host.equals(user.getHost())) {
                return user;
            }
        }
        return null;
    }

    /**
     * @return eine Liste aller DisplayNames
     */
    public ArrayList<String> getNameList() {

        ArrayList<String> names = new ArrayList<>();

        for (UserImpl user : this.list) {
            names.add(user.getDisplayName());
        }
        return names;
    }
}
