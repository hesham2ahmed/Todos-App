package com.todos.api.service;

import com.todos.api.utility.PasswordAuthentication;
import com.todos.api.utility.Validation;

public final class LogInService {
    PasswordAuthentication passwordAuthentication;

    public LogInService(PasswordAuthentication passwordAuthentication){
        this.passwordAuthentication = passwordAuthentication;
    }

    public boolean logUserIn(long id){
        return true;
    }

    public boolean validate(String email, String pass, String token){
        return  (Validation.isEmailValid(email) && authenticatePass(pass, token));
    }

    private boolean authenticatePass(String pass, String token){
        return passwordAuthentication.authenticate(pass.toCharArray(), token);
    }
}
