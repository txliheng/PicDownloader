package edu.ccu.console;

import java.net.URLEncoder;
import edu.ccu.utils.DownloadPic;

public class ConsoleDownload {

	/**
	 * 图片下载器演示程序(控制台)
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			String keyWord="美女";
			//下载明星图片
			String keyWord="杨颖";
			System.out.println(URLEncoder.encode(keyWord, "utf-8"));
			
			int pageNow=2;
//			String path="http://image.baidu.com/i?tn=resultjsonavatarnew&ie=utf-8&word="+URLEncoder.encode(keyWord, "utf-8")+"&cg=girl&pn="+60*pageNow+"&rn=60&z=&fr=&width=&height=&lm=-1&ic=0&s=0/";
			String path="http://image.baidu.com/i?tn=resultjsonavatarnew&ie=utf-8&word="+URLEncoder.encode(keyWord, "utf-8")+"&cg=star&pn="+60*pageNow+"&rn=60&z=&fr=&width=&height=&lm=-1&ic=0&s=0/";
			new DownloadPic().sendHttpRequest2(path);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}


}