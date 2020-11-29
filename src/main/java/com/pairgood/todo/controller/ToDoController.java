package com.pairgood.todo.controller;

import com.pairgood.todo.priority.Prioritizer;
import com.pairgood.todo.repository.ToDo;
import com.pairgood.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
@RestController
public class ToDoController {

    private final ToDoRepository repository;
    private Prioritizer prioritizer;

    @Autowired
    public ToDoController(ToDoRepository repository, Prioritizer prioritizer) {
        this.repository = repository;
        this.prioritizer = prioritizer;
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
    ToDo add(@Valid @RequestBody ToDo newToDo) {
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

    @PutMapping(value = "/todos/prioritize/{priority}/up/{amount}")
    public List<ToDo> prioritizeUp(@PathVariable long priority, @PathVariable long amount) {
        List<ToDo> toDos = repository.findAllByOrderByPriorityAsc();
        List reprioritizedToDos = prioritizer.prioritize(toDos, priority, amount);
        repository.saveAll(reprioritizedToDos);
        return reprioritizedToDos;
    }

    @PutMapping(value = "/todos/prioritize/{priority}/down/{amount}")
    public List<ToDo> prioritizeDown(@PathVariable long priority, @PathVariable long amount) {
        List<ToDo> toDos = repository.findAllByOrderByPriorityAsc();
        List reprioritizedToDos = prioritizer.prioritize(toDos, priority, (amount * -1));
        repository.saveAll(reprioritizedToDos);
        return reprioritizedToDos;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
