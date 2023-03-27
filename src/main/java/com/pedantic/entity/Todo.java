package com.pedantic.entity;

import java.time.LocalDate;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TodoTable")
public class Todo extends AbstractEntity {

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

}
