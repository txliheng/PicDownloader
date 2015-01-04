package edu.ccu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

//import edu.ccu.utils.DownloadPic;
import edu.ccu.utils.ImageDownloader;

/**
 * Servlet implementation class Download
 */
@WebServlet(description = "下载图片", urlPatterns = { "/Download" })
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	private DownloadTask downloadTask;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();

		session = request.getSession();
		String pagenow = request.getParameter("pagenow");
		int pageNow=1;// 默认下载第1页
		if(!"".equals(pagenow)&&pagenow!=null){
			pageNow = Integer.parseInt(pagenow);
		}
		
		String keyword = request.getParameter("keyword");
		/*
		 * 如果是get方式提交，需要转码；如果是post方式提交则不需要转码
		 */
		//keyword = new String(keyword.getBytes("ISO8859-1"),"utf-8");
		//System.out.println(keyword);
		//System.out.println(URLEncoder.encode(keyword, "utf-8"));
		try {
			if(!"".equals(keyword)&&keyword!=null){
				int pageSize = 60;//每页默认60张图
				String path="http://image.baidu.com/i?tn=resultjsonavatarnew&ie=utf-8&word="+URLEncoder.encode(keyword, "utf-8")+"&cg=star&pn="+pageSize*pageNow+"&rn="+pageSize+"&z=&fr=&width=&height=&lm=-1&ic=0&s=0/";
				downloadTask=new DownloadTask(path);
				new Thread(downloadTask).start();
				//DownloadPic.sendHttpRequest2(path);
				//new downloadThread(path).start();
				//request.setAttribute("message", "下载中...");
				//request.getRequestDispatcher("/WEB-INF/view/downloading.jsp").forward(request, response);
				return;
			}else{
				out.print("明星名字不能为空");
				//request.setAttribute("message", "明星名字不能为空");
				//request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);

			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private final class DownloadTask implements Runnable {

		private String path;
	
		public DownloadTask(String path){
			this.path = path;
		}
		
		public void run() {
			try {
				new ImageDownloader(session).sendHttpRequest2(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
