package com.zfsoft.mobile.common.imageServlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String IMGROOT = "/uploads/";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String flag = request.getParameter("flag");
		if(flag.equals("index")){
			request.getRequestDispatcher("pages/mobile/image/index.jsp").forward(request, response);
		}else{
			
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String userWebAppPath = getWebAppPath();
			/** ����Ƿ���ͼƬ�ϴ��ļ��� */
			checkImageDir(userWebAppPath);
			/** ͼƬ�ϴ������·�� */
			String imgUploadPath = null;
			String imgWebAppPath = null;
			/** ͼƬ��׺ */
			String imgFileExt = null;
			/** ͼƬ���:�Ե�ǰ���� */
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String imgFileId = formatter.format(new Date());
			// ͼƬ��ʼ���߶�����
			String width = null;
			String height = null;
			int imgWidth = 0;
			int imgHeight = 0;
			try {
				com.jspsmart.upload.SmartUpload smartUpload = new com.jspsmart.upload.SmartUpload();
				smartUpload.initialize(getServletConfig(), request, response);
				smartUpload.upload();
				com.jspsmart.upload.Request rqest = smartUpload.getRequest();
				// ָ��ͼƬ��Ⱥ͸߶�
				width = rqest.getParameter("width");
				if (null == width) {
					width = "500";
				}
				height = rqest.getParameter("height");
				if (null == height) {
					height = "400";
				}
				imgWidth = Integer.parseInt(width);
				imgHeight = Integer.parseInt(height);
				// �ļ�����
				int fileCounts = smartUpload.getFiles().getCount();
				for (int i = 0; i < fileCounts; i++) {
					com.jspsmart.upload.File myFile = smartUpload.getFiles()
					.getFile(i);
					if (!myFile.isMissing()) {
						imgFileExt = myFile.getFileExt();
						// ��װͼƬ��ʵ���
						imgFileId += i + System.currentTimeMillis() + "."
						+ imgFileExt;
						// ͼƬ���·��
						imgWebAppPath = userWebAppPath + imgFileId;
						// ���ͼƬ�ļ�
						myFile.saveAs(imgWebAppPath);
						// ͼƬ�����·��
						imgUploadPath = IMGROOT + imgFileId;
						// ���ͼƬ��С
						BufferedImage src = ImageIO.read(new File(imgWebAppPath)); // �����ļ�
						int imgSrcWidth = src.getWidth(); 	// �õ�Դͼ��
						int imgSrcHeight = src.getHeight(); // �õ�Դͼ��
						// ����ָ����С
						imgWidth = imgSrcWidth > 400 ? 400 : imgSrcWidth;
						imgHeight = (int) (imgWidth*(float)imgSrcHeight/(float)imgSrcWidth);
						// ����ͼƬ�����ô�С���
						ImageCut.scale(imgWebAppPath, imgWebAppPath, imgWidth,
								imgHeight);
						File f = new File(imgWebAppPath);
						if (f.exists()) {
							System.out.println("����" + imgWidth + "*" + imgHeight
									+ "ͼƬ�ɹ�");
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			String path = "/imgcrop.jsp?tag=0&oldImgPath=" + imgUploadPath
			+ "&imgFileExt=" + imgFileExt + "&imgRoot=" + IMGROOT
			+ "&width=" + imgWidth + "&height=" + imgHeight;
			request.getRequestDispatcher(path).forward(request, response);
		}
	}

	private String getWebAppPath() {
		String webAppPath = this.getServletContext().getRealPath("/");
		String userWebAppPath = webAppPath + IMGROOT;
		return userWebAppPath;
	}

	private void checkImageDir(String userWebAppPath) {
		File file = new File(userWebAppPath);
		if (!file.exists()) {
			file.mkdir();
		}
	}
}