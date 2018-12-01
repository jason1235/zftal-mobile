package com.zfsoft.hrm.baseinfo.org.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.OrgType;
import com.zfsoft.orcus.lang.TimeUtil;

public class OrgInfo implements Serializable {

	private static final long serialVersionUID = -5753286709321608982L;
	
	private String oid;				//部门编号
	
	private String name;			//部门名称
	
	private OrgInfo parent;			//上级部门
	
	private String level;			//部门级别
	
	private String index;			//索引码
	
	private String orderCode;		//排序码
	
	private String type;			//部门类型
	
	private String prin;			//部门负责人工号
	
	private String manager;			//部门主管工号
	
	private boolean disused;		//是否废弃
	
	private Date createTime;		//创建时间
	
	private Date disuseTime;		//废弃时间
	
	private String remark;			//备注
	
	private String dwqc;//单位全称
	private String sszz;//所属总支
	private Integer bzs;//编制数
	
	private List<OrgInfo> children; //子部门序列
	
	/**
	 * 返回部门类型描述信息
	 */
	public OrgType getOrgTypeInfo() {
		
		if( type == null ) {
			return null;
		}
		
		return (OrgType) TypeFactory.getType( OrgType.class, type );
	}

	/**
	 * 显示创建时间
	 * @return
	 */
	public String getCreateTimeText() {
		return TimeUtil.format(createTime, "yyyy-MM-dd");
	}
	
	/**
	 * 显示废弃时间
	 * @return
	 */
	public String getDisuseTimeText() {
		return TimeUtil.format(disuseTime, "yyyy-MM-dd");
	}
	
	/**
	 * 显示是否废弃
	 * @return
	 */
	public String getDisusedText(){
		if(disused){
			return "是";
		}
		return "否";
	}
	
	public Person getPrinInfo(){
		IPersonUtil personUtil=(IPersonUtil)SpringHolder.getBean("personUtil");
		return personUtil.getPersonById(prin);
	}
	
	public Person getManagerInfo(){
		IPersonUtil personUtil=(IPersonUtil)SpringHolder.getBean("personUtil");
		return personUtil.getPersonById(manager);
	}
	
	public String getNameAndId(){
		if(StringUtils.isEmpty(this.type)){
			return this.name;
		}else{
			return new StringBuilder(this.name).append("(").append(this.oid).append(")").toString();
		}
	}
	/**
	 * 返回
	 * @return 
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * 设置
	 * @param oid 
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回
	 * @return 
	 */
	public OrgInfo getParent() {
		return parent;
	}

	/**
	 * 设置
	 * @param parent 
	 */
	public void setParent(OrgInfo parent) {
		this.parent = parent;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * 设置
	 * @param level 
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * 设置
	 * @param index 
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * 设置
	 * @param orderCode 
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置
	 * @param type 
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getPrin() {
		return prin;
	}

	/**
	 * 设置
	 * @param prin 
	 */
	public void setPrin(String prin) {
		this.prin = prin;
	}

	/**
	 * 返回
	 * @return 
	 */
	public boolean isDisused() {
		return disused;
	}

	/**
	 * 设置
	 * @param disuse 
	 */
	public void setDisused(boolean disused) {
		this.disused = disused;
	}

	/**
	 * 返回
	 * @return 
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置
	 * @param createTime 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 返回
	 * @return 
	 */
	public Date getDisuseTime() {
		return disuseTime;
	}

	/**
	 * 设置
	 * @param disuseTime 
	 */
	public void setDisuseTime(Date disuseTime) {
		this.disuseTime = disuseTime;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置
	 * @param remark 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<OrgInfo> getChildren() {
		return children;
	}

	/**
	 * 设置
	 * @param children 
	 */
	public void setChildren(List<OrgInfo> children) {
		this.children = children;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * 设置
	 * @param manager 
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getDwqc() {
		return dwqc;
	}

	public void setDwqc(String dwqc) {
		this.dwqc = dwqc;
	}

	public String getSszz() {
		return sszz;
	}

	public void setSszz(String sszz) {
		this.sszz = sszz;
	}

	public Integer getBzs() {
		return bzs;
	}

	public void setBzs(Integer bzs) {
		this.bzs = bzs;
	}
	
}
