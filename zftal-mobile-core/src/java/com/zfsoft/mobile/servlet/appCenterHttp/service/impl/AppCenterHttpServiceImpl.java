/**
 * 
 */
package com.zfsoft.mobile.servlet.appCenterHttp.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.common.Config;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.MD5Util.MD5Util;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.servlet.appCenterHttp.dao.daointerface.IAppCenterHttpDao;
import com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService;
import com.zfsoft.mobile.webservices.entity.MemoCatalog;
import com.zfsoft.mobile.webservices.entity.MemoDB;
import com.zfsoft.mobile.webservices.entity.MemoPictureEntity;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.DBEncrypt;

/**
 * @author zhangxu
 *
 */
public class AppCenterHttpServiceImpl implements IAppCenterHttpService {
	private static Logger logger = Logger.getLogger(AppCenterHttpServiceImpl.class);
	private final String infromation=Config.getString("mobile.infromation");
	private static final String KEY_PROCODE = "${procode}";
	private static final String KEY_CHOICE = "${choice}";
	private static final String KEY_USERID = "${uid}";
	private static final String KEY_PUBLICKEY = "${key}";
	
	private IAppCenterHttpDao appCenterHttpDao;

	public void setAppCenterHttpDao(IAppCenterHttpDao appCenterHttpDao) {
		this.appCenterHttpDao = appCenterHttpDao;
	}

	public IAppCenterHttpDao getAppCenterHttpDao() {
		return appCenterHttpDao;
	}
	

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#getAllXtYwByUser(java.lang.String)
	 */
	@Override
	public List<Business> getAllXtYwByUser(String userId) {
		return appCenterHttpDao.getAllXtYwByUser(userId);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#getAllServiceByUser(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ServiceManager> getAllServiceByUser(String userId, String ywId) {
		Map<String, Object> param = new HashMap<String, Object>();
        List<ServiceManager> ret = null;
        param.put("userId", userId);
        if (!StringUtils.isEmpty(ywId)) {
            param.put("ywId", ywId);
        }
        int size = appCenterHttpDao.getAllServiceByUserCnt(param);
        if (size > 0) {
            param.put("startRow", 1);
            param.put("endRow", size);
            ret = appCenterHttpDao.getAllServiceByUser(param);
        } else {
            ret = new ArrayList<ServiceManager>();
        }

        return getOpenUrl(ret, userId);
	}
	
	
	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#getAllServiceByUser(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ServiceManager> getSearchedServiceList(String userId, String fwmc) {
		Map<String, Object> param = new HashMap<String, Object>();
        List<ServiceManager> ret = null;
        param.put("userId", userId);
        if (!StringUtils.isEmpty(fwmc)) {
            param.put("fwmc", fwmc);
        }
        ret = appCenterHttpDao.getSearchedServiceList(param);
       
        if(ret == null){
        	ret = new ArrayList<ServiceManager>();
        }
        return getOpenUrl(ret, userId);
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
	    			if(!StringUtil.isEmpty(s.getIsSignal()) && s.getIsSignal().equals("1")){
	    				s.setSignalUrl(fwdz);
	    			}
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
	    			if(!StringUtil.isEmpty(s.getIsSignal()) && s.getIsSignal().equals("1")){
	    				s.setSignalUrl(fwdz);
	    			}
	    			logger.error("fwbm:"+s.getClassFwbm()+",url="+s.getAppUrl());
	    		}
	    		
	    		boolean check = false;
	    		try {
	    			check = checkImage(s.getClassFwtbdz(), s.getClassFwtbid());
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
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
    
    @Override
	public boolean checkImage(String url, String imageId) throws IOException {
		// TODO Auto-generated method stub
		
		if(StringUtils.isEmpty(url) && StringUtils.isEmpty(imageId))
			return false;
		if(StringUtils.isEmpty(url)){
			if(!StringUtils.isEmpty(imageId)){
				return doImage(url,imageId);
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
						return doImage(url,imageId);
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
		ImageDB image = appCenterHttpDao.findImageById(imageId);
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

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#insertMemo(com.zfsoft.mobile.webservices.entity.MemoDB)
	 */
	@Override
	public void insertMemo(MemoDB memoEntity) {
		appCenterHttpDao.insertMemo(memoEntity);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#updateMemo(com.zfsoft.mobile.webservices.entity.MemoDB)
	 */
	@Override
	public void updateMemo(MemoDB memoEntity) {
		appCenterHttpDao.updateMemo(memoEntity);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#getMyMemoList(com.zfsoft.mobile.webservices.entity.MemoDB)
	 */
	@Override
	public PageList<MemoDB> getMyMemoList(MemoDB query) {
		PageList<MemoDB> pageList = new PageList<MemoDB>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(appCenterHttpDao.getMyMemoListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<MemoDB> list = appCenterHttpDao.getMyMemoList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#getMemoPicture(com.zfsoft.mobile.webservices.entity.MemoPictureEntity)
	 */
	@Override
	public int getMemoPicture(MemoPictureEntity pictureEnity) {
		return appCenterHttpDao.getMemoPicture(pictureEnity);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#insertMemoPicture(com.zfsoft.mobile.webservices.entity.MemoPictureEntity)
	 */
	@Override
	public void insertMemoPicture(MemoPictureEntity pictureEnity) {
		appCenterHttpDao.insertMemoPicture(pictureEnity);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#deleteMyMemo(com.zfsoft.mobile.webservices.entity.MemoDB)
	 */
	@Override
	public void deleteMyMemo(MemoDB memoQuery) {
		File file;
		String filePath;
		HttpServletRequest request = ServletActionContext.getRequest();
		appCenterHttpDao.deleteMyMemo(memoQuery);
		filePath = request.getSession().getServletContext().getRealPath("/") + "memoFile/"+ memoQuery.getMemoFileName(); 
		file = new File(filePath);  
		// 路径为文件且不为空则进行删除  
		if (file.isFile() && file.exists()) {  
			file.delete();  
		} 
		
		List<MemoPictureEntity> memoPictureList = appCenterHttpDao.getMemoPictureList(memoQuery.getMemoFileName());
		if(memoPictureList != null && memoPictureList.size() > 0){
    		for (int i = 0; i < memoPictureList.size(); i++) {
    			filePath = request.getSession().getServletContext().getRealPath("/") +memoPictureList.get(i).getPicturePath(); 
    			file = new File(filePath);  
    			// 路径为文件且不为空则进行删除  
    			if (file.isFile() && file.exists()) {  
    				file.delete();  
    			}  
    		}
		}
		appCenterHttpDao.deleteMyMemoPicture(memoQuery.getMemoFileName());
	}
	
	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#insertmemoCatalogByUser(com.zfsoft.mobile.webservices.entity.MemoCatalog)
	 */
	@Override
	public void insertmemoCatalogByUser(MemoCatalog memoCatalog) {
		appCenterHttpDao.insertmemoCatalogByUser(memoCatalog);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#getMemoCatalogList(java.lang.String)
	 */
	@Override
	public List<MemoCatalog> getMemoCatalogList(String userId) {
		return appCenterHttpDao.getMemoCatalogList(userId);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#deleteAllmemoCatalogByUser(java.lang.String)
	 */
	@Override
	public void deleteAllmemoCatalogByUser(String userId) {
		appCenterHttpDao.deleteAllmemoCatalogByUser(userId);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#checkMemoPictureEntity(java.lang.String)
	 */
	@Override
	public void checkMemoPictureEntity(String memoFileName) {
		List<MemoPictureEntity> memoPictureList = appCenterHttpDao.getMemoPictureList(memoFileName);
		if(memoPictureList != null && memoPictureList.size() > 0){
			HttpServletRequest request = ServletActionContext.getRequest();
			String filePath = request.getSession().getServletContext().getRealPath("/") + "memoFile"; 
            filePath = filePath.replace("\\", "/");
            File newFile = new File(filePath);
    		if (!newFile.exists()) {
    			newFile.mkdir();
    		}
    		for (int i = 0; i < memoPictureList.size(); i++) {
    			try {
    			File outFile = new File(filePath, memoPictureList.get(i).getTitle());
    			if (!outFile.exists()) {
						outFile.createNewFile();
    				
    			} else {
    				outFile.delete();
    				outFile.createNewFile();
    			}
    			
    			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
    			fileOutputStream.write(memoPictureList.get(i).getPictureContent(), 0, memoPictureList.get(i).getPictureContent().length);
    			fileOutputStream.close();
    			} catch (IOException e) {
    				logger.error("checkMemoPictureEntity IOException----");
    				logger.error(e, e.fillInStackTrace());
    				continue;
    			}
				
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.appCenterHttp.service.IAppCenterHttpService#checkMemo(com.zfsoft.mobile.webservices.entity.MemoDB)
	 */
	@Override
	public boolean checkMemo(MemoDB memoDB) {
		if(memoDB.getMemoContent() == null || StringUtil.isEmpty(memoDB.getMemoFileName()) 
    			|| StringUtil.isEmpty(memoDB.getMemoPath())){
    		return false;
    	}else{
    		HttpServletRequest request = ServletActionContext.getRequest();
			String tempPath = request.getSession().getServletContext().getRealPath("/") + memoDB.getMemoPath();
			File file = new File(tempPath);
			if(file.exists())
				return true;
			else{
				logger.error("-----checkmemo:,url文件不存在，id为空-------");
				
				String filePath = request.getSession().getServletContext().getRealPath("/") + "memoFile"; 
                filePath = filePath.replace("\\", "/");
                File newFile = new File(filePath);
        		if (!newFile.exists()) {
        			newFile.mkdir();
        		}
        		
        		try {
	                File outFile = new File(filePath, memoDB.getMemoFileName());
	                if (!outFile.exists()) {
							outFile.createNewFile();
						
					} else {
						outFile.delete();
						outFile.createNewFile();
					}
	                
	                FileOutputStream fileOutputStream = new FileOutputStream(outFile);
	                fileOutputStream.write(memoDB.getMemoContent(), 0, memoDB.getMemoContent().length);
	                fileOutputStream.close();
	                return true;
        		} catch (IOException e) {
					e.printStackTrace();
				}
			}
    	}
    	return false;
	}

}
