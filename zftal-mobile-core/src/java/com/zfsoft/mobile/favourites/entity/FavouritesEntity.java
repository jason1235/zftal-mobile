package com.zfsoft.mobile.favourites.entity;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.mobile.common.utils.FileUntils;
/**
 * 
 *我的收藏实体类 
 * @author yangbilin
 */
public class FavouritesEntity extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String favourid;		//收藏id
	private String favouritesort; 	//收藏种类    1为文本，2图片，3视频， 4网址，5附件
	private String favouriteavatar;	//被收藏头像
	private String favouritecustom;	//被收藏的人名
	private String favouritetitle;	//收藏内容标题
	private String favouritecontent;//收藏内容,其实根据种类的不同，存储不同的收藏文件路径
	private String favouriteimage;	//收藏图片
	private String favouriteattachmentsort;//收藏附件种类这里单指收藏种类为5的时候
	private String favouriteattachmentsize;//收藏附件大小
	private Date favouritedate;		//收藏时间
	private String favouritedateStr;//时间字符串格式化
	private String userid;
	
	private byte[] attachmentcon;//其实根据种类的不同，存储不同的收藏文件的byte字节
	private String attachmentPath;//附件路径

	private String md5str;//上传内容的MD5值，用于判断收藏的是否是相同内容
	
	
	public FavouritesEntity() {
		super();
	}
	
	
	public FavouritesEntity(String userid, String md5str) {
		super();
		this.userid = userid;
		this.md5str = md5str;
	}



	public String getFavourid() {
		return favourid;
	}
	public void setFavourid(String favourid) {
		this.favourid = favourid;
	}
	public String getFavouritesort() {
		return favouritesort;
	}
	public void setFavouritesort(String favouritesort) {
		this.favouritesort = favouritesort;
	}
	public String getFavouriteavatar() {
		return favouriteavatar;
	}
	public void setFavouriteavatar(String favouriteavatar) {
		this.favouriteavatar = favouriteavatar;
	}
	public String getFavouritecustom() {
		return favouritecustom;
	}
	public void setFavouritecustom(String favouritecustom) {
		this.favouritecustom = favouritecustom;
	}
	public String getFavouritetitle() {
		return favouritetitle;
	}
	public void setFavouritetitle(String favouritetitle) {
		this.favouritetitle = favouritetitle;
	}
	public String getFavouritecontent() {
		return favouritecontent;
	}
	public void setFavouritecontent(String favouritecontent) {
		this.favouritecontent = favouritecontent;
	}
	public String getFavouriteimage() {
		return favouriteimage;
	}
	public void setFavouriteimage(String favouriteimage) {
		this.favouriteimage = favouriteimage;
	}
	
	public String getFavouriteattachmentsort() {
		return favouriteattachmentsort;
	}
	public void setFavouriteattachmentsort(String favouriteattachmentsort) {
		this.favouriteattachmentsort = favouriteattachmentsort;
	}
	
	public String getFavouriteattachmentsize() {
		return favouriteattachmentsize;
	}


	public void setFavouriteattachmentsize(String favouriteattachmentsize) {
		this.favouriteattachmentsize = favouriteattachmentsize;
	}


	public Date getFavouritedate() {
		return favouritedate;
	}
	public void setFavouritedate(Date favouritedate) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		favouritedateStr = format.format(favouritedate);
		this.favouritedate = favouritedate;
	}
	public byte[] getAttachmentcon() {
		return attachmentcon;
	}
	public void setAttachmentcon(byte[] attachmentcon) {
		this.attachmentcon = attachmentcon;
	}
	
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMd5str() {
		return md5str;
	}
	public void setMd5str(String md5str) {
		this.md5str = md5str;
	}


	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath =  attachmentPath;
	}


	public String getAttachmentPath() {
		return attachmentPath;
	}


	public void setFavouritedateStr(String favouritedateStr) {
		this.favouritedateStr = favouritedateStr;
	}


	public String getFavouritedateStr() {
		return favouritedateStr;
	}
	
	
}
