package com.pairgood.todo.integration;

import com.pairgood.todo.repository.ToDo;
import com.pairgood.todo.repository.ToDoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

}
