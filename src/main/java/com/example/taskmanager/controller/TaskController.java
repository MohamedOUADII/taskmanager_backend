package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskCreateDTO;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskUpdateDTO;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Create Task
    @PostMapping("/admin/tasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskCreateDTO dto) {
        Task task = taskService.createTask(dto);
        TaskDTO response = taskService.toDTO(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/admin/tasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks()
                .stream()
                .map(taskService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    // Get tasks by user ID
    @GetMapping("/admin/tasks/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskDTO>> getTasksByUser(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getTasksByUser(userId)
                .stream()
                .map(taskService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    // Get task by ID
    @GetMapping("/admin/tasks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(taskService.toDTO(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Update Task
    @PutMapping("/admin/tasks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO dto) {
        Task updated = taskService.updateTask(id, dto);
        return ResponseEntity.ok(taskService.toDTO(updated));
    }

    // Delete Task
    @DeleteMapping("/admin/tasks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/tasks/me")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<TaskDTO>> getMyTasks(Authentication authentication) {
        String username = authentication.getName();
        List<TaskDTO> tasks = taskService.getTasksForCurrentUser(username);
        return ResponseEntity.ok(tasks);
    }


}
