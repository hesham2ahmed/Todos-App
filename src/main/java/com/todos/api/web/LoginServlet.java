package com.todos.api.web;

import com.todos.api.dao.impl.PersonDAO;
import com.todos.api.service.PersonService;
import com.todos.api.utility.JsonResponse;
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
        HttpSession session = null;
        String email = (String) request.getAttribute("email");
        String password = (String) request.getAttribute("password");
        JSONObject jsonObject = null;
        boolean loggedIn = false;

        try {
            jsonObject = personService.logIn(email, password);
            if(jsonObject != null) {
                session = setSession(jsonObject, request);
                jsonObject.put("maxInteractiveTime", session.getMaxInactiveInterval());
                System.out.println(session.getMaxInactiveInterval());
                loggedIn = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(loggedIn) {
            response.getWriter().println(JsonResponse.createResponse(200, "ok", jsonObject));
        }
        else
            response.getWriter().println(JsonResponse.createResponse(404, "wrong email or password", null));
    }

    private HttpSession setSession(JSONObject jsonObject, HttpServletRequest request) throws JSONException {
        HttpSession session = request.getSession();
        session.setAttribute("person_id", jsonObject.get("person_id"));
        session.setAttribute("person_email", jsonObject.get("person_email"));
        session.setAttribute("person_first_name", jsonObject.get("person_firstname"));
        session.setAttribute("person_last_name", jsonObject.get("person_lastname"));
        return session;
    }

}
