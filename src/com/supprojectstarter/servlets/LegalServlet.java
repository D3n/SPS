package com.supprojectstarter.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/legal")
public class LegalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String VIEW_LEGAL = "/WEB-INF/views/mentionsLegales.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_LEGAL).forward(request, response);
    }
}
