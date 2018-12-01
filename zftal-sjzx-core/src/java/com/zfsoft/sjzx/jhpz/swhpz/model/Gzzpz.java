package com.zfsoft.sjzx.jhpz.swhpz.model;

import com.zfsoft.framework.page.BaseDataObject;

public class Gzzpz extends BaseDataObject {

    /**
	 * 工作组对象序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer gzzbh;

    public void setGzzbh(Integer gzzbh){
        this.gzzbh = gzzbh;
    }

    public Integer getGzzbh(){
        return this.gzzbh;
    }

    private String gzzmc;

    public void setGzzmc(String gzzmc){
        this.gzzmc = gzzmc;
    }

    public String getGzzmc(){
        return this.gzzmc;
    }

    private String cjsj;

    public void setCjsj(String cjsj){
        this.cjsj = cjsj;
    }

    public String getCjsj(){
        return this.cjsj;
    }

    private String xgsj;

    public void setXgsj(String xgsj){
        this.xgsj = xgsj;
    }

    public String getXgsj(){
        return this.xgsj;
    }

    private String ms;

    public void setMs(String ms){
        this.ms = ms;
    }

    public String getMs(){
        return this.ms;
    }

    private Integer yyfwqlsh;

    public void setYyfwqlsh(Integer yyfwqlsh){
        this.yyfwqlsh = yyfwqlsh;
    }

    public Integer getYyfwqlsh(){
        return this.yyfwqlsh;
    }

    private String cjr;

    public void setCjr(String cjr){
        this.cjr = cjr;
    }

    public String getCjr(){
        return this.cjr;
    }

    private String xgr;

    public void setXgr(String xgr){
        this.xgr = xgr;
    }

    public String getXgr(){
        return this.xgr;
    }

    private String cjrXm;

	/**
	 * @return the cjrXm
	 */
	public String getCjrXm() {
		return cjrXm;
	}

	/**
	 * @param cjrXm the cjrXm to set
	 */
	public void setCjrXm(String cjrXm) {
		this.cjrXm = cjrXm;
	}
	
	private String xgrXm;

	/**
	 * @return the xgrXm
	 */
	public String getXgrXm() {
		return xgrXm;
	}

	/**
	 * @param xgrXm the xgrXm to set
	 */
	public void setXgrXm(String xgrXm) {
		this.xgrXm = xgrXm;
	}
}