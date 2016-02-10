package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.User;
import com.supprojectstarter.forms.SigninForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/createUser")
public class AddUserServletAdmin extends HttpServlet {
    public static final String VIEW_ADD_USER = "/WEB-INF/views/admin/addUser.jsp";
    public static final String SERVLET_MNG_USERS = "/admin/manageUsers";
    public static final String ATT_USER = "user";
    public static final String ATT_FORM = "form";
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_ADD_USER).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SigninForm form = new SigninForm();
        User user = form.registerUser(request);
        if (form.getErrors().isEmpty()) {
            response.sendRedirect(request.getContextPath() + SERVLET_MNG_USERS);
        } else {
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_USER, user);
            request.getRequestDispatcher(VIEW_ADD_USER).forward(request, response);
        }
    }
}
