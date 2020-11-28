package com.pairgood.todo.priority;

import java.util.List;

public interface Prioritizer<P extends Prioritizable> {
    List<P> prioritize(List<P> toDos, int targetPriority, int amount);
}
