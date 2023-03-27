package com.pedantic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "TodoUserTable")
public class TodoUser extends AbstractEntity {


    @Column(length = 100, nullable = false, unique = true)
    @NotEmpty(message = "Email should not be empty field.")
    @Email(message = "Email must be in the format user@domain.cm")
    private String email;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 8, max = 20, message = "Password must be min 8 and max 20 characters.")
    @Pattern(regexp = "")
    private String password;

    @NotEmpty(message = "Name must be set")
    @Size(min = 2, max = 140, message = "Name must be min 2 and max 140 character")
    private String fullName;

//    @OneToMany
//    private final Collection<Todo> todos = new ArrayList<>();


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
