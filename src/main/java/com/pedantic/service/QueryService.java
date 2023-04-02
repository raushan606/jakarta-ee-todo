package com.pedantic.service;

import com.pedantic.entity.Todo;
import com.pedantic.entity.TodoUser;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Collection;
import java.util.List;

@Stateless
public class QueryService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    MySession mySession;

    public TodoUser findTodoUserByEmail(String email) {
        return entityManager.createNamedQuery(TodoUser.FIND_TODO_USER_BY_EMAIL, TodoUser.class).setParameter("email", email).getSingleResult();

    }

    public List findAllTodoUsers() {
        return entityManager.createNamedQuery(TodoUser.FIND_ALL_TODO_USERS).getResultList();
    }

    public TodoUser findTodoUserById(Long id) {
        return entityManager.createNamedQuery(TodoUser.FIND_TODO_USER_BY_id, TodoUser.class).setParameter("id", id).setParameter("email", mySession.getEmail()).getSingleResult();
    }

    public Collection<TodoUser> findTodoUserByName(String name) {
        return entityManager.createNamedQuery(TodoUser.FIND_TODO_BY_NAME, TodoUser.class).setParameter("name", "%" + name + "%").getResultList();
    }

    public Collection<Todo> findAllTodo(String email) {
        return entityManager.createNamedQuery(Todo.FIND_ALL_TODOS_BY_OWNER_EMAIL, Todo.class).setParameter("email", email).getResultList();
    }
}
