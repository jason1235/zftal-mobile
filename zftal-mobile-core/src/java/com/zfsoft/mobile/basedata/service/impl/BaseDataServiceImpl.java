package com.zfsoft.mobile.basedata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.Config;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.query.CatalogQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.ICatalogService;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoClassService;
import com.zfsoft.mobile.basedata.dao.daointerface.IBaseDataDao;
import com.zfsoft.mobile.basedata.entity.InfoClazzMenuEntity;
import com.zfsoft.mobile.basedata.entity.InfoClazzServiceEntity;
import com.zfsoft.mobile.basedata.entity.ProcedureEntity;
import com.zfsoft.mobile.basedata.entity.ViewPropertyEntity;
import com.zfsoft.mobile.basedata.service.IBaseDataService;
import com.zfsoft.mobile.common.enums.ServiceSourceEnum;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IBusinessService;
import com.zfsoft.mobile.services.service.IServiceManagerService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author Administrator
 *
 */
public class BaseDataServiceImpl implements IBaseDataService {

    private IBaseDataDao baseDataDao;
    private ICatalogService catalogService;
    private IInfoClassService infoClassService;
    private String baseTypeDir;
    private IMobileCommonService mobileCommonService;
    private IServiceManagerService serviceManagerService;
    private IBusinessService businessService;
    
    /**
     * @return the baseDataDao
     */
    public IBaseDataDao getBaseDataDao() {
        return baseDataDao;
    }

    /**
     * @param baseDataDao the baseDataDao to set
     */
    public void setBaseDataDao(IBaseDataDao baseDataDao) {
        this.baseDataDao = baseDataDao;
    }

    /**
     * @return the catalogService
     */
    public ICatalogService getCatalogService() {
        return catalogService;
    }

    /**
     * @param catalogService the catalogService to set
     */
    public void setCatalogService(ICatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * @return the infoClassService
     */
    public IInfoClassService getInfoClassService() {
        return infoClassService;
    }

    /**
     * @param infoClassService the infoClassService to set
     */
    public void setInfoClassService(IInfoClassService infoClassService) {
        this.infoClassService = infoClassService;
    }

    /**
     * @return the baseTypeDir
     */
    public String getBaseTypeDir() {
        return baseTypeDir;
    }

    /**
     * @param baseTypeDir the baseTypeDir to set
     */
    public void setBaseTypeDir(String baseTypeDir) {
        this.baseTypeDir = baseTypeDir;
    }

    /**
     * @return the mobileCommonService
     */
    public IMobileCommonService getMobileCommonService() {
        return mobileCommonService;
    }

    /**
     * @param mobileCommonService the mobileCommonService to set
     */
    public void setMobileCommonService(IMobileCommonService mobileCommonService) {
        this.mobileCommonService = mobileCommonService;
    }

    /**
     * @return the serviceManagerService
     */
    public IServiceManagerService getServiceManagerService() {
        return serviceManagerService;
    }

    /**
     * @param serviceManagerService the serviceManagerService to set
     */
    public void setServiceManagerService(IServiceManagerService serviceManagerService) {
        this.serviceManagerService = serviceManagerService;
    }

    /**
     * 获取同步数据
     */
    @Override
    public PageList<DynaBean> getSynchronizedBaseData(DynaBeanQuery query) {
        PageList<DynaBean> pageList = new PageList<DynaBean>();
        Paginator paginator = new Paginator();
        if (query != null) {
            /*paginator.setItemsPerPage(query.getPerPageSize());
            int count = baseDataDao.getSynchronizedBaseDataCount(query);
            pageList.setPaginator(paginator);
            paginator.setItemsPerPage(query.getPerPageSize());
            paginator.setPage(query.getToPage());
            if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
            paginator.setItems(count);*/
            paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(baseDataDao.getSynchronizedBaseDataCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}

            if (paginator.getBeginIndex() <= paginator.getItems()) {
                query.setStartRow(paginator.getBeginIndex());
                query.setEndRow(paginator.getEndIndex());
                List<Map<String, Object>> list = baseDataDao.getSynchronizedBaseDataList(query);
                DynaBean dynaBean;
                
                for (Map<String, Object> m : list) {
                    dynaBean = new DynaBean(query.getClazz());
                    dynaBean.setValues(m);
                    pageList.add(dynaBean);
                }
            }
        }
        return pageList;
    }

    /**
     * 同步数据
     */
    @Override
    public Map<String, String> executeSynchronized(String procedureId) {
        ProcedureEntity procedure = getProceduresById(procedureId, "");
        Map<String, String> param = new HashMap<String, String>();
        String sqlProcedure = "{call Procedure_Name (Parameters)}";
        
        // 拼接SQL，设置参数
        String outParam = "#{out_code, mode=OUT, jdbcType=VARCHAR}, #{out_message, mode=OUT, jdbcType=VARCHAR}";
        sqlProcedure = sqlProcedure.replace("Procedure_Name", procedure.getProcedureName());
        sqlProcedure = sqlProcedure.replace("Parameters", outParam);
        
        param.put("procedure", sqlProcedure);
        baseDataDao.executeProcedure(param);
        
        return param;
    }

    /**
     * 取得存储过程
     */
    @Override
    public List<ProcedureEntity> getProcedures(String catalogId) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("catalogType", baseTypeDir);
        param.put("catalogId", catalogId);
        
        return baseDataDao.getProcedures(param);
    }

    /**
     * 取得目录
     */
    @Override
    public List<Catalog> getBaseCatalog() {
        CatalogQuery query=new CatalogQuery();
        query.setType(baseTypeDir);
        
        return catalogService.getFullList( query );
    }

    /**
     * 取得DB中存储过程
     */
    @Override
    public List<String> getDBProcedures() {
        return baseDataDao.getDBProcedures();
    }

    /**
     * 获取存储过程
     */
    @Override
    public ProcedureEntity getProceduresById(String procedureId, String classId) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("procedureId", procedureId);
        param.put("classId", classId);
        return baseDataDao.getProceduresById(param);
    }

    /**
     * 插入存储过程
     */
    @Override
    public void insertProcedure(ProcedureEntity model) {
        baseDataDao.insertProcedure(model);
    }

    /**
     * 修改存储过程
     */
    @Override
    public void modifyProcedure(ProcedureEntity model) {
        baseDataDao.modifyProcedure(model);
    }

    /**
     * 取得信息类信息
     */
    @Override
    public InfoClass getFullInfoClass(String classId) {
        return infoClassService.getFullInfoClass(classId);
    }

    /**
     * 修改显示属性
     */
    @Override
    public void modifyViewPro(ViewPropertyEntity vpModel) {
        baseDataDao.modifyViewPro(vpModel);
    }

    /**
     * 刷新显示属性
     */
    @Override
    public List<ViewPropertyEntity> refreshViewPro(String classId) {
        List<ViewPropertyEntity> vepros = new ArrayList<ViewPropertyEntity>();
        InfoClass infoClass = this.getFullInfoClass(classId);
        List<InfoProperty> vpros = infoClass.getViewables();
        List<ViewPropertyEntity> epros = baseDataDao.findViewPro(classId);
        
        if (vpros != null && vpros.size() > 0) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("classId", classId);
            param.put("vpros", vpros);
            
            vepros = baseDataDao.findViewProByPIds(param);
        }
        ViewPropertyEntity vpModel;
        
        // 刷新数据，将信息类可显示的属性全部置成画面可显示和查询条件
        if (epros != null && epros.size() > 0) {
            // 现有信息类字段和画面显示属性全部不匹配
            if (vepros == null || vepros.size() == 0) {
                vpModel = new ViewPropertyEntity();
                vpModel.setClassId(classId);
                baseDataDao.removeViewPro(vpModel);
                for (InfoProperty vp : vpros) {
                    vpModel = new ViewPropertyEntity();
                    vpModel.setClassId(classId);
                    vpModel.setPropertyId(vp.getGuid());
                    vpModel.setLviewProperty("c");
                    //vpModel.setConditionStatus("y");
                    baseDataDao.addViewPro(vpModel);
                }
            } else {
                // 部分匹配
                if (epros.size() != vepros.size()) {
                    for (ViewPropertyEntity ep : epros) {
                        boolean isExist = false;
                        for (ViewPropertyEntity vep : vepros) {
                            if (ep.getPropertyId().equals(vep.getPropertyId())) {
                                isExist = true;
                                break;
                            }
                        }
                        // 不存在
                        if (!isExist) {
                            baseDataDao.removeViewPro(ep);
                        }
                    }
                }
                
                if (vpros.size() != vepros.size()) {
                    for (InfoProperty vp : vpros) {
                        boolean isExist = false;
                        for (ViewPropertyEntity vep : vepros) {
                            if (vp.getGuid().equals(vep.getPropertyId())) {
                                isExist = true;
                                break;
                            }
                        }
                        // 不存在
                        if (!isExist) {
                            vpModel = new ViewPropertyEntity();
                            vpModel.setClassId(classId);
                            vpModel.setPropertyId(vp.getGuid());
                            vpModel.setLviewProperty("c");
                            //vpModel.setConditionStatus("y");
                            baseDataDao.addViewPro(vpModel);
                        }
                    }
                }
                
                
            }
        // 初始数据，将信息类可显示的属性全部置成画面可显示和查询条件
        } else {
            for (InfoProperty vp : vpros) {
                vpModel = new ViewPropertyEntity();
                vpModel.setClassId(classId);
                vpModel.setPropertyId(vp.getGuid());
                vpModel.setLviewProperty("c");
                //vpModel.setConditionStatus("y");
                baseDataDao.addViewPro(vpModel);
            }
        }
        
        return baseDataDao.findViewPro(classId);
    }
    
    /**
     * 查询显示属性
     */
    @Override
    public List<ViewPropertyEntity> findViewPro(String classId) {
        return baseDataDao.findViewPro(classId);
    }

    /**
     * 新增服务
     */
    @Override
    public void insertPublish(InfoClazzServiceEntity icsem) {
        // 调用维护服务接口
        String fwtbdz = mobileCommonService.getMinUploadImagePath(icsem.getFwtbid());
        ServiceManager sm = new ServiceManager();
        // 服务编码
        int maxWebFwbm = serviceManagerService.getMaxWebFwbm();
        String fwbm = String.valueOf(maxWebFwbm);
        icsem.setFwbm(fwbm);
        sm.setClassFwbm(fwbm);
        // 服务名称
        sm.setClassFwmc(icsem.getFwmc());
        // 服务类型
        sm.setClassFwlx(ServiceTypeEnum.WEB_SERVICE.getKey());
        // 服务链接
        if(StringUtil.isEmpty(Config.getString("caurl")) || 
				(!StringUtil.isEmpty(Config.getString("caurl")) && !Config.getString("caurl").equals("yes"))){
        	sm.setClassFwlj("ydht/ydht_getInfoClass.html?procode=${procode}&choice=${choice}&uid=${uid}&key=${key}&type=1");
        }else{
        	sm.setClassFwlj("ydht/ydht_getInfoClass.html");
        }
        // 服务图标地址
        sm.setClassFwtbdz(fwtbdz);
        // 服务图标ID
        sm.setClassFwtbid(icsem.getFwtbid());
        // 服务来源
        sm.setClassFwly(ServiceSourceEnum.INFOCLASS_SOURCE.getKey());
        // 创建者ID
        sm.setClassCjzid(SessionFactory.getUser().getYhm());
        // 更新者ID
        sm.setClassGxzid(SessionFactory.getUser().getYhm());
        // 设置choice为信息类id
        sm.setChoice(icsem.getXxlid());
        // 设置不为通用服务
        sm.setIscommon("0");
        
        //判断是否存在移动后台的服务,即procode编码为999的业务系统
		BusinessQuery ydhtQuery = new BusinessQuery();
		ydhtQuery.setProcode("999");
		businessService.getYdht(ydhtQuery);
        // 设置procode
        sm.setProcode("999");
        Business ydhtBusiness = businessService.getList(ydhtQuery).get(0);
        //设置所属业务系统
        sm.setClassSsywxt(ydhtBusiness.getClassId());
        
        if(ydhtBusiness.getClassSyzt().equals("1")){
        	sm.setClassFbzt("1");
        }else{
        	sm.setClassFbzt("0");
        }
        serviceManagerService.insert(sm);
        
        // 保存服务与信息类的关系
        baseDataDao.insertPublish(icsem);
    }
    
    /**
     * 维护服务
     */
    @Override
    public void modifyPublish(InfoClazzServiceEntity icsem) {
        // 调用维护服务接口
        String fwtbdz = mobileCommonService.getUploadImagePath(icsem.getFwtbid());
        ServiceManager sm = new ServiceManager();
        // 服务编码
        sm.setClassFwbm(icsem.getFwbm());
        // 服务名称
        sm.setClassFwmc(icsem.getFwmc());
        // 服务图标地址
        sm.setClassFwtbdz(fwtbdz);
        // 服务图标ID
        sm.setClassFwtbid(icsem.getFwtbid());
        // 更新者ID
        sm.setClassGxzid(SessionFactory.getUser().getYhm());
        serviceManagerService.update(sm);
    }

    /**
     * 取得信息类服务信息
     */
    @Override
    public InfoClazzServiceEntity getPublishService(String classId) {
        return baseDataDao.getPublishService(classId);
    }

	@Override
	public ArrayList<InfoClazzMenuEntity> getFirstMenuList(InfoClazzMenuEntity entity) {
		// TODO Auto-generated method stub
		return baseDataDao.getFirstMenuList(entity);
	}

	@Override
	public ArrayList<InfoClazzMenuEntity> getSecondMenuList(InfoClazzMenuEntity entity) {
		// TODO Auto-generated method stub
		return baseDataDao.getSecondMenuList(entity);
	}

	@Override
	public void insertMenu(InfoClazzMenuEntity menuModel) {
		// TODO Auto-generated method stub
		baseDataDao.insertMenu(menuModel);
	}

	@Override
	public void updateMenu(InfoClazzMenuEntity model) {
		// TODO Auto-generated method stub
		baseDataDao.updateMenu(model);
	}

	@Override
	public ArrayList<InfoClazzMenuEntity> getMenuList(
			InfoClazzMenuEntity menuTempModel) {
		// TODO Auto-generated method stub
		return baseDataDao.getMenuList(menuTempModel);
	}

	@Override
	public void deleteMenuItem(String cdId) {
		// TODO Auto-generated method stub
		baseDataDao.deleteMenuItem(cdId);
	}

	@Override
	public List<InfoClazzMenuEntity> getAllMenuList(String classId) {
		// TODO Auto-generated method stub
		return baseDataDao.getAllMenuList(classId);
	}

	@Override
	public String findZdmc(String propertyId) {
		// TODO Auto-generated method stub
		return baseDataDao.findZdmc(propertyId);
	}

	@Override
	public String findSxmc(String propertyId) {
		// TODO Auto-generated method stub
		return baseDataDao.findSxmc(propertyId);
	}

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public IBusinessService getBusinessService() {
		return businessService;
	}

	@Override
	public void modifyPicture(ProcedureEntity icsem) {
		// TODO Auto-generated method stub
		baseDataDao.modifyPicture(icsem);
	}

	@Override
	public void insertPicture(ProcedureEntity icsem) {
		// TODO Auto-generated method stub
		baseDataDao.insertPicture(icsem);
	}

	@Override
	public String getZymcFromJw(String zgh) {
		return baseDataDao.getZymcFromJw(zgh);
	}

	@Override
	public void deleteMenuProperty(Map<String,String> map) {
		baseDataDao.deleteMenuProperty(map);
	}


}
