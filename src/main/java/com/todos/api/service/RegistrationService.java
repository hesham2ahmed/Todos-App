package com.todos.api.service;

import com.todos.api.dao.impl.PersonDAO;
import com.todos.api.model.Person;
import com.todos.api.utility.PassAuth;
import org.json.JSONException;
import org.json.JSONObject;

public final class RegistrationService {
    private PersonDAO personDAO;
    private PassAuth passAuth;

    public RegistrationService(PersonDAO personDAO, PassAuth passAuth){
        this.personDAO = personDAO;
        this.passAuth = passAuth;
    }

    public long signUp(String email, String first_name, String last_name, String pass)
    {
        return personDAO.insert(new Person(email, first_name, last_name, passAuth.hash(pass.toCharArray())));
    }

    public JSONObject logIn(String email, String pass) throws JSONException {
        JSONObject jsonObject = null;
        Person person = personDAO.read(email);
        if(person != null && authenticate(pass, person.getPassword())){
            jsonObject = getToken(person);
        }
        return jsonObject;
    }


    private boolean authenticate(String pass, String token) {
        return passAuth.authenticate(pass.toCharArray(), token);
    }

    private static JSONObject getToken(Person person) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("person_id", person.getId());
        jsonObject.put("person_email", person.getEmail());
        jsonObject.put("person_firstname", person.getFirst_name());
        jsonObject.put("person_lastname", person.getLast_name());
        return jsonObject;
    }
}
