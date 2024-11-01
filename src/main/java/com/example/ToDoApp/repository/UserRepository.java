package com.example.ToDoApp.repository;

import com.example.ToDoApp.model.AppUser; // Ensure you're importing AppUser
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);


    boolean existsByEmail(String email); // Method to check email existence
}

