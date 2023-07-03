package io.novatec.todobackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TodobackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodobackendApplicationIntegrationTests {

    public TodobackendApplicationIntegrationTests(){}

    @LocalServerPort
    private int port;

    @Autowired
    private TodobackendApplication todobackendApplication;

    TestRestTemplate restTemplate = new TestRestTemplate();
    
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testGetTodos() {
        todobackendApplication.addTodo("todo1");
        todobackendApplication.addTodo("todo2");
        todobackendApplication.addTodo("todo3");

        assertEquals(3, todobackendApplication.getTodos().size());
    }
}