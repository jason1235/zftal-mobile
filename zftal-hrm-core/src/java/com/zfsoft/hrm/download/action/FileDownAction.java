package com.zfsoft.hrm.download.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.download.entity.FileDown;
import com.zfsoft.hrm.download.query.FileDownQuery;
import com.zfsoft.hrm.download.service.IFileDownService;
import com.zfsoft.util.base.StringUtil;

/** 
 * @author jinjj
 * @date 2013-5-21 上午11:47:09 
 *  
 */
public class FileDownAction extends HrmAction {

	private static final long serialVersionUID = -248800052305572620L;

	private IFileDownService fileDownService;
	
	private PageList<FileDown> pageList;
	private FileDownQuery query = new FileDownQuery();
	private FileDown file;
	private String sortFieldName = null;
	private String asc = "up";
	
	public String page() throws Exception{
		List<Item> types= CodeUtil.getItem(ICodeConstants.DM_FILE_TYPE, IConstants.ROOT).getChildren();
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " top desc, createTime desc" );
		}
		pageList = fileDownService.getPagingList(query);
		getValueStack().set("types", types);
		return "page";
	}
	
	public String viewPage() throws Exception{
		List<Item> types= CodeUtil.getItem(ICodeConstants.DM_FILE_TYPE, IConstants.ROOT).getChildren();
		query.setStatus(1);
		query.setUserName(getUser().getYhm());
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " top desc, createTime desc" );
		}
		pageList = fileDownService.getPagingList(query);
		getValueStack().set("types", types);
		return "viewPage";
	}
	
	public String viewList() throws Exception{
		query.setStatus(1);
		query.setUserName(getUser().getYhm());
		query.setOrderStr( " top desc, createTime desc" );
		pageList = fileDownService.getPagingList(query);
		List<FileDown> list = new ArrayList<FileDown>();
		list.addAll(pageList);
		if(list.size()>7){
			list = list.subList(0, 7);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", list);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String save() throws Exception{
		try{
			fileDownService.save(file);
			setMessage(true, "操作成功");
		}catch (Exception e) {
			setErrorMessage("保存失败:" + e.getMessage());
			logException(e);
		}
		getValueStack().set(DATA, getMessage());
		return "success";
	}
	
	public String update() throws Exception{
		try{
			fileDownService.update(file);
			setMessage(true, "操作成功");
		}catch (Exception e) {
			setErrorMessage("更新失败:" + e.getMessage());
		}
		getValueStack().set(DATA, getMessage());
		return "success";
	}
	
	public String updateStatus() throws Exception{
		fileDownService.updateStatus(file);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception{
		fileDownService.delete(file.getGuid());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String input() throws Exception{
		List<Item> types= CodeUtil.getItem(ICodeConstants.DM_FILE_TYPE, IConstants.ROOT).getChildren();
		getValueStack().set("types", types);
		return "input";
	}
	
	public String edit() throws Exception{
		List<Item> types= CodeUtil.getItem(ICodeConstants.DM_FILE_TYPE, IConstants.ROOT).getChildren();
		file = fileDownService.getById(file.getGuid());
		getValueStack().set("types", types);
		return "input";
	}

	public FileDownQuery getQuery() {
		return query;
	}

	public void setQuery(FileDownQuery query) {
		this.query = query;
	}

	public FileDown getFile() {
		return file;
	}

	public void setFile(FileDown file) {
		this.file = file;
	}

	public PageList<FileDown> getPageList() {
		return pageList;
	}

	public void setFileDownService(IFileDownService fileDownService) {
		this.fileDownService = fileDownService;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}
	
}
