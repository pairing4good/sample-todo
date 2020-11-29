package com.pairgood.todo.priority;

import com.pairgood.todo.repository.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.swap;

@Component
@Profile("swap")
public class ToDoSwapPrioritizer implements Prioritizer<ToDo, Long> {

    Logger logger = LoggerFactory.getLogger(ToDoSwapPrioritizer.class);

    public ToDoSwapPrioritizer() {
        logger.info("constructing...");
    }

    public List<ToDo> prioritizeUp(List<ToDo> toDos, Long targetPriority, Long amount) {
        long index = 0;

        for (int i = 0; i < toDos.size(); i++) {
            if (toDos.get(i).getPriority() == targetPriority) {
                index = i;
            }
        }

        for (int i = 0; i < Math.abs(amount); i++) {
            if (amount > 0 && destinationUpLocation(index, i) > -1) {
                swap(toDos, targetUpLocation(index, i), destinationUpLocation(index, i));
            } else if (amount < 0 && destinationDownLocation(index, i) < toDos.size()) {
                swap(toDos, targetDownLocation(index, i), destinationDownLocation(index, i));
            }
        }

        for (int i = 0; i < toDos.size(); i++) {
            toDos.get(i).setPriority(i + 1);
        }

        return toDos;
    }

    @Override
    public List<ToDo> prioritizeDown(List<ToDo> toDos, Long targetPriority, Long amount) {
        return null;
    }

    private int destinationDownLocation(long index, int offset) {
        return targetDownLocation(index, offset) + 1;
    }

    private int targetDownLocation(long index, int offset) {
        return (int) index + offset;
    }

    private int destinationUpLocation(long index, int offset) {
        return targetUpLocation(index, offset) - 1;
    }

    private int targetUpLocation(long index, int offset) {
        return (int) index - offset;
    }
}
