package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.Category;
import com.supprojectstarter.dao.DAOCategory;
import com.supprojectstarter.forms.AddCategoryAdminForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manageCategories")
public class ShowManageCategoriesServletAdmin extends HttpServlet {
    public static final String ATT_ID = "id";
    public static final String ATT_PROJECT = "project";
    public static final String ATT_MODFORM = "modForm";
    private static final long serialVersionUID = 1L;
    private static final String SERVLET_MNG_CATEGORIES = "/admin/manageCategories";
    private static final String VIEW_CATEGORIES = "/WEB-INF/views/admin/manageCategories.jsp";
    private static final String ATT_CATEGORY = "category";
    private static final String ATT_CATEGORIES = "categories";
    private static final String ATT_FORM = "form";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> listCategories = DAOCategory.getInstance().findAll();
        request.setAttribute(ATT_CATEGORIES, listCategories);
        request.setAttribute(ATT_MODFORM, "add");
        request.getRequestDispatcher(VIEW_CATEGORIES).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddCategoryAdminForm form = new AddCategoryAdminForm();
        Category category = form.createCategory(request);
        List<Category> listCategories = DAOCategory.getInstance().findAll();
        if (form.getErrors().isEmpty()) {
            response.sendRedirect(request.getContextPath() + SERVLET_MNG_CATEGORIES);
        } else {
            request.setAttribute(ATT_CATEGORIES, listCategories);
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_CATEGORY, category);
            request.getRequestDispatcher(VIEW_CATEGORIES).forward(request, response);
        }
    }
}
