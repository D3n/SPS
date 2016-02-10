package com.supprojectstarter.filter;

import com.supprojectstarter.bean.User;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    public static final String ATT_SESSION = "sessionUser";

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession session = req.getSession();
            User user;
            user = (User) session.getAttribute(ATT_SESSION);
            if (user == null || !user.getGroup().getLabel().equals("Admin")) {
                res.sendRedirect(req.getContextPath() + "/accessRefused");
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
