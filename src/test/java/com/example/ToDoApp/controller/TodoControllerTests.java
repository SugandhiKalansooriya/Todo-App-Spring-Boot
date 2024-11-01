package com.example.ToDoApp.controller;

import com.example.ToDoApp.model.AppUser;
import com.example.ToDoApp.model.Todo;
import com.example.ToDoApp.repository.TodoRepository;
import com.example.ToDoApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

class TodoControllerTests {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private TodoController todoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @Test
    void createTodo_Success() throws Exception {
        // Create a mock user
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("test@example.com");

        // Create a mock UserDetails object
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(appUser.getEmail())
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        // Create a Todo object
        Todo todo = new Todo();
        todo.setTitle("New Todo");
        todo.setDescription("Todo Description");

        when(userRepository.findByEmail(any(String.class))).thenReturn(java.util.Optional.of(appUser));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo createdTodo = todoController.createTodo(todo, userDetails);
        assertNotNull(createdTodo);
        assertEquals("New Todo", createdTodo.getTitle());
        verify(todoRepository).save(todo);
    }

    @Test
    void getTodos_Success() throws Exception {
        // Create a mock user
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("test@example.com");

        // Create a mock UserDetails object
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(appUser.getEmail())
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        Todo todo1 = new Todo();
        todo1.setTitle("Todo 1");
        todo1.setDescription("Description 1");

        Todo todo2 = new Todo();
        todo2.setTitle("Todo 2");
        todo2.setDescription("Description 2");

        when(userRepository.findByEmail(any(String.class))).thenReturn(java.util.Optional.of(appUser));
        when(todoRepository.findByUser(appUser)).thenReturn(List.of(todo1, todo2));

        List<Todo> todos = todoController.getTodos(userDetails);
        assertEquals(2, todos.size());
        verify(todoRepository).findByUser(appUser);
    }
}

