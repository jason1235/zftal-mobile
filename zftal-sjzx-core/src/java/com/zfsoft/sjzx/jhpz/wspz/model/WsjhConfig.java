package com.zfsoft.sjzx.jhpz.wspz.model;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.framework.page.BaseDataObject;


/**
 * <p>
 * WebService 交换配置表
 * </p>
 *
 * @author wangjian
 *
 */
public class WsjhConfig extends BaseQuery{

	private static final long serialVersionUID = 5428261547765194428L;

	private String wsjhmc;//WebService交换配置名称
	private Integer wsjhxh;//WebService 交换配置编号
	private String url;//地址
	private String namespace;//命名空间
	private String operate;//操作方法
	private String isdzq="否";//是否定时
	private String wsjhds;//目的数据库
	private String wsjhb;//目的表
	private String wsjhzcb;//传参数据表
	private String  xgsj;//修改时间
	private String fhbl;//返回变量
	private String params;//方法参数
	private int issjc;//启用时间戳
	private String  sjcz;//时间戳对应的变量
	private int istype;//返回类型0JSON 1XML 2Object(2待定)
	private String  gys;//根元素
	private String  zys;//子元素
	private int oflag=0;//插入操作标志(1是,0否)
	private int type;//类型1WebService 交换配置2 WebService报送配置
	private int plbs=0;//批量报送


	public int getOflag() {
		return oflag;
	}

	public void setOflag(int oflag) {
		this.oflag = oflag;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPlbs() {
		return plbs;
	}

	public void setPlbs(int plbs) {
		this.plbs = plbs;
	}

	public String getGys() {
		return gys;
	}

	public void setGys(String gys) {
		this.gys = gys;
	}

	public String getZys() {
		return zys;
	}

	public void setZys(String zys) {
		this.zys = zys;
	}


	public String getSjcz() {
		return sjcz;
	}

	public void setSjcz(String sjcz) {
		this.sjcz = sjcz;
	}

	public String getWsjhzcb() {
		return wsjhzcb;
	}

	public void setWsjhzcb(String wsjhzcb) {
		this.wsjhzcb = wsjhzcb;
	}

	public Integer getWsjhxh() {
		return wsjhxh;
	}

	public void setWsjhxh(Integer wsjhxh) {
		this.wsjhxh = wsjhxh;
	}

	public String getFhbl() {
		return fhbl;
	}

	public void setFhbl(String fhbl) {
		this.fhbl = fhbl;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getXgsj() {
		return xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	public String getWsjhds() {
		return wsjhds;
	}

	public void setWsjhds(String wsjhds) {
		this.wsjhds = wsjhds;
	}

	public String getWsjhmc() {
		return wsjhmc;
	}

	public void setWsjhmc(String wsjhmc) {
		this.wsjhmc = wsjhmc;
	}

	public String getWsjhb() {
		return wsjhb;
	}

	public void setWsjhb(String wsjhb) {
		this.wsjhb = wsjhb;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getIsdzq() {
		return isdzq;
	}

	public void setIsdzq(String isdzq) {
		this.isdzq = isdzq;
	}


	public void setIssjc(int issjc) {
		this.issjc = issjc;
	}

	public int getIssjc() {
		return issjc;
	}

	public void setIstype(int istype) {
		this.istype = istype;
	}

	public int getIstype() {
		return istype;
	}



}
