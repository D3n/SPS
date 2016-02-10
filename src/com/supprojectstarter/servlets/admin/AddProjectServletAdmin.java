package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.Project;
import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOCategory;
import com.supprojectstarter.dao.DAOUser;
import com.supprojectstarter.forms.AddProjectForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/createProject")
public class AddProjectServletAdmin extends HttpServlet {
    public static final String VIEW_ADD_PROJECT = "/WEB-INF/views/admin/createProject.jsp";
    public static final String SERVLET_MNG_PROJECTS = "/admin/manageProjects";
    public static final String ATT_PROJECT = "project";
    public static final String ATT_FORM = "form";
    public static final String ATT_USERS = "users";
    public static final String ATT_CATEGORIES = "categories";
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> list = DAOUser.getInstance().findAllByGroup("User");
        request.setAttribute(ATT_USERS, list);
        request.setAttribute(ATT_CATEGORIES, DAOCategory.getInstance().findAll());
        request.getRequestDispatcher(VIEW_ADD_PROJECT).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddProjectForm form = new AddProjectForm();
        Project project = form.createProject(request, "admin");
        if (form.getErrors().isEmpty()) {
            response.sendRedirect(request.getContextPath() + SERVLET_MNG_PROJECTS);
        } else {
            List<User> list = DAOUser.getInstance().findAllByGroup("User");
            request.setAttribute(ATT_USERS, list);
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_PROJECT, project);
            request.setAttribute(ATT_CATEGORIES, DAOCategory.getInstance().findAll());
            request.getRequestDispatcher(VIEW_ADD_PROJECT).forward(request, response);
        }
    }
}
