package com.pairgood.todo.controller;

import com.pairgood.todo.repository.ToDo;
import com.pairgood.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
public class ToDoController {

    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @GetMapping("/todos")
    List<ToDo> all() {
        return toDoRepository.findAll();
    }

    @PostMapping("/todos")
    ToDo newEmployee(@RequestBody ToDo newToDo) {
        return toDoRepository.save(newToDo);
    }

    @DeleteMapping(value = "/todos/{id}")
    void delete(@PathVariable Long id) {
        toDoRepository.deleteById(id);
    }
}
