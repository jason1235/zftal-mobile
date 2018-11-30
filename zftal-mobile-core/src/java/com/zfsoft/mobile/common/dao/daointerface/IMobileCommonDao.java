package com.zfsoft.mobile.common.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.file.entity.ImageDB;
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
public interface IMobileCommonDao {
	
	/**
	 * 根据服务编码获取当前日期访问量是否存在
	 * @param fwbm
	 * @return
	 */
	public int getVisitsByFwbm(String fwbm);
	
	/**
	 * 根据服务编码插入当前日期的应用访问量记录
	 * @param fwbm
	 */
	public void insertVisitsByFwbm(String fwbm);
	
	/**
	 *  根据服务编码更新当前日期安装统计
	 * @param fwbm
	 */
	public void updateVisitsByFwbm(String fwbm);

    /**
     * 获取上传图片的名称
     * @param imageId
     * @return
     */
    public String getUploadImageName(String imageId);
    
    /**
     * 获取上传图片的名称
     * @param imageId
     * @return
     */
    public String getUploadFileName(String fjbh);

    /**
     * 根据用户取得该用户的所有服务
     * @param param
     * @return
     */
    public List<ServiceManager> getAllServiceByUser(Map<String, Object> param);
    
    /**
     * 根据用户取得该用户的所有服务数量
     * @param param
     * @return
     */
    public int getAllServiceByUserCnt(Map<String, Object> param);

    /**
     * 根据用户取得该用户的常用服务
     * @param userId
     * @return
     */
    public List<ServiceManager> getFrequentlyServiceByUser(Map<String, Object> params);

    /**
     * 取得用户的所有业务系统
     * @param userId
     * @return
     */
    public List<Business> getAllXtYwByUser(String userId);

    /**
     * 添加常用服务
     * @param param
     */
    public void insertFrequentlyService(Map<String, Object> param);

    /**
     * 删除常用服务
     * @param param
     */
    public void deleteFrequentlyService(Map<String, Object> param);
    
    /**
     * 删除用户的所有常用服务
     * @param param
     */
    public void deleteAllFrequentlyService(Map<String, Object> param);

    /**
     * 更新常用服务
     * @param param
     */
    public void modifyFrequentlyService(Map<String, Object> param);
    
    /**
     * 更新常用服务--上移
     * @param param
     */
    public void upFrequentlyService(Map<String, Object> param);

    /**
     * 更新常用服务--下移
     * @param param
     */
    public void downFrequentlyService(Map<String, Object> param);
    
    /**
     * 更新常用服务--交换
     * @param param
     */
    public void swapFrequentlyService(Map<String, Object> param);
    
    /**
     * 更新常用服务--拖拽
     * @param param
     */
    public void moveFrequentlyService(Map<String, Object> param);
    
    /**
     * 取得需要交换的服务
     * @param param
     */
    public List<Map<String, Object>> getSwapFrequentlyService(Map<String, Object> param);
    
    /**
     * 取得服务热度
     * @param param
     * @return
     */
    public int getRd(Map<String, Object> param);

    /**
     * 获取图片
     * @param imageId
     * @return
     */
	public ImageDB findImageById(String imageId);

	/**
	 * 登录的服务
	 * @param param
	 * @return
	 */
	public List<ServiceManager> getLoginFw(Map<String, Object> param);
	
	/**
	 * 获取我的门户图书馆未归还图书数，一卡通余额，工资信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getPortalInfo(Map<String,String> param);
	
	public void submitSuggestion(Map<String, String> param);

	public void insertMyPicture(ImageDB imageDB);

	public List<ImageDB> getMyPicture(String userId);

	public void updateMyPicture(ImageDB imageDB);

	public List<NumberDepartment> getAllDepartment(Map<String, String> map);

	public List<PersonEntity> getAllPerson(Map<String, String> map);

	public List<InformationListEntity> getAllInformationList();

	public void deleteFwbmFromYhqx(String fwBm);

	public void deleteFrequentlyServiceNotInYhqx(Map<String, Object> params);

	public void deleteTPByUserAndJmid(Map<String, String> map);

	public void insertTPByUserAndJmid(Map<String, String> map);

	public List<VoteActivity> getVoteList(String tphdid);

	public List<Program> getProgramList(String tphdid);

	public List<Program> getRankListByTphdid(String tphdid);

	public int getTPByUserAndJmid(Map<String, String> map);

	public int getPersonCount(PersonQuery query);

	public PageList<PersonEntity> getPersonList(PersonQuery query);

	public float getCardNumber(String userid);

	public int getodetailCount(CardBusinessQuery businessQuery);

	public List<CardBusinessEntity> getodetailList(
			CardBusinessQuery businessQuery);

	public int getcdetailCount(CardBusinessQuery businessQuery);

	public List<CardBusinessEntity> getcdetailList(
			CardBusinessQuery businessQuery);

	public String getCardKH(String userid);

	public int getWeiBoListCount(WeiBoEntity query);

	public List<WeiBoEntity> getWeiBoList(WeiBoEntity query);

	public List<ServiceManager> getOtherServiceByUser(Map<String, Object> params);

	public List<NoticeInfoEntity> getNoticeInfo(String resource_id);

	public int getNoticeListCount(NoticeInfoEntity query);

	public List<NoticeInfoEntity> getNoticeList(NoticeInfoEntity query);

	public List<String> getNoticeListNewTableType();

	public  List<CardBusinessEntity> getCardKHByXB(CardBusinessQuery query);

	public int getdetailCountByXB(CardBusinessQuery businessQuery);

	public List<CardBusinessEntity> getdetailListByXB(
			CardBusinessQuery businessQuery);

	public List<CardBusinessEntity> getCardByUser(Map<String, Object> param);

	public List<ScalendarEntity> getTermWeek(String ymd);

	public List<ServiceManager> getCommonService();

	public int getBsiCountByLsh(String lsh);

	public void updateBsiByLsh(OauthCardBusinessEntity entity);

	public void insertBsi(OauthCardBusinessEntity entity);
	
	

	public int getLibBusCountById(String lsh);

	public void updateLibBusById(LibraryBusinessEntity entity);

	public void insertLibBus(LibraryBusinessEntity entity);
	
	

	public void insertLibUser(LibraryUserEntity entity);

	public void updateLibUserById(LibraryUserEntity entity);

	public int getLibUserCountById(String ryh);
	
	

	public void insertSal(SalaryEntity entity);

	public void updateSalById(SalaryEntity entity);

	public int getSalCountById(String globalid);

	public int getMyMemoListCount(MemoDB query);

	public List<MemoDB> getMyMemoList(MemoDB query);

	public void insertMemo(MemoDB memoEntity);

	public void updateMemo(MemoDB memoEntity);

	public void deleteAllmemoCatalogByUser(String userId);

	public void insertmemoCatalogByUser(MemoCatalog memoCatalog);

	public List<MemoCatalog> getMemoCatalogList(String userId);

	public void deleteMyMemo(MemoDB memoQuery);

	public List<ThirdPartyEntity> selectThirdParty(ThirdPartyEntity thirdEntity);

	public void deleteThirdParty(ThirdPartyEntity thirdEntity);

	public void insertThirdParty(ThirdPartyEntity thirdEntity);

	public void insertMemoPicture(MemoPictureEntity pictureEnity);

	public int getMemoPicture(MemoPictureEntity pictureEnity);
	
	public void deleteMyMemoPicture(String memoFileName);

	public void insertVisitsToZFByFwbm(Map<String, String> map);

	public void updateVisitsToZFByFwbm(Map<String, String> map);

	public List<MemoPictureEntity> getMemoPictureList(String memoFileName);

	/** 
	* @author: zhangxu
	* @Title: getAllServiceNotreading 
	* @Description: 
	* @param @return    设定文件 
	* @return List<ServiceNotReading>    返回类型 
	* @throws 
	*/
	public List<ServiceNotReading> getAllServiceNotreadingByUserName(Map<String,Object> params);

	public List<ServiceNotReading> getAllServiceNotreading();


	//public String getYearWeekStart(String code);

	//public String getYearWeekEnd(String code);
}
