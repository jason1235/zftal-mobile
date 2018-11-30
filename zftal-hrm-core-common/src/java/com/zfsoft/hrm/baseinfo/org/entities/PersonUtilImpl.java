package com.zfsoft.hrm.baseinfo.org.entities;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;

public class PersonUtilImpl implements IPersonUtil{

	@Override
	public Person getPersonById(String id) {
		if(id==null||"".equals(id)){
			return new Person();
		}
		DynaBean result = DynaBeanUtil.getPerson(id);
		if(result==null){
			return new Person();
		}
		Person person=new Person();
		person.setId(id);
		if(result.getValue("xm")==null){
			person.setName("");
		}else{
			person.setName(result.getValue("xm").toString());
		}
		return person;
	}

}
