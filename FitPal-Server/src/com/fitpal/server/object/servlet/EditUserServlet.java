package com.fitpal.server.object.servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitpal.server.object.db.UserProfileDAO;
import com.fitpal.server.object.entity.UserProfile;
import com.fitpal.server.utils.Utils;
import com.google.gson.Gson;

public class EditUserServlet extends HttpServlet {

	private static final long serialVersionUID = -1451532615304774587L;
	private static Gson gson = new Gson();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String jsonData = Utils.convertStreamToString(req.getInputStream());
		UserProfile userProfile = gson.fromJson(jsonData, UserProfile.class);
		try {
			new UserProfileDAO().insertOrEditUser(userProfile, true);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		res.setContentType("text/plain");
		res.getWriter().println(gson.toJson(userProfile));
	}
}
