package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {
    private static  final Model instance = new Model();

    private final Map<Integer, User> model;

    public static Model getInstance() {
        return instance;
    }

    private Model() {
        model = new HashMap<>();

        model.put(1, new User("Name1","Surname1",99999));
        model.put(2, new User("Name2","Surname2",88888));
        model.put(4, new User("Name3","Surname3",77777));
    }

    public void add(User user,int id){
        model.put(id,user);
    }

    public Map<Integer,User> getFromList() {
        return model;
    }

}
