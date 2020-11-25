package com.pairgood.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ToDoController {

    @GetMapping("/todos")
    List<String> all() {
        return new ArrayList<>();
    }
}
