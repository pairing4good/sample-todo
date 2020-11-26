package com.pairgood.todo.repository;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ToDoTest {

    @Test
    void markAsDone() {
        ToDo toDo = new ToDo();

        assertThat(toDo.isDone()).isFalse();

        toDo.markAsDone();

        assertThat(toDo.isDone()).isTrue();
    }
}