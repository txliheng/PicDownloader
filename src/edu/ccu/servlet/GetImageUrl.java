package edu.ccu.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GetImageUrl
 */
@WebServlet("/GetImageUrl")
public class GetImageUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		if(session!=null)
			if(session.getAttribute("imageUrl")!=null){
				String imageUrl = (String) session.getAttribute("imageUrl");
				session.removeAttribute("imageUrl");
				PrintWriter out=response.getWriter();
				out.println(imageUrl);
		}

	}

}
