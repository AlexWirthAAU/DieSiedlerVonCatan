package com.example.catanserver.businessLogic.services;

import java.util.List;

public interface Service<S, T> {

    T findElement(S id);

    List<T> fetchAll();

    T insert(T element);

    void delete(S id);

    T update(T element);

}
