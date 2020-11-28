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

    private final ToDoRepository repository;

    @Autowired
    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todos")
    List<ToDo> listAll() {
        return repository.findAllByOrderByPriorityAsc();
    }

    @GetMapping("/todos/active")
    List<ToDo> listActive() {
        return repository.findByDoneOrderByPriorityAsc(false);
    }

    @PostMapping("/todos")
    ToDo add(@RequestBody ToDo newToDo) {
        long toDoCount = repository.count();
        newToDo.setPriority(toDoCount + 1);
        return repository.save(newToDo);
    }

    @DeleteMapping(value = "/todos/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping(value = "/todos/{id}/done")
    void markAsDone(@PathVariable Long id) {
        Optional<ToDo> results = repository.findById(id);

        if (results.isPresent()) {
            ToDo toDo = results.get();
            toDo.markAsDone();
            repository.save(toDo);
        }
    }
}
