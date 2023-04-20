package com.pedantic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "TodoUserTable")
@NamedQuery(name = TodoUser.FIND_TODO_USER_BY_EMAIL, query = "select tu from TodoUser tu where tu.email = :email")
@NamedQuery(name = TodoUser.FIND_ALL_TODO_USERS, query = "select todoUser from TodoUser todoUser order by todoUser.fullName")
@NamedQuery(name = TodoUser.FIND_TODO_USER_BY_id, query = "select t from TodoUser t where t.id = :id and t.email = :email")
@NamedQuery(name = TodoUser.FIND_TODO_BY_NAME, query = "select t from TodoUser t where t.fullName like :name")
public class TodoUser extends AbstractEntity {

    public static final String FIND_TODO_USER_BY_EMAIL = "TodoUser.findByEmail";
    public static final String FIND_ALL_TODO_USERS = "TodoUser.findAll";
    public static final String FIND_TODO_USER_BY_id = "TodoUser.findByIdAndEmail";
    public static final String FIND_TODO_BY_NAME = "TodoUser.findByName";

    // Create a TodoUser - /api/v1/user/create - TodoUser(JSON)
    // Update a TodoUser- /api/v1/user/update - Id, with path to updated TodoUser
    // Find a TodoUser by Email- /api/v1/user/find/{email} - /api/v1/user/find?email= TodoUser(JSON)
    // Find a TodoUser by Name- /api/v1/user/name/{name} - List of TodoUsers(JSON)
    // Search for a TodoUser by email- /api/v1/user/search/{email} - /api/v1/user/search?email - A number

    private String salt;

    @Column(length = 100)
    @NotEmpty(message = "Email should not be empty field")
    @Email(message = "Email must be in the format user@domain.com")
    private String email;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 8, max = 20, message = "Password must be min 8 and max 20 characters.")
//    @Pattern(regexp = "")
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
