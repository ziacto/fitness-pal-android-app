package com.fitpal.server.object.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitpal.server.object.db.RoutineDAO;

public class DeleteRoutine extends HttpServlet{

	private static final long serialVersionUID = -1451532615304774587L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String routineId = req.getParameter("id");
		(new RoutineDAO()).deleteRoutine(Integer.parseInt(routineId));
	}
}
