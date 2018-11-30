package com.zfsoft.hrm.file.entity;

import java.math.BigDecimal;

/**
 * 
 * @author xiaolu ͼ������
 * 
 */
public class ImageCutPropertyBean {

	private int x;
	private int y;
	private int w;
	private int h;
	private int width;
	private int height;
	private String oldImgPath;
	private String oldImgExt;
	private String oldImgRoot;

	public int getX() {
		return x;
	}

	public void setX(String x) {
		this.x = obtainImageSize(x);
	}

	public int getY() {
		return y;
	}

	public void setY(String y) {
		this.y = obtainImageSize(y);
	}

	public int getW() {
		return w;
	}

	public void setW(String w) {
		this.w = obtainImageSize(w);
	}

	public int getH() {
		return h;
	}

	public void setH(String h) {
		this.h = obtainImageSize(h);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = obtainImageSize(width);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = obtainImageSize(height);
	}

	public String getOldImgPath() {
		return oldImgPath;
	}

	public void setOldImgPath(String oldImgPath) {
		this.oldImgPath = oldImgPath;
	}

	public String getOldImgExt() {
		return oldImgExt;
	}

	public void setOldImgExt(String oldImgExt) {
		this.oldImgExt = oldImgExt;
	}

	public String getOldImgRoot() {
		return oldImgRoot;
	}

	public void setOldImgRoot(String oldImgRoot) {
		this.oldImgRoot = oldImgRoot;
	}

	// ��������
	private int obtainImageSize(String value) {
		return new BigDecimal(value).setScale(0, BigDecimal.ROUND_HALF_UP)
				.intValue();
	}

}
