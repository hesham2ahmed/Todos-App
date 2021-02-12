package com.todos.api.filter;

import com.todos.api.utility.JsonResponse;
import com.todos.api.utility.Util;
import com.todos.api.utility.Validation;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

@WebFilter(filterName = "SignUpFilter")
public class SignUpFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request = Util.readDataFromRequest(request);
        String email = (String) request.getAttribute("email");
        String password = (String) request.getAttribute("password");
        String first_name = (String) request.getAttribute("first_name");
        String last_name = (String) request.getAttribute("last_name");
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
