package com.pairgood.todo.controller;

import com.pairgood.todo.priority.ToDoPrioritizer;
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
    private ToDoPrioritizer toDoPrioritizer;

    @Autowired
    public ToDoController(ToDoRepository repository, ToDoPrioritizer toDoPrioritizer) {
        this.repository = repository;
        this.toDoPrioritizer = toDoPrioritizer;
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

    public List<ToDo> prioritizeUp(int targetPriority, int increaseAmount) {
        List<ToDo> toDos = repository.findAllByOrderByPriorityAsc();
        return toDoPrioritizer.prioritize(toDos, targetPriority, increaseAmount);
    }

    public List<ToDo> prioritizeDown(int targetPriority, int decreaseAmount) {
        List<ToDo> toDos = repository.findAllByOrderByPriorityAsc();
        return toDoPrioritizer.prioritize(toDos, targetPriority, (decreaseAmount * -1));
    }
}
