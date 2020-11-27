package com.pairgood.todo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

    @Override
    List<ToDo> findAll();

    List<ToDo> findByDone(boolean done);
}
