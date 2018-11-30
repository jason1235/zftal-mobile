/**
 * 
 */
package com.zfsoft.mobile.services.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.services.entity.ExpressEntity;
import com.zfsoft.mobile.services.service.IExpressService;

/**
 * @author zhangxu
 * @description 
 * @date 2017-12-25 上午09:51:12
 */
public class ExpressAction extends HrmAction{
	
	private IExpressService expressService;
	private ExpressEntity query = new ExpressEntity();
	private PageList<ExpressEntity> list = new PageList<ExpressEntity>();
	private String[] id;

	/**
	 * 获取表白墙列表页面
	* @author: zhangxu
	* @Title: list 
	* @Description: 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String list(){
		list = expressService.getPageList(query);
		return "list";
	}
	
	public String remove(){
		Map<String, Object> param = new HashMap<String, Object>();
        List<String> tids = new ArrayList<String>();
        for (String idCheck : id) {
        	tids.add(idCheck.trim());
        }
        param.put("ids", tids);
		expressService.delete(param);
		this.setSuccessMessage("删除成功！");
		this.getValueStack().set("data", this.getMessage());
		return DATA;
	}

	public void setExpressService(IExpressService expressService) {
		this.expressService = expressService;
	}

	public IExpressService getExpressService() {
		return expressService;
	}

	public ExpressEntity getQuery() {
		return query;
	}

	public void setQuery(ExpressEntity query) {
		this.query = query;
	}

	public PageList<ExpressEntity> getList() {
		return list;
	}

	public void setList(PageList<ExpressEntity> list) {
		this.list = list;
	}

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}
	
	
}
