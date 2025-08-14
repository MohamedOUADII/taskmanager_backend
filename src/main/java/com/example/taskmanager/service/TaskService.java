package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskCreateDTO;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.dto.TaskUpdateDTO;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // Convert from DTOs and Entities will be manual here for now

    public Task createTask(TaskCreateDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus() != null ? Task.Status.valueOf(dto.getStatus()) : Task.Status.TODO);
        task.setDueDate(dto.getDueDate());
        task.setPriority(dto.getPriority());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);

        return taskRepository.save(task);
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, TaskUpdateDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        dto.getTitle().ifPresent(task::setTitle);
        dto.getDescription().ifPresent(task::setDescription);
        dto.getStatus().ifPresent(status -> task.setStatus(Task.Status.valueOf(status)));
        dto.getDueDate().ifPresent(task::setDueDate);
        dto.getPriority().ifPresent(task::setPriority);

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    // Helper to convert Task entity to TaskDTO
    public TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getDueDate(),
                task.getPriority(),
                task.getUser().getId()
        );
    }
    public List<TaskDTO> getTasksForCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Task> tasks = taskRepository.findByUsername(user.getUsername());

        return tasks.stream()
                .map(this::toDTO) // or whatever your mapper method is
                .collect(Collectors.toList());
    }

}

