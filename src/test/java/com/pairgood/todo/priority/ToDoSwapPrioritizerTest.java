package com.pairgood.todo.priority;

import com.pairgood.todo.repository.ToDo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ToDoSwapPrioritizerTest {

    private ToDoSwapPrioritizer prioritizer;

    @BeforeEach
    void setUp() {
        prioritizer = new ToDoSwapPrioritizer();
    }

    @Test
    void prioritize_ShouldPrioritizeTheSecondItemToTheFirstItem() {
        List<ToDo> toDos = Arrays.asList(
                new ToDo("first", 1),
                new ToDo("second", 2)
        );

        List<ToDo> reprioritizedToDos = prioritizer.prioritizeUp(toDos, 2L, 1L);

        assertThat(reprioritizedToDos.size()).isEqualTo(2);

        ToDo firstToDo = reprioritizedToDos.get(0);
        assertThat(firstToDo.getDescription()).isEqualTo("second");
        assertThat(firstToDo.getPriority()).isEqualTo(1);

        ToDo secondToDo = reprioritizedToDos.get(1);
        assertThat(secondToDo.getDescription()).isEqualTo("first");
        assertThat(secondToDo.getPriority()).isEqualTo(2);
    }

    @Test
    void prioritize_ShouldNotPrioritizeToDosHigherThanOne() {
        List<ToDo> toDos = Arrays.asList(
                new ToDo("first", 1),
                new ToDo("second", 2)
        );

        List<ToDo> reprioritizedToDos = prioritizer.prioritizeUp(toDos, 1L, 1L);

        assertThat(reprioritizedToDos.size()).isEqualTo(2);

        ToDo firstToDo = reprioritizedToDos.get(0);
        assertThat(firstToDo.getDescription()).isEqualTo("first");
        assertThat(firstToDo.getPriority()).isEqualTo(1);

        ToDo secondToDo = reprioritizedToDos.get(1);
        assertThat(secondToDo.getDescription()).isEqualTo("second");
        assertThat(secondToDo.getPriority()).isEqualTo(2);
    }

    @Test
    void prioritize_ShouldNotPrioritizeToDosLowerThanOne() {
        List<ToDo> toDos = Arrays.asList(
                new ToDo("first", 1),
                new ToDo("second", 2)
        );

        List<ToDo> reprioritizedToDos = prioritizer.prioritizeUp(toDos, 2L, -1L);

        assertThat(reprioritizedToDos.size()).isEqualTo(2);

        ToDo firstToDo = reprioritizedToDos.get(0);
        assertThat(firstToDo.getDescription()).isEqualTo("first");
        assertThat(firstToDo.getPriority()).isEqualTo(1);

        ToDo secondToDo = reprioritizedToDos.get(1);
        assertThat(secondToDo.getDescription()).isEqualTo("second");
        assertThat(secondToDo.getPriority()).isEqualTo(2);
    }

    @Test
    void prioritize_ShouldPrioritizeTheThirdItemToTheFirstItem() {
        List<ToDo> toDos = Arrays.asList(
                new ToDo("first", 1),
                new ToDo("second", 2),
                new ToDo("third", 3)
        );

        List<ToDo> reprioritizedToDos = prioritizer.prioritizeUp(toDos, 3L, 2L);

        assertThat(reprioritizedToDos.size()).isEqualTo(3);

        ToDo firstToDo = reprioritizedToDos.get(0);
        assertThat(firstToDo.getDescription()).isEqualTo("third");
        assertThat(firstToDo.getPriority()).isEqualTo(1);

        ToDo secondToDo = reprioritizedToDos.get(1);
        assertThat(secondToDo.getDescription()).isEqualTo("first");
        assertThat(secondToDo.getPriority()).isEqualTo(2);

        ToDo thirdToDo = reprioritizedToDos.get(2);
        assertThat(thirdToDo.getDescription()).isEqualTo("second");
        assertThat(thirdToDo.getPriority()).isEqualTo(3);
    }

    @Test
    void prioritize_ShouldPerformPartialPrioritizationUpWithinLargerList() {
        List<ToDo> toDos = Arrays.asList(
                new ToDo("first", 1),
                new ToDo("second", 2),
                new ToDo("third", 3),
                new ToDo("fourth", 4),
                new ToDo("fifth", 5)
        );

        List<ToDo> reprioritizedToDos = prioritizer.prioritizeUp(toDos, 4L, 2L);

        assertThat(reprioritizedToDos.size()).isEqualTo(5);

        ToDo firstToDo = reprioritizedToDos.get(0);
        assertThat(firstToDo.getDescription()).isEqualTo("first");
        assertThat(firstToDo.getPriority()).isEqualTo(1);

        ToDo secondToDo = reprioritizedToDos.get(1);
        assertThat(secondToDo.getDescription()).isEqualTo("fourth");
        assertThat(secondToDo.getPriority()).isEqualTo(2);

        ToDo thirdToDo = reprioritizedToDos.get(2);
        assertThat(thirdToDo.getDescription()).isEqualTo("second");
        assertThat(thirdToDo.getPriority()).isEqualTo(3);

        ToDo fourthToDo = reprioritizedToDos.get(3);
        assertThat(fourthToDo.getDescription()).isEqualTo("third");
        assertThat(fourthToDo.getPriority()).isEqualTo(4);

        ToDo fifthToDo = reprioritizedToDos.get(4);
        assertThat(fifthToDo.getDescription()).isEqualTo("fifth");
        assertThat(fifthToDo.getPriority()).isEqualTo(5);
    }

    @Test
    void prioritize_ShouldPerformPartialPrioritizationDownWithinLargerList() {
        List<ToDo> toDos = Arrays.asList(
                new ToDo("first", 1),
                new ToDo("second", 2),
                new ToDo("third", 3),
                new ToDo("fourth", 4),
                new ToDo("fifth", 5)
        );

        List<ToDo> reprioritizedToDos = prioritizer.prioritizeUp(toDos, 2L, -2L);

        assertThat(reprioritizedToDos.size()).isEqualTo(5);

        ToDo firstToDo = reprioritizedToDos.get(0);
        assertThat(firstToDo.getDescription()).isEqualTo("first");
        assertThat(firstToDo.getPriority()).isEqualTo(1);

        ToDo secondToDo = reprioritizedToDos.get(1);
        assertThat(secondToDo.getDescription()).isEqualTo("third");
        assertThat(secondToDo.getPriority()).isEqualTo(2);

        ToDo thirdToDo = reprioritizedToDos.get(2);
        assertThat(thirdToDo.getDescription()).isEqualTo("fourth");
        assertThat(thirdToDo.getPriority()).isEqualTo(3);

        ToDo fourthToDo = reprioritizedToDos.get(3);
        assertThat(fourthToDo.getDescription()).isEqualTo("second");
        assertThat(fourthToDo.getPriority()).isEqualTo(4);

        ToDo fifthToDo = reprioritizedToDos.get(4);
        assertThat(fifthToDo.getDescription()).isEqualTo("fifth");
        assertThat(fifthToDo.getPriority()).isEqualTo(5);
    }

    @Test
    void prioritize_ShouldPrioritizeTheFirstItemToTheSecondItem() {
        List<ToDo> toDos = Arrays.asList(
                new ToDo("first", 1),
                new ToDo("second", 2)
        );

        List<ToDo> reprioritizedToDos = prioritizer.prioritizeUp(toDos, 1L, -1L);

        assertThat(reprioritizedToDos.size()).isEqualTo(2);

        ToDo firstToDo = reprioritizedToDos.get(0);
        assertThat(firstToDo.getDescription()).isEqualTo("second");
        assertThat(firstToDo.getPriority()).isEqualTo(1);

        ToDo secondToDo = reprioritizedToDos.get(1);
        assertThat(secondToDo.getDescription()).isEqualTo("first");
        assertThat(secondToDo.getPriority()).isEqualTo(2);
    }
}