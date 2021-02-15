package com.todos.api.filter;

import com.todos.api.utility.JsonResponse;
import com.todos.api.utility.Util;
import com.todos.api.utility.Validation;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.ws.rs.core.Request;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(filterName = "logInValidationFilter", urlPatterns = "/login")
public class LogInFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request = Util.readDataFromRequest(request);
        String email = ((String) request.getAttribute("email")).toLowerCase();
        String password = (String) request.getAttribute("password");
        if(Validation.isEmailValid(email) && Validation.isPasswordValid(password))
        {
            chain.doFilter(request, response);
        }
        else {
            response.getWriter().println(JsonResponse.createResponse(400, "validation Error", null));
        }
    }

}
