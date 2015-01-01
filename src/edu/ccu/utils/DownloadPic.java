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
				throw new RuntimeException("���ش�����룺"+conn.getResponseCode()+"\t���ش�����Ϣ��"+conn.getResponseMessage());
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	/**
	 * ����Json�ַ���
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
			//������صĲ��Ƕ��������ݣ�����json,html��,��Ҫתutf-8,�������Ļ�����
			sb.append(new String(buffer,0,len,"utf-8"));
		}
		return sb;
	}


	/**
	 * ��������ͼƬ
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
				System.out.println("Sucess--����"+objUrl+"���");
			} catch (Exception e) {
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
				throw new Exception("Error:--����"+objUrl+"ʧ�ܣ������룺"+conn.getResponseCode());
			}			
		} catch (Exception e) {
			System.out.println("Error:--����"+objUrl+"ʧ�ܣ�"+e.getMessage());
		}

	}

	/**
	 * ����һ��ͼƬ
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
			//������ص��Ƕ��������ݣ�����ͼƬ����һ����Ҫת�룬�����޷���ʾͼƬ
			out.write(buffer,0,len);
		}
		out.flush();
		out.close();
	}
	/**
	 * ��ȡ�ļ�����
	 * @param path ����·��
	 * @return
	 */
	private String getFileName(String path){
		return path.substring(path.lastIndexOf("/")+1);
	}
}
