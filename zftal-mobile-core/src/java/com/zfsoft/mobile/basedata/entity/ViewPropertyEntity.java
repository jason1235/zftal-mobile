package com.zfsoft.mobile.basedata.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public class ViewPropertyEntity {
    // 信息类ID
    private String classId;
    // 属性ID
    private String propertyId;
    // 显示项目
    private String lviewProperty;
    // 显示项目
    private String dviewProperty;
    // 条件状态
    private String conditionStatus;
    
    private List<String> dviewPropertyList = new ArrayList<String>();
    
    //字段名称
    private String zdmc;
    
    //字段中文名称
    private String sxmc;


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
     * @return the propertyId
     */
    public String getPropertyId() {
        return propertyId;
    }

    /**
     * @param propertyId the propertyId to set
     */
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * @return the lviewProperty
     */
    public String getLviewProperty() {
        return lviewProperty;
    }

    /**
     * @param lviewProperty the lviewProperty to set
     */
    public void setLviewProperty(String lviewProperty) {
        this.lviewProperty = lviewProperty;
    }

    /**
     * @return the dviewProperty
     */
    public String getDviewProperty() {
        return dviewProperty;
    }

    /**
     * @param dviewProperty the dviewProperty to set
     */
    public void setDviewProperty(String dviewProperty) {
    	if(dviewProperty != null){
			String[] list = dviewProperty.split(",");
			for(String temp : list){
				dviewPropertyList.add(temp.trim());
			}
			//this.classSscpList = java.util.Arrays.asList(list);
		}
        this.dviewProperty = dviewProperty;
    }

    /**
     * @return the conditionStatus
     */
    public String getConditionStatus() {
        return conditionStatus;
    }

    /**
     * @param conditionStatus the conditionStatus to set
     */
    public void setConditionStatus(String conditionStatus) {
        this.conditionStatus = conditionStatus;
    }

	public void setDviewPropertyList(List<String> dviewPropertyList) {
		this.dviewPropertyList = dviewPropertyList;
	}

	public List<String> getDviewPropertyList() {
		return dviewPropertyList;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setSxmc(String sxmc) {
		this.sxmc = sxmc;
	}

	public String getSxmc() {
		return sxmc;
	}
    
}
