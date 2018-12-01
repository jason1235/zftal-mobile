package com.zfsoft.mobile.favourites.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

public class FavouritesQuery extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String favourid;		//收藏id
	private String favouritesort; 	//收藏种类    1为文本，2图片，3视频， 4网址，5附件
	private String favouriteavatar;	//被收藏头像
	private String favouritecustom;	//被收藏的人名
	private String favouritetitle;	//收藏内容标题
	private String favouritecontent;//收藏内容
	private String favouriteimage;	//收藏图片
	private String imgpath;			//收藏图片的路径
	private String favouritesurl;   //收藏网址
	private String favouriteattachmentsort;//收藏附件种类这里单指收藏种类为5的时候
	private Long favouriteattachmentsize;//收藏附件大小
	private Date favouritedate;		//收藏时间
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
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public String getFavouriteattachmentsort() {
		return favouriteattachmentsort;
	}
	public void setFavouriteattachmentsort(String favouriteattachmentsort) {
		this.favouriteattachmentsort = favouriteattachmentsort;
	}
	public Long getFavouriteattachmentsize() {
		return favouriteattachmentsize;
	}
	public void setFavouriteattachmentsize(Long favouriteattachmentsize) {
		this.favouriteattachmentsize = favouriteattachmentsize;
	}
	public Date getFavouritedate() {
		return favouritedate;
	}
	public void setFavouritedate(Date favouritedate) {
		this.favouritedate = favouritedate;
	}
	public String getFavouritesurl() {
		return favouritesurl;
	}
	public void setFavouritesurl(String favouritesurl) {
		this.favouritesurl = favouritesurl;
	}
	
	

}
