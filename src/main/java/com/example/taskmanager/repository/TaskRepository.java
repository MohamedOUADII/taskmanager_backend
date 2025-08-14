package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find tasks by user id
    List<Task> findByUserId(Long userId);
    @Query("SELECT t FROM Task t WHERE t.user.username = :username")
    List<Task> findByUsername(@Param("username") String username);

    // You can add more custom queries later (e.g. by status)
}
