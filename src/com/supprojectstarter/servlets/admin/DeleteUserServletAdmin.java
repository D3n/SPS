package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.User;
import com.supprojectstarter.dao.DAOUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServletAdmin extends HttpServlet {
    public static final String SERVLET_MNG_USERS = "/admin/manageUsers";
    public static final String ATT_ID = "mailAddress";
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = (String) request.getParameter(ATT_ID);
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + SERVLET_MNG_USERS);
        } else {
            User user = DAOUser.getInstance().findByEmail(idParam);
            if (user != null) {
                DAOUser.getInstance().delete(user);
                response.sendRedirect(request.getContextPath() + SERVLET_MNG_USERS);
            } else {
                response.sendRedirect(request.getContextPath() + SERVLET_MNG_USERS);
            }
        }
    }
}
