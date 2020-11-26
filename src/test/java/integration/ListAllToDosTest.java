package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pairgood.todo.Application;
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

@SpringBootTest(classes = Application.class)
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
        ToDo firstToDo = toDoRepository.save(new ToDo("First ToDo"));
        ToDo secondToDo = toDoRepository.save(new ToDo("Second ToDo"));
        ToDo thirdToDo = toDoRepository.save(new ToDo("Third ToDo"));

        mockMvc.perform(get("/todos/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" +
                        asJsonString(firstToDo) + "," +
                        asJsonString(secondToDo) + "," +
                        asJsonString(thirdToDo) +
                        "]"));
    }

    @Test
    void givenThatNoToDosExist_WhenNewToDoAdded_ThenTheListContainsOneItme() throws Exception {
        assertThat(toDoRepository.count()).isEqualTo(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/todos/")
                .content(asJsonString(new ToDo("test todo")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<ToDo> toDos = toDoRepository.findAll();

        assertThat(toDos.size()).isEqualTo(1);
        assertThat(toDos.get(0).getDescription()).isEqualTo("test todo");
    }

    @Test
    void givenThatOneToDoExists_WhenToDoIsDeleted_ThenNoMoreToDosExist() throws Exception {
        ToDo toDo = toDoRepository.save(new ToDo("test todo"));
        Long id = toDo.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<ToDo> toDos = toDoRepository.findAll();

        assertThat(toDos.size()).isEqualTo(0);
    }

    @Test
    void givenThatOneToDoExists_WhenThatItemIsMarkedAsDone_ThenThatItemIsListedAsDone() throws Exception {
        ToDo toDo = toDoRepository.save(new ToDo("test todo"));
        Long id = toDo.getId();

        assertThat(toDo.isDone()).isFalse();

        mockMvc.perform(MockMvcRequestBuilders.put("/todos/{id}/done", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<ToDo> toDos = toDoRepository.findAll();

        assertThat(toDos.size()).isEqualTo(1);
        assertThat(toDos.get(0).isDone()).isTrue();
    }

    String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
