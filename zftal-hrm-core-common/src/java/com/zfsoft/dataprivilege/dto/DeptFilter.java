package com.zfsoft.dataprivilege.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dataprivilege.filter.impl.DealDeptFilter;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.config.ICodeConstants;

/**
 * 部门过滤数据封装对象
 * @author Patrick Shen
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="deptfilter")
public class DeptFilter extends AbstractFilter implements Serializable  {

	private static final long serialVersionUID = -7835884057136885210L;
	
	public DeptFilter(){
		this.filterId="bmgl";
	}
	@XmlAttribute(name="filtertype")
	private String dataType;
	
	@XmlElement(name="deptid")
	private List<String> orgList = new ArrayList<String>();
	
	public String getViewOrgs(int maxlength){
		String dwmc = getOwnerOrg();
		
		if(DealDeptFilter.TYPE_SELF.equals(dataType)){
//			return "本部门";
			return dwmc;
		}
		
		if(DealDeptFilter.TYPE_ALL.equals(dataType)){
			return "全部";
		}
		if(orgList.size() == 0){
			return "";
		}
		int i = 0;
		StringBuffer sb = new StringBuffer();
		while(i < orgList.size()){
			if(i > 0){
				sb.append(",");
			}
			sb.append(CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, orgList.get(i)));
			i++;
		}
		String result = sb.toString();
		if(result.length() == 0){
			return "";
		}
		else if(result.length() <= maxlength){
			return result;
		}
		else{
			return result.substring(0, maxlength) + " ...";
		}
	}

	public String getWholeOrgsText(){
		String dwmc = getOwnerOrg();
		
		if(DealDeptFilter.TYPE_SELF.equals(dataType)){
//			return "本部门";
			return dwmc;
		}
		if(DealDeptFilter.TYPE_ALL.equals(dataType)){
			return "全部";
		}
		if(orgList.size() == 0){
			return "";
		}
		int i = 0;
		StringBuffer sb = new StringBuffer();
		while(i < orgList.size()){
			if(i > 0){
				sb.append(",<br />");
			}
			sb.append(CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, orgList.get(i)));
			i++;
		}
		if(orgList.size() > 1){
			sb.append("  等部门");
		}
		return sb.toString();
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * 设置
	 * @param dataType 
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<String> getOrgList() {
		if(orgList==null){
			orgList=new ArrayList<String>();
		}
		return orgList;
	}

	/**
	 * 设置
	 * @param orgList 
	 */
	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}

	/**
	 * 本部门转换为部门名称
	 * @return
	 */
	private String getOwnerOrg() {
		String dwm="";
		DynaBean dynaBean=DynaBeanUtil.getPerson(userId);
		if(dynaBean==null||dynaBean.getValue("dwm")==null){
			dwm = "";
		}else{
			dwm = (String)dynaBean.getValue("dwm");
		}
		return CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, dwm);
	}
}
