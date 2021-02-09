package com.todos.api.web;

import com.todos.api.dao.impl.PersonDAO;
import com.todos.api.service.PersonService;
import com.todos.api.utility.ConnectionManager;
import com.todos.api.utility.PasswordAuthentication;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(false);
        Connection connection = (Connection) request.getServletContext().getAttribute("dbConnection");
        PasswordAuthentication passAuth = (PasswordAuthentication) request.getServletContext().getAttribute("passAuth");

        if(httpSession == null){
            String email = request.getParameter("email");
            String pass = request.getParameter("pass");
            try {

            }catch (Exception exception){

            }
        }else {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
