package com.todos.api.service;

import com.todos.api.dao.impl.PersonDAO;
import com.todos.api.dao.impl.TaskDAO;
import com.todos.api.model.Person;
import com.todos.api.model.Task;

import java.util.List;


public class PersonService {
    private PersonDAO personDAO;
    private Person person;
    private TaskDAO taskDAO;

    public PersonService(Person person, PersonDAO personDAO, TaskDAO taskDAO){
        this.person = person;
        this.taskDAO = taskDAO;
        this.personDAO = personDAO;
    }

    public PersonService(PersonDAO personDAO, TaskDAO taskDAO, long id){
        this.personDAO = personDAO;
        this.taskDAO = taskDAO;
        this.person = loadPersonDate(id);
    }

    public Person getPerson() {
        return person;
    }

    private Person loadPersonDate(long id)
    {
        return personDAO.read(id);
    }

    public List<Task> gePersonTasks(){
        if(person.getTasks() == null)
            this.person.setTasks(taskDAO.getPersonTasks(person.getId()));
        return person.getTasks();
    }
}
