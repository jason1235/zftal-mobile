package com.zfsoft.hrm.dataorigin.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name="dataOriginClasses")
public class XmlDataOriginClasses {
    
    private List<XmlDataOriginClass> dataOriginClasses;

    /**
     * @return the dataOriginClasses
     */
    @XmlElement(name="dataOriginClass")
    public List<XmlDataOriginClass> getDataOriginClasses() {
        return dataOriginClasses;
    }

    /**
     * @param dataOriginClasses the dataOriginClasses to set
     */
    public void setDataOriginClasses(List<XmlDataOriginClass> dataOriginClasses) {
        this.dataOriginClasses = dataOriginClasses;
    }

    public XmlDataOriginClass getDataOriginClassById(Long id){
        if (dataOriginClasses == null) {
            return null;
        }
        for (XmlDataOriginClass dataOriginClass : dataOriginClasses) {
            if (id.equals(dataOriginClass.getId())) {
                return dataOriginClass;
            }
        }
        return null;
    }
    
    public XmlDataOriginClass getDataOriginClassByFieldName(String identityName){
        if(dataOriginClasses == null) {
            return null;
        }
        for (XmlDataOriginClass dataOriginClass : dataOriginClasses) {
            if (identityName.equals(dataOriginClass.getIdentityName())) {
                return dataOriginClass;
            }
        }
        return null;
    }
}