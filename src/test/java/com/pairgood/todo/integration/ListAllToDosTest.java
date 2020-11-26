package com.pairgood.todo.integration;

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
public class ListAllToDosTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;

    @AfterEach
    public void tearDown() {
        List<String> toDos = toDoRepository.findAll();
        int toDoLength = toDos.size();

        for (int i = 0; i < toDoLength; i++) {
            toDoRepository.delete(0);
        }
    }

    @Test
    public void givenNoToDosExist_WhenListingAll_ThenEmptyListOfToDosIsDisplayed() throws Exception {
        mockMvc.perform(get("/todos/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }


    @Test
    public void givenThatThreeItemsHaveBeenEntered_WhenTheListIsDisplayed_ThenTheListContainsThreeItems()
            throws Exception {
        toDoRepository.save("First ToDo");
        toDoRepository.save("Second ToDo");
        toDoRepository.save("Third ToDo");

        mockMvc.perform(get("/todos/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\"First ToDo\",\"Second ToDo\",\"Third ToDo\"]"));
    }

}
