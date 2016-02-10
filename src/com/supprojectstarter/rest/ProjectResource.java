package com.supprojectstarter.rest;

import com.supprojectstarter.bean.Project;
import com.supprojectstarter.dao.DAOProject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/projects")
public class ProjectResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> getAllProjects() {
        List<Project> projects = DAOProject.getInstance().findAll();
        return projects;
    }

    @GET
    @Path("/{ id }")
    @Produces(MediaType.APPLICATION_JSON)
    public Project getProject(@PathParam("id") int id) {
        Project project = DAOProject.getInstance().find(id);
        return project;
    }

    @GET
    @Path("/byName/{ name }")
    @Produces(MediaType.APPLICATION_JSON)
    public Project getProject(@PathParam("name") String name) {
        Project project = DAOProject.getInstance().findByName(name);
        return project;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProject(Project project) {
        int id = -1;
        id = DAOProject.getInstance().insert(project);
        URI uri = URI.create("/" + id);
        return Response.created(uri).build();
    }
}
