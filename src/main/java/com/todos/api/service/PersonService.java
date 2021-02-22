package com.todos.api.service;

import com.todos.api.dao.impl.PersonDAO;
import com.todos.api.dao.impl.TaskDAO;
import com.todos.api.model.Task;
import com.todos.api.utility.PassAuth;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private PersonDAO personDAO;
    private TaskDAO taskDAO;
    private PassAuth passAuth;
    private RegistrationService registrationService;

    public PersonService(Connection connection)
    {
        this.personDAO = PersonDAO.createIns(connection);
        this.taskDAO = TaskDAO.createIns(connection);
        this.passAuth = PassAuth.createIns();
        registrationService = new RegistrationService(personDAO, passAuth);
    }

    public JSONObject logIn(String email, String pass) throws JSONException {
       return registrationService.logIn(email, pass);
    }

    public boolean signUp(String email, String first_name, String last_name, String pass) {
        long id = registrationService.signUp(email, first_name, last_name, pass);
        return true ? id > 0 : false;
    }

    public List<JSONObject> getTasks(long id) throws JSONException{
        List<Task> tasks = taskDAO.getPersonTasks(id);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for(Task task : tasks){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("task_id", task.getId());
            jsonObject.put("task_person_id", task.getPerson_id());
            jsonObject.put("task_name", task.getName());
            jsonObject.put("task_note", task.getNote());
            jsonObject.put("task_created_date", task.getCreated_date());
            jsonObject.put("task_due_date", task.getDue_date());
            jsonObject.put("task_is_done", task.isDone());
            jsonObjectList.add(jsonObject);
        }
        return jsonObjectList;
    }

    public long addTask(Task task){
        return taskDAO.insert(task);
    }
}
