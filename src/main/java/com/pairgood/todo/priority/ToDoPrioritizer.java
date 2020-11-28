package com.pairgood.todo.priority;

import com.pairgood.todo.repository.ToDo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ToDoPrioritizer implements Prioritizer<ToDo, Long> {
    public List<ToDo> prioritize(List<ToDo> toDos, Long targetPriority, Long amount) {
        List<ToDo> reprioritizedToDos = new ArrayList<>();

        long offset = targetPriority - amount;

        if (offset < 1 || toDos.size() < offset) {
            return toDos;
        }

        for (ToDo toDo : toDos) {
            long priority = toDo.getPriority();
            if (priority == targetPriority) {
                toDo.setPriority(priority - amount);
            } else if (amount > 0 && priority > (offset - 1) && priority < targetPriority) {
                toDo.setPriority(priority + 1);
            } else if (amount < 0 && priority > (targetPriority - 1) && priority < (offset + 1)) {
                toDo.setPriority(priority - 1);
            }

            reprioritizedToDos.add(toDo);
        }

        reprioritizedToDos.sort((ToDo first, ToDo second) -> (int) (first.getPriority() - second.getPriority()));

        return reprioritizedToDos;
    }
}
