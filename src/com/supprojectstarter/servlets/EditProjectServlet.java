package com.supprojectstarter.servlets;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/editProject")
public class EditProjectServlet extends HttpServlet {
    public static final String ATT_ID = "id";
    public static final String ATT_USERS = "users";
    public static final String ATT_PROJECT = "project";
    public static final String ATT_CATEGORIES = "categories";
    public static final String ATT_SESSION = "sessionUser";
    public static final String ATT_FORM = "form";
    public static final String VIEW_EDIT_PROJECT = "/WEB-INF/views/editProject.jsp";
    public static final String VIEW_SHOW_PROJECT = "/showProject?id=";
    private static final long serialVersionUID = 1L;

    public EditProjectServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userAuth = (User) session.getAttribute(ATT_SESSION);
        String idParam = (String) request.getParameter(ATT_ID);
        int id = -1;
        boolean ok = true;
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            try {
                id = Integer.parseInt(idParam);
            } catch (Exception e) {
                ok = false;
                e.printStackTrace();
            }
            if (!ok) {
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                Project project = DAOProject.getInstance().find(id);
                if (project != null && project.getCreatorUser().getMailAddress().equals(userAuth.getMailAddress())) {
                    request.setAttribute(ATT_PROJECT, project);
                    request.setAttribute(ATT_CATEGORIES, DAOCategory.getInstance().findAll());
                    request.getRequestDispatcher(VIEW_EDIT_PROJECT).forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/home");
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EditProjectForm form = new EditProjectForm();
        Project project = form.editProject(request, "user");
        if (form.getErrors().isEmpty()) {
            response.sendRedirect(request.getContextPath() + VIEW_SHOW_PROJECT + project.getId());
        } else {
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_PROJECT, project);
            request.setAttribute(ATT_USERS, DAOUser.getInstance().findAll());
            request.setAttribute(ATT_CATEGORIES, DAOCategory.getInstance().findAll());
            request.getRequestDispatcher(VIEW_EDIT_PROJECT).forward(request, response);
        }
    }
}
