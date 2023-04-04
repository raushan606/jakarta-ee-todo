package com.pedantic.service;

import com.pedantic.entity.Todo;
import com.pedantic.entity.TodoUser;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataSourceDefinition(
        name = "java:app/Todo/MyDS",
        className = "org.sqlite.JDBC",
        url = "jdbc:sqlite:C:/Users/Seeraj/Documents/sql/todo.db"
)
@Stateless
public class PersistenceService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    private MySession mySession;

    @Inject
    QueryService queryService;

    public TodoUser saveTodoUser(TodoUser todoUser) {
        if (todoUser.getId() == null) entityManager.persist(todoUser);
        else entityManager.merge(todoUser);
        return todoUser;
    }

    public Todo saveTodo(Todo todo) {
        String email = mySession.getEmail();
        TodoUser todoUser = queryService.findTodoUserByEmail(email);

        if (todo.getId() == null && todoUser != null) {
            todo.setTodoOwner(todoUser);
            entityManager.persist(todo);
        } else {
            entityManager.merge(todo);
        }
        return todo;
    }

}
