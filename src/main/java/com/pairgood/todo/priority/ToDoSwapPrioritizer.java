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

        index = locateIndexOfTargetPriority(toDos, targetPriority, index);

        for (int i = 0; i < Math.abs(amount); i++) {
            if (destinationUpLocation(index, i) > -1) {
                swap(toDos, targetUpLocation(index, i), destinationUpLocation(index, i));
            }
        }

        return adjustPriority(toDos);
    }

    @Override
    public List<ToDo> prioritizeDown(List<ToDo> toDos, Long targetPriority, Long amount) {
        long index = 0;

        index = locateIndexOfTargetPriority(toDos, targetPriority, index);

        for (int i = 0; i < Math.abs(amount); i++) {
            if (destinationDownLocation(index, i) < toDos.size()) {
                swap(toDos, targetDownLocation(index, i), destinationDownLocation(index, i));
            }
        }

        return adjustPriority(toDos);
    }

    private List<ToDo> adjustPriority(List<ToDo> toDos) {
        for (int i = 0; i < toDos.size(); i++) {
            toDos.get(i).setPriority(i + 1);
        }
        return toDos;
    }

    private long locateIndexOfTargetPriority(List<ToDo> toDos, Long targetPriority, long index) {
        for (int i = 0; i < toDos.size(); i++) {
            if (toDos.get(i).getPriority() == targetPriority) {
                index = i;
            }
        }
        return index;
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
