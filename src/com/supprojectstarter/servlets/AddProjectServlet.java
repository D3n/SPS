package com.supprojectstarter.servlets;

import com.supprojectstarter.bean.Project;
import com.supprojectstarter.dao.DAOCategory;
import com.supprojectstarter.forms.AddProjectForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/addProject")
public class AddProjectServlet extends HttpServlet {
    public static final String VIEW = "/WEB-INF/views/addProject.jsp";
    public static final String SHOW_PROJECT_VIEW = "/showProject?id=";
    public static final String ATT_PROJECT = "project";
    public static final String ATT_CATEGORIES = "categories";
    public static final String ATT_FORM = "form";
    private static final long serialVersionUID = 1L;

    public AddProjectServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ATT_CATEGORIES, DAOCategory.getInstance().findAll());
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddProjectForm form = new AddProjectForm();
        Project project = form.createProject(request, "user");
        if (form.getErrors().isEmpty()) {
            response.sendRedirect(request.getContextPath() + SHOW_PROJECT_VIEW + project.getId());
        } else {
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_PROJECT, project);
            request.setAttribute(ATT_CATEGORIES, DAOCategory.getInstance().findAll());
            request.getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}
