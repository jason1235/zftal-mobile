package com.zfsoft.mobile.common.imageServlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zfsoft.mobile.common.entity.ImageCutPropertyBean;


public class ImageCrop extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �û�����������ͼƬ�Ĵ�С
		ImageCutPropertyBean imageCutBean = new ImageCutPropertyBean();
		imageCutBean.setX(request.getParameter("x"));
		imageCutBean.setY(request.getParameter("y"));
		imageCutBean.setW(request.getParameter("w"));
		imageCutBean.setH(request.getParameter("h"));
		// ��ȡԭ��ʾͼƬ·��
		imageCutBean.setOldImgPath(request.getParameter("oldImgPath"));
		// ͼƬ��׺
		imageCutBean.setOldImgRoot(request.getParameter("imgRoot"));
		imageCutBean.setOldImgExt(request.getParameter("imgFileExt"));
		imageCutBean.setWidth(request.getParameter("width"));
		imageCutBean.setHeight(request.getParameter("height"));
		// WEBӦ�ó����·��
		String webAppPath = getServletContext().getRealPath("/");
		/** ͼƬ���:�Ե�ǰ���� */
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String imgFileId = formatter.format(new Date());
		String imgName = imageCutBean.getOldImgRoot() + imgFileId + System.currentTimeMillis() + "."
				+ imageCutBean.getOldImgExt();
		System.out.println("im:" + imgName);
		// ��װͼƬ��ʵ���
		String createImgPath = webAppPath + imgName;
		// ֮ǰ�ϴ���ͼƬ·��
		webAppPath += imageCutBean.getOldImgPath();
		// ���м���ͼƬ����
		ImageCut.abscut(webAppPath, createImgPath,imageCutBean);
		String path = "/personalInfo.jsp?tag=1&oldImgPath=" + imageCutBean.getOldImgPath()
				+ "&imgFileExt=" + imageCutBean.getOldImgExt() + "&imgRoot=" + imageCutBean.getOldImgRoot()
				+ "&imgName=" + imgName + "&height=" + imageCutBean.getHeight() + "&width="
				+ imageCutBean.getWidth();
		request.getRequestDispatcher(path).forward(request, response);
	}
}
