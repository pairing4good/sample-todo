package com.pairgood.todo.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ToDoAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenThatNoItemsHaveBeenEntered_WhenTheListIsDisplayed_ThenTheListIsEmpty(){
        ResponseEntity<String[]> responseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/todos/", String[].class);
        List<String> todoItems = Arrays.asList(responseEntity.getBody());

        assertThat(todoItems).isEmpty();
    }
}