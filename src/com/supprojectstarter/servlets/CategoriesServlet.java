package com.supprojectstarter.servlets;

import com.supprojectstarter.bean.Category;
import com.supprojectstarter.dao.DAOCategory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/categories")
public class CategoriesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String ATT_CATEGORIES = "categories";
    private static final String VIEW = "/WEB-INF/views/categories.jsp";
    private static final String ATT_ID = "idCateg";
    private static final String ATT_CATEGORY = "category";
    private static final String ATT_MODSHOW = "modShow";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = DAOCategory.getInstance().findAll();
        request.setAttribute(ATT_CATEGORIES, categories);
        request.setAttribute(ATT_MODSHOW, "all");
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = (String) request.getParameter(ATT_ID);
        int id = -1;
        boolean ok = true;
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/categories");
        } else {
            try {
                id = Integer.parseInt(idParam);
            } catch (Exception e) {
                ok = false;
                e.printStackTrace();
            }
            if (!ok) {
                response.sendRedirect(request.getContextPath() + "/categories");
            } else {
                Category category ;
                category = DAOCategory.getInstance().find(id);
                if (category != null) {
                    request.setAttribute(ATT_CATEGORY, category);
                    List<Category> categories = DAOCategory.getInstance().findAll();
                    request.setAttribute(ATT_CATEGORIES, categories);
                    request.setAttribute(ATT_MODSHOW, "one");
                    request.getRequestDispatcher(VIEW).forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/categories");
                }
            }
        }
    }
}
