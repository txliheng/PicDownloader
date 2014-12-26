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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DownloadPic {
	public static void sendHttpRequest2( String path)
	{
		try {
			HttpURLConnection conn;
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
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
				throw new RuntimeException("返回错误代码："+conn.getResponseCode()+"\t返回错误信息："+conn.getResponseMessage());
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
	private static StringBuffer getJsonString(InputStream in)
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
	private static void downloadPics( StringBuffer sb) throws JSONException
	{
		JSONObject jsonObj = new JSONObject(sb.toString());
		JSONArray jsonArray = jsonObj.getJSONArray("imgs");
		for(int i = 0;i < jsonArray.length();i++){
			String objUrl = jsonArray.getJSONObject(i).getString("objURL");
			try {
				sendHttpRequest(objUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	private static void sendHttpRequest(String objUrl)
			 {

		try {
			URL url = new URL(objUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.connect();
			if(conn.getResponseCode() == 200){
				InputStream in = conn.getInputStream();
				downloadPic(objUrl, in);
				in.close();
				conn.disconnect();
			}else{
				throw new RuntimeException("下载"+objUrl+"失败，错误码："+conn.getResponseCode());
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 下载一张图片
	 * @param objUrl
	 * @param in
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void downloadPic(String objUrl, InputStream in)
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
	 * 
	 * @param path
	 * @return
	 */
	private static String getFileName(String path){
		return path.substring(path.lastIndexOf("/")+1);
	}
}
