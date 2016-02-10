package com.supprojectstarter.servlets;

import com.supprojectstarter.bean.User;
import com.supprojectstarter.forms.SigninForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signin")
public class SigninServlet extends HttpServlet {
    public static final String SIGNIN_VIEW = "/WEB-INF/views/signin2.jsp";
    public static final String ATT_USER = "user";
    public static final String ATT_FORM = "form";
    public static final String ATT_SESSION_USER = "sessionUser";
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if ((User) session.getAttribute(ATT_SESSION_USER) != null) {
            response.sendRedirect(request.getContextPath());
        } else {
            request.getRequestDispatcher(SIGNIN_VIEW).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SigninForm form = new SigninForm();
        User user = form.registerUser(request);
        HttpSession session = request.getSession();
        if (form.getErrors().isEmpty()) {
            session.setAttribute(ATT_SESSION_USER, user);
            response.sendRedirect(request.getContextPath());
        } else {
            session.setAttribute(ATT_SESSION_USER, null);
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_USER, user);
            request.getRequestDispatcher(SIGNIN_VIEW).forward(request, response);
        }
    }
}
