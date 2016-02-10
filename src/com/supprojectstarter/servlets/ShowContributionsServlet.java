package com.supprojectstarter.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/showContributions")
public class ShowContributionsServlet extends HttpServlet {
    public static final String VIEW_SHOW_CONTRIBUTIONS = "/WEB-INF/views/showContributions.jsp";
    private static final long serialVersionUID = 1L;

    public ShowContributionsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_SHOW_CONTRIBUTIONS).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
