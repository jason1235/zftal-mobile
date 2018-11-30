package com.zfsoft.hrm.baseinfo.org.query;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.orcus.lang.TimeUtil;

public class OrgQuery extends BaseQuery {

	private static final long serialVersionUID = -9194922381575553016L;
	
	private String oid;				//部门编号
	
	private String name;			//部门名称
	
	private OrgInfo parent;			//上级部门
	
	private String level;			//部门级别
	
	private String index;			//索引码
	
	private String orderCode;		//排序码
	
	private String type;			//部门类型
	
	private List<String> typeList;	//部门类型集合
	
	private String prin;			//部门负责人工号
	
	private String manager;			//部门主管工号
	
	private boolean disused;		//是否废弃
	
	private boolean useDisused = true;		//查询是否使用"废弃"字段
	
	private Date createTime;		//创建时间
	
	private Date createTimeMin;		//创建时间下限,时间段查询时用
	
	private Date createTimeMax;		//创建时间上限,时间段查询时用
	
	private Date disuseTime;		//废弃时间
	
	private Date disuseTimeMin;		//废弃时间下限,时间段查询时用
	
	private Date disuseTimeMax;		//废弃时间上限,时间段查询时用
	
	private String remark;			//备注
	
	private String sortCol;			//排序字段
	
	private String commonProperty;	//通用查询字段
	
	/**
	 * 显示创建时间
	 * @return
	 */
	public String getCreateTimeText() {
		return TimeUtil.format(createTime, "yyyy-MM-dd");
	}
	
	/**
	 * 显示创建时间下限
	 * @return
	 */
	public String getCreateTimeMinText() {
		return TimeUtil.format(createTimeMin, "yyyy-MM-dd");
	}
	
	/**
	 * 显示创建时间上限
	 * @return
	 */
	public String getCreateTimeMaxText() {
		return TimeUtil.format(createTimeMax, "yyyy-MM-dd");
	}
	
	/**
	 * 显示废弃时间
	 * @return
	 */
	public String getDisuseTimeText() {
		return TimeUtil.format(disuseTime, "yyyy-MM-dd");
	}
	
	/**
	 * 显示废弃时间下限
	 * @return
	 */
	public String getDisuseTimeMinText() {
		return TimeUtil.format(disuseTimeMin, "yyyy-MM-dd");
	}
	
	/**
	 * 显示废弃时间上限
	 * @return
	 */
	public String getDisuseTimeMaxText() {
		return TimeUtil.format(disuseTimeMax, "yyyy-MM-dd");
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
	public List<String> getTypeList() {
		return typeList;
	}

	/**
	 * 设置
	 * @param typeList 
	 */
	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
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
	 * @param disused 
	 */
	public void setDisused(boolean disused) {
		this.disused = disused;
	}

	/**
	 * 返回
	 * @return 
	 */
	public boolean isUseDisused() {
		return useDisused;
	}

	/**
	 * 设置
	 * @param useDisused 
	 */
	public void setUseDisused(boolean useDisused) {
		this.useDisused = useDisused;
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
	public Date getCreateTimeMin() {
		return createTimeMin;
	}

	/**
	 * 设置
	 * @param createTimeMin 
	 */
	public void setCreateTimeMin(Date createTimeMin) {
		this.createTimeMin = createTimeMin;
	}

	/**
	 * 返回
	 * @return 
	 */
	public Date getCreateTimeMax() {
		return createTimeMax;
	}

	/**
	 * 设置
	 * @param createTimeMax 
	 */
	public void setCreateTimeMax(Date createTimeMax) {
		this.createTimeMax = createTimeMax;
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
	public String getCommonProperty() {
		return commonProperty;
	}

	/**
	 * 设置
	 * @param commonProperty 
	 */
	public void setCommonProperty(String commonProperty) {
		this.commonProperty = commonProperty;
	}

	/**
	 * 返回
	 * @return 
	 */
	public Date getDisuseTimeMin() {
		return disuseTimeMin;
	}

	/**
	 * 设置
	 * @param disuseTimeMin 
	 */
	public void setDisuseTimeMin(Date disuseTimeMin) {
		this.disuseTimeMin = disuseTimeMin;
	}

	/**
	 * 返回
	 * @return 
	 */
	public Date getDisuseTimeMax() {
		return disuseTimeMax;
	}

	/**
	 * 设置
	 * @param disuseTimeMax 
	 */
	public void setDisuseTimeMax(Date disuseTimeMax) {
		this.disuseTimeMax = disuseTimeMax;
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
	public String getSortCol() {
		return sortCol;
	}

	/**
	 * 设置
	 * @param sortCol 
	 */
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
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

}
