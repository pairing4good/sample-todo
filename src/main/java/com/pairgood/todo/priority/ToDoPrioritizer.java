package com.pairgood.todo.priority;

import com.pairgood.todo.repository.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("default")
public class ToDoPrioritizer implements Prioritizer<ToDo, Long> {

    Logger logger = LoggerFactory.getLogger(ToDoPrioritizer.class);

    public ToDoPrioritizer() {
        logger.info("constructing...");
    }

    public List<ToDo> prioritizeUp(List<ToDo> toDos, Long targetPriority, Long amount) {
        List<ToDo> reprioritizedToDos = new ArrayList<>();

        long offset = targetPriority - amount;

        if (offset < 1) {
            return toDos;
        }

        for (ToDo toDo : toDos) {
            long priority = toDo.getPriority();
            if (priority == targetPriority) {
                toDo.setPriority(priority - amount);
            } else if (inShiftDownZone(targetPriority, offset, priority)) {
                toDo.setPriority(priority + 1);
            }

            reprioritizedToDos.add(toDo);
        }

        reprioritizedToDos.sort((ToDo first, ToDo second) -> (int) (first.getPriority() - second.getPriority()));

        return reprioritizedToDos;
    }

    @Override
    public List<ToDo> prioritizeDown(List<ToDo> toDos, Long targetPriority, Long amount) {
        List<ToDo> reprioritizedToDos = new ArrayList<>();

        long offset = targetPriority + amount;

        if (toDos.size() < offset) {
            return toDos;
        }

        for (ToDo toDo : toDos) {
            long priority = toDo.getPriority();
            if (priority == targetPriority) {
                toDo.setPriority(priority + amount);
            } else if (inShiftUpZone(targetPriority, offset, priority)) {
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
}
