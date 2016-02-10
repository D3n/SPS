package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class ShowManageUsersServlet
 */
@WebServlet("/admin/manageUsers")
public class ShowManageUsersServletAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/views/admin/manageUsers.jsp";
    private static final String ATT_USERS = "users";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowManageUsersServletAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> listUsers = DAOUser.getInstance().findAll();
        request.setAttribute(ATT_USERS, listUsers);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
}
