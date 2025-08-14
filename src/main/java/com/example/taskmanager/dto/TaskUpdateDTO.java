package com.example.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateDTO {

    private Optional<String> title = Optional.empty();
    private Optional<String> description = Optional.empty();
    private Optional<String> status = Optional.empty();
    private Optional<LocalDate> dueDate = Optional.empty();
    private Optional<Integer> priority = Optional.empty();

    public Optional<String> getTitle() {
        return title;
    }

    public void setTitle(Optional<String> title) {
        this.title = title;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    public Optional<String> getStatus() {
        return status;
    }

    public void setStatus(Optional<String> status) {
        this.status = status;
    }

    public Optional<LocalDate> getDueDate() {
        return dueDate;
    }

    public void setDueDate(Optional<LocalDate> dueDate) {
        this.dueDate = dueDate;
    }

    public Optional<Integer> getPriority() {
        return priority;
    }

    public void setPriority(Optional<Integer> priority) {
        this.priority = priority;
    }
}
