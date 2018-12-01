package com.zfsoft.sjzx.jhpz.wspz.model;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.framework.page.BaseDataObject;


/**
 * <p>
 * WebService �������ñ�
 * </p>
 * 
 * @author wangjian
 * 
 */
public class WsjhConfig extends BaseQuery{

	private static final long serialVersionUID = 5428261547765194428L;

	private String wsjhmc;//WebService������������
	private Integer wsjhxh;//WebService �������ñ��
	private String url;//��ַ
	private String namespace;//�����ռ�
	private String operate;//��������
	private String isdzq="��";//�Ƿ�ʱ
	private String wsjhds;//Ŀ�����ݿ�
	private String wsjhb;//Ŀ�ı�	
	private String wsjhzcb;//�������ݱ�
	private String  xgsj;//�޸�ʱ��
	private String fhbl;//���ر��� 
	private String params;//��������
	private int issjc;//����ʱ���
	private String  sjcz;//ʱ�����Ӧ�ı���
	private int istype;//��������0JSON 1XML 2Object(2����)
	private String  gys;//��Ԫ��
	private String  zys;//��Ԫ��
	private int oflag=0;//���������־(1��,0��)
	private int type;//����1WebService ��������2 WebService��������
	private int plbs=0;//��������
	

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
