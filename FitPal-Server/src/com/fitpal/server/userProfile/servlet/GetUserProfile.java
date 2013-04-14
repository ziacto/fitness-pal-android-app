package com.fitpal.server.userProfile.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitpal.server.userProfile.db.UserProfileDAO;
import com.fitpal.server.userProfile.entity.UserProfile;
import com.google.gson.Gson;

public class GetUserProfile extends HttpServlet{

	private static final long serialVersionUID = -1451532615304774587L;
	private static Gson gson = new Gson();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String userName = req.getParameter("username");
		UserProfile userProfile = new UserProfileDAO().getUserProfile(userName);
		if(userProfile == null)
			userProfile = new UserProfile();

		res.setContentType("text/plain");
		res.getWriter().println(gson.toJson(userProfile));

	}
}
