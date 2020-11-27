package com.pairgood.todo.controller;

import com.pairgood.todo.repository.ToDo;
import com.pairgood.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@RestController
public class ToDoController {

    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @GetMapping("/todos")
    List<ToDo> listAll() {
        return toDoRepository.findAll();
    }

    @GetMapping("/todos/active")
    List<ToDo> listActive() {
        return toDoRepository.findByDone(false);
    }

    @PostMapping("/todos")
    ToDo newEmployee(@RequestBody ToDo newToDo) {
        return toDoRepository.save(newToDo);
    }

    @DeleteMapping(value = "/todos/{id}")
    void delete(@PathVariable Long id) {
        toDoRepository.deleteById(id);
    }

    @PutMapping(value = "/todos/{id}/done")
    void markAsDone(@PathVariable Long id) {
        Optional<ToDo> results = toDoRepository.findById(id);

        if (results.isPresent()) {
            ToDo toDo = results.get();
            toDo.markAsDone();
            toDoRepository.save(toDo);
        }
    }
}
