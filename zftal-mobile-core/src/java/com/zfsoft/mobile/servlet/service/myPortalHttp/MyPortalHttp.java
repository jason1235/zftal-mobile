package com.zfsoft.mobile.servlet.service.myPortalHttp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.common.Config;
import com.google.gson.Gson;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.service.IMyPortalService;
import com.zfsoft.mobile.servlet.entity.FailureEntity;
import com.zfsoft.mobile.servlet.entity.ListEntity;
import com.zfsoft.mobile.servlet.entity.MyPortalEntity;
import com.zfsoft.mobile.servlet.entity.MyPortalEntityItemEntity;
import com.zfsoft.mobile.servlet.entity.OneCardConsumeItemEntity;
import com.zfsoft.mobile.servlet.entity.OneCardEntity;
import com.zfsoft.mobile.servlet.entity.ProgramEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.servlet.entity.VoteActivityEntity;
import com.zfsoft.mobile.webservices.entity.CardBusinessEntity;
import com.zfsoft.mobile.webservices.entity.Program;
import com.zfsoft.mobile.webservices.entity.VoteActivity;
import com.zfsoft.mobile.webservices.query.CardBusinessQuery;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class MyPortalHttp {
	private static Logger logger = Logger.getLogger(MyPortalHttp.class);
	private final String infromation=Config.getString("mobile.infromation");
	/**
	 * 获取服务器访问路径
	 * @return
	 */
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
	
	
	
	
	
	public String votelike(String userName, String jmid, String tag,
			String strKey,String apptoken) {
		Gson gson = new Gson();
		try {
			if(!ApptokenUtils.compare(userName, apptoken)){
				FailureEntity failure = new FailureEntity();
				failure.failure = "app_token error";
				return gson.toJson(failure);
			}
				userName  			= CodeUtil.decode(userName, apptoken);
				strKey  			= CodeUtil.decode(strKey, apptoken);
				jmid  				= CodeUtil.decode(jmid, apptoken);
				tag  				= CodeUtil.decode(tag, apptoken);
				
			
			if(infromation.equals("0")){
				logger.error("投票交互："+"userName="+userName+",strKey="+strKey+",apptoken="+apptoken+
						",jmid="+jmid+",tag="+tag);
			}	
		
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", userName);
			map.put("jmid", jmid);
			IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
			if(tag.equals("1")){
				mobileCommonService.deleteTPByUserAndJmid(map);
				mobileCommonService.insertTPByUserAndJmid(map);
			}else{
				mobileCommonService.deleteTPByUserAndJmid(map);
			}
			return "{\"message\":\"操作成功\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("投票交互end");
		}
		FailureEntity failure = new FailureEntity();
		failure.failure = "app_token error";
		return gson.toJson(failure);
	}
	
	public String voteRanklist(String userName, String tphdid, String strKey,String apptoken) {
		Gson gson = new Gson();
		try {
			if(!ApptokenUtils.compare(userName, apptoken)){
				FailureEntity failure = new FailureEntity();
				failure.failure = "app_token error";
				return gson.toJson(failure);
			}
			userName  			= CodeUtil.decode(userName, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			tphdid  				= CodeUtil.decode(tphdid, apptoken);
			IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");	
			List<Program> programList = mobileCommonService.getRankListByTphdid(tphdid);
			
			return gson.toJson(programList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("投票交互end");
		}
		FailureEntity failure = new FailureEntity();
		failure.failure = "app_token error";
		return gson.toJson(failure);
	}
	
	public String votelist(String userName, String tphdid, String strKey ,String apptoken) {
		Gson gson = new Gson();
		try {
			if(!ApptokenUtils.compare(userName, apptoken)){
				FailureEntity failure = new FailureEntity();
				failure.failure = "app_token error";
				return gson.toJson(failure);
			}
			userName  			= CodeUtil.decode(userName, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			tphdid  				= CodeUtil.decode(tphdid, apptoken);
			
			IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");	
			List<VoteActivity> VoteList = mobileCommonService.getVoteList(tphdid);
			
			VoteActivityEntity result = new VoteActivityEntity();
			VoteActivity voteActivity = new VoteActivity();
			if(VoteList != null && VoteList.size() == 1){
				voteActivity = VoteList.get(0);
			}
			List<Program> programList = new ArrayList<Program>();
			if(voteActivity.getTphdid() != null){
				programList = mobileCommonService.getProgramList(voteActivity.getTphdid());
			}
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><votelist>");
			if(programList.size() > 0){
				ProgramEntity entity;
				for(Program program : programList){
					entity = new ProgramEntity();
					entity.jmid=program.getJmid();
					entity.jmbh=program.getJmbh();
					entity.title=program.getTitle();
					entity.dp=program.getDp();
					entity.yy=program.getYy();
					String url = getImageHost() + "/file/file_image.html?fileGuid=" + program.getJmtpid();
					entity.url=url;
					Map<String, String> map = new HashMap<String, String>();
					map.put("username", userName);
					map.put("jmid", program.getJmid());
					int count = mobileCommonService.getTPByUserAndJmid(map);
					String tag = count > 1 ? "1" : "0";
					entity.tag=String.valueOf(count);
					result.list.add(entity);
				}
			}
			result.ztys=voteActivity.getZtys();
			String zturl = getImageHost() + "/file/file_image.html?fileGuid=" + voteActivity.getZttpid();
			result.zturl = zturl;
			return gson.toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("投票交互end");
		}
		FailureEntity failure = new FailureEntity();
		failure.failure = "app_token error";
		return gson.toJson(failure);
	}
	
	/**
	 * 获取一卡通消费记录
	 * @param detailtype 查询的类型
	 * @param ocid 一卡通卡号
	 * @param startdate 查询时间起始日期
	 * @param enddate 查询时间结束日期
	 * @param pageindex 当前页号
	 * @param pagesize 一页个数
	 * @param strkey 秘钥
	 * @param apptoken 秘钥
	 * @return
	 */
	public String getocdetail(
			String detailtype, String ocid, 
			String pageindex, String pagesize, 
			String strkey,String apptoken
			) {
		Gson gson = new Gson();
		ListEntity<OneCardConsumeItemEntity> entity = null;
		if(StringUtil.isEmpty(detailtype) || StringUtil.isEmpty(ocid) ||
				StringUtil.isEmpty(pageindex) || StringUtil.isEmpty(pagesize) ||
				StringUtil.isEmpty(strkey) || StringUtil.isEmpty(apptoken)){
			ResultEntity result = new ResultEntity<ListEntity>(0, "参数传值出错！", entity);
			return gson.toJson(result);
		}
		try {
			if(!ApptokenUtils.compare(apptoken)){
				ResultEntity result = new ResultEntity<ListEntity>(2, "app_token error!", entity);
				return gson.toJson(result);
			}
			detailtype  		= CodeUtil.decode(detailtype, apptoken);
			ocid  				= CodeUtil.decode(ocid, apptoken);
			pageindex  			= CodeUtil.decode(pageindex, apptoken);
			pagesize  			= CodeUtil.decode(pagesize, apptoken);
			strkey  			= CodeUtil.decode(strkey, apptoken);
			if(infromation.equals("0")){
				logger.error("获取一卡通消费明细："+"detailtype="+detailtype+",ocid="+ocid+
						",pageindex="+pageindex+",pagesize="+pagesize+",strkey="+strkey);
				}	
			int count = 0;
			PageList<CardBusinessEntity> list = new PageList<CardBusinessEntity>();
			IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
			CardBusinessQuery businessQuery = new CardBusinessQuery();
			businessQuery.setOcid(ocid);
			businessQuery.setToPage(Integer.parseInt(pageindex));
			businessQuery.setPerPageSize(Integer.parseInt(pagesize));
			if(detailtype.equals("1")){
				count = mobileCommonService.getodetailCount(businessQuery);
				list = mobileCommonService.getodetailList(businessQuery);
			}
			if(detailtype.equals("0")){
				count = mobileCommonService.getcdetailCount(businessQuery);
				list = mobileCommonService.getcdetailList(businessQuery);
			}
			List<OneCardConsumeItemEntity> consumeList = new ArrayList<OneCardConsumeItemEntity>();
			OneCardConsumeItemEntity oneCardConsumeItemEntity;
			if(list != null && list.size() > 0){
				for(CardBusinessEntity cardBusinessEntity : list){
					oneCardConsumeItemEntity= new OneCardConsumeItemEntity();
					oneCardConsumeItemEntity.setConsumeAspect(cardBusinessEntity.getClassSh());
					oneCardConsumeItemEntity.setConsumetime(
							(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm")).format(cardBusinessEntity.getClassJysj())
							);
					oneCardConsumeItemEntity.setBalance(cardBusinessEntity.getClassYe());
					oneCardConsumeItemEntity.setOutlay((detailtype.equals("1")? "-" : "+") + cardBusinessEntity.getClassJye());
					consumeList.add(oneCardConsumeItemEntity);
				}
			}
			int pagecount = count / Integer.parseInt(pagesize);

			if ((count % Integer.parseInt(pagesize)) != 0) {
				pagecount++;
			}
			entity = new ListEntity<OneCardConsumeItemEntity>();
			entity.setItemList(consumeList);
			if(consumeList == null || consumeList.size() == 0 || consumeList.size() < 10){
				entity.setOvered(true);
			}else{
				entity.setOvered(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultEntity<ListEntity> result = new ResultEntity<ListEntity>(1, "成功", entity);
		return gson.toJson(result);
		
	}
	
	/**
	 * 获取一卡通余额
	 * @param userName
	 * @param strKey
	 * @param apptoken
	 * @return
	 */
	public String getocbalance(String userName, String strKey, String apptoken) {
		Gson gson = new Gson();
		OneCardEntity entity = null;
		if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(strKey)
				|| StringUtil.isEmpty(apptoken)){
			ResultEntity result = new ResultEntity<OneCardEntity>(0, "参数传值出错！", entity);
			return gson.toJson(result);
		}
		try {
		if(!ApptokenUtils.compare(userName, apptoken)){
			ResultEntity result = new ResultEntity<OneCardEntity>(2, "app_token error!", entity);
			return gson.toJson(result);
		}
			userName  			= CodeUtil.decode(userName, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			
		
		if(infromation.equals("0")){
			logger.error("获取一卡通余额："+"userName="+userName+",strKey="+strKey+",apptoken="+apptoken);
			}
		
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		String cardNumber = mobileCommonService.getCardKH(userName);
		if(StringUtil.isEmpty(cardNumber)){
			ResultEntity result = new ResultEntity<OneCardEntity>(0, "不存在此用户的一卡通", entity);
			return gson.toJson(result);
		}
		entity = new OneCardEntity();
		entity.setCardNumber(cardNumber);
		entity.setCardBlance(String.valueOf(mobileCommonService.getCardNumber(userName)));
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultEntity result = new ResultEntity<OneCardEntity>(1, "成功", entity);
		return gson.toJson(result);
	}
	
	
	/**
	 * 我的门户接口未读未还接口
	 * @param userName
	 * @param strKey
	 * @return
	 */
	public String myPortalFunction(String userName, String strKey,String apptoken) {
		Gson gson = new Gson();
		List<MyPortalEntityItemEntity> entityList = new ArrayList<MyPortalEntityItemEntity>();
		if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(strKey)
				|| StringUtil.isEmpty(apptoken)){
			ResultEntity result = new ResultEntity<MyPortalEntity>(0, "参数传值出错！", new MyPortalEntity());
			return gson.toJson(result);
		}
		MyPortalEntityItemEntity entity = null;
		try {
		if(!ApptokenUtils.compare(userName, apptoken)){
			ResultEntity result = new ResultEntity<MyPortalEntity>(2, "app_token error!", new MyPortalEntity());
			return gson.toJson(result);
		}
			userName  			= CodeUtil.decode(userName, apptoken);
			strKey  			= CodeUtil.decode(strKey, apptoken);
			
		
		if(infromation.equals("0")){
			logger.error("我的门户接口未读未还接口获取："+"userName="+userName+",strKey="+strKey+",apptoken="+apptoken);
			}
		IMyPortalService myPortalService = (IMyPortalService) SpringHolder.getBean("myPortalService");
		List<MyPortal> apps = myPortalService.getAllMyPortal(userName);
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		apps = mobileCommonService.getPortalUrl(apps, userName);
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userName);
		Map<String, Object> retMap = mobileCommonService.getPortalInfo(param);
		String url = getImageHost();
		if (apps != null && apps.size() > 0) {
			for (MyPortal app : apps) {
				entity = new MyPortalEntityItemEntity();
				entity.id = app.getId();
				String type = app.getType();
				type = type.equals("1") ? "WEB_SERVICE" : 
					   type.equals("0") ? "APP_SERVICE" : " FROM_OTHER";	
				entity.serviceType = type;
				entity.name = app.getName();
				if ("903".equals(app.getCode()) && retMap!=null && retMap.get("WHS") != null) {
					entity.itemName  = "未还图书";
					entity.itemValue = StringUtil.isEmpty(retMap.get("WHS").toString())  ? 
										"0" : retMap.get("WHS").toString();
					entity.itemUnit  = "本";
				}else if ("506".equals(app.getCode()) && retMap!=null && retMap.get("YUE") != null) {
					entity.itemName  = "余额";
					entity.itemValue = StringUtil.isEmpty(retMap.get("YUE").toString()) ? 
							 			"0" : retMap.get("YUE").toString();
					entity.itemUnit  = "元";
				} else if ("904".equals(app.getCode()) && retMap!=null && retMap.get("YUE") != null) {
					entity.itemName  = "余额";
					entity.itemValue = StringUtil.isEmpty(retMap.get("YUE").toString()) ? 
										"0" : retMap.get("YUE").toString();
					entity.itemUnit  = "元";
				} else if ("906".equals(app.getCode()) && retMap!=null && retMap.get("SFGZ") != null) {
					entity.itemName  = "收入";
					entity.itemValue = StringUtil.isEmpty(retMap.get("YUE").toString()) ? 
										retMap.get("SFGZ").toString() : "0";
					entity.itemUnit  = "元";
				} else {
					entity.itemName  = "";
					entity.itemValue = "";
					entity.itemUnit  = "";
				}
				entity.icon = url + app.getTburl();
				entity.url = app.getAddr();
				entity.serviceCode = app.getCode();
				entity.bak ="";
				entityList.add(entity);
				if(infromation.equals("0")){
					logger.error("我的门户接口未读未还接口获取entity："+entity);
					}
			}
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infromation.equals("0")){
			logger.error("我的门户接口未读未还接口end");
		}
		
		String headPicturePath = null;
		//我的头像读取
		IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
		List<ImageDB> imageList = mobileCommonService.getMyPicture(userName);
		ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
		if(image == null){
			logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
			headPicturePath = "";
		}else{
			String path = image.getPath();
			byte[] content = image.getFileContent();
			String headname = image.getFileName();
			String filename = StringUtil.isEmpty(headname) ? userName+"headPicture" : headname;
			
			HttpServletRequest request = ServletActionContext.getRequest();
			
			if(content == null && StringUtil.isEmpty(path)){
				logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
				headPicturePath = "";
			}else{
				String pathFile = BaseHolder.getPropertiesValue("MyPicture","headPicture");
				String pathurl = request.getSession().getServletContext().getRealPath("/") + pathFile;
				File newFile = new File(pathurl);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				String url = request.getSession().getServletContext().getRealPath("/") + path;
				url = url.replace("\\", "/");
				File outFile = new File(url);
				if (!outFile.exists()) {
					try {
						outFile.createNewFile();
						
						if(content == null){
							logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
							headPicturePath = "";
						}
						ImageIO.setUseCache(false);
						ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
						BufferedImage newImage = ImageIO.read(byteArrayInputStream);
						ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename), outFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} 
				headPicturePath = getImageHost() + path;
			}
		}
			
		
		MyPortalEntity myPortalEntity = new MyPortalEntity(headPicturePath, entityList);
		ResultEntity result = new ResultEntity<MyPortalEntity>(1, "成功", myPortalEntity);
		return gson.toJson(result);
	}
	
	
}
