package io.novatec.todobackend;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TodobackendApplicationTests {

    public TodobackendApplicationTests(){}

    @Test
    public void testMockExample() {
        TodobackendApplication todobackendApplication = mock(TodobackendApplication.class);

        List<String> todos = new ArrayList<>();
        todos.add("todo1");
        todos.add("todo2");
        todos.add("todo3");

        when(todobackendApplication.getTodos()).thenReturn(todos);
        
        assertEquals(3, todobackendApplication.getTodos().size());
    }
}