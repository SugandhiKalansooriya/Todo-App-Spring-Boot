package com.example.ToDoApp.repository;



import com.example.ToDoApp.model.AppUser;
import com.example.ToDoApp.model.Todo;
import com.example.ToDoApp.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser(AppUser user);
}

