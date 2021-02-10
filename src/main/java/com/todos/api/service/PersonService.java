package com.todos.api.service;

import com.todos.api.dao.impl.PersonDAO;
import com.todos.api.utility.PassAuth;
import org.json.JSONException;
import org.json.JSONObject;

public class PersonService {
    private PersonDAO personDAO;
    private PassAuth passAuth;
    private RegistrationService registrationService;

    public PersonService(PersonDAO personDAO, PassAuth passAuth)
    {
        this.personDAO = personDAO;
        this.passAuth = passAuth;
        registrationService = new RegistrationService(personDAO, passAuth);
    }

    public JSONObject logIn(String email, String pass) throws JSONException {
       return registrationService.logIn(email, pass);
    }

    public boolean signUp(String email, String first_name, String last_name, String pass) {
        return registrationService.signUp(email, first_name, last_name, pass);
    }


}
