package com.zfsoft.hrm.authpost.post.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.authpost.post.entities.PostInfo;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-24
 * @version V1.0.0
 */
public interface IPostInfoService {
	/**
	 * 
	 * @param typeCode
	 * @return
	 */
	public List<PostInfo> getListByType(String typeCode);
	/**
	 * 
	 * @param guid
	 * @return
	 */
	public PostInfo getById(String guid);
	/**
	 * 
	 * @param entity
	 */
	public void add(PostInfo entity);
	/**
	 * 
	 * @param entity
	 */
	public void modify(PostInfo entity);
	public void modify(List<PostInfo> entitys);
	/**
	 * 
	 * @param guid
	 */
	public void remove(String guid);
	/**
	 * 
	 * @param type
	 * @param id
	 */
	public void sort(String type,String id);
	
	/**
	 * 
	 * @param typeItems 
	 * @param typeCode
	 * @return
	 */
	public List<Item> getItemList();
}
