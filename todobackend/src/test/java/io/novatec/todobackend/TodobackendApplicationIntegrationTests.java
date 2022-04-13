package io.novatec.todobackend;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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