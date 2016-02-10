package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.Project;
import com.supprojectstarter.dao.DAOProject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class ShowManageProjectsServlet
 */
@WebServlet("/admin/manageProjects")
public class ShowManageProjectsServletAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/admin/manageProjects.jsp";
    private static final String ATT_PROJECTS = "projects";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowManageProjectsServletAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Project> listProjects = DAOProject.getInstance().findAll();
        request.setAttribute(ATT_PROJECTS, listProjects);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
}
