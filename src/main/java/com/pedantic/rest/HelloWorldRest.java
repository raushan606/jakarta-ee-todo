package com.pedantic.rest;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("hello")
public class HelloWorldRest {


    @Path("{name}")
    @GET
    public JsonObject greet(@PathParam("name") String name) {

        return Json.createObjectBuilder().add("name", name)
                .add("greeting", "Hello, " + name)
                .add("message", "Hello from Jakarta EE!").build();
    }
}
