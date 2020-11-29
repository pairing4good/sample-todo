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
            } else if (isPositiveNumber(amount) && inShiftDownZone(targetPriority, offset, priority)) {
                toDo.setPriority(priority + 1);
            } else if (isNegativeNumber(amount) && inShiftUpZone(targetPriority, offset, priority)) {
                toDo.setPriority(priority - 1);
            }

            reprioritizedToDos.add(toDo);
        }

        reprioritizedToDos.sort((ToDo first, ToDo second) -> (int) (first.getPriority() - second.getPriority()));

        return reprioritizedToDos;
    }

    private boolean inShiftUpZone(Long targetPriority, long offset, long priority) {
        return priority > targetPriority && priority < (offset + 1);
    }

    private boolean inShiftDownZone(Long targetPriority, long offset, long priority) {
        return priority > (offset - 1) && priority < targetPriority;
    }

    private boolean isNegativeNumber(Long amount) {
        return amount < 0;
    }

    private boolean isPositiveNumber(long amount) {
        return amount > 0;
    }
}
