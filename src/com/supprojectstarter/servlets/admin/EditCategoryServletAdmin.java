package com.supprojectstarter.servlets.admin;

import com.supprojectstarter.bean.Category;
import com.supprojectstarter.dao.DAOCategory;
import com.supprojectstarter.forms.EditCategoryAdminForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/editCategory")
public class EditCategoryServletAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String SERVLET_MNG_CATEGORIES = "/admin/manageCategories";
    private static final String ATT_ID = "id";
    private static final String ATT_MODFORM = "modForm";
    private static final String VIEW_CATEGORIES = "/WEB-INF/views/admin/manageCategories.jsp";
    private static final String ATT_CATEGORY = "category";
    private static final String ATT_CATEGORIES = "categories";
    private static final String ATT_FORM = "form";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = (String) request.getParameter(ATT_ID);
        int id = -1;
        List<Category> listCategories = DAOCategory.getInstance().findAll();
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
                    request.setAttribute(ATT_MODFORM, "edit");
                    request.setAttribute(ATT_CATEGORY, category);
                    request.setAttribute(ATT_CATEGORIES, listCategories);
                    request.getRequestDispatcher(VIEW_CATEGORIES).forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + SERVLET_MNG_CATEGORIES);
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EditCategoryAdminForm form = new EditCategoryAdminForm();
        Category category = form.editCategory(request);
        List<Category> listCategories = DAOCategory.getInstance().findAll();
        if (form.getErrors().isEmpty()) {
            response.sendRedirect(request.getContextPath() + SERVLET_MNG_CATEGORIES);
        } else {
            request.setAttribute(ATT_MODFORM, "edit");
            request.setAttribute(ATT_CATEGORY, category);
            request.setAttribute(ATT_CATEGORIES, listCategories);
            request.setAttribute(ATT_FORM, form);
            request.getRequestDispatcher(VIEW_CATEGORIES).forward(request, response);
        }
    }
}
