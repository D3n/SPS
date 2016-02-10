package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.Contribution;
import com.supprojectstarter.dao.DAOContribution;
import com.supprojectstarter.dao.DAOProject;
import com.supprojectstarter.dao.DAOUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/showDashboard")
public class ShowDashboardServletAdmin extends HttpServlet {
    public static final String VIEW_SHOW_DASHBOARD = "/WEB-INF/views/admin/showDashboard.jsp";
    public static final String ATT_NBPROJ = "nbProjects";
    public static final String ATT_NBUSR = "nbUsers";
    public static final String ATT_NBCONTRIB = "contributions";
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int nbProjects = DAOProject.getInstance().findAll().size();
        int nbUsers = DAOUser.getInstance().findAll().size();
        List<Contribution> contribs = DAOContribution.getInstance().findAll();
        request.setAttribute(ATT_NBPROJ, nbProjects);
        request.setAttribute(ATT_NBUSR, nbUsers);
        request.setAttribute(ATT_NBCONTRIB, contribs);
        request.getRequestDispatcher(VIEW_SHOW_DASHBOARD).forward(request, response);
    }
}
