package com.supprojectstarter.servlets;

import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/showProfile")
public class ShowProfileServlet extends HttpServlet {
    public static final String ATT_USER = "user";
    public static final String ATT_SESSION = "sessionUser";
    public static final String VIEW_SHOW_PROFILE = "/WEB-INF/views/showProfile.jsp";
    private static final long serialVersionUID = 1L;

    public ShowProfileServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userSession = (User) request.getSession().getAttribute(ATT_SESSION);
        User userUpdated = DAOUser.getInstance().findByEmail(userSession.getMailAddress());
        //userAuth.setContributions(DAOContribution.getInstance().findAllByUser(userAuth.getMailAddress()));
        //userAuth.setProjects(DAOProject.getInstance().findAllByUser(userAuth.getMailAddress()));
        request.setAttribute(ATT_USER, userUpdated);
        request.getRequestDispatcher(VIEW_SHOW_PROFILE).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
