package com.pairgood.todo.priority;

import java.util.List;

public interface Prioritizer<P extends Prioritizable, T> {
    List<P> prioritizeUp(List<P> toDos, T targetPriority, T amount);

    List<P> prioritizeDown(List<P> toDos, T targetPriority, T amount);
}
