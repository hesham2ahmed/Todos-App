package com.todos.api.listener;

import com.todos.api.utility.ConnectionManager;
import com.todos.api.utility.PasswordAuthentication;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String dbURL = ctx.getInitParameter("dbURL");
        String user = ctx.getInitParameter("dbUser");
        String pwd = ctx.getInitParameter("dbPassword");
        String dbDriver = ctx.getInitParameter("dbDriver");

        try {
            PasswordAuthentication passwordAuthentication = PasswordAuthentication.createIns();
            ConnectionManager connectionManager = ConnectionManager.createConnectionManager(dbURL, user, pwd, dbDriver);
            ctx.setAttribute("dbConnection", connectionManager.getConnection());
            ctx.setAttribute("passAuth", passwordAuthentication);

            System.out.println("DB Connection initialized successfully.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection connection = (Connection) sce.getServletContext().getAttribute("dbConnection");
        try {
            connection.close();
            System.out.println("Connection Closed!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
