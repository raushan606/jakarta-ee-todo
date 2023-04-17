package com.pedantic.service;

import com.pedantic.entity.Todo;
import com.pedantic.entity.TodoUser;
import com.sun.org.apache.xpath.internal.operations.Bool;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
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
        TodoUser todoUser;
        try {
            todoUser = entityManager.createNamedQuery(TodoUser.FIND_TODO_USER_BY_EMAIL, TodoUser.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
        return todoUser;

    }

    public List findAllTodoUsers() {
        return entityManager.createNamedQuery(TodoUser.FIND_ALL_TODO_USERS).getResultList();
    }

    public TodoUser findTodoUserById(Long id) {
        try {
            return entityManager.createNamedQuery(TodoUser.FIND_TODO_USER_BY_id, TodoUser.class).setParameter("id", id).setParameter("email", mySession.getEmail()).getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    public TodoUser findTodoUser(Long id) {
        return entityManager.find(TodoUser.class, id);
    }

    public Collection<TodoUser> findTodoUserByName(String name) {
        return entityManager.createNamedQuery(TodoUser.FIND_TODO_BY_NAME, TodoUser.class).setParameter("name", "%" + name + "%").getResultList();
    }

    public Collection<Todo> findAllTodo() {
        return entityManager.createNamedQuery(Todo.FIND_ALL_TODOS_BY_OWNER_EMAIL, Todo.class).setParameter("email", mySession.getEmail()).getResultList();
    }

    public List countTodoUserByEmail(String email) {
        List resultList = entityManager.createNativeQuery("select count(id) from TodoUserTable where exists(select id from TodoUserTable where email = ?) ")
                .setParameter(1, email).getResultList();
        return resultList;
    }

    public List countTodoUser(Long id, String email) {
        return entityManager.createNativeQuery("select count(id) from TodoUserTable where exists(select id from TodoUserTable where email = ?1 and id = ?2)")
                .setParameter(1, email)
                .setParameter(2, id)
                .getResultList();
    }

    public Todo findTodoById(Long id) {
        List<Todo> list = entityManager.createQuery("select t from Todo t where t.id =: id and t.todoOwner.email=:email", Todo.class)
                .setParameter("id", id)
                .setParameter("email", mySession.getEmail())
                .getResultList();

        if (!list.isEmpty())
            return list.get(0);

        return null;
    }

    public void markTodoAsCompleted(Long id) {
//        entityManager.createQuery("update Todo t set t.completed=true").executeUpdate();
        Todo todoById = findTodoById(id);
        if (todoById != null) {
            todoById.setCompleted(true);
            entityManager.merge(todoById);
        }
    }

    public List<Todo> getTodoByState(boolean state) {
        return entityManager.createQuery("select t from Todo t where t.todoOwner.email=:email and t.completed=:state", Todo.class)
                .setParameter("email", mySession.getEmail())
                .setParameter("complted", state)
                .getResultList();
    }


}
