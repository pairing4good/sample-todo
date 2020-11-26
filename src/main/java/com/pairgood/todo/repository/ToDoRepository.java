package com.pairgood.todo.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ToDoRepository {

    private List<String> store;

    public ToDoRepository() {
        store = new ArrayList<>();
    }

    public void save(String item) {
        store.add(item);
    }

    public List<String> findAll() {
        return store;
    }

    public void delete(int id) {
        store.remove(id);
    }
}
