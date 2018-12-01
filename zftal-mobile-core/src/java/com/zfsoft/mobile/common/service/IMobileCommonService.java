package com.zfsoft.mobile.common.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.file.entity.ImageDB;
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

/**
 * 
 * @author Administrator
 *
 */
public interface IMobileCommonService {
	
	/**
	 * 获取上传的服务器地址
	 * @param salaryList
	 */
	public String getNewsVedioPath(String vedioId);
	
	/**
	 * 教师工资信息更新及录入
	 * @param salaryList
	 */
	public void insertSalaryList(List<SalaryEntity> salaryList);
	
	/**
	 * 图书馆读者信息更新及插入
	 * @param userList
	 */
	public void insertLibUserList(List<LibraryUserEntity> userList);
	
	/**
	 * 图书馆借阅记录数据更新及插入
	 * @param businessList
	 */
	public void insertLibBusiList(List<LibraryBusinessEntity> businessList);
	
	/**
	 * 一卡通信息插入
	 * @param businessList
	 */
	public void insertCardBusiness(List<OauthCardBusinessEntity> businessList);
	
	/**
	 * 判断图片地址是否存在,true存在，false不存在
	 * @param url
	 * @param imageId
	 * @throws IOException 
	 */
	public boolean checkImage(String url, String imageId) throws IOException;
	

    /**
     * 获取上传图片的服务器地址
     * @param imageId
     * @return
     */
    public String getUploadImagePath(String imageId);
    public String getGoodsImagePath(String imageId,String folderName);
    
    /**
     * 根据用户、业务ID取得该用户的所有服务
     * @param userId
     * @param ywId
     * @return
     */
    public List<ServiceManager> getAllServiceByUser(String userId, String ywId);
    
    /**
     * 根据用户取得该用户的常用服务
     * @param userId
     * @return
     */
    public List<ServiceManager> getFrequentlyServiceByUser(String userId);

    /**
     * 取得用户的所有业务系统
     * @param userId
     * @return
     */
    public List<Business> getAllXtYwByUser(String userId);
    
    /**
     * 添加常用服务
     * @param userId
     * @param fwBm
     * @param isadd
     */
    public void insertFrequentlyService(String userId, String fwBm, boolean isadd);
    
    /**
     * 删除常用服务
     * @param userId
     * @param fwBm
     */
    public void deleteFrequentlyService(String userId, String fwBm);
    
    /**
     * 删除用户所有常用服务
     * @param userId
     */
    public void deleteAllFrequentlyService(String userId);
    
    /**
     * 更新常用服务
     * @param userId
     * @param fwBm
     * @param type 0-更新使用次数,1-更新热度 向下,2-更新热度 向上,3-更新热度 拖拽
     * @param toMark 拖拽的位置
     */
    public void modifyFrequentlyService(String userId, String fwBm, String type, int toMark);

    /**
     * 根据imageId在项目中生成图片
     */
	public boolean doImage(String url, String imageId) throws IOException;
    
    /**
     * 登录的服务
     * @param userId
     * @return
     */
    public List<ServiceManager> getLoginFw(Map<String, Object> param);
    
    /**
     * 服务地址模板替换
     * @param lst
     * @param userId
     * @return
     */
    public List<ServiceManager> getOpenUrl(List<ServiceManager> lst, String userId);
    
    /**
     * 通用服务服务地址模板替换
     * @param lst
     * @return
     */
    public List<ServiceManager> getOpenUrl(List<ServiceManager> lst);
//    /**
//     * 取得资讯类别
//     */
//    public List<> getAllNewsCategory();
//    
//    /**
//     * 根据资讯类别取得推荐资讯
//     */
//    public List<> getRecommendationByCategory(String categoryId);
//    
//    /**
//     * 根据资讯类别取得资讯（除推荐资讯）
//     */
//    public List<> getNewsByCategory(String categoryId);


	public List<MyPortal> getPortalUrl(List<MyPortal> apps, String userName);
	
	/**
	 * 获取我的门户图书馆未归还图书数，一卡通余额，工资信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getPortalInfo(Map<String,String> param);

	/**
	 * 获取应用中心和我的门户缩略图地址
	 * @param imageId
	 * @return
	 */
	public String getMinUploadImagePath(String imageId);

	/**
	 * 获取新闻资讯缩略图地址
	 * @param imageId
	 * @return
	 */
	public String getNewsUploadImagePath(String imageId);


	public void submitSuggestion(Map<String, String> param);


	public void insertMyPicture(ImageDB imageDB);


	public List<ImageDB> getMyPicture(String userId);


	public void updateMyPicture(ImageDB imageDB);


	public List<NumberDepartment> getAllDepartment(Map<String, String> map);


	public List<PersonEntity> getAllPerson(Map<String, String> map);


	public List<InformationListEntity> getAllInformationList();


	public void deleteTPByUserAndJmid(Map<String, String> map);


	public void insertTPByUserAndJmid(Map<String, String> map);


	public List<VoteActivity> getVoteList(String tphdid);


	public List<Program> getRankListByTphdid(String tphdid);


	public List<Program> getProgramList(String tphdid);


	public int getTPByUserAndJmid(Map<String, String> map);


	public PageList<PersonEntity> getPersonList(PersonQuery personQuery);


	public float getCardNumber(String userid);


	public int getodetailCount(CardBusinessQuery businessQuery);


	public PageList<CardBusinessEntity> getodetailList(
			CardBusinessQuery businessQuery);


	public int getcdetailCount(CardBusinessQuery businessQuery);


	public PageList<CardBusinessEntity> getcdetailList(
			CardBusinessQuery businessQuery);


	public String getCardKH(String userid);

	
	public PageList<WeiBoEntity> getWeiBoList(WeiBoEntity query);
	
	public List<ServiceManager> getOtherServiceByUser(Map<String, Object> params);


	public void visitsByFwbm(String fwbm);


	public List<NoticeInfoEntity> getNoticeInfo(String resource_id);


	public PageList<NoticeInfoEntity> getNoticeList(NoticeInfoEntity query);


	public List<String> getNoticeListNewTableType();


	public  List<CardBusinessEntity> getCardKHByXB(String userid);


	public int getdetailCountByXB(CardBusinessQuery businessQuery);


	public PageList<CardBusinessEntity> getdetailListByXB(
			CardBusinessQuery businessQuery);


	public List<ScalendarEntity> getTermWeek(String ymd);


	public List<ServiceManager> getCommonService();

	public PageList<MemoDB> getMyMemoList(MemoDB memoDB);

	public void insertMemo(MemoDB memoEntity);

	public void updateMemo(MemoDB memoEntity);

	public void deleteAllmemoCatalogByUser(String userId);

	public void insertmemoCatalogByUser(MemoCatalog memoCatalog);

	public List<MemoCatalog> getMemoCatalogList(String userId);

	public boolean checkMemo(MemoDB memoDB);

	public void deleteMyMemo(MemoDB memoQuery);

	public List<ThirdPartyEntity> selectThirdParty(ThirdPartyEntity thirdEntity);

	public void deleteThirdParty(ThirdPartyEntity thirdEntity);

	public void insertThirdParty(ThirdPartyEntity thirdEntity);

	public void insertMemoPicture(MemoPictureEntity pictureEnity);

	public int getMemoPicture(MemoPictureEntity pictureEnity);
	
	public void deleteMyMemoPicture(String memoFileName);

	public void insertVisitsToZFByFwbm(Map<String, String> map);

	public void checkMemoPictureEntity(String memoFileName);

	/** 
	* @author: zhangxu
	* @Title: getAllServiceNotreading 
	* @Description: 
	* @param @return    设定文件 
	* @return List<ServiceNotReading>    返回类型 
	* @throws 
	*/
	public List<ServiceNotReading> getAllServiceNotreadingByUserName(String username);

	public List<ServiceNotReading> getAllServiceNotreading();


	//public String getYearWeekStart(String code);


	//public String getYearWeekEnd(String code);


	

}
