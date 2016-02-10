package com.supprojectstarter.rest;

import com.supprojectstarter.bean.Group;
import com.supprojectstarter.dao.DAOGroup;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/groups")
public class GroupResource {
    @GET
    @Path("/{ label }")
    @Produces(MediaType.APPLICATION_JSON)
    public Group getGroup(@PathParam("label") String label) {
        Group group = DAOGroup.getInstance().findByName(label);
        return group;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Group> getAllGroups(@PathParam("label") String label) {
        List<Group> groups = DAOGroup.getInstance().findAll();
        return groups;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGroup(Group group) {
        int id = -1;
        id = DAOGroup.getInstance().insert(group);
        URI uri = URI.create("/" + id);
        return Response.created(uri).build();
    }
}
