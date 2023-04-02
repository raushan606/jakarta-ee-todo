package com.pedantic.service;

import com.pedantic.entity.Todo;
import com.pedantic.entity.TodoUser;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class PersistenceService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    private MySession mySession;

    @Inject
    QueryService queryService;

    public TodoUser saveTodoUser(TodoUser todoUser) {
        if (todoUser.getId() == null)
            entityManager.persist(todoUser);
        else
            entityManager.merge(todoUser);
        return todoUser;
    }

    public Todo saveTodo(Todo todo) {
        String email = mySession.getEmail();
        TodoUser todoUser = queryService.findTodoUserByEmail(email);
        todo.setTodoOwner(todoUser);
        if (todo.getId() == null)
            entityManager.persist(todo);
        else
            entityManager.merge(todo);
        return todo;
    }

}
