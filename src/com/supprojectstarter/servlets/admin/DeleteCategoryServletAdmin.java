package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.Category;
import com.supprojectstarter.dao.DAOCategory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/deleteCategory")
public class DeleteCategoryServletAdmin extends HttpServlet {
    public static final String SERVLET_MNG_CATEGORIES = "/admin/manageCategories";
    public static final String ATT_ID = "id";
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = (String) request.getParameter(ATT_ID);
        int id = -1;
        boolean ok = true;
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + SERVLET_MNG_CATEGORIES);
        } else {
            try {
                id = Integer.parseInt(idParam);
            } catch (Exception e) {
                ok = false;
                e.printStackTrace();
            }
            if (!ok) {
                response.sendRedirect(request.getContextPath() + SERVLET_MNG_CATEGORIES);
            } else {
                Category category = DAOCategory.getInstance().find(id);
                if (category != null) {
                    DAOCategory.getInstance().delete(category);
                    response.sendRedirect(request.getContextPath() + SERVLET_MNG_CATEGORIES);
                } else {
                    response.sendRedirect(request.getContextPath() + SERVLET_MNG_CATEGORIES);
                }
            }
        }
    }
}
