package com.pairgood.todo.priority;

import java.util.List;

public interface Prioritizer<P extends Prioritizable, T> {
    List<P> prioritize(List<P> toDos, T targetPriority, T amount);
}
