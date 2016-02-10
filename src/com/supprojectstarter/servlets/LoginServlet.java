package com.supprojectstarter.servlets;

import com.supprojectstarter.bean.User;
import com.supprojectstarter.forms.LoginForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public static final String ATT_USER = "user";
    public static final String ATT_FORM = "form";
    public static final String ATT_SESSION_USER = "sessionUser";
    public static final String VIEW = "/WEB-INF/views/home.jsp";
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(VIEW);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginForm form = new LoginForm();
        User user = form.connectUser(request);
        HttpSession session = request.getSession();
        if (form.getErrors().isEmpty()) {
            session.setAttribute(ATT_SESSION_USER, user);
            response.sendRedirect(request.getHeader("referer"));
        } else {
            session.setAttribute(ATT_SESSION_USER, null);
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_USER, user);
            request.getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}
