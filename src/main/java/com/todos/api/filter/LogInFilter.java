package com.todos.api.filter;

import com.todos.api.utility.JsonResponse;
import com.todos.api.utility.Validation;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter(filterName = "logInValidationFilter", urlPatterns = "/login")
public class LogInFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if(Validation.isEmailValid(email) && Validation.isPasswordValid(password))
            chain.doFilter(request, response);
        else
            response.getWriter().println(JsonResponse.createResponse(400, "bad request"));
    }
}
