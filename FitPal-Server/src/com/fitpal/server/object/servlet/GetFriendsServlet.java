package com.fitpal.server.object.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitpal.server.object.db.UserProfileDAO;
import com.fitpal.server.object.entity.FriendsResponse;
import com.google.gson.Gson;

public class GetFriendsServlet extends HttpServlet{

	private static final long serialVersionUID = -1451532615304774587L;
	private static Gson gson = new Gson();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String userName = req.getParameter("username");
		FriendsResponse response = new UserProfileDAO().getFriends(userName);
		res.setContentType("text/plain");
		res.getWriter().println(gson.toJson(response));
	}
}
