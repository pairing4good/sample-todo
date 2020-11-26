package acceptance;

import com.pairgood.todo.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
class ToDoAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenThatNoItemsHaveBeenEntered_WhenTheListIsDisplayed_ThenTheListIsEmpty() {
        ResponseEntity<String[]> responseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/todos/", String[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        String[] body = responseEntity.getBody();
        assertThat(body).isNotNull();

        List<String> todoItems = Arrays.asList(body);

        assertThat(todoItems).isEmpty();
    }
}
