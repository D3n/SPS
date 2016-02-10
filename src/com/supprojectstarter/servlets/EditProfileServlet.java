package com.supprojectstarter.servlets;

import com.supprojectstarter.bean.User;
import com.supprojectstarter.forms.EditProfilForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/editProfile")
public class EditProfileServlet extends HttpServlet {
    public static final String ATT_USER = "user";
    public static final String ATT_SESSION = "sessionUser";
    public static final String ATT_FORM = "form";
    public static final String VIEW_EDIT_PROFILE = "/WEB-INF/views/editProfile.jsp";
    public static final String VIEW_SHOW_PROFILE = "/user/showProfile";
    private static final long serialVersionUID = 1L;

    public EditProfileServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User userAuth = (User) request.getSession().getAttribute(ATT_SESSION);
        request.setAttribute(ATT_USER, userAuth);
        request.getRequestDispatcher(VIEW_EDIT_PROFILE).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        EditProfilForm form = new EditProfilForm();
        User user = form.editProfile(request);
        if (form.getErrors().isEmpty()) {
            session.setAttribute(ATT_SESSION, user);
            response.sendRedirect(request.getContextPath() + VIEW_SHOW_PROFILE);
        } else {
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_USER, user);
            request.getRequestDispatcher(VIEW_EDIT_PROFILE).forward(request, response);
        }
    }
}
