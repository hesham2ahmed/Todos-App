package com.todos.api.web;

import com.todos.api.service.PersonService;
import com.todos.api.utility.JsonResponse;
import com.todos.api.utility.Util;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "deletetodo", value = "/deletetodo")
public class DeleteTodoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Util.readDataFromRequest(request);
        long task_id = (Integer) request.getAttribute("task_id");
        Connection connection = (Connection) request.getServletContext().getAttribute("dbConnection");
        PersonService personService = new PersonService(connection);

        boolean deleted = personService.deleteTask(task_id);

        if(deleted){
            response.getWriter().println(JsonResponse.createResponse(200,"ok", null));
        }
        else
            response.getWriter().println(JsonResponse.createResponse(400, "failed", null));
    }
}
