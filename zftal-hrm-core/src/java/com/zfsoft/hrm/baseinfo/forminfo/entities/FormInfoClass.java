package com.zfsoft.hrm.baseinfo.forminfo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/**
 * 登记类别Entity
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public class FormInfoClass implements Serializable {

	private static final long serialVersionUID = 4516843834354244994L;

	private String formInfoTypeId;				//登记类别
	
	private List<InfoClass> infoClazzes;		//信息类实体
	
	private List<FormInfoMetadata> metadatas; 	//元数据
	
	private List<DynaBean> dynaBeans;			//	
	
	private Map<String,List<DynaBean>> infoClassOfDynaBeanMap;//信息类对应的动态bean
	
	
	public void addDynaBean(DynaBean dynaBean){
		if(this.getInfoClassOfDynaBeanMap().get(dynaBean.getClazz().getGuid())==null)
			this.getInfoClassOfDynaBeanMap().put(dynaBean.getClazz().getGuid(), new ArrayList<DynaBean>());
		this.getDynaBeans().add(dynaBean);
		this.getInfoClassOfDynaBeanMap().get(dynaBean.getClazz().getGuid()).add(dynaBean);
	}
	
	public void removeDynaBean(String infoClassGuid,int index){
		DynaBean dynaBean=this.getInfoClassOfDynaBeanMap().get(infoClassGuid).get(index);
		this.getInfoClassOfDynaBeanMap().get(infoClassGuid).remove(index);
		if(this.getInfoClassOfDynaBeanMap().get(infoClassGuid).size()==0){
			this.getInfoClassOfDynaBeanMap().remove(infoClassGuid);
		}
		this.getDynaBeans().remove(dynaBean);
	}
	public void removeAllDynaBean() {
		this.getInfoClassOfDynaBeanMap().clear();
		this.getDynaBeans().clear();
	}
	public DynaBean getDynaBeanByClassId(String infoClassGuid,int index){
		return this.getInfoClassOfDynaBeanMap().get(infoClassGuid).get(index);
	}
	
	public List<DynaBean> getDynaBeans() {
		if(dynaBeans==null)dynaBeans=new ArrayList<DynaBean>();
		return dynaBeans;
	}

	public void setDynaBeans(List<DynaBean> dynaBeans) {
		this.dynaBeans = dynaBeans;
	}
	public Map<String, List<DynaBean>> getInfoClassOfDynaBeanMap() {
		if(infoClassOfDynaBeanMap==null)infoClassOfDynaBeanMap=new HashMap<String, List<DynaBean>>();
		return infoClassOfDynaBeanMap;
	}

	public void setInfoClassOfDynaBeanMap(
			Map<String, List<DynaBean>> infoClassOfDynaBeanMap) {
		this.infoClassOfDynaBeanMap = infoClassOfDynaBeanMap;
	}

	public List<InfoClass> getInfoClazzes() {
		if(infoClazzes==null)infoClazzes=new ArrayList<InfoClass>();
		return infoClazzes;
	}

	public void setInfoClazzes(List<InfoClass> infoClazzes) {
		this.infoClazzes = infoClazzes;
	}

	public String getFormInfoTypeId() {
		return formInfoTypeId;
	}

	public void setFormInfoTypeId(String formInfoTypeId) {
		this.formInfoTypeId = formInfoTypeId;
	}

	public List<FormInfoMetadata> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(List<FormInfoMetadata> metadatas) {
		this.metadatas = metadatas;
	}
	
	public void copy(FormInfoClass formInfoClass) {
		this.dynaBeans=formInfoClass.dynaBeans;
		this.formInfoTypeId=formInfoClass.formInfoTypeId;
		this.infoClassOfDynaBeanMap=formInfoClass.infoClassOfDynaBeanMap;
		this.infoClazzes=formInfoClass.infoClazzes;
		this.metadatas=formInfoClass.metadatas;
	}

	
	
}
