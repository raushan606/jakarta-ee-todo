package com.pedantic.rest;

import com.pedantic.entity.TodoUser;
import com.pedantic.service.PersistenceService;
import com.pedantic.service.QueryService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoUserRest {

    @Inject
    private PersistenceService persistenceService;

    @Inject
    private QueryService queryService;

    @Path("create")
    @POST
    public Response createTodoUser(@NotNull @Valid TodoUser todoUser) {
        persistenceService.saveTodoUser(todoUser);
        return Response.ok(todoUser).build();
    }

    @Path("update")
    @PUT
    public Response updateTodoUser(@NotNull @Valid TodoUser todoUser) {
        persistenceService.saveTodoUser(todoUser);
        return Response.ok(todoUser).build();
    }

    @GET
    @Path("find/{email}")
    public TodoUser findTodoUserByEmail(@NotNull @PathParam("email") @DefaultValue("") String email) {
        return queryService.findTodoUserByEmail(email);
    }

    @GET
    @Path("query")
    public TodoUser findTodoUserByEmailQuery(@NotNull @QueryParam("email") @DefaultValue("") String email) {
        return queryService.findTodoUserByEmail(email);
    }

    @GET
    @Path("search")
    public Response searchTodoUserByName(@NotNull @QueryParam("name") @DefaultValue("") String name) {
        return Response.ok(queryService.findTodoUserByName(name)).build();
    }

    @GET
    @Path("count")
    public Response countTodoUserByEmail(@NotNull @QueryParam("email") String email) {
        return Response.ok(queryService.countTodoUserByEmail(email)).build();
    }
}
