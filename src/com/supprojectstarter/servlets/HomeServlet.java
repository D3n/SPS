package com.supprojectstarter.servlets;

import com.supprojectstarter.bean.Project;
import com.supprojectstarter.dao.DAOProject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/home.jsp";
    private static final String ATT_PROJECTS = "projects";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Project> listProjects = DAOProject.getInstance().findForHome();
        request.setAttribute(ATT_PROJECTS, listProjects);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
