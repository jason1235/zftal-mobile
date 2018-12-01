package com.zfsoft.mobile.basedata.dao.daointerface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.mobile.basedata.entity.InfoClazzMenuEntity;
import com.zfsoft.mobile.basedata.entity.InfoClazzServiceEntity;
import com.zfsoft.mobile.basedata.entity.ProcedureEntity;
import com.zfsoft.mobile.basedata.entity.ViewPropertyEntity;

/**
 * 
 * @author Administrator
 *
 */
public interface IBaseDataDao {

    /**
     * 获取同步数据条数
     * @param query
     * @return
     */
    public int getSynchronizedBaseDataCount(DynaBeanQuery query);

    /**
     * 获取同步数据
     * @param query
     * @return
     */
    public List<Map<String, Object>> getSynchronizedBaseDataList(DynaBeanQuery query);

    /**
     * 执行数据存储
     * @param param
     * @return
     */
    public String executeProcedure(Map<String, String> param);

    /**
     * 获取存储过程
     * @param param
     * @return
     */
    public List<ProcedureEntity> getProcedures(Map<String, String> param);
    
    /**
     * 获取存储过程
     * @param param
     * @return
     */
    public ProcedureEntity getProceduresById(Map<String, String> param);
    
    /**
     * 插入存储过程
     * @param param
     * @return
     */
    public void insertProcedure(ProcedureEntity entity);
    
    /**
     * 修改存储过程
     * @param param
     * @return
     */
    public void modifyProcedure(ProcedureEntity entity);
    
    /**
     * 取得DB中存储过程
     * @return
     */
    public List<String> getDBProcedures();

    /**
     * 增加显示属性
     * @param vpModel
     */
    public void addViewPro(ViewPropertyEntity vpModel);

    /**
     * 移除显示属性
     * @param vpModel
     */
    public void removeViewPro(ViewPropertyEntity vpModel);

    /**
     * 修改显示属性
     * @param vpModel
     */
    public void modifyViewPro(ViewPropertyEntity vpModel);

    /**
     * 获取显示属性
     * @param classId
     */
    public List<ViewPropertyEntity> findViewPro(String classId);

    /**
     * 获取显示属性
     * @param vpModel
     * @return
     */
    public List<ViewPropertyEntity> findViewProByPIds(Map<String, Object> param);

    /**
     * 保存服务与信息类的关系
     * @param icsem
     */
    public void insertPublish(InfoClazzServiceEntity icsem);

    /**
     * 取得信息类服务信息
     * @param classId
     * @return
     */
    public InfoClazzServiceEntity getPublishService(String classId);

    /**
     * 获取所有以及菜单
     * @param classId
     * @return
     */
	public ArrayList<InfoClazzMenuEntity> getFirstMenuList(InfoClazzMenuEntity entity);

	/**
	 * 获取所有二级菜单
	 * @param classId
	 * @return
	 */
	public ArrayList<InfoClazzMenuEntity> getSecondMenuList(InfoClazzMenuEntity entity);
	
	/**
	 * 修改排序
	 */
	public void modifyMenuPxm(InfoClazzMenuEntity infoClassMenu);
	
	/**
	 * 增加信息类菜单
	 */
	public void insertMenu(InfoClazzMenuEntity infoClassMenu);

	public void updateMenu(InfoClazzMenuEntity model);
	
	public ArrayList<InfoClazzMenuEntity> getMenuList(InfoClazzMenuEntity infoClassMenu);

	public void deleteMenuItem(String classCdid);

	public List<InfoClazzMenuEntity> getAllMenuList(String infoClassId);

	public String findZdmc(String propertyId);

	public String findSxmc(String propertyId);
	
	public void removePublish(String fwbm);

	public void modifyPicture(ProcedureEntity icsem);

	public void insertPicture(ProcedureEntity icsem);

	public String getZymcFromJw(String zgh);

	public void deleteMenuProperty(Map<String,String> map);

}
