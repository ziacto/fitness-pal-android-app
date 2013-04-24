package com.fitpal.server.object.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitpal.server.object.db.RoutineDAO;
import com.fitpal.server.object.entity.Routine;
import com.fitpal.server.object.entity.RoutineResponse;
import com.google.gson.Gson;

public class GetRoutines extends HttpServlet{

	private static final long serialVersionUID = -1451532615304774587L;
	private static Gson gson = new Gson();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String userName = req.getParameter("username");
		List<Routine> routines = (new RoutineDAO()).getRoutines(userName);
		RoutineResponse response = new RoutineResponse();
		response.list = routines;
		res.setContentType("text/plain");
		res.getWriter().println(gson.toJson(response));
	}
}
