package com.zfsoft.hrm.baseinfo.data.dao;

import java.util.List;

import com.zfsoft.hrm.baseinfo.data.entity.Template;

public interface ITemplateDao {
	public List<Template> getPagedTemplate(Template template);
	
	public void insert(Template template);
	
	public void delete(String id);
	
	public void update(Template template);
	
	public Template getById(String id);
}
