package com.fitpal.server.object.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitpal.server.object.db.RoutineDAO;
import com.fitpal.server.object.entity.Task;
import com.fitpal.server.object.entity.TaskResponse;
import com.google.gson.Gson;

public class GetTasks extends HttpServlet {

	private static final long serialVersionUID = -1451532615304774587L;
	private static Gson gson = new Gson();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String userName = req.getParameter("username");
		String date = req.getParameter("date");
		System.out.println("userName: " + userName);
		System.out.println("date: " + date);
		List<Task> tasks = (new RoutineDAO()).getTasksByDate(userName, date);
		TaskResponse response = new TaskResponse();
		response.list = tasks;
		res.setContentType("text/plain");
		System.out.println("JSON res: " + gson.toJson(response));
		res.getWriter().println(gson.toJson(response));
	}
}
