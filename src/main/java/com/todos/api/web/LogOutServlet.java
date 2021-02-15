package com.todos.api.web;

import com.todos.api.utility.JsonResponse;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "logout", value = "/logout")
public class LogOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        session.invalidate();
        res.getWriter().println(JsonResponse.createResponse(200,"logged out", null));
    }
}
