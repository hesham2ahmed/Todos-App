package com.todos.api.filter;

import com.todos.api.utility.JsonResponse;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SessionFilter")
public class SessionFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        String url = httpServletRequest.getServletPath().toString();

        if(url.equals("/login")
                || url.equals("/signup")
                || url.equals("/welcome")
                ||url.equals("/todos")
                ||url.equals("/logout"))
        {
            if(session == null){
                if(url.equals("/todos"))
                    response.getWriter().println(JsonResponse.createResponse(405, "method not allowed", null));
                else
                    chain.doFilter(request, response);
            }
            else{
                if(url.equals("/login") || url.equals("/signup")){
                    response.getWriter().println(JsonResponse.createResponse(405, "method not allowed", null));
                }
                else {
                    chain.doFilter(request, response);
                }
            }
        }
        else
            response.getWriter().println(JsonResponse.createResponse(404, "not found", null));
    }
}
