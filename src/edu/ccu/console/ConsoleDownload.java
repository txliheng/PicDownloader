package edu.ccu.console;

import java.net.URLEncoder;
import edu.ccu.utils.DownloadPic;

public class ConsoleDownload {

	/**
	 * ͼƬ��������ʾ����(����̨)
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			String keyWord="��Ů";
			//��������ͼƬ
			String keyWord="��ӱ";
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