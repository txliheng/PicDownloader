package edu.ccu.download;

import edu.ccu.utils.DownloadPic;

public class DownloadThread extends Thread {

	private String path;
	public DownloadThread(String path){
		this.path = path;
	}
	@Override
	public void run() {
		try {
			new DownloadPic().sendHttpRequest2(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
