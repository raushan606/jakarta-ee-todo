package com.pedantic.rest;

import com.pedantic.entity.Todo;
import com.pedantic.service.PersistenceService;
import com.pedantic.service.QueryService;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

@Path("todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoRest {
    @Inject
    PersistenceService persistenceService;
    @Inject
    QueryService queryService;

    @Path("create")
    @POST
    public Response createTodo(Todo todo) {
        persistenceService.saveTodo(todo);
        return Response.ok(todo).build();
    }

    @GET
    @Path("list")
    public Response listTodo() {
        return Response.ok(queryService.findAllTodo()).build();
    }

    @GET
    @Path("find")
    public Response findTodoByID(@NotNull @QueryParam("id") Long id) {
        return Response.ok(queryService.findTodoById(id)).build();
    }

    @PUT
    @Path("mark")
    public Response markCompleted(@NotNull @QueryParam("id") Long id) {
        queryService.markTodoAsCompleted(id);
        return Response.ok().build();
    }

    @GET
    @Path("completed")
    public Response getAllCompleted() {
        return Response.ok(queryService.getTodoByState(true)).build();
    }

    @GET
    @Path("uncompleted")
    public Response getAllUncompleted() {
        return Response.ok(queryService.getTodoByState(false)).build();
    }

    @GET
    @Path("due-date")
    public Response getTodoByDueDate(@NotNull @QueryParam("date") @Pattern(regexp = "^\\d{4}-\\d{2}-\\{2}$") String date) {
        LocalDate dueDate = LocalDate.parse(date);
        return Response.ok(queryService.getTodosByDueDates(dueDate)).build();
    }

    @PUT
    @Path("archive")
    public Response archiveTodo(@NotNull @QueryParam("id") Long id) {
        queryService.markTodoAsArchived(id);
        return Response.ok().build();
    }
}
