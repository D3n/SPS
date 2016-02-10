package com.supprojectstarter.servlets;

import com.supprojectstarter.bean.Category;
import com.supprojectstarter.dao.DAOCategory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/showCategory")
public class ShowCategoryServlet extends HttpServlet {
    public static final long serialVersionUID = 1L;
    private static final String PARAM_CATEGORY_ID = "id";
    private static final String ATT_CATEGORY = "category";
    private static final String VIEW = "/WEB-INF/views/showCategory.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(PARAM_CATEGORY_ID));
        Category category = DAOCategory.getInstance().find(id);
        request.setAttribute(ATT_CATEGORY, category);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }
}
