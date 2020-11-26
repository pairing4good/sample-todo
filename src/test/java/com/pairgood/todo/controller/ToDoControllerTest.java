package com.pairgood.todo.controller;

import com.pairgood.todo.repository.ToDo;
import com.pairgood.todo.repository.ToDoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoControllerTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoController controller;

    @Test
    void all_ShouldReturnAllOfTheToDos() {
        ToDo firstToDo = new ToDo("first");
        ToDo secondToDo = new ToDo("second");
        ToDo thirdToDo = new ToDo("thrid");

        List<ToDo> toDos = Arrays.asList(firstToDo, secondToDo, thirdToDo);

        when(toDoRepository.findAll()).thenReturn(toDos);

        List<ToDo> all = controller.all();

        assertThat(all).isEqualTo(toDos);
    }

    @Test
    void newEmployee_ShouldSaveTheToDoItem() {
        ToDo newToDo = new ToDo();

        controller.newEmployee(newToDo);

        verify(toDoRepository).save(newToDo);
    }

    @Test
    void delete_ShouldDeleteToDoById() {

        controller.delete(1L);

        verify(toDoRepository).deleteById(1L);
    }

    @Test
    void markAsDone_whenToDoExists_ShouldUpdateTheToDoItemToDone() {
        ToDo toDo = new ToDo();
        Optional<ToDo> optionalToDo = Optional.of(toDo);

        when(toDoRepository.findById(1L)).thenReturn(optionalToDo);

        controller.markAsDone(1L);

        assertThat(toDo.isDone()).isTrue();

        verify(toDoRepository).save(toDo);
    }

    @Test
    void markAsDone_whenToDoDoesNotExist_ShouldNotUpdateAnyToDoItem() {
        Optional<ToDo> optionalToDo = Optional.empty();

        when(toDoRepository.findById(1L)).thenReturn(optionalToDo);

        controller.markAsDone(1L);

        verifyNoMoreInteractions(toDoRepository);
    }
}