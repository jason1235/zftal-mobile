package com.zfsoft.hrm.staffturn.retire.entities;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.util.date.DateTimeUtil;


/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-30
 * @version V1.0.0
 */
@Table("RYYD_TXRYB")
public class RetireInfo extends MyBatisBean{
	
	@SQLField(value="gh",key=true)
	private String userId;				//退休人员工号
	@SQLField(value="txsj")
	private Date retireTime;			//退休时间
	@SQLField(value="txzt")
	private Integer state=0;			//退休状态 0预退休 1退休
	@SQLField(value="txzw")
	private String retirePost;			//退休行政级别
	@SQLField(value="txlb")
	private String retireType;			//退休类型 这些都是取代码表的
	@SQLField(value="txzyjb")
	private String retirePostLevel;		//退休专业级别
	@SQLField(value="grdj")
	private String grdj;		//工人等级
	@SQLField(value="sfgl")
	private Boolean lonely=false;				//是否孤老0不是 1是
	@SQLField(value="sfzc")
	private Boolean turnout=false;			//是否转出 0不是 1是
	@SQLField(value="txjbf")
	private double retireBaseFee;		//退休基本费 
	@SQLField(value="txbt")
	private double retireSubsidy;		//退休补贴 金钱类型
	@SQLField(value="bz")
	private String remark;				//备注
	@SQLField(value="txzbh")
	private String identifier;			//退休证编号
	@SQLField(value="txwh")
	private String num;			//退休文号
	
	private String txsgwgz;				//退休时岗位工资
	private String txsxzjgz;			//退休时薪级工资
	private String jbtxfbl;			    //基本退休费
	
	private String workAge;				//工龄
	private DynaBean overall;			//人员信息
	
	// 20140424 add start
	@SQLField(value="ytxsj")
	private Date oldRetireTime;  // 原退休时间
	private String receiver;     // 退休通知人员
	// 20140424 add end
	@SQLField(value="cljg")
	private String doResult;     //处理结果
	@SQLField(value="ypsj")
	private Date employTime;     //延聘时间
	
	@SQLField
	private String lthxsjbm;// 离退后享受级别
	@SQLField
	private String ltxfzfdw; // 离退休费支付单位
	@SQLField
	private String lthgldw; // 离退后管理单位
	@SQLField
	private String ydazdd;// 异地安置地点
	
	public String getTxsgwgz() {
		return txsgwgz;
	}

	public void setTxsgwgz(String txsgwgz) {
		this.txsgwgz = txsgwgz;
	}

	public String getTxsxzjgz() {
		return txsxzjgz;
	}

	public void setTxsxzjgz(String txsxzjgz) {
		this.txsxzjgz = txsxzjgz;
	}

	public String getJbtxfbl() {
		return jbtxfbl;
	}

	public void setJbtxfbl(String jbtxfbl) {
		this.jbtxfbl = jbtxfbl;
	}
	
	public String getRetireTimeString() {
		if(retireTime==null){
			return "";
		}
		return DateTimeUtil.getFormatDate(getRetireTime(),"yyyy-MM-dd");
	}
	
	public DynaBean getOverall() {
		return overall;
	}
	public void setOverall(DynaBean overall) {
		this.overall = overall;
	}
	public String getRetireType() {
		return retireType;
	}
	
	public String getRetireTypeValue() {
		String str = CodeUtil.getItemValue(ICodeConstants.RETIRE_TYPE, getRetireType());
		if (StringUtils.isEmpty(str)) {
			return getRetireType();
		}
		return str;
	}
	public void setRetireType(String retireType) {
		this.retireType = retireType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIdentifier(){
		return identifier;
	}
	public void setIdentifier(String identifier){
		this.identifier=identifier;
	}
	public Boolean getLonely() {
		return lonely;
	}
	public void setLonely(boolean lonely) {
		this.lonely = lonely;
	}
	public Boolean getTurnout() {
		return turnout;
	}
	public void setTurnout(boolean turnout) {
		this.turnout = turnout;
	}
	public double getRetireBaseFee() {
		return retireBaseFee;
	}
	public void setRetireBaseFee(double retireBaseFee) {
		this.retireBaseFee = retireBaseFee;
	}
	public double getRetireSubsidy() {
		return retireSubsidy;
	}
	public void setRetireSubsidy(double retireSubsidy) {
		this.retireSubsidy = retireSubsidy;
	}
	public String getUserId() {
		if(userId != null &&userId.trim().equals(""))
			return null;
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getRetireTime() {
		return retireTime;
	}
	public void setRetireTime(Date retireTime) {
		this.retireTime = retireTime;
	}
	
	public String getRetirePost() {
		return retirePost;
	}
	public void setRetirePost(String retirePost) {
		this.retirePost = retirePost;
	}
	

	public String getRetirePostLevel() {
		return retirePostLevel;
	}

	public void setRetirePostLevel(String retirePostLevel) {
		this.retirePostLevel = retirePostLevel;
	}

	/**
	 * 预退休 0
	 * 退休      1
	 * @return
	 */
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getWorkAge() {
//		if (overall.getValue("rzrq") != null)
//			workAge = overall.getValue("rzrq").toString();
		return workAge;
	}
	

	public String getRetirePostValue() {
		String str = CodeUtil.getItemValue(ICodeConstants.ADMIN_DUTY_LEVEL,
				this.getRetirePost());
		if (StringUtils.isEmpty(str)) {
			return this.getRetirePost();
		}
		return str;
	}

	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}

	public String getGrdj() {
		return grdj;
	}

	public void setGrdj(String grdj) {
		this.grdj = grdj;
	}
	/**
	 * @return the oldRetireTime
	 */
	public Date getOldRetireTime() {
		return oldRetireTime;
	}

	/**
	 * @param oldRetireTime the oldRetireTime to set
	 */
	public void setOldRetireTime(Date oldRetireTime) {
		this.oldRetireTime = oldRetireTime;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getOldRetireTimeString() {
		if(retireTime==null){
			return "";
		}
		return DateTimeUtil.getFormatDate(getOldRetireTime(), "yyyy-MM-dd");
	}
	
	public String getEmployTimeString() {
		if(employTime==null){
			return "";
		}
		return DateTimeUtil.getFormatDate(getEmployTime(), "yyyy-MM-dd");
	}

	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getDoResult() {
		return doResult;
	}

	public void setDoResult(String doResult) {
		this.doResult = doResult;
	}

	public Date getEmployTime() {
		return employTime;
	}

	public void setEmployTime(Date employTime) {
		this.employTime = employTime;
	}

	public String getLthxsjbm() {
		return lthxsjbm;
	}

	public void setLthxsjbm(String lthxsjbm) {
		this.lthxsjbm = lthxsjbm;
	}

	public String getLtxfzfdw() {
		return ltxfzfdw;
	}

	public void setLtxfzfdw(String ltxfzfdw) {
		this.ltxfzfdw = ltxfzfdw;
	}

	public String getLthgldw() {
		return lthgldw;
	}

	public void setLthgldw(String lthgldw) {
		this.lthgldw = lthgldw;
	}

	public String getYdazdd() {
		return ydazdd;
	}

	public void setYdazdd(String ydazdd) {
		this.ydazdd = ydazdd;
	}
	
	// 20140424 add end
}
