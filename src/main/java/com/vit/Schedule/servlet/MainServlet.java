package com.vit.Schedule.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vit.Schedule.model.Student;

@WebServlet(
		name = "StudentServlet",
		urlPatterns = "/info.jsp")
public class MainServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Student student = new Student("David", "Economics");
		request.setAttribute("student", student);
		RequestDispatcher dispatcher = request.getRequestDispatcher(
		          "/views/student.jsp");
        dispatcher.forward(request, response);
	}
}
