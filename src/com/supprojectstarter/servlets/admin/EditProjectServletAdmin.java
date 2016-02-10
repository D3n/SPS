package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.Project;
import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOCategory;
import com.supprojectstarter.dao.DAOProject;
import com.supprojectstarter.dao.DAOUser;
import com.supprojectstarter.forms.EditProjectForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class EditProjectServletAdmin
 */
@WebServlet("/admin/editProject")
public class EditProjectServletAdmin extends HttpServlet {
    public static final String ATT_ID = "id";
    public static final String ATT_PROJECT = "project";
    public static final String ATT_CATEGORIES = "categories";
    public static final String ATT_FORM = "form";
    public static final String ATT_USERS = "users";
    public static final String VIEW_EDIT_PROJECT = "/WEB-INF/views/admin/editProject.jsp";
    public static final String SERVLET_MNG_PROJECTS = "/admin/manageProjects";
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = (String) request.getParameter(ATT_ID);
        int id = -1;
        boolean ok = true;
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + SERVLET_MNG_PROJECTS);
        } else {
            try {
                id = Integer.parseInt(idParam);
            } catch (Exception e) {
                ok = false;
                e.printStackTrace();
            }
            if (!ok) {
                response.sendRedirect(request.getContextPath() + SERVLET_MNG_PROJECTS);
            } else {
                Project project = DAOProject.getInstance().find(id);
                if (project != null) {
                    List<User> list = DAOUser.getInstance().findAllByGroup("User");
                    request.setAttribute(ATT_USERS, list);
                    request.setAttribute(ATT_PROJECT, project);
                    request.setAttribute(ATT_CATEGORIES, DAOCategory.getInstance().findAll());
                    request.getRequestDispatcher(VIEW_EDIT_PROJECT).forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/home");
                }
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EditProjectForm form = new EditProjectForm();
        Project project = form.editProject(request, "admin");
        if (form.getErrors().isEmpty()) {
            response.sendRedirect(request.getContextPath() + SERVLET_MNG_PROJECTS);
        } else {
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_PROJECT, project);
            request.setAttribute(ATT_CATEGORIES, DAOCategory.getInstance().findAll());
            request.getRequestDispatcher(VIEW_EDIT_PROJECT).forward(request, response);
        }
    }
}
