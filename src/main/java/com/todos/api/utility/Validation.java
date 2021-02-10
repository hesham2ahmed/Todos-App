package com.todos.api.utility;

public abstract class Validation {

    public static boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    public static boolean isNameValid(String name) {
        return name.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
    }
    public static boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$");
    }
}
