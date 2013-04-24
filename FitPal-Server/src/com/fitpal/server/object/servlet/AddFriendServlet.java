package com.fitpal.server.object.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitpal.server.object.db.UserProfileDAO;

public class AddFriendServlet extends HttpServlet {

	private static final long serialVersionUID = -1451532615304774587L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String userName = req.getParameter("user");
		String friend = req.getParameter("friend");
		UserProfileDAO dao = new UserProfileDAO();
		dao.addFriend(userName, friend);
		dao.addFriend(friend, userName);
		res.setContentType("text/plain");
	}
}
