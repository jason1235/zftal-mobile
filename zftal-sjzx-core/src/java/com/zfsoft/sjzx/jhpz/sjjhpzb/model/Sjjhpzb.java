package com.zfsoft.sjzx.jhpz.sjjhpzb.model;

import com.zfsoft.framework.page.BaseDataObject;

public class Sjjhpzb extends BaseDataObject { 
	private static final long serialVersionUID = 7596360198200475115L;	
	private Integer jhpzxh;
    private String jhpzmc;
    private String cjsj;
    private String ysql;
    private String mdb;
    private Integer tjjls;
    private String yxztbzw;
    private String xgsj;
    private String dyccqbz;
    private String scmrnr;
    private String ysjk;
    private String mbsjk;
    private String cjr;
    private Integer scmsbh;
    private Integer yyfwqlsh;
    private String xgr;
    private String sfwqcr;
    private String sfqkmdb;
    private String sfplcr;
    private Integer rzblsj;
    private Integer sfqy;
    private String sfssjh; //是否实时交换
    private String ssjhlx; //实时交换类型
    private String bzwzd;  //标志位字段
    private String sjczd;  //时间戳字段
    private String yb;  //源表
    private String dsqzt;//定时器状态
    
    public String getDsqzt() {
		return dsqzt;
	}

	public void setDsqzt(String dsqzt) {
		this.dsqzt = dsqzt;
	}

	public String getSfwqcr() {
		return sfwqcr;
	}

	public void setSfwqcr(String sfwqcr) {
		this.sfwqcr = sfwqcr;
	}

	public String getSfqkmdb() {
		return sfqkmdb;
	}

	public void setSfqkmdb(String sfqkmdb) {
		this.sfqkmdb = sfqkmdb;
	}

	public String getSfplcr() {
		return sfplcr;
	}

	public void setSfplcr(String sfplcr) {
		this.sfplcr = sfplcr;
	}

	public void setJhpzxh(Integer jhpzxh){
        this.jhpzxh = jhpzxh;
    }

    public Integer getJhpzxh(){
        return this.jhpzxh;
    }

    public void setJhpzmc(String jhpzmc){
        this.jhpzmc = jhpzmc;
    }

    public String getJhpzmc(){
        return this.jhpzmc;
    }

    public void setCjsj(String cjsj){
        this.cjsj = cjsj;
    }

    public String getCjsj(){
        return this.cjsj;
    }

    public void setYsql(String ysql){
        this.ysql = ysql;
    }

    public String getYsql(){
        return this.ysql;
    }

    public void setMdb(String mdb){
        this.mdb = mdb;
    }

    public String getMdb(){
        return this.mdb;
    }

    public void setTjjls(Integer tjjls){
        this.tjjls = tjjls;
    }

    public Integer getTjjls(){
        return this.tjjls;
    }

    public void setYxztbzw(String yxztbzw){
        this.yxztbzw = yxztbzw;
    }

    public String getYxztbzw(){
        return this.yxztbzw;
    }

    public void setXgsj(String xgsj){
        this.xgsj = xgsj;
    }

    public String getXgsj(){
        return this.xgsj;
    }

    public void setDyccqbz(String dyccqbz){
        this.dyccqbz = dyccqbz;
    }

    public String getDyccqbz(){
        return this.dyccqbz;
    }

    private String sczd;

    public void setSczd(String sczd){
        this.sczd = sczd;
    }

    public String getSczd(){
        return this.sczd;
    }

    public void setScmrnr(String scmrnr){
        this.scmrnr = scmrnr;
    }

    public String getScmrnr(){
        return this.scmrnr;
    }

    public void setYsjk(String ysjk){
        this.ysjk = ysjk;
    }

    public String getYsjk(){
        return this.ysjk;
    }

    public void setMbsjk(String mbsjk){
        this.mbsjk = mbsjk;
    }

    public String getMbsjk(){
        return this.mbsjk;
    }

    public void setCjr(String cjr){
        this.cjr = cjr;
    }

    public String getCjr(){
        return this.cjr;
    }

    public void setScmsbh(Integer scmsbh){
        this.scmsbh = scmsbh;
    }

    public Integer getScmsbh(){
        return this.scmsbh;
    }

    public void setYyfwqlsh(Integer yyfwqlsh){
        this.yyfwqlsh = yyfwqlsh;
    }

    public Integer getYyfwqlsh(){
        return this.yyfwqlsh;
    }

    public void setXgr(String xgr){
        this.xgr = xgr;
    }

    public String getXgr(){
        return this.xgr;
    }
    
    public void setRzbjsl(Integer rzblsj){
        this.rzblsj = rzblsj;
    }

    public Integer getRzblsj(){
        return this.rzblsj;
    }
    
    public void setRzblsj(Integer rzblsj) {
		this.rzblsj = rzblsj;
	}
    
	public Integer getSfqy() {
		return sfqy;
	}

	public void setSfqy(Integer sfqy) {
		this.sfqy = sfqy;
	}

	public String getSfssjh() {
		return sfssjh;
	}

	public void setSfssjh(String sfssjh) {
		this.sfssjh = sfssjh;
	}

	public String getSsjhlx() {
		return ssjhlx;
	}

	public void setSsjhlx(String ssjhlx) {
		this.ssjhlx = ssjhlx;
	}

	public String getBzwzd() {
		return bzwzd;
	}

	public void setBzwzd(String bzwzd) {
		this.bzwzd = bzwzd;
	}

	public String getSjczd() {
		return sjczd;
	}

	public void setSjczd(String sjczd) {
		this.sjczd = sjczd;
	}

	public String getYb() {
		return yb;
	}

	public void setYb(String yb) {
		this.yb = yb;
	}

}