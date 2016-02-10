package com.supprojectstarter.servlets;

import com.supprojectstarter.bean.Contribution;
import com.supprojectstarter.forms.ContributionForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/makeContribution")
public class MakeContributionServlet extends HttpServlet {
    public static final String ATT_CONTRIBUTION = "contribution";
    public static final String ATT_FORM = "form";
    public static final String ATT_PROJECT = "project";
    public static final String VIEW_CURRENT_PROJECT = "/WEB-INF/views/showProject.jsp";
    public static final String SERVLET_VIEW_PROJECT = "/showProject?id=";
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContributionForm form = new ContributionForm();
        Contribution contribution = form.createContribution(request);
        if (form.getErrors().isEmpty()) {
            response.sendRedirect(request.getContextPath() + SERVLET_VIEW_PROJECT + contribution.getProject().getId());
        } else {
            request.setAttribute(ATT_FORM, form);
            request.setAttribute(ATT_CONTRIBUTION, contribution);
            request.setAttribute(ATT_PROJECT, contribution.getProject());
            request.getRequestDispatcher(VIEW_CURRENT_PROJECT).forward(request, response);
        }
    }
}
