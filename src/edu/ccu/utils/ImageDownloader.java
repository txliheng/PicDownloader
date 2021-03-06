package edu.ccu.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageDownloader {
	private HttpSession session;
	public ImageDownloader(HttpSession session) {
		this.session = session;
	}
	public void sendHttpRequest2( String path)
	{
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.connect();
			if(conn.getResponseCode() == 200){
				InputStream in = conn.getInputStream();
				StringBuffer sb = getJsonString(in);
				in.close();
				conn.disconnect();
				downloadPics(sb);
				
			}else{
				throw new Exception("返回错误代码："+conn.getResponseCode()+"\t返回错误信息："+conn.getResponseMessage());
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	/**
	 * 返回Json字符串
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private StringBuffer getJsonString(InputStream in)
			throws IOException, UnsupportedEncodingException {
		byte[] buffer=new byte[1024];
		StringBuffer sb=new StringBuffer();
		int len = 0;
		while((len = in.read(buffer))!=-1){
			//如果返回的不是二进制数据，比如json,html等,需要转utf-8,否则中文会乱码
			sb.append(new String(buffer,0,len,"utf-8"));
		}
		return sb;
	}


	/**
	 * 下载所有图片
	 * @param sb
	 * @throws JSONException
	 */
	private void downloadPics( StringBuffer sb) throws JSONException
	{
		JSONObject jsonObj = new JSONObject(sb.toString());
		JSONArray jsonArray = jsonObj.getJSONArray("imgs");
		for(int i = 0;i < jsonArray.length();i++){
			String objUrl = jsonArray.getJSONObject(i).getString("objURL");
			try {
				sendHttpRequest(objUrl);
				session.setAttribute("downloadImageNo",i+1);
				session.setAttribute("imageUrl", "Sucess--下载"+objUrl+"完成");
				//System.out.println("Sucess--下载"+objUrl+"完成");
				//System.out.println(session.getId());
			} catch (Exception e) {
				session.setAttribute("imageUrl", e.getMessage());
				System.out.println(e.getMessage());
			}

		}
	}
	private void sendHttpRequest(String objUrl)
			
	{

		try {
			URL url = new URL(objUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.connect();
			if(conn.getResponseCode() == 200){
				//int imageSize = conn.getContentLength();
				InputStream in = conn.getInputStream();
				downloadPic(objUrl, in);
				in.close();
				conn.disconnect();
			}else{
				throw new Exception("Error:--下载"+objUrl+"失败，错误码："+conn.getResponseCode());
			}			
		} catch (Exception e) {
			session.setAttribute("imageUrl","Error:--下载"+objUrl+"失败，"+e.getMessage());
			System.out.println("Error:--下载"+objUrl+"失败，"+e.getMessage());
		}

	}

	/**
	 * 下载一张图片
	 * @param objUrl
	 * @param in
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void downloadPic(String objUrl, InputStream in)
			throws FileNotFoundException, IOException {
		byte[] buffer;
		int len;
		String fileName = "".equals(getFileName(objUrl)) ? "index.html":getFileName(objUrl);
		OutputStream out = new FileOutputStream(new File("d:\\"+fileName));
		buffer=new byte[1024];
		len = 0;
		while((len = in.read(buffer))!=-1){
			//如果返回的是二进制数据，比如图片，则一定不要转码，否则无法显示图片
			out.write(buffer,0,len);
		}
		out.flush();
		out.close();
	}
	/**
	 * 获取文件名称
	 * @param path 下载路径
	 * @return
	 */
	private String getFileName(String path){
		return path.substring(path.lastIndexOf("/")+1);
	}
}
