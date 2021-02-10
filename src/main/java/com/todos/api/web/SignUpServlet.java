package com.todos.api.web;

import com.todos.api.dao.impl.PersonDAO;
import com.todos.api.service.PersonService;
import com.todos.api.utility.JsonResponse;
import com.todos.api.utility.PassAuth;
import org.json.JSONException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "SignUp", value = "/signup")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Connection connection = (Connection) request.getServletContext().getAttribute("dbConnection");
        PersonService personService = new PersonService(PersonDAO.createIns(connection), PassAuth.createIns());
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        boolean signed_up = false;
        signed_up = personService.signUp(email, first_name, last_name, password);

        if(signed_up)
            response.getWriter().println(JsonResponse.createResponse(200, "ok"));
        else
            response.getWriter().println(JsonResponse.createResponse(400,"bad request"));
    }
}
