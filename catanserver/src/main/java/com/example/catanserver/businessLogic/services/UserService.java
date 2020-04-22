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
public interface UserService extends Service<Integer, UserImpl> {

    UserImpl findUserByUsername(String name);

    UserImpl findUserByHost(String name);

    ArrayList<String> getNameList();

}
