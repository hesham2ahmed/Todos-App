package com.todos.api.web;

import com.todos.api.dao.impl.PersonDAO;
import com.todos.api.model.Person;
import com.todos.api.service.PersonService;
import com.todos.api.utility.PassAuth;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = (Connection) request.getServletContext().getAttribute("dbConnection");
        PersonService personService = new PersonService(PersonDAO.createIns(connection), PassAuth.createIns());
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean loggedIn = false;
        try {
            JSONObject jsonObject = personService.logIn(email, password);
            if(jsonObject != null) {
                HttpSession session = setSession(jsonObject, request);
                loggedIn = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().println(loggedIn);
    }

    private HttpSession setSession(JSONObject jsonObject, HttpServletRequest request) throws JSONException {
        HttpSession session = request.getSession();
        session.setAttribute("person_id", jsonObject.get("person_id"));
        session.setAttribute("person_email", jsonObject.get("person_email"));
        session.setAttribute("person_first_name", jsonObject.get("person_first_name"));
        session.setAttribute("person_last_name", jsonObject.get("person_last_name"));
        return session;
    }

}
