package com.pedantic.entity;

import java.time.LocalDate;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@NamedQuery(name = Todo.FIND_ALL_TODOS_BY_OWNER_EMAIL, query = "select todo from Todo todo where todo.todoOwner.email = :email")
public class Todo extends AbstractEntity {

    public static final String FIND_ALL_TODOS_BY_OWNER_EMAIL = "TodoUser.findByAllByEmail";
    @NotEmpty(message = "Task must be Set")
    @Size(min = 3, max = 200, message = "The min char length should be 3 and max 200.")
    private String task;
    private LocalDate dateCreated;

    @NotNull(message = "Due date must be set")
    @FutureOrPresent(message = "Due date must be in current or future.")
    @JsonbDateFormat
    private LocalDate dueDate;
    private boolean completed;
    private boolean archived;
    private boolean remind;
    @ManyToOne
    @JoinColumn(name = "TodoUser_Id")
    private TodoUser todoOwner;

    @PrePersist
    private void init() {
        setDateCreated(LocalDate.now());
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public TodoUser getTodoOwner() {
        return todoOwner;
    }

    public void setTodoOwner(TodoUser todoOwner) {
        this.todoOwner = todoOwner;
    }
}
