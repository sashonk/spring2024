package ru.asocial.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class SPAFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //StringUtils.
        //httpServletRequest.getPathInfo()
        if ((httpServletRequest.getRequestURI().contains("/static")
                || httpServletRequest.getRequestURI().contains("/api"))) {

            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            System.out.println("SPA: forwarded");
            httpServletRequest.getRequestDispatcher("/static/index.html").forward(servletRequest, servletResponse);
        }

        System.out.println("SPA filter: " + httpServletRequest.getRequestURI());
    }
}
