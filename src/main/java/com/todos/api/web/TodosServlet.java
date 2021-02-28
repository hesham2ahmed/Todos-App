package com.todos.api.web;

import com.todos.api.model.Task;
import com.todos.api.service.PersonService;
import com.todos.api.utility.JsonResponse;

import com.todos.api.utility.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

import java.util.List;

@WebServlet(name = "Todos", value = "/todos")
public class TodosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = (Connection) getServletContext().getAttribute("dbConnection");;
        PersonService personService = new PersonService(connection);
        long person_id = Long.parseLong(request.getParameter("id"));
        List<JSONObject> tasks = null;
        JSONArray jsonTasks = null;
        JSONObject jsonObject = null;
        try {
            tasks = personService.getTasks(person_id);
            jsonTasks = new JSONArray(tasks);
            jsonObject = new JSONObject();
            jsonObject.put("tasks", jsonTasks);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().println(JsonResponse.createResponse(200, "ok", jsonObject));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = (Connection) getServletContext().getAttribute("dbConnection");;
        PersonService personService = new PersonService(connection);
        Util.readDataFromRequest(request);
        Integer person_id = (Integer) request.getAttribute("person_id");
        String name = (String) request.getAttribute("name");
        String note = (String) request.getAttribute("note");
        Long created_date = (Long) request.getAttribute("created_date");
        boolean is_done = (boolean) request.getAttribute("is_done");
        Task task = new Task(person_id,note,name, new Date(created_date), new Date(created_date), is_done);
        long id = personService.addTask(task);
        JSONObject jsonObject = null;
        if(id > -1) {
            try {
                jsonObject = new JSONObject();
                jsonObject.put("task_id", id);
                jsonObject.put("task_person_id", 31);
                jsonObject.put("task_name", name);
                jsonObject.put("task_note", note);
                jsonObject.put("task_created_date", created_date);
                jsonObject.put("task_due_date", null);
                jsonObject.put("task_is_done", null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            response.getWriter().println(JsonResponse.createResponse(200, "ok", jsonObject));
        }
        else
            response.getWriter().println(JsonResponse.createResponse(400, "failed", null));
    }
}
