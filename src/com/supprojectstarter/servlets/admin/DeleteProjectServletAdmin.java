package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.Project;
import com.supprojectstarter.dao.DAOProject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/deleteProject")
public class DeleteProjectServletAdmin extends HttpServlet {
    public static final String SERVLET_MNG_PROJECTS = "/admin/manageProjects";
    public static final String ATT_ID = "id";
    private static final long serialVersionUID = 1L;

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
                    DAOProject.getInstance().delete(project);
                    response.sendRedirect(request.getContextPath() + SERVLET_MNG_PROJECTS);
                } else {
                    response.sendRedirect(request.getContextPath() + SERVLET_MNG_PROJECTS);
                }
            }
        }
    }
}
