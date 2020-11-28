package com.pairgood.todo.priority;

import com.pairgood.todo.repository.ToDo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToDoPrioritizer implements Prioritizer<ToDo> {
    public List<ToDo> prioritize(List<ToDo> toDos, int targetPriority, int amount) {
        return toDos;
    }
}
