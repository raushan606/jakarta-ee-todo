package com.pedantic.rest;

import com.pedantic.entity.Todo;
import com.pedantic.entity.TodoUser;
import com.pedantic.service.PersistenceService;
import com.pedantic.service.QueryService;
import com.pedantic.service.SecurityUtil;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoUserRest {

    @Inject
    private PersistenceService persistenceService;

    @Inject
    private QueryService queryService;

    @Inject
    private SecurityUtil securityUtil;
    @Context
    private UriInfo uriInfo;

    @Path("create")
    @POST
    public Response createTodoUser(@NotNull @Valid TodoUser todoUser) {
        persistenceService.saveTodoUser(todoUser);
        return Response.ok(todoUser).build();
    }

    @Path("update")
    @PUT
    public Response updateTodoUser(@NotNull @Valid TodoUser todoUser) {
        persistenceService.updateTodoUser(todoUser);
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

    @GET
    @Path("list")
    public Response listAllTodoUsers() {
        return Response.ok(queryService.findAllTodoUsers()).build();
    }

    @PUT
    @Path("update-email")
    public Response updateEmail(@NotNull @QueryParam("id") Long id, @NotNull @QueryParam("email") String email) {
        TodoUser todoUser = persistenceService.updateTodoUsreEMail(id, email);
        return Response.ok(todoUser).build();
    }

    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response login(@NotEmpty(message = "EMail field must be set") @FormParam("email") String email,
                          @NotEmpty(message = "Password field must be set") @FormParam("password") String password) {

        // Authenticate User
        // Generate token
        // Return token in Response header to client

        if(!securityUtil.authenticateUser(email, password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok().build();
    }

    private String getToken(String email) {
        Key key = securityUtil.generateKey(email);
        String token = Jwts.builder().setSubject(email).setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date()).signWith(key).setExpiration(securityUtil.toDate(LocalDateTime.now().plusMinutes(15)))
                .signWith(SignatureAlgorithm.HS512, key).setAudience(uriInfo.getBaseUri().toString()).compact();
        return token;
    }
}
