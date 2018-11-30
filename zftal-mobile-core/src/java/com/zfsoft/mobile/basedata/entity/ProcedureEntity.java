package com.zfsoft.mobile.basedata.entity;

import com.zfsoft.mobile.common.enums.ExecuteCycleEnum;


/**
 * 
 * @author Administrator
 *
 */
public class ProcedureEntity {
    // 信息类ID
    private String classId;
    // 信息类名称
    private String className;
    // 存储过程名
    private String procedureName;
    // 存储过程ID
    private String procedureId;
    // 执行周期
    private ExecuteCycleEnum executeCyc;
    // 定时开关（on开off关）
    private String regularSwitch;
    // 是否模糊查询
    private String isOrNotLike;
    //数字档案logo地址
    private String datadz;
    //数字档案logoId
    private String dataid;
    //是否可查看全部记录
    private String xxlall;
    

    /**
     * @return the classId
     */
    public String getClassId() {
        return classId;
    }

    /**
     * @param classId the classId to set
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * @return the procedureName
     */
    public String getProcedureName() {
        return procedureName;
    }

    /**
     * @param procedureName the procedureName to set
     */
    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the procedureId
     */
    public String getProcedureId() {
        return procedureId;
    }

    /**
     * @param procedureId the procedureId to set
     */
    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    /**
     * @return the executeCyc
     */
    public ExecuteCycleEnum getExecuteCyc() {
        return executeCyc;
    }

    /**
     * @param executeCyc the executeCyc to set
     */
    public void setExecuteCyc(ExecuteCycleEnum executeCyc) {
        this.executeCyc = executeCyc;
    }

    /**
     * @return the regularSwitch
     */
    public String getRegularSwitch() {
        return regularSwitch;
    }

    /**
     * @param regularSwitch the regularSwitch to set
     */
    public void setRegularSwitch(String regularSwitch) {
        this.regularSwitch = regularSwitch;
    }

	public void setIsOrNotLike(String isOrNotLike) {
		this.isOrNotLike = isOrNotLike;
	}

	public String getIsOrNotLike() {
		return isOrNotLike;
	}

	public void setDatadz(String datadz) {
		this.datadz = datadz;
	}

	public String getDatadz() {
		return datadz;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getDataid() {
		return dataid;
	}

	public void setXxlall(String xxlall) {
		this.xxlall = xxlall;
	}

	public String getXxlall() {
		return xxlall;
	}
    
}
