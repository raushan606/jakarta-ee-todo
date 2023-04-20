package com.pedantic.service;

import com.pedantic.entity.Todo;
import com.pedantic.entity.TodoUser;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Map;

@DataSourceDefinition(name = "java:app/Todo/MyDS", className = "org.sqlite.JDBC", url = "jdbc:sqlite:C:/Users/Seeraj/Documents/sql/todo.db")
@Stateless
public class PersistenceService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    private MySession mySession;

    @Inject
    QueryService queryService;

    @Inject
    SecurityUtil securityUtil;

    public TodoUser saveTodoUser(TodoUser todoUser) {
        List list = queryService.countTodoUserByEmail(todoUser.getEmail());
        Long count = (Long) list.get(0);

        Map<String, String> credentials = securityUtil.hashPassword(todoUser.getPassword());

        if (todoUser.getId() == null && count == 0) {
            todoUser.setPassword(credentials.get("hashedPassword"));
            todoUser.setSalt(credentials.get("salt"));
            entityManager.persist(todoUser);
        }

        credentials.clear();

        return todoUser;
    }

    public TodoUser updateTodoUser(TodoUser todoUser) {
        List list = queryService.countTodoUser(todoUser.getId(), todoUser.getEmail());
        Integer count = (Integer) list.get(0);

        if (todoUser.getId() != null && count <= 1) entityManager.merge(todoUser);
        return todoUser;
    }

    public TodoUser updateTodoUsreEMail(Long id, String email) {
        List list = queryService.countTodoUserByEmail(email);
        Integer count = (Integer) list.get(0);
        if (count == 0) {
            TodoUser todoUser = queryService.findTodoUser(id);
            if (todoUser != null) {
                todoUser.setEmail(email);
                entityManager.merge(todoUser);
                return todoUser;
            }
        }
        return null;
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
