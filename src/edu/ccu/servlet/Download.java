package edu.ccu.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ccu.utils.DownloadPic;

/**
 * Servlet implementation class Download
 */
@WebServlet(description = "下载图片", urlPatterns = { "/Download" })
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private int pageNow=1;
//	private String keyword=null;
//    public Download(int pageNow,String keyword) {
//       this.pageNow = pageNow;
//       this.keyword = keyword;
//    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pagenow = request.getParameter("pagenow");
		int pageNow=1;
		if(!"".equals(pagenow)&&pagenow!=null){
			pageNow = Integer.parseInt(pagenow);
		}
		//%E6%9D%A8%E9%A2%96
		String keyword = request.getParameter("keyword");
		keyword = new String(keyword.getBytes("ISO8859-1"),"utf-8");
		//System.out.println(keyword);
		//System.out.println(URLEncoder.encode(keyword, "utf-8"));
		try {
			if(!"".equals(keyword)&&keyword!=null){
				String path="http://image.baidu.com/i?tn=resultjsonavatarnew&ie=utf-8&word="+URLEncoder.encode(keyword, "utf-8")+"&cg=star&pn="+60*pageNow+"&rn=60&z=&fr=&width=&height=&lm=-1&ic=0&s=0/";
				
				DownloadPic.sendHttpRequest2(path);
				request.setAttribute("message", "下载成功");
				request.getRequestDispatcher("/WEB-INF/view/success.jsp").forward(request, response);
				return;
			}else{
				request.setAttribute("message", "请输入明星的名字");
				request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);

			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
