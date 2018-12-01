/**
 * 
 */
package com.zfsoft.mobile.servlet.homePageHttp.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.MD5Util.MD5Util;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.servlet.homePageHttp.action.HomePageHttpAction;
import com.zfsoft.mobile.servlet.homePageHttp.dao.daointerface.IHomePageHttpDao;
import com.zfsoft.mobile.servlet.homePageHttp.service.IHomePageHttpService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.DBEncrypt;

/**
 * @author zhangxu
 *
 */
public class HomePageHttpServiceImpl implements IHomePageHttpService {
	private static Logger logger = Logger.getLogger(HomePageHttpServiceImpl.class);
	private static final String KEY_PROCODE = "${procode}";
	private static final String KEY_CHOICE = "${choice}";
	private static final String KEY_USERID = "${uid}";
	private static final String KEY_PUBLICKEY = "${key}";
	
	private IHomePageHttpDao homePageHttpDao;

	public void setHomePageHttpDao(IHomePageHttpDao homePageHttpDao) {
		this.homePageHttpDao = homePageHttpDao;
	}

	public IHomePageHttpDao getHomePageHttpDao() {
		return homePageHttpDao;
	}
	
	/**
	 * 登录的服务
	 */
	@Override
	public List<ServiceManager> getLoginFw(Map<String, Object> param) {
		return homePageHttpDao.getLoginFw(param);
	}
	
	/**
     * 删除用户所有的常用服务
     */
    @Override
    public void deleteAllFrequentlyService(String userId) {
    	Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        
        homePageHttpDao.deleteAllFrequentlyService(param);
    }
	
	/**
     * 添加常用服务
     */
    @Override
    public void insertFrequentlyService(String userId, String fwBm, boolean isadd) {
    
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("fwBm", fwBm);
        if (isadd) {
            param.put("isadd", "yes");
        } else {
            param.put("isadd", "no");
            param.put("mark", 1);
            homePageHttpDao.downFrequentlyService(param);
        }
        homePageHttpDao.insertFrequentlyService(param);
    }
	
	/**
     * 服务地址模板替换
     */
    @Override
    public List<ServiceManager> getOpenUrl(List<ServiceManager> lst, String userId) {
    	String appUrl = "";
    	String webUrl = "";
    	String xtdz = "";
    	String fwdz = "";
    	String choice = "";
    	String procode = "";
    	String privatekey = "";
    	String pk = "";
    	String publickey = "";
    	DBEncrypt tool = new DBEncrypt();
    	try {
	    	for (ServiceManager s : lst) {
	    		if (ServiceTypeEnum.WEB_SERVICE.getKey().equals(s.getClassFwlx())) {
	    			if (StringUtils.isEmpty(s.getProcode())) {
	    				procode = "";
	    			} else {
	    				procode = s.getProcode();
	    			}
	    			
	    			if (StringUtils.isEmpty(s.getChoice())) {
	    				choice = "";
	    			} else {
	    				choice = s.getChoice();
	    			}
	    			xtdz = s.getXtdz();
	    			if(!StringUtil.isEmpty(xtdz)){
	    				xtdz = xtdz.replace("\\", "/");
	    				if (!xtdz.endsWith("/")) {
	    					xtdz += "/";
	    				}
	    			}else{
	    				xtdz = "";
	    			}
	    			if(choice.equals("20")){
	    				System.out.println("aaa");
	    			}
	    			fwdz = s.getClassFwlj();
	    			fwdz = fwdz.replace("\\", "/");
	    			appUrl = xtdz + fwdz;
	    			
	    			//if(StringUtil.isEmpty(Config.getString("caurl")) || 
	    			//		(!StringUtil.isEmpty(Config.getString("caurl")) && !Config.getString("caurl").equals("yes"))){
		    	    	pk = MobileSystemHolder.getPropertiesValue(s.getXtbm() + "_private_key");
		    	    	if (StringUtils.isEmpty(pk)) {
		    	    		privatekey = "";
		    	    	} else {
		    	    		privatekey = tool.dCode(pk.getBytes());
		    	    	}
		    	        publickey = MD5Util.md5Encode(procode + choice + userId + privatekey);
						appUrl = appUrl.replace(KEY_PROCODE, procode);
						appUrl = appUrl.replace(KEY_CHOICE, choice);
						appUrl = appUrl.replace(KEY_USERID, userId);
						/*LoginModel model = new LoginModel();
						model.setYhm(userId);
						User user = loginService.cxYhxx(model);*/
						/*if(s.getXtbm().equals("jw")){
							if ("teacher".equals(user.getYhlx())) {
								appUrl += "&role=" + "JS";
							} else {
								appUrl += "&role=" + "XS";
							}
						}else{
						}*/
						appUrl = appUrl.replace(KEY_PUBLICKEY, publickey);
	    			//}else{
	    				//appUrl = appUrl + "?login=" + procode;
	    				//if(procode.equals("999")){
	    				//	appUrl = appUrl + "?type=1" + "&choice=" + choice +"&uid=" + userId;
	    				//}
	    			//}
	    			webUrl = appUrl.replace("&", "&amp;");
	    	    	
	    	    	
	    			s.setAppUrl(appUrl);
	    			s.setWebUrl(webUrl);
	    			logger.error("fwbm:"+s.getClassFwbm()+",url="+s.getAppUrl());
	    		}
	    		
	    		boolean check = false;
	    		try {
	    			check = checkImage(s.getClassFwtbdz(), s.getClassFwtbid());
	    		} catch (Exception e) {
	    			logger.error("getOpenUrl2 checkImage进入id验证 err：");
    				logger.error(e, e.fillInStackTrace());
	    		}
	    		if(!check)
	    			s.setClassFwtbdz("upload/default_image.jpg");
	    		
	    	}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return lst;
    }
	
	/**
     * 根据用户取得该用户的常用服务
     */
    @Override
    public List<ServiceManager> getFrequentlyServiceByUser(String userId) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("userId", userId);
    	homePageHttpDao.deleteFrequentlyServiceNotInYhqx(params);
        List<ServiceManager> fs = homePageHttpDao.getFrequentlyServiceByUser(params);
        
        // 初始化
        if (fs == null || fs.size() == 0) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", userId);
            param.put("startRow", 1);
            param.put("endRow", 7);
            
            List<ServiceManager> fsds = homePageHttpDao.getAllServiceByUser(param);
            
            for (ServiceManager fsd : fsds) {
                insertFrequentlyService(userId, fsd.getClassFwbm(), true);
            }
            
            return getOpenUrl(fsds, userId);
        }
        return getOpenUrl(fs, userId);
    }
	
	/**
     * 通用服务服务地址模板替换
     */
    @Override
    public List<ServiceManager> getOpenUrl(List<ServiceManager> lst) {
    	String appUrl = "";
    	String webUrl = "";
    	String xtdz = "";
    	String fwdz = "";
    	try {
	    	for (ServiceManager s : lst) {
	    		if (ServiceTypeEnum.WEB_SERVICE.getKey().equals(s.getClassFwlx())) {
	    			xtdz = s.getXtdz();
	    			if(!StringUtil.isEmpty(xtdz)){
	    				xtdz = xtdz.replace("\\", "/");
	    				if (!xtdz.endsWith("/")) {
	    					xtdz += "/";
	    				}
	    			}else{
	    				xtdz = "";
	    			}
	    			fwdz = s.getClassFwlj();
	    			fwdz = fwdz.replace("\\", "/");
	    			appUrl = xtdz + fwdz;
	    			
	    			webUrl = appUrl.replace("&", "&amp;");
	    	    	
	    	    	
	    			s.setAppUrl(appUrl);
	    			s.setWebUrl(webUrl);
	    		}
	    		
	    		boolean check = false;
	    		try {
	    			check = checkImage(s.getClassFwtbdz(), s.getClassFwtbid());
	    		} catch (Exception e) {
	    			logger.error("getOpenUrl checkImage进入id验证 err：");
    				logger.error(e, e.fillInStackTrace());
	    		}
	    		if(!check)
	    			s.setClassFwtbdz("upload/default_image.jpg");
	    		
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return lst;
    }
	
	@Override
	public List<ServiceManager> getCommonService() {
    	List<ServiceManager> ret = homePageHttpDao.getCommonService();
    	return getOpenUrl(ret);
	}
	
	@Override
	public boolean checkImage(String url, String imageId){
		// TODO Auto-generated method stub
		
		if(StringUtils.isEmpty(url) && StringUtils.isEmpty(imageId))
			return false;
		if(StringUtils.isEmpty(url)){
			if(!StringUtils.isEmpty(imageId)){
				try {
					return doImage(url,imageId);
				} catch (IOException e) {
					logger.error("checkImage进入id验证1 err：");
    				logger.error(e, e.fillInStackTrace());
				}
			}
		}
		if(!StringUtils.isEmpty(url)){
				HttpServletRequest request = ServletActionContext.getRequest();
				String tempPath = request.getSession().getServletContext().getRealPath("/") + url;
				File file = new File(tempPath);
				if(file.exists())
					return true;
				else{
					logger.error("-----checkImage进入id验证-----" +"图标url文件不存在，id为空："+StringUtil.isEmpty(imageId));
					if(!StringUtils.isEmpty(imageId)){
						try {
							return doImage(url,imageId);
						} catch (IOException e) {
							logger.error("checkImage进入id验证2 err：");
            				logger.error(e, e.fillInStackTrace());
						}
					}
				}
		}
		return false;
	}
	
	@Override
	/**
	 * 根据imageID处理图片
	 * @throws IOException 
	 */
	public boolean doImage(String url, String imageId) throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		ImageDB image = homePageHttpDao.findImageById(imageId);
		if(image == null || image.getFileContent() == null) 
			return false;
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image.getFileContent());
		BufferedImage newImage = ImageIO.read(byteArrayInputStream);
		
		String uploadPath = BaseHolder.getPropertiesValue("uploadPath");
		String tempPath1 = null;
		if (StringUtil.isEmpty(url)) {
			tempPath1 = request.getSession().getServletContext().getRealPath("/") + uploadPath;
		} else {
			tempPath1 = request.getSession().getServletContext().getRealPath("/") + url;
		}
		String fileName = image.getFileName();
		//File newFile = new File(tempPath);
		if (tempPath1 == null || tempPath1.isEmpty()) {
            return false;
        }
		File outFile = null;
		if (tempPath1.toLowerCase().endsWith(".png") 
				|| tempPath1.toLowerCase().endsWith(".jpeg") 
				|| tempPath1.toLowerCase().endsWith(".jpg") 
				|| tempPath1.toLowerCase().endsWith(".bmp")
				|| tempPath1.toLowerCase().endsWith(".gif")
				) {
			outFile = new File(tempPath1);
		} else {
			File folder = new File(tempPath1);
			if(!folder.exists() || !folder.isDirectory())
				folder.mkdirs();
			outFile = new File(tempPath1, fileName);
		}
		String path = outFile.getAbsolutePath();
		String dirPath = path.substring(0, path.lastIndexOf(File.separator));
		File dirFile = new File(dirPath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		outFile.createNewFile();
		ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);
		return true;
	}
}
