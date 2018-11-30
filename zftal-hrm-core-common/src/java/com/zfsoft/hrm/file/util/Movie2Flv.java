package com.zfsoft.hrm.file.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.zfsoft.common.system.BaseHolder;



public class Movie2Flv extends CmdExecutor{
	
	private static Movie2Flv model = new Movie2Flv();
	
	public static Movie2Flv getInstace(){
		return model;
	}


	private String rmvbCmd = null;
	private String movieCmd = null;
	private String flvCmd = null;

	public String getRmvbCmd() {
		return rmvbCmd;
	}

	public void setRmvbCmd(String rmvbCmd) {
		this.rmvbCmd = rmvbCmd;
	}

	public String getMovieCmd() {
		return movieCmd;
	}

	public void setMovieCmd(String movieCmd) {
		this.movieCmd = movieCmd;
	}

	public String getFlvCmd() {
		return flvCmd;
	}

	public void setFlvCmd(String flvCmd) {
		this.flvCmd = flvCmd;
	}
	
	public void tran(String othervedioFileName){
		//String movieCmd= BaseHolder.getPropertiesValue("ffmpegApp");
		Movie2Flv.getInstace().movieCmd=BaseHolder.getPropertiesValue("ffmpegApp");
		String vedio = BaseHolder.getPropertiesValue("vedio", "vedioFile");
		HttpServletRequest request = ServletActionContext.getRequest();
		String vedioPath = request.getSession().getServletContext().getRealPath("/")  + vedio;
		String otherVedioPath = vedioPath +"/"+ othervedioFileName;
		String mp4FileName = othervedioFileName.substring(0, othervedioFileName.indexOf("."));
    	String mp4VedioPath = vedioPath +"/"+ mp4FileName+".mp4";
//    	String otherVedioPath = "D:\\myeclipse_space\\demo_workspace\\ConverVedio\\src\\ffmpeg\\asd.avi";
//    	String mp4VedioPath = "D:\\myeclipse_space\\demo_workspace\\ConverVedio\\src\\ffmpeg\\asd.mp4";
		List<String> command = new ArrayList<String>();
		command.add(Movie2Flv.getInstace().movieCmd);// 浠庨厤缃枃浠堕噷璇诲彇
		//command.add("ffmpeg ");// 浠庨厤缃枃浠堕噷璇诲彇
		command.add("-y");//yhx 2016-1-30 瑕嗙洊鍘熸湁鐨勬枃浠躲�灏卞洜涓鸿繖,Linux涓嬮潰涓�寚鍗℃銆�
		command.add("-i");
		command.add(otherVedioPath);

		command.add("-strict");
		command.add("-2");

		command.add(mp4VedioPath);
		
		try {
			if(!othervedioFileName.endsWith("mp4")){
				Movie2Flv.getInstace().execCommand(command);
			}
			String mp4Path = mp4VedioPath;
			String outPath = vedioPath +"/"+ mp4FileName+".png";
			//String outPath = req.getSession().getServletContext().getRealPath("")+"/target.png";
			VideoThumbTaker.getThumb(mp4Path, outPath, 600, 400, 0, 0, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//String lj = "/zfsoftapp/apache-tomcat-6.0.43_demo/webapps/ConverVedio/asd.avi";
		//ConverterService.getConverterService().startService();
		//ConverterService.getConverterService().addTask(lj);//鍙戦�涓�潯瑙嗛杞爜鍛戒护
	}

	

	
	public static void main(String[] args) {
		Movie2Flv aaa = new Movie2Flv();
		aaa.movieCmd="E:/setup/transformTools/ffmpeg/ffmpeg";
		
		List<String> command = new ArrayList<String>();
		command.add(aaa.movieCmd);// 浠庨厤缃枃浠堕噷璇诲彇
		command.add("-y");//yhx 2016-1-30 瑕嗙洊鍘熸湁鐨勬枃浠躲�灏卞洜涓鸿繖,Linux涓嬮潰涓�寚鍗℃銆�
		command.add("-i");
		command.add("D:\\myeclipse_space\\demo_workspace\\ConverVedio\\src\\ffmpeg\\asd.avi");

		command.add("-strict");
		command.add("-2");

		command.add("D:\\myeclipse_space\\demo_workspace\\ConverVedio\\src\\ffmpeg\\asd.mp4");

		
		
		try {
			aaa.execCommand(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// inputReadThread.setOver(true);// 杞崲瀹岋紝鍋滄娴佺殑澶勭悊
		// errorInputReadThread.setOver(true);
		
	}

	

}
