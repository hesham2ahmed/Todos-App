package com.todos.api.filter;

import com.todos.api.utility.JsonResponse;
import com.todos.api.utility.Util;
import com.todos.api.utility.Validation;
import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "SignUpFilter")
public class SignUpFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request = Util.readDataFromRequest(request);
        String email = ((String) request.getAttribute("email")).toLowerCase();
        String password = (String) request.getAttribute("password");
        String first_name = (String) request.getAttribute("firstname");
        String last_name = (String) request.getAttribute("lastname");

        if(Validation.isEmailValid(email)
        && Validation.isNameValid(first_name)
        && Validation.isNameValid(last_name)
        && Validation.isPasswordValid(password)){
            chain.doFilter(request, response);
        }
        else
            response.getWriter().println(JsonResponse.createResponse(400, "validation error", null));
    }

}
