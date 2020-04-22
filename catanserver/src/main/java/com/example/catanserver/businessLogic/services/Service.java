package com.example.catanserver.businessLogic.services;

import java.util.ArrayList;

/**
 * @param <I> Platzhalter für den Objekttyp des Suchparameters
 * @param <T> Platzhalter für den Objekttyp des Elements
 *            <p>
 *            Der Service kennt Methoden, um ein Element zu finden,
 *            einzufügen, upzudaten, zu löschen oder alle Elemente zu
 *            finden.
 * @author Christina Senger
 */
public interface Service<I, T> {

    T findObject(I id);

    ArrayList<T> fetchAll();

    T insert(T element);

    void delete(I id);

    T update(T element);

}
