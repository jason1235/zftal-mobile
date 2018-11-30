/**
 * 
 */
package com.zfsoft.mobile.news.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.mobile.common.dao.daointerface.IMobileCommonDao;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.util.base.StringUtil;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-10 上午10:10:09
 */
public class SchoolScenery extends BaseQuery{

	private String sceneryId;//风景id
	private String sceneryName;//风景名称
	private String sceneryCatalogId;//类别id
	private String sceneryPicId;//风景图片id值
	private String sceneryPicUrl;//风景图片url
	private Date createTime; // 创建时间
	private int sortNumber;//排序码
	private String isActive;//是否启用
	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getSceneryName() {
		return sceneryName;
	}
	public void setSceneryName(String sceneryName) {
		this.sceneryName = sceneryName;
	}
	public String getSceneryCatalogId() {
		return sceneryCatalogId;
	}
	public void setSceneryCatalogId(String sceneryCatalogId) {
		this.sceneryCatalogId = sceneryCatalogId;
	}
	public String getSceneryPicId() {
		return sceneryPicId;
	}
	public void setSceneryPicId(String sceneryPicId) {
		this.sceneryPicId = sceneryPicId;
	}
	public String getSceneryPicUrl() {
		return sceneryPicUrl;
	}
	public void setSceneryPicUrl(String sceneryPicUrl) {
		//如果数据库中存在图片而项目下不存在，则生成图片
		String filename = sceneryPicUrl.substring(sceneryPicUrl.indexOf("/images/")+8, sceneryPicUrl.length());
		HttpServletRequest request = ServletActionContext.getRequest();
		String tempPath = request.getSession().getServletContext().getRealPath("/") + "images";
		tempPath = tempPath.replace("\\", "/");
		File outFile = new File(tempPath, filename);
		if (!outFile.exists() && !StringUtil.isEmpty(sceneryPicId)) {
			try {
				outFile.createNewFile();
				IMobileCommonDao mobileCommonDao = (IMobileCommonDao) SpringHolder.getBean("mobileCommonDao");
				ImageDB image = mobileCommonDao.findImageById(sceneryPicId);
				FileOutputStream fileOutputStream = new FileOutputStream(outFile);
				fileOutputStream.write(image.getFileContent(), 0, image.getFileContent().length);
    			fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.sceneryPicUrl = sceneryPicUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getIsActive() {
		return isActive;
	}
	
	
}
