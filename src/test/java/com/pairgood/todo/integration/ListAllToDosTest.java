package com.pairgood.todo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pairgood.todo.repository.ToDo;
import com.pairgood.todo.repository.ToDoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ListAllToDosTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;

    @AfterEach
    void tearDown() {
        List<ToDo> toDos = toDoRepository.findAll();

        for (ToDo toDo : toDos) {
            toDoRepository.delete(toDo);
        }
    }

    @Test
    void givenNoToDosExist_WhenListingAll_ThenEmptyListOfToDosIsDisplayed() throws Exception {
        mockMvc.perform(get("/todos/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }


    @Test
    void givenThatThreeItemsHaveBeenEntered_WhenTheListIsDisplayed_ThenTheListContainsThreeItems()
            throws Exception {
        toDoRepository.save(new ToDo("First ToDo"));
        toDoRepository.save(new ToDo("Second ToDo"));
        toDoRepository.save(new ToDo("Third ToDo"));

        mockMvc.perform(get("/todos/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" +
                        "{\"id\":1,\"description\":\"First ToDo\"}," +
                        "{\"id\":2,\"description\":\"Second ToDo\"}," +
                        "{\"id\":3,\"description\":\"Third ToDo\"}" +
                        "]"));
    }

    @Test
    void givenThatNoToDosExist_WhenNewToDoAdded_ThenTheListContainsOneItme() throws Exception {
        assertThat(toDoRepository.count()).isEqualTo(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/")
                .content(asJsonString(new ToDo("test todo")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        List<ToDo> toDos = toDoRepository.findAll();

        assertThat(toDos.size()).isEqualTo(1);
        assertThat(toDos.get(0).getDescription()).isEqualTo("test todo");
    }

    String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
