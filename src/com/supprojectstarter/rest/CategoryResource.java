package com.supprojectstarter.rest;

import com.supprojectstarter.bean.Category;
import com.supprojectstarter.dao.DAOCategory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/categories")
public class CategoryResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getAllCategory() {
        List<Category> categories = DAOCategory.getInstance().findAll();
        return categories;
    }

    @GET
    @Path("/{ name }")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getCategory(@PathParam("name") String name) {
        Category category = DAOCategory.getInstance().findByName(name);
        return category;
    }
}
