package com.example.catanserver.businessLogic.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceImpl<S, T> {

    protected List<T> list = new ArrayList<>();
    protected Map<S, T> map = new HashMap<>();
    S currentId;

}
