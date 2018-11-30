package com.zfsoft.mobile.common.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.MD5Util.MD5Util;
import com.zfsoft.mobile.common.dao.daointerface.IMobileCommonDao;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.webservices.entity.CardBusinessEntity;
import com.zfsoft.mobile.webservices.entity.InformationListEntity;
import com.zfsoft.mobile.webservices.entity.LibraryBusinessEntity;
import com.zfsoft.mobile.webservices.entity.LibraryUserEntity;
import com.zfsoft.mobile.webservices.entity.MemoCatalog;
import com.zfsoft.mobile.webservices.entity.MemoDB;
import com.zfsoft.mobile.webservices.entity.MemoPictureEntity;
import com.zfsoft.mobile.webservices.entity.NoticeInfoEntity;
import com.zfsoft.mobile.webservices.entity.NumberDepartment;
import com.zfsoft.mobile.webservices.entity.OauthCardBusinessEntity;
import com.zfsoft.mobile.webservices.entity.PersonEntity;
import com.zfsoft.mobile.webservices.entity.Program;
import com.zfsoft.mobile.webservices.entity.SalaryEntity;
import com.zfsoft.mobile.webservices.entity.ScalendarEntity;
import com.zfsoft.mobile.webservices.entity.ServiceNotReading;
import com.zfsoft.mobile.webservices.entity.ThirdPartyEntity;
import com.zfsoft.mobile.webservices.entity.VoteActivity;
import com.zfsoft.mobile.webservices.entity.WeiBoEntity;
import com.zfsoft.mobile.webservices.query.CardBusinessQuery;
import com.zfsoft.mobile.webservices.query.PersonQuery;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.DBEncrypt;

/**
 * 
 * @author Administrator
 *
 */
public class MobileCommonServiceImpl implements IMobileCommonService {

	private static final String KEY_PROCODE = "${procode}";
	private static final String KEY_CHOICE = "${choice}";
	private static final String KEY_USERID = "${uid}";
	private static final String KEY_PUBLICKEY = "${key}";

	private static final transient Logger log = Logger.getLogger(MobileCommonServiceImpl.class);
    private IMobileCommonDao mobileCommonDao;
    
    private ILoginService loginService;
    
    
    /**
     * @return the mobileCommonDao
     */
    public IMobileCommonDao getMobileCommonDao() {
        return mobileCommonDao;
    }

    /**
     * @param mobileCommonDao the mobileCommonDao to set
     */
    public void setMobileCommonDao(IMobileCommonDao mobileCommonDao) {
        this.mobileCommonDao = mobileCommonDao;
    }
    
    /**
	 * 教师工资信息更新及录入
	 * @param salaryList
	 */
	public void insertSalaryList(List<SalaryEntity> salaryList){
		for(SalaryEntity entity : salaryList){
    		int count = mobileCommonDao.getSalCountById(entity.getGlobalid());
    		if(count > 0){
    			mobileCommonDao.updateSalById(entity);
    		}else{
    			mobileCommonDao.insertSal(entity);
    		}
    	}
	}
	
	/**
	 * 图书馆读者信息更新及插入
	 * @param userList
	 */
	public void insertLibUserList(List<LibraryUserEntity> userList){
		for(LibraryUserEntity entity : userList){
    		int count = mobileCommonDao.getLibUserCountById(entity.getRyh());
    		if(count > 0){
    			mobileCommonDao.updateLibUserById(entity);
    		}else{
    			mobileCommonDao.insertLibUser(entity);
    		}
    	}
	}
	
	/**
	 * 图书馆借阅记录数据更新及插入
	 * @param businessList
	 */
	public void insertLibBusiList(List<LibraryBusinessEntity> businessList){
		for(LibraryBusinessEntity entity : businessList){
    		int count = mobileCommonDao.getLibBusCountById(entity.getLsh());
    		if(count > 0){
    			mobileCommonDao.updateLibBusById(entity);
    		}else{
    			mobileCommonDao.insertLibBus(entity);
    		}
    	}
	}

    /**
	 * 一卡通信息插入
	 * @param businessList
	 */
    public void insertCardBusiness(List<OauthCardBusinessEntity> businessList){
    	for(OauthCardBusinessEntity entity : businessList){
    		int count = mobileCommonDao.getBsiCountByLsh(entity.getLsh());
    		if(count > 0){
    			mobileCommonDao.updateBsiByLsh(entity);
    		}else{
    			mobileCommonDao.insertBsi(entity);
    		}
    	}
    }
    
    /**
     * 获取上传图片的服务器地址
     */
    @Override
    public String getUploadImagePath(String imageId) {
        if (StringUtils.isEmpty(imageId)) {
            return "";
        }

        String uploadPath = BaseHolder.getPropertiesValue("uploadPath");
        String uploadSwitch = BaseHolder.getPropertiesValue("uploadSwitch");

        uploadPath = uploadPath.replace("\\", "/");
        if (!uploadPath.endsWith("/")) {
            uploadPath += "/";
        }
        
        String filename;
        
        if (StringUtils.isEmpty(uploadSwitch) || StringUtils.isEmpty(uploadPath)) {
            return "";
        } else {
            if ("on".equals(uploadSwitch)) {
                filename = mobileCommonDao.getUploadImageName(imageId);
                
                if (StringUtils.isEmpty(filename)) {
                    return "";
                } else {
                    uploadPath += filename;
                    return uploadPath;
                }
            } else {
                return "";
            }
        }
    }
    /**
     * 获取上传图片缩略图的服务器地址
     */
    @Override
    public String getMinUploadImagePath(String imageId) {
        if (StringUtils.isEmpty(imageId)) {
            return "";
        }

        String uploadPath = "min" + BaseHolder.getPropertiesValue("uploadPath");
        String uploadSwitch = BaseHolder.getPropertiesValue("uploadSwitch");

        uploadPath = uploadPath.replace("\\", "/");
        if (!uploadPath.endsWith("/")) {
            uploadPath += "/";
        }
        
        String filename;
        
        if (StringUtils.isEmpty(uploadSwitch) || StringUtils.isEmpty(uploadPath)) {
            return "";
        } else {
            if ("on".equals(uploadSwitch)) {
                filename = mobileCommonDao.getUploadImageName(imageId);
                
                if (StringUtils.isEmpty(filename)) {
                    return "";
                } else {
                    uploadPath += filename;
                    return uploadPath;
                }
            } else {
                return "";
            }
        }
    }
    /**
     * 获取上传的服务器地址
     */
    @Override
    public String getNewsVedioPath(String vedioId){
        if (StringUtils.isEmpty(vedioId)) {
            return "";
        }

        String vedio = BaseHolder.getPropertiesValue("vedio", "vedioFile");

        vedio = vedio.replace("\\", "/");
        if (!vedio.endsWith("/")) {
        	vedio += "/";
        }
        
        String filename;
        
        filename = mobileCommonDao.getUploadFileName(vedioId);
        
        if (StringUtils.isEmpty(filename)) {
            return "";
        } else {
        	filename = filename.substring(0, filename.indexOf("."));
        	vedio += filename;
            return vedio;
        }
    }
    
    /**
     * 获取资讯上传图片缩略图的服务器地址
     */
    @Override
    public String getNewsUploadImagePath(String imageId) {
        if (StringUtils.isEmpty(imageId)) {
            return "";
        }

        String uploadPath = "home" + BaseHolder.getPropertiesValue("uploadPath");
        String uploadSwitch = BaseHolder.getPropertiesValue("uploadSwitch");

        uploadPath = uploadPath.replace("\\", "/");
        if (!uploadPath.endsWith("/")) {
            uploadPath += "/";
        }
        
        String filename;
        
        if (StringUtils.isEmpty(uploadSwitch) || StringUtils.isEmpty(uploadPath)) {
            return "";
        } else {
            if ("on".equals(uploadSwitch)) {
                filename = mobileCommonDao.getUploadImageName(imageId);
                
                if (StringUtils.isEmpty(filename)) {
                    return "";
                } else {
                    uploadPath += filename;
                    return uploadPath;
                }
            } else {
                return "";
            }
        }
    }
    
    @Override
	public String getGoodsImagePath(String imageId,String folderName) {
		String uploadPath = folderName+ BaseHolder.getPropertiesValue("uploadPath");
		uploadPath = uploadPath.replace("\\", "/");
        if (!uploadPath.endsWith("/")) {
            uploadPath += "/";
        }
        try {
			if(StringUtils.isNotBlank(imageId)){
				String imgName=mobileCommonDao.getUploadImageName(imageId);
				if(StringUtils.isBlank(imgName)){
					return "";
				}else{
					uploadPath += imgName;
					return uploadPath;
				}
			}
        } catch (Exception e) {
        	e.fillInStackTrace();
        }
		return null;
	}

    @Override
	public List<ServiceManager> getCommonService() {
    	List<ServiceManager> ret = mobileCommonDao.getCommonService();
    	return getOpenUrl(ret);
	}
    
    
    
    /**
     * 根据用户取得该用户的所有服务
     */
    @Override
    public List<ServiceManager> getAllServiceByUser(String userId, String ywId) {
        Map<String, Object> param = new HashMap<String, Object>();
        List<ServiceManager> ret = null;
        param.put("userId", userId);
        if (!StringUtils.isEmpty(ywId)) {
            param.put("ywId", ywId);
        }
        int size = mobileCommonDao.getAllServiceByUserCnt(param);
        if (size > 0) {
            param.put("startRow", 1);
            param.put("endRow", size);
            ret = mobileCommonDao.getAllServiceByUser(param);
        } else {
            ret = new ArrayList<ServiceManager>();
        }

        return getOpenUrl(ret, userId);
    }

    /**
     * 根据用户取得该用户的常用服务
     */
    @Override
    public List<ServiceManager> getFrequentlyServiceByUser(String userId) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("userId", userId);
    	mobileCommonDao.deleteFrequentlyServiceNotInYhqx(params);
        List<ServiceManager> fs = mobileCommonDao.getFrequentlyServiceByUser(params);
        
        // 初始化
        if (fs == null || fs.size() == 0) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", userId);
            param.put("startRow", 1);
            param.put("endRow", 7);
            
            List<ServiceManager> fsds = mobileCommonDao.getAllServiceByUser(param);
            
            for (ServiceManager fsd : fsds) {
                insertFrequentlyService(userId, fsd.getClassFwbm(), true);
            }
            
            return getOpenUrl(fsds, userId);
        }
        return getOpenUrl(fs, userId);
    }

    /**
     * 取得用户的所有业务系统
     */
    @Override
    public List<Business> getAllXtYwByUser(String userId) {
        return mobileCommonDao.getAllXtYwByUser(userId);
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
            mobileCommonDao.downFrequentlyService(param);
        }
        mobileCommonDao.insertFrequentlyService(param);
    }

    /**
     * 删除常用服务
     */
    @Override
    public void deleteFrequentlyService(String userId, String fwBm) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("fwBm", fwBm);
        
        int rd = mobileCommonDao.getRd(param);
        param.put("mark", rd);
        mobileCommonDao.upFrequentlyService(param);
        mobileCommonDao.deleteFrequentlyService(param);
        mobileCommonDao.deleteFwbmFromYhqx(fwBm);
    }
    
    /**
     * 删除用户所有的常用服务
     */
    @Override
    public void deleteAllFrequentlyService(String userId) {
    	Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        
        mobileCommonDao.deleteAllFrequentlyService(param);
    }

    /**
     * 更新常用服务
     */
    @Override
    public void modifyFrequentlyService(String userId, String fwBm, String type, int toMark) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("fwBm", fwBm);
        // 更新使用次数
        if ("0".equals(type)) {
            mobileCommonDao.modifyFrequentlyService(param);
            // 更新热度 向下
        } else if ("1".equals(type)) {
            int rd = mobileCommonDao.getRd(param);
            int startMark = rd;
            int endMark = rd + 1;
            param.put("startMark", startMark);
            param.put("endMark", endMark);
            List<Map<String, Object>> sfs = mobileCommonDao.getSwapFrequentlyService(param);
            Map<String, Object> tparam;
            for (Map<String, Object> m : sfs) {
                tparam = new HashMap<String, Object>();
                tparam.put("userId", userId);
                tparam.put("fwBm", m.get("FWBM"));
                if (fwBm.equals(m.get("FWBM"))) {
                    tparam.put("rd", Integer.parseInt(((BigDecimal)m.get("RD")).toString()) + 1);
                } else {
                    tparam.put("rd", Integer.parseInt(((BigDecimal)m.get("RD")).toString()) - 1);
                }
                mobileCommonDao.swapFrequentlyService(tparam);
            }

            // 更新热度 向上
        } else if ("2".equals(type)) {
            int rd = mobileCommonDao.getRd(param);
            int startMark = rd - 1;
            int endMark = rd;
            param.put("startMark", startMark);
            param.put("endMark", endMark);
            List<Map<String, Object>> sfs = mobileCommonDao.getSwapFrequentlyService(param);
            Map<String, Object> tparam;
            for (Map<String, Object> m : sfs) {
                tparam = new HashMap<String, Object>();
                tparam.put("userId", userId);
                tparam.put("fwBm", m.get("FWBM"));
                if (fwBm.equals(m.get("FWBM"))) {
                    tparam.put("rd", Integer.parseInt(((BigDecimal)m.get("RD")).toString()) - 1);
                } else {
                    tparam.put("rd", Integer.parseInt(((BigDecimal)m.get("RD")).toString()) + 1);
                }
                mobileCommonDao.swapFrequentlyService(tparam);
            }
            // 更新热度 拖拽
        } else if ("3".equals(type)) {
            int rd = mobileCommonDao.getRd(param);
            param.put("mark", toMark);
            param.put("self", rd);
            // 向前拖拽
            if (toMark < rd) {
                param.put("move", "up");
                // 向后拖拽
            } else if (toMark > rd) {
                param.put("move", "dowm");
            }
            mobileCommonDao.moveFrequentlyService(param);
            param.put("rd", toMark);
            mobileCommonDao.swapFrequentlyService(param);
        }else if ("4".equals(type)) {
        	param.put("rd", toMark);
        	mobileCommonDao.swapFrequentlyService(param);
        }

    }
    
    
    public static void main(String[] args) {
		
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
//	    			if(choice.equals("20")){
//	    				System.out.println("aaa");
//	    			}
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
	    			log.error("fwbm:"+s.getClassFwbm()+",url="+s.getAppUrl());
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
	    			if(!StringUtil.isEmpty(s.getIsSignal()) && s.getIsSignal().equals("1")){
	    				s.setSignalUrl(fwdz);
	    			}
	    		}
	    		
	    		boolean check = false;
	    		try {
	    			check = checkImage(s.getClassFwtbdz(), s.getClassFwtbid());
	    		} catch (IOException e) {
	    			e.printStackTrace();
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
					log.error("-----checkImage进入id验证-----" +"图标url文件不存在，id为空："+StringUtil.isEmpty(imageId));
					if(!StringUtils.isEmpty(imageId)){
						return doImage(url,imageId);
					}
				}
		}
		return false;
	}
    @Override
    public boolean checkMemo(MemoDB memoDB){
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
				log.error("-----checkmemo:,url文件不存在，id为空-------");
				
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
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		ImageDB image = mobileCommonDao.findImageById(imageId);
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

	/**
	 * 登录的服务
	 */
	@Override
	public List<ServiceManager> getLoginFw(Map<String, Object> param) {
		return mobileCommonDao.getLoginFw(param);
	}

	@Override
	public List<MyPortal> getPortalUrl(List<MyPortal> apps, String userName) {
		// TODO Auto-generated method stub
		String addr = "";
		String procode = "";
		String choice = "";
		String publickey = "";
		String pk;
		String privatekey;
		DBEncrypt tool = new DBEncrypt();
		pk = MobileSystemHolder.getPropertiesValue("ydmh_private_key");
		if (StringUtils.isEmpty(pk)) {
			privatekey = "";
		} else {
			privatekey = tool.dCode(pk.getBytes());
		}
		
    	try {
	    	for (MyPortal s : apps) {
	    	    	
	    		addr = s.getAddr();
	    	    	if(!StringUtils.isEmpty(addr) && addr.contains(KEY_USERID) && addr.contains(KEY_PUBLICKEY)){
	    	    		addr = addr.replace("\\", "/");
	    	    		Map<String, String> map = getMapParam(addr);
//	    	    		int begin = addr.indexOf("choice")+"choice".length()+1;
//	    	    		int end   = begin + 1;
//	    	    		choice = addr.substring(begin, end);
	    	    		choice = map.get("choice");
	    	    		procode = map.get("procode");
	    	    		
	    	    		publickey = MD5Util.md5Encode(procode + choice + userName + privatekey);
	    	    		
	    	    		addr = addr.replace(KEY_USERID, userName);
	    	    		addr = addr.replace(KEY_PUBLICKEY, publickey);
	    	    		//addr = addr.replace("&", "&amp;");
	    	    		
	    	    	}else{
	    	    		addr += "?uid=" + userName + "&type=1";
	    	    	}
	    	    	s.setAddr(addr);
	    		
	    		
	    		boolean check = false;
	    		try {
	    			check = checkImage(s.getTburl(), s.getTbid());
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		if(!check)
	    			s.setTburl("upload/default_image.jpg");
	    		
	    	}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return apps;
	}
	
	/**
	 * 对url进行解析，解析成map，如[<procode,value>,<choice,value>,<uid,value>]
	 * @param url
	 * @return
	 */
	public  Map<String, String> getMapParam(String url){
		int index = url.indexOf("?");
		int index2 = 0;
		if ( url == null || url == "" || index == -1 ) {
			return null;
		}
		
		Map<String,String> params = new HashMap<String, String>();
		url = url.substring(index + 1);
		String[] paramArray = url.split("&");
		for(String param : paramArray){
			index2 = param.indexOf("=");
			if (index2 <= 0) {
				continue;
			}
			params.put(param.substring(0,index2), param.substring(index2 + 1));
			//System.out.println("----key----" + param.substring(0,index2));
			//System.out.println("---value---" + param.substring(index2 + 1));
		}
		
		return params;
	}
	
	@Override
	public Map<String, Object> getPortalInfo(Map<String, String> param) {
		return mobileCommonDao.getPortalInfo(param);
	}
	

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	public ILoginService getLoginService() {
		return loginService;
	}

	@Override
	public void submitSuggestion(Map<String, String> param) {
		// TODO Auto-generated method stub
		mobileCommonDao.submitSuggestion(param);
	}

	@Override
	public void insertMyPicture(ImageDB imageDB) {
		// TODO Auto-generated method stub
		 mobileCommonDao.insertMyPicture(imageDB);
	}

	@Override
	public List<ImageDB> getMyPicture(String userId) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getMyPicture(userId);
	}

	@Override
	public void updateMyPicture(ImageDB imageDB) {
		// TODO Auto-generated method stub
		mobileCommonDao.updateMyPicture(imageDB);
	}

	@Override
	public List<NumberDepartment> getAllDepartment(Map<String, String> map) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getAllDepartment(map);
	}

	@Override
	public List<InformationListEntity> getAllInformationList(){
		return mobileCommonDao.getAllInformationList();
	}
	
	@Override
	public List<PersonEntity> getAllPerson(Map<String, String> map) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		List<PersonEntity> personList = mobileCommonDao.getAllPerson(map);
		File newFile;
		try {
			for(PersonEntity person : personList){
				ImageDB image = mobileCommonDao.findImageById(person.getRyicoid());
				if(image == null){
					//throw new Exception("人员头像id存在，但是在表hrm_xtgl_zpb中不存在此人头像！");
					System.err.println(person.getRyid() + "人员头像id存在，但是在表hrm_xtgl_zpb中不存在此人头像！");
					person.setRyico("upload/default_image.jpg");
					continue;
				}
				byte[] content = image.getFileContent();
				String tempPath = request.getSession().getServletContext().getRealPath("/") + "infoclassHead";
				tempPath = tempPath.replace("\\", "/");
				newFile = new File(tempPath);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				String fileName = image.getFileName();
	
				/*ImageIO.setUseCache(false);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
				BufferedImage newImage = ImageIO.read(byteArrayInputStream);
				ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);
				BufferedImage img = ImageIO.read(byteArrayInputStream);      // 构造Image对象
				BufferedImage bi = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);     
		        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
		        g2.setBackground(Color.WHITE);     
		        g2.clearRect(0, 0, 80, 80);     
		        g2.drawImage(img, 0, 0, 80, 80, null); // 绘制缩小后的图
		        File destFile = new File(tempPath+"/"+fileName);
		        if(destFile.exists()){
		        	destFile.delete();
		        }
				FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(bi); // JPEG编码
				out.close();*/
				
				ImageIO.setUseCache(false);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
				/*BufferedImage newImage = ImageIO.read(byteArrayInputStream);
				ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);*/
				BufferedImage img = ImageIO.read(byteArrayInputStream);      // 构造Image对象
				
				int width = img.getWidth(null);
				int height = img.getHeight(null);  // 得到源图长
				int w=120;
				//if (width / height < w / h) {
					//resizeByWidth(w);
				int h = (int) (height * w / width);
				/*} else {
					//resizeByHeight(h);
					w = (int) (width * h / height);
				}*/
				BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);     
		        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
		        g2.setBackground(Color.WHITE);     
		        g2.clearRect(0, 0, w, h);     
		        g2.drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		        File destFile = new File(tempPath+"/"+fileName);
		        if(destFile.exists()){
		        	destFile.delete();
		        }
				FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
				// 可以正常实现bmp、png、gif转jpg
				//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				//encoder.encode(bi); // JPEG编码
				FileImageOutputStream imageOutput = null;
				imageOutput  = new FileImageOutputStream(new File(tempPath + "/" + fileName));  
				imageOutput.write(content, 0, content.length);  
				imageOutput.close(); 
				out.close();
				
				
				/*//再次把图片压缩成圆形图片
				BufferedImage bi1 = ImageIO.read(new File(tempPath+"/"+fileName));

				// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
				BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
						BufferedImage.TYPE_INT_RGB);

				Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
						.getHeight());

				Graphics2D g3 = bi2.createGraphics();
				g3.setBackground(Color.WHITE);
				g3.fill(new Rectangle(bi2.getWidth(), bi2.getHeight()));
				g3.setClip(shape);
				// 使用 setRenderingHint 设置抗锯齿
				g3.drawImage(bi1, 0, 0, null);
				g3.dispose();

				try {
					ImageIO.write(bi2, "jpg", new File(tempPath+"/"+fileName));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				person.setRyico("infoclassHead" +"/" +fileName);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return personList;
	}
	
	private String getImageHost() {
		String url = BaseHolder.getPropertiesValue("suploadPath");
		if (url == null) {
			return "/";
		}
        url = url.replace("\\", "/");
        if (!url.endsWith("/")) {
        	url += "/";
        }
		return url;
	}

	@Override
	public void deleteTPByUserAndJmid(Map<String, String> map) {
		// TODO Auto-generated method stub
		mobileCommonDao.deleteTPByUserAndJmid(map);
	}

	@Override
	public void insertTPByUserAndJmid(Map<String, String> map) {
		// TODO Auto-generated method stub
		mobileCommonDao.insertTPByUserAndJmid(map);
	}

	@Override
	public List<VoteActivity> getVoteList(String tphdid) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getVoteList(tphdid);
	}

	@Override
	public List<Program> getProgramList(String tphdid) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getProgramList(tphdid);
	}
	
	@Override
	public List<Program> getRankListByTphdid(String tphdid) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getRankListByTphdid(tphdid);
	}

	@Override
	public int getTPByUserAndJmid(Map<String, String> map) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getTPByUserAndJmid(map);
	}

	@Override
	public PageList<PersonEntity> getPersonList(PersonQuery query) {
		// TODO Auto-generated method stub
		PageList<PersonEntity> pageList = new PageList<PersonEntity>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(mobileCommonDao.getPersonCount(query));
			pageList.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(mobileCommonDao.getPersonList(query));
			}
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		File newFile;
		try {
			for (int i = 0; i < pageList.size(); i++) {
				ImageDB image = !StringUtils.isEmpty(pageList.get(i).getRyicoid()) ? 
									mobileCommonDao.findImageById(pageList.get(i).getRyicoid()) : null;
					
				if(image == null || image.getFileContent() == null){
					//throw new Exception("人员头像id存在，但是在表hrm_xtgl_zpb中不存在此人头像！");
					System.err.println(pageList.get(i).getRyid() + "人员头像id存在，但是在表hrm_xtgl_zpb中不存在此人头像或者content字段为空！");
					pageList.get(i).setRyico("upload/default_image.jpg");
					continue;
				}
				byte[] content = image.getFileContent();
				String tempPath = request.getSession().getServletContext().getRealPath("/") + "infoclassHead";
				tempPath = tempPath.replace("\\", "/");
				newFile = new File(tempPath);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				String fileName = image.getFileName();
	
				/*ImageIO.setUseCache(false);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
				BufferedImage newImage = ImageIO.read(byteArrayInputStream);
				ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);
				BufferedImage img = ImageIO.read(byteArrayInputStream);      // 构造Image对象
				BufferedImage bi = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);     
		        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
		        g2.setBackground(Color.WHITE);     
		        g2.clearRect(0, 0, 80, 80);     
		        g2.drawImage(img, 0, 0, 80, 80, null); // 绘制缩小后的图
		        File destFile = new File(tempPath+"/"+fileName);
		        if(destFile.exists()){
		        	destFile.delete();
		        }
				FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(bi); // JPEG编码
				out.close();*/
				
				ImageIO.setUseCache(false);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
				/*BufferedImage newImage = ImageIO.read(byteArrayInputStream);
				ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);*/
				BufferedImage img = ImageIO.read(byteArrayInputStream);      // 构造Image对象
				
				int width = img.getWidth(null);
				int height = img.getHeight(null);  // 得到源图长
				int w=120;
				//if (width / height < w / h) {
					//resizeByWidth(w);
				int h = (int) (height * w / width);
				/*} else {
					//resizeByHeight(h);
					w = (int) (width * h / height);
				}*/
				BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);     
		        Graphics2D g2 = (Graphics2D)bi.getGraphics();     
		        g2.setBackground(Color.WHITE);     
		        g2.clearRect(0, 0, w, h);     
		        g2.drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		        File destFile = new File(tempPath+"/"+fileName);
		        if(destFile.exists()){
		        	destFile.delete();
		        }
				FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
				out.close();
				// 可以正常实现bmp、png、gif转jpg
				//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				//encoder.encode(bi); // JPEG编码
				FileImageOutputStream imageOutput = null;
				imageOutput  = new FileImageOutputStream(new File(tempPath + "/" + fileName));  
				imageOutput.write(content, 0, content.length);  
				imageOutput.close();
				
				
				/*//再次把图片压缩成圆形图片
				BufferedImage bi1 = ImageIO.read(new File(tempPath+"/"+fileName));

				// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
				BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
						BufferedImage.TYPE_INT_RGB);

				Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
						.getHeight());

				Graphics2D g3 = bi2.createGraphics();
				g3.setBackground(Color.WHITE);
				g3.fill(new Rectangle(bi2.getWidth(), bi2.getHeight()));
				g3.setClip(shape);
				// 使用 setRenderingHint 设置抗锯齿
				g3.drawImage(bi1, 0, 0, null);
				g3.dispose();

				try {
					ImageIO.write(bi2, "jpg", new File(tempPath+"/"+fileName));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				pageList.get(i).setRyico("infoclassHead" +"/" +fileName);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pageList;
	}

	@Override
	public float getCardNumber(String userid) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getCardNumber(userid);
	}

	@Override
	public int getodetailCount(CardBusinessQuery businessQuery) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getodetailCount(businessQuery);
	}

	@Override
	public PageList<CardBusinessEntity> getodetailList(
			CardBusinessQuery businessQuery) {
		// TODO Auto-generated method stub
		PageList<CardBusinessEntity> pageList = new PageList<CardBusinessEntity>();
		Paginator paginator = new Paginator();
		if(businessQuery!=null){
			paginator.setItemsPerPage(businessQuery.getPerPageSize());
			paginator.setPage((Integer)businessQuery.getToPage());
			paginator.setItems(mobileCommonDao.getodetailCount(businessQuery));
			pageList.setPaginator(paginator);
			if((Integer)businessQuery.getToPage() > paginator.getPages()){
				return pageList;
			}
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				businessQuery.setStartRow(paginator.getBeginIndex());
				businessQuery.setEndRow(paginator.getEndIndex());
				List<CardBusinessEntity> list = mobileCommonDao.getodetailList(businessQuery);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public int getcdetailCount(CardBusinessQuery businessQuery) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getcdetailCount(businessQuery);
	}

	@Override
	public PageList<CardBusinessEntity> getcdetailList(
			CardBusinessQuery businessQuery) {
		// TODO Auto-generated method stub
		PageList<CardBusinessEntity> pageList = new PageList<CardBusinessEntity>();
		Paginator paginator = new Paginator();
		if(businessQuery!=null){
			paginator.setItemsPerPage(businessQuery.getPerPageSize());
			paginator.setPage((Integer)businessQuery.getToPage());
			paginator.setItems(mobileCommonDao.getcdetailCount(businessQuery));
			pageList.setPaginator(paginator);
			if((Integer)businessQuery.getToPage() > paginator.getPages()){
				return pageList;
			}
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				businessQuery.setStartRow(paginator.getBeginIndex());
				businessQuery.setEndRow(paginator.getEndIndex());
				List<CardBusinessEntity> list = mobileCommonDao.getcdetailList(businessQuery);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public String getCardKH(String userid) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getCardKH(userid);
	}

	@Override
	public PageList<WeiBoEntity> getWeiBoList(WeiBoEntity query) {
		// TODO Auto-generated method stub
		PageList<WeiBoEntity> pageList = new PageList<WeiBoEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(mobileCommonDao.getWeiBoListCount(query));
			pageList.setPaginator(paginator);
			/*if((Integer)businessQuery.getToPage() > paginator.getPages()){
				return pageList;
			}*/
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<WeiBoEntity> list = mobileCommonDao.getWeiBoList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	@Override
	public List<ServiceManager> getOtherServiceByUser(Map<String, Object> params) {
		List<ServiceManager> otherServiceList = mobileCommonDao.getOtherServiceByUser(params);
		return getOpenUrl(otherServiceList);
	}

	
	/**
	 * 根据服务编码对应用做统计
	 */
	@Override
	public void visitsByFwbm(String fwbm) {
		int count = mobileCommonDao.getVisitsByFwbm(fwbm);
		if(count == 1){
			mobileCommonDao.updateVisitsByFwbm(fwbm);
		}
		if(count == 0){
			mobileCommonDao.insertVisitsByFwbm(fwbm);
		}
	}

	@Override
	public List<NoticeInfoEntity> getNoticeInfo(String id) {
		return mobileCommonDao.getNoticeInfo(id);
	}

	@Override
	public PageList<NoticeInfoEntity> getNoticeList(NoticeInfoEntity query) {
		PageList<NoticeInfoEntity> pageList = new PageList<NoticeInfoEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(mobileCommonDao.getNoticeListCount(query));
			pageList.setPaginator(paginator);
			/*if((Integer)businessQuery.getToPage() > paginator.getPages()){
				return pageList;
			}*/
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<NoticeInfoEntity> list = mobileCommonDao.getNoticeList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public List<String> getNoticeListNewTableType() {
		// TODO Auto-generated method stub
		return mobileCommonDao.getNoticeListNewTableType();
	}

	@Override
	public  List<CardBusinessEntity> getCardKHByXB(String userid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userid", userid);
		return mobileCommonDao.getCardByUser(param);
	}


	@Override
	public int getdetailCountByXB(CardBusinessQuery businessQuery) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getdetailCountByXB(businessQuery);
	}

	@Override
	public PageList<CardBusinessEntity> getdetailListByXB(
			CardBusinessQuery businessQuery) {
		// TODO Auto-generated method stub
		PageList<CardBusinessEntity> pageList = new PageList<CardBusinessEntity>();
		Paginator paginator = new Paginator();
		if(businessQuery!=null){
			paginator.setItemsPerPage(businessQuery.getPerPageSize());
			paginator.setPage((Integer)businessQuery.getToPage());
			paginator.setItems(mobileCommonDao.getdetailCountByXB(businessQuery));
			pageList.setPaginator(paginator);
			if((Integer)businessQuery.getToPage() > paginator.getPages()){
				return pageList;
			}
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				businessQuery.setStartRow(paginator.getBeginIndex());
				businessQuery.setEndRow(paginator.getEndIndex());
				List<CardBusinessEntity> list = mobileCommonDao.getdetailListByXB(businessQuery);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public List<ScalendarEntity> getTermWeek(String ymd) {
		return mobileCommonDao.getTermWeek(ymd);
	}

	@Override
	public PageList<MemoDB> getMyMemoList(MemoDB query) {
		PageList<MemoDB> pageList = new PageList<MemoDB>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(mobileCommonDao.getMyMemoListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<MemoDB> list = mobileCommonDao.getMyMemoList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insertMemo(MemoDB memoEntity) {
		mobileCommonDao.insertMemo(memoEntity);
	}

	@Override
	public void updateMemo(MemoDB memoEntity) {
		mobileCommonDao.updateMemo(memoEntity);
	}

	@Override
	public void deleteAllmemoCatalogByUser(String userId) {
		mobileCommonDao.deleteAllmemoCatalogByUser(userId);
	}

	@Override
	public void insertmemoCatalogByUser(MemoCatalog memoCatalog) {
		mobileCommonDao.insertmemoCatalogByUser(memoCatalog);
	}

	@Override
	public List<MemoCatalog> getMemoCatalogList(String userId) {
		return mobileCommonDao.getMemoCatalogList(userId);
	}

	@Override
	public void deleteMyMemo(MemoDB memoQuery) {
		File file;
		String filePath;
		HttpServletRequest request = ServletActionContext.getRequest();
		mobileCommonDao.deleteMyMemo(memoQuery);
		filePath = request.getSession().getServletContext().getRealPath("/") + "memoFile/"+ memoQuery.getMemoFileName(); 
		file = new File(filePath);  
		// 路径为文件且不为空则进行删除  
		if (file.isFile() && file.exists()) {  
			file.delete();  
		} 
		
		List<MemoPictureEntity> memoPictureList = mobileCommonDao.getMemoPictureList(memoQuery.getMemoFileName());
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
	    mobileCommonDao.deleteMyMemoPicture(memoQuery.getMemoFileName());
	}

	@Override
	public List<ThirdPartyEntity> selectThirdParty(ThirdPartyEntity thirdEntity) {
		return mobileCommonDao.selectThirdParty(thirdEntity);
	}

	@Override
	public void deleteThirdParty(ThirdPartyEntity thirdEntity) {
		mobileCommonDao.deleteThirdParty(thirdEntity);
	}

	@Override
	public void insertThirdParty(ThirdPartyEntity thirdEntity) {
		mobileCommonDao.insertThirdParty(thirdEntity);
	}

	@Override
	public void insertMemoPicture(MemoPictureEntity pictureEnity) {
		mobileCommonDao.insertMemoPicture(pictureEnity);
	}

	@Override
	public int getMemoPicture(MemoPictureEntity pictureEnity) {
		return mobileCommonDao.getMemoPicture(pictureEnity);
	}

	@Override
	public void deleteMyMemoPicture(String memoFileName) {
		mobileCommonDao.deleteMyMemoPicture(memoFileName);
	}

	@Override
	public void insertVisitsToZFByFwbm(Map<String, String> map) {
		int count = mobileCommonDao.getVisitsByFwbm(map.get("fwbm"));
		if(count == 1){
			mobileCommonDao.updateVisitsToZFByFwbm(map);
		}
		if(count == 0){
			mobileCommonDao.insertVisitsToZFByFwbm(map);
		}
	}

	@Override
	public void checkMemoPictureEntity(String memoFileName) {
		List<MemoPictureEntity> memoPictureList = mobileCommonDao.getMemoPictureList(memoFileName);
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
    				log.error(e, e.fillInStackTrace());
    				continue;
    			}
				
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.common.service.IMobileCommonService#getAllServiceNotreading()
	 */
	@Override
	public List<ServiceNotReading> getAllServiceNotreadingByUserName(String username) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("username", username);
		return mobileCommonDao.getAllServiceNotreadingByUserName(params);
	}

	@Override
	public List<ServiceNotReading> getAllServiceNotreading(){
		return mobileCommonDao.getAllServiceNotreading();
	}
	

	/*@Override
	public String getYearWeekStart(String code) {
		return mobileCommonDao.getYearWeekStart(code);
	}

	@Override
	public String getYearWeekEnd(String code) {
		// TODO Auto-generated method stub
		return mobileCommonDao.getYearWeekEnd(code);
	}*/


	
}
