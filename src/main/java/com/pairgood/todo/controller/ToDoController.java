package com.pairgood.todo.controller;

import com.pairgood.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping("/todos")
    List<String> all() {
        return toDoRepository.findAll();
    }
}
