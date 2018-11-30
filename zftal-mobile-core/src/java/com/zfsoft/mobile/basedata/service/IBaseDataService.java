package com.zfsoft.mobile.basedata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.mobile.basedata.entity.InfoClazzMenuEntity;
import com.zfsoft.mobile.basedata.entity.InfoClazzServiceEntity;
import com.zfsoft.mobile.basedata.entity.ProcedureEntity;
import com.zfsoft.mobile.basedata.entity.ViewPropertyEntity;

/**
 * 
 * @author Administrator
 *
 */
public interface IBaseDataService {

    /**
     * 获取同步数据
     * @param query
     * @return
     */
    public PageList<DynaBean> getSynchronizedBaseData(DynaBeanQuery query);

    /**
     * 同步数据
     * @param procedureId 
     * @param paramVal 
     * @return
     */
    public Map<String, String> executeSynchronized(String procedureId);

    /**
     * 取得存储过程
     * @param catalogId 
     */
    public List<ProcedureEntity> getProcedures(String catalogId);

    /**
     * 取得目录
     * @return
     */
    public List<Catalog> getBaseCatalog();

    /**
     * 取得DB中存储过程
     * @return
     */
    public List<String> getDBProcedures();

    /**
     * 获取存储过程
     * @param procedureId
     * @param classId
     * @return
     */
    public ProcedureEntity getProceduresById(String procedureId, String classId);

    /**
     * 插入存储过程
     * @param model
     */
    public void insertProcedure(ProcedureEntity model);

    /**
     * 修改存储过程
     * @param model
     */
    public void modifyProcedure(ProcedureEntity model);

    /**
     * 取得信息类信息
     * @param classId
     * @return
     */
    public InfoClass getFullInfoClass(String classId);
    
    /**
     * 查询显示属性
     */
    public List<ViewPropertyEntity> findViewPro(String classId);

    /**
     * 修改显示属性
     * @param vpModel
     */
    public void modifyViewPro(ViewPropertyEntity vpModel);

    /**
     * 刷新显示属性
     * @param classId
     */
    public List<ViewPropertyEntity> refreshViewPro(String classId);

    /**
     * 新增服务
     * @param icsem
     * @param hRequest 
     */
    public void insertPublish(InfoClazzServiceEntity icsem);
    
    /**
     * 维护服务
     * @param icsem
     * @param hRequest 
     */
    public void modifyPublish(InfoClazzServiceEntity icsem);

    /**
     * 取得信息类服务信息
     * @param classId
     * @return
     */
    public InfoClazzServiceEntity getPublishService(String classId);

	public ArrayList<InfoClazzMenuEntity> getFirstMenuList(InfoClazzMenuEntity entity);

	public ArrayList<InfoClazzMenuEntity> getSecondMenuList(InfoClazzMenuEntity entity);

	public void insertMenu(InfoClazzMenuEntity menuModel);

	public void updateMenu(InfoClazzMenuEntity model);

	public ArrayList<InfoClazzMenuEntity> getMenuList(InfoClazzMenuEntity menuTempModel);

	public void deleteMenuItem(String cdId);

	public List<InfoClazzMenuEntity> getAllMenuList(String classId);

	public String findZdmc(String propertyId);

	public String findSxmc(String propertyId);

	public void modifyPicture(ProcedureEntity model);

	public void insertPicture(ProcedureEntity model);

	public String getZymcFromJw(String zgh);

	public void deleteMenuProperty(Map<String,String> map);


}
