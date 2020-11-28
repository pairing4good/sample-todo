package com.pairgood.todo.priority;

import com.pairgood.todo.repository.ToDo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ToDoPrioritizer implements Prioritizer<ToDo, Long> {
    public List<ToDo> prioritize(List<ToDo> toDos, Long targetPriority, Long amount) {
        List<ToDo> reprioritizedToDos = new ArrayList<>();

        if (amount > 0) {
            for (ToDo toDo : toDos) {
                long priority = toDo.getPriority();
                if (priority == targetPriority) {
                    toDo.setPriority(priority - amount);
                } else if (priority > (targetPriority - amount - 1) && priority < targetPriority) {
                    toDo.setPriority(priority + 1);
                }

                reprioritizedToDos.add(toDo);
            }
        } else {
            for (ToDo toDo : toDos) {
                long priority = toDo.getPriority();
                if (priority == targetPriority) {
                    toDo.setPriority(priority - amount);
                } else {
                    toDo.setPriority(priority - 1);
                }

                reprioritizedToDos.add(toDo);
            }
        }

        reprioritizedToDos.sort((ToDo first, ToDo second) -> (int) (first.getPriority() - second.getPriority()));

        return reprioritizedToDos;
    }
}
