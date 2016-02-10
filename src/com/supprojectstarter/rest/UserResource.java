package com.supprojectstarter.rest;

import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/users")
public class UserResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        List<User> users = DAOUser.getInstance().findAll();
        return users;
    }

    @GET
    @Path("/{ email }")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") String email) {
        User user = DAOUser.getInstance().findByEmail(email);
        return user;
    }

    @POST
    public Response addUser(User user) {
        DAOUser.getInstance().insert(user);
        URI uri = URI.create("/" + user.getMailAddress());
        return Response.created(uri).build();
    }

    @PUT
    public Response updateUser(User user) {
        DAOUser.getInstance().update(user);
        return Response.noContent().build();
    }
}
