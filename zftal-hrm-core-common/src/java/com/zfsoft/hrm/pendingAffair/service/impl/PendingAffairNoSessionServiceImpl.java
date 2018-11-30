package com.zfsoft.hrm.pendingAffair.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dataprivilege.dao.IDataPrivilegeDao;
import com.zfsoft.dataprivilege.dto.AbstractFilter;
import com.zfsoft.dataprivilege.dto.DeptFilter;
import com.zfsoft.dataprivilege.entity.DataPrivilege;
import com.zfsoft.dataprivilege.filter.impl.DealDeptFilter;
import com.zfsoft.dataprivilege.util.ResourceUtil;
import com.zfsoft.dataprivilege.xentity.Context;
import com.zfsoft.dataprivilege.xentity.Param;
import com.zfsoft.dataprivilege.xentity.Resource;
import com.zfsoft.hrm.pendingAffair.dao.IPendingAffairInfoDao;
import com.zfsoft.hrm.pendingAffair.dao.IPendingAffairNoSessionDao;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;
import com.zfsoft.hrm.pendingAffair.enums.PendingAffairBusinessTypeEnum;
import com.zfsoft.hrm.pendingAffair.service.svcinterface.IPendingAffairNoSessionService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.jaxb.JaxbUtil;

public class PendingAffairNoSessionServiceImpl implements IPendingAffairNoSessionService {

    private IPendingAffairInfoDao pendingAffairInfoDao;
    private IPendingAffairNoSessionDao paNoSessionDao;
    
    /**
     * 
     */
    @Override
    public List<PendingAffairInfo> getListByRoles(User user) {
        PendingAffairInfo query = new PendingAffairInfo();
        query.setQueryRoleIds(user.getJsdms());
        String expression = getCondition("t", "bm_id", user);
        if (expression != null && !expression.trim().isEmpty()) {
            query.setExpression(expression);
        }
        return this.getNewList(pendingAffairInfoDao.findByRoleIds(query));
    }
    
    /**
     * @return the pendingAffairInfoDao
     */
    public IPendingAffairInfoDao getPendingAffairInfoDao() {
        return pendingAffairInfoDao;
    }


    /**
     * @param pendingAffairInfoDao the pendingAffairInfoDao to set
     */
    public void setPendingAffairInfoDao(IPendingAffairInfoDao pendingAffairInfoDao) {
        this.pendingAffairInfoDao = pendingAffairInfoDao;
    }

    /**
     * @return the paNoSessionDao
     */
    public IPendingAffairNoSessionDao getPaNoSessionDao() {
        return paNoSessionDao;
    }

    /**
     * @param paNoSessionDao the paNoSessionDao to set
     */
    public void setPaNoSessionDao(IPendingAffairNoSessionDao paNoSessionDao) {
        this.paNoSessionDao = paNoSessionDao;
    }

    /**
     * 方法描述：重新组装LIST
     * @param list
     * @return
     */
    private List<PendingAffairInfo> getNewList(List<PendingAffairInfo> list) {
        Map<String, String> typeMap = PendingAffairBusinessTypeEnum.getMap();
        if (list != null && list.size() > 0) {
            for (PendingAffairInfo pa : list) {
                if (pa != null && StringUtil.isNotEmpty(pa.getAffairType())
                        && StringUtil.isNotEmpty(typeMap.get(pa.getAffairType()))) {
                    pa.setAffairName(typeMap.get(pa.getAffairType()).replace("@@", String.valueOf(pa.getSumNumber())));
                }
            }
        }
        return list;
    }
    
    /**
     * 重写部门过滤
     * @param aslisName
     * @param fieldName
     * @return
     */
    private String getCondition(String aslisName, String fieldName, User user){
        Resource resource = new Resource();
        
        resource.setParam(new ArrayList<Param>());
        Param param = new Param();
        param.setName("otherName");
        param.setValue(aslisName);
        resource.getParam().add(param);
        param = new Param();
        param.setName("deptField");
        param.setValue(fieldName);
        resource.getParam().add(param);
        Context context = ResourceUtil.getContextById("bmgl");

        //取得部门
        List<DataPrivilege> dataPrivilegesAll = getDataPrivilege(context.getId(), user.getYhm());
        String dwm = paNoSessionDao.getDwmByUserId(user.getYhm());

        if (dataPrivilegesAll.size() <= 0) {//默认查询的是本部门
            
            if (dwm == null) {//部门为空则返回不成立表达式
                return " 1=2 ";
            }
            List<String> strs = new ArrayList<String>();
            strs.add("'" + dwm.toString() + "'");
            return getChildCondition(resource, strs);
        }
        List<DataPrivilege> dataPrivileges = new ArrayList<DataPrivilege>();
        for (DataPrivilege dataPrivilege : dataPrivilegesAll) {
            if (user.getJsdms().indexOf(dataPrivilege.getRoleId()) != -1) {
                dataPrivileges.add(dataPrivilege);
            }
        }
        if (dataPrivileges.size() == 0) {
            if (dwm == null) {//部门为空则返回不成立表达式
                return " 1=2 ";
            }
            List<String> strs = new ArrayList<String>();
            strs.add("'" + dwm.toString() + "'");
            return getChildCondition(resource, strs);
        }
        //开始查看权限，并组装数据权限
        String condition="";
        int i = 0;
        for (DataPrivilege dataPrivilege : dataPrivileges) {
            DeptFilter deptFilter = (DeptFilter)getObjectFromXml(dataPrivilege.getXmlValue());//将xml解析成对象
            
            String typeObj = deptFilter.getDataType();//取得部门过滤类型
            Object objlist = deptFilter.getOrgList();//取得组织机构代码列表
            
            String childcondition = "";
            if (typeObj != null) {
                if (DealDeptFilter.TYPE_SELF.equalsIgnoreCase(typeObj.toString())) {//本部门
                    if (dwm == null && dataPrivileges.size() <= 1) {
                        return " 1=2 ";
                    }
                    List<String> strs = new ArrayList<String>();
                    strs.add("'" + dwm.toString() + "'");
                    childcondition = getChildCondition(resource, strs);
                } else if (DealDeptFilter.TYPE_IN.equalsIgnoreCase(typeObj.toString())) {//多个部门
                    if (objlist != null) {
                        @SuppressWarnings("unchecked")
                        List<String> list = (List<String>)objlist;
                        int index = 0;
                        for (String string : list) {
                            list.set(index, "'" + string + "'");
                            index++;
                        }
                        childcondition = getChildCondition(resource, list);
                    } else {
                        childcondition = "1=2";
                    }
                } else {//全部
                    return " 1=1 ";
                }
            }
            if (i < dataPrivileges.size() - 1) {
                condition = condition + " (" + childcondition + ") or ";
            } else {
                condition = "("+condition+" (" + childcondition + ") )";
            }
            i++;
        }
        
        return condition;
    }
    
    private List<DataPrivilege> getDataPrivilege(String contextId, String yhm) {
        Context context = ResourceUtil.getContextById(contextId);
        IDataPrivilegeDao dataPrivilegeDao = (IDataPrivilegeDao)SpringHolder.getBean("dataPrivilegeDao");
        DataPrivilege query = new DataPrivilege();
        query.setUserId(yhm);
        query.setFilterId(context.getId());
        List<DataPrivilege> dataPrivileges = dataPrivilegeDao.findByQuery(query);
        return dataPrivileges;
    }
    
    private String getChildCondition(Resource resource, List<String> list) {
        String childCondition = "";
        if (!StringUtil.isEmpty(resource.getParamValue("otherName"))) {
            childCondition += resource.getParamValue("otherName") + ".";
        }
        String str=list.toString();
        
        if (list.size() > 0) {
            return childCondition+resource.getParamValue("deptField")+" in ("+str.substring(1, str.length()-1)+")";
        } else {
            return " 1=2 ";
        }
        
    }
    
    private AbstractFilter getObjectFromXml(String value) {
        return (DeptFilter)JaxbUtil.getObjectFromXml(value, DeptFilter.class);
    }
    

}
