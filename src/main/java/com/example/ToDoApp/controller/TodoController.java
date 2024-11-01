package com.example.ToDoApp.controller;

import com.example.ToDoApp.model.Todo;
import com.example.ToDoApp.model.AppUser;
import com.example.ToDoApp.repository.TodoRepository;
import com.example.ToDoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping
    public Todo createTodo(@RequestBody Todo todo, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("User is not authenticated");
        }
        AppUser user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        todo.setUser(user);
        return todoRepository.save(todo);
    }


@GetMapping
public List<Todo> getTodos(@AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
        throw new RuntimeException("User is not authenticated");
    }
    // Retrieve the AppUser entity based on email or username
    AppUser user = userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Fetch todos by user
    return todoRepository.findByUser(user);
}


    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todoRequest, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("User is not authenticated");
        }
        AppUser user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        if (todo.getUser().getId().equals(user.getId())) { // Ensure user matches
            todo.setTitle(todoRequest.getTitle());
            todo.setDescription(todoRequest.getDescription());
            return todoRepository.save(todo);
        } else {
            throw new RuntimeException("You are not authorized to update this todo");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("User is not authenticated");
        }
        AppUser user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        if (todo.getUser().getId().equals(user.getId())) {
            todoRepository.delete(todo);
        } else {
            throw new RuntimeException("You are not authorized to delete this todo");
        }
    }
}
