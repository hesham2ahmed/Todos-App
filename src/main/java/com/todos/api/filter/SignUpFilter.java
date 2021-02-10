package com.todos.api.filter;

import com.todos.api.utility.JsonResponse;
import com.todos.api.utility.Validation;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SignUpFilter")
public class SignUpFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        if(Validation.isEmailValid(email)
        && Validation.isNameValid(first_name)
        && Validation.isNameValid(last_name)
        && Validation.isPasswordValid(password)){
            chain.doFilter(request, response);
        }
        else
            response.getWriter().println(JsonResponse.createResponse(400, "bad request"));
    }
}
