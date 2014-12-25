package edu.ccu.deomo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


import org.json.JSONArray;
import org.json.JSONObject;

public class Demo {

	/**
	 * 图片下载器演示程序
	 * @param args
	 */
	public static void main(String[] args) {
		HttpURLConnection conn=null;
		String objUrl=null;
		try {
//			String keyWord="美女";
			//下载明星图片
			String keyWord="杨颖";
//			System.out.println(URLEncoder.encode(keyWord, "utf-8"));
			
			int pageCount=2;
//			String path="http://image.baidu.com/i?tn=resultjsonavatarnew&ie=utf-8&word="+URLEncoder.encode(keyWord, "utf-8")+"&cg=girl&pn="+60*pageCount+"&rn=60&z=&fr=&width=&height=&lm=-1&ic=0&s=0/";
			String path="http://image.baidu.com/i?tn=resultjsonavatarnew&ie=utf-8&word="+URLEncoder.encode(keyWord, "utf-8")+"&cg=star&pn="+60*pageCount+"&rn=60&z=&fr=&width=&height=&lm=-1&ic=0&s=0/";
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
//          conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//          conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
//          conn.addRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
//          conn.addRequestProperty("Cache-Control", "no-cache");
//          conn.addRequestProperty("Cookie", 
//						"BAIDUPSID=FA36364B074613B8B89015CB30A236DC; H_PS_BABANNER=5; BDREFER=%7Burl%3A%22http%3A//lady.baidu.com/%22%2Cword%3A%22%22%7D; Hm_lvt_9f14aaa038bbba8b12ec2a4a3e51d254=1417105625,1417620091,1417778407,1418132434; BAIDUID=85332BA7CDC05C5876E2942145F425DD:FG=1; B64_BOT=1; __zpspc=188.12.1418551211.1418551211.1%234%7C%7C%7C%7C%7C%23; cflag=65535%3A2; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; H_PS_645EC=04d0aR68NlFrOifWiEH2GWOx%2BRu8F2bMQT332nGgcg6UAA%2Fo72P4uY48jLjk7QkoHYIe; BD_CK_SAM=1; BDRCVFR[NLqAXCw1_h_]=mk3SLVN4HKm; BD_HOME=0; H_PS_PSSID=10255_1467_10167_9451_10496_10511_10645_10218_10686_10356_10666_10095_10657_10442_10403_10360; BD_UPN=12314153");
//          conn.addRequestProperty("Accept-Charset", "UTF-8;");
//          conn.addRequestProperty("User-Agent",
//                  	"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
            conn.connect();
			if(conn.getResponseCode() == 200){
				InputStream in = conn.getInputStream();
				//String fileName = "".equals(getFileName(path)) ? "index.html":getFileName(path);
				//OutputStream out = new FileOutputStream(new File("d:\\"+fileName));
				//OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File("d:\\"+fileName)));
				byte[] buffer=new byte[1024];
				StringBuffer sb=new StringBuffer();
				int len = 0;
				while((len = in.read(buffer))!=-1){
					//如果返回的不是二进制数据，比如json,html等,需要转utf-8,否则中文会乱码
					sb.append(new String(buffer,0,len,"utf-8"));
					//如果返回的是二进制数据，比如图片，则一定不要转码，否则无法显示图片
					//out.write(buffer,0,len);
					//如果返回是json，需要手工转utf-8
					//osw.write(new String(buffer,0,len,"utf-8"));
					
				}
				in.close();
				//out.close();
				conn.disconnect();
				JSONObject jsonObj = new JSONObject(sb.toString());
				JSONArray jsonArray = jsonObj.getJSONArray("imgs");
				for(int i = 0;i < jsonArray.length();i++){
					objUrl = jsonArray.getJSONObject(i).getString("objURL");
					//System.out.println(objUrl);
					url = new URL(objUrl);
					conn = (HttpURLConnection) url.openConnection();
					
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					conn.connect();
					if(conn.getResponseCode() == 200){
						in = conn.getInputStream();
						String fileName = "".equals(getFileName(objUrl)) ? "index.html":getFileName(objUrl);
						OutputStream out = new FileOutputStream(new File("d:\\"+fileName));
						buffer=new byte[1024];
						len = 0;
						while((len = in.read(buffer))!=-1){
							//如果返回的是二进制数据，比如图片，则一定不要转码，否则无法显示图片
							out.write(buffer,0,len);
						}
						out.flush();
						in.close();
						out.close();
						conn.disconnect();
					}else{
						System.out.println("下载"+objUrl+"失败，错误码："+conn.getResponseCode());
						//System.out.println("错误信息："+conn.getResponseMessage());
						
					}
						

				}
				//System.out.println(sb);
				
			}else{
				System.out.println("返回错误代码："+conn.getResponseCode());
				System.out.println("返回错误信息："+conn.getResponseMessage());
			}
			
		} catch (Exception e) {
			
			if("connect timed out".equals(e.getMessage())){
				System.out.println(objUrl+"连接超时了");
			}	
			e.printStackTrace();
		}
		
	}
	private static String getFileName(String path){
		return path.substring(path.lastIndexOf("/")+1);
	}

}
