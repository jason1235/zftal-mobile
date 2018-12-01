package com.zfsoft.hrm.file.util;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.zfsoft.common.system.BaseHolder;

/**
 * @author Bashan
 * FFMPEG homepage http://ffmpeg.org/about.html
 * By Google Get first and last thumb of a video using Java and FFMpeg
 * From http://www.codereye.com/2010/05/get-first-and-last-thumb-of-video-using.html
 */

public class VideoThumbTaker
{
    protected static String ffmpegApp=BaseHolder.getPropertiesValue("ffmpegApp");;

    public VideoThumbTaker(String ffmpegApp)
    {
        this.ffmpegApp = ffmpegApp;
    }

    @SuppressWarnings("unused")
    /****
     * ��ȡָ��ʱ���ڵ�ͼƬ
     * @param videoFilename:��Ƶ·��
     * @param thumbFilename:ͼƬ����·��
     * @param width:ͼƬ��
     * @param height:ͼƬ��
     * @param hour:ָ��ʱ
     * @param min:ָ����
     * @param sec:ָ����
     * @throws IOException
     * @throws InterruptedException
     */
    public static void getThumb(String videoFilename, String thumbFilename, int width,
            int height, int hour, int min, float sec) throws IOException,
            InterruptedException
    {
    	ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y",
                "-i", videoFilename, "-vframes", "1", "-ss", hour + ":" + min
                        + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height,
                "-an", thumbFilename);

        Process process = processBuilder.start();

        InputStream stderr = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null)
            ;
        process.waitFor();
        
        if(br != null)
            br.close();
        if(isr != null)
            isr.close();
        if(stderr != null)
            stderr.close();
    }

    public static void main(String[] args)
    {
        VideoThumbTaker videoThumbTaker = new VideoThumbTaker("E:/setup/transformTools/ffmpeg/ffmpeg");
        try
        {
            videoThumbTaker.getThumb("E:\\12565422101689d9c1144560.mp4", "E:\\thumbTest.png",    800, 600, 0, 0, 1);
            System.out.println("over");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        
        
        
//        System.out.println("===========os.name:"+System.getProperties().getProperty("os.name"));  
//        System.out.println("===========file.separator:"+System.getProperties().getProperty("file.separator"));  
    }
}