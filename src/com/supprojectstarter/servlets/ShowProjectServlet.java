package com.supprojectstarter.servlets;

import com.supprojectstarter.bean.Project;
import com.supprojectstarter.dao.DAOProject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/showProject")
public class ShowProjectServlet extends HttpServlet {
    public static final String SHOW_PROJECT_VIEW = "/WEB-INF/views/showProject.jsp";
    public static final String ATT_ID = "id";
    public static final String ATT_PROJECT = "project";
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                if (project != null) {
                    request.setAttribute(ATT_PROJECT, project);
                    request.getRequestDispatcher(SHOW_PROJECT_VIEW).forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/home");
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
