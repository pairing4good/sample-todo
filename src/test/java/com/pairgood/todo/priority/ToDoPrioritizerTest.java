package com.pairgood.todo.priority;

import com.pairgood.todo.repository.ToDo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ToDoPrioritizerTest {

    private ToDoPrioritizer prioritizer;

    @BeforeEach
    void setUp() {
        prioritizer = new ToDoPrioritizer();
    }

    @Test
    void prioritize_ShouldPrioritizeTheSecondItemToTheFirstItem() {
        List<ToDo> toDos = Arrays.asList(
                new ToDo("first", 1),
                new ToDo("second", 2)
        );

        List<ToDo> reprioritizedToDos = prioritizer.prioritize(toDos, 2L, 1L);

        assertThat(reprioritizedToDos.size()).isEqualTo(2);

        ToDo firstToDo = reprioritizedToDos.get(0);
        assertThat(firstToDo.getDescription()).isEqualTo("second");
        assertThat(firstToDo.getPriority()).isEqualTo(1);

        ToDo secondToDo = reprioritizedToDos.get(1);
        assertThat(secondToDo.getDescription()).isEqualTo("first");
        assertThat(secondToDo.getPriority()).isEqualTo(2);
    }

    @Test
    void prioritize_ShouldPrioritizeTheFirstItemToTheSecondItem() {
        List<ToDo> toDos = Arrays.asList(
                new ToDo("first", 1),
                new ToDo("second", 2)
        );

        List<ToDo> reprioritizedToDos = prioritizer.prioritize(toDos, 1L, -1L);

        assertThat(reprioritizedToDos.size()).isEqualTo(2);

        ToDo firstToDo = reprioritizedToDos.get(0);
        assertThat(firstToDo.getDescription()).isEqualTo("second");
        assertThat(firstToDo.getPriority()).isEqualTo(1);

        ToDo secondToDo = reprioritizedToDos.get(1);
        assertThat(secondToDo.getDescription()).isEqualTo("first");
        assertThat(secondToDo.getPriority()).isEqualTo(2);
    }
}