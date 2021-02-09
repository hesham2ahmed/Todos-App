package com.todos.api.service;

public class SignUpService
{
    private LogInService logInService;

    public SignUpService(LogInService logInService){
        this.logInService = logInService;
    }
    public boolean signUserUp(){ return true;}
    private boolean logUserIn(){return true;}
    private boolean registerNewUser(){return true;}
    private boolean isUserLoggedIn(){return true;}

}
