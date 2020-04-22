package com.example.catanserver.businessLogic.services;

import java.util.ArrayList;

/**
 * @param <I> Platzhalter für den Objekttyp des Suchparameters
 * @param <T> Platzhalter für den Objekttyp des Elements
 *            <p>
 *            Der Service verfügt über eine Liste und eine Id,
 * @author Christina Senger
 */
class ServiceImpl<I, T> {

    ArrayList<T> list = new ArrayList<>();
    I currentId;

}
