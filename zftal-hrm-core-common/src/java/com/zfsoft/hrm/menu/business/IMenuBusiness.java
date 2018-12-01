package com.zfsoft.hrm.menu.business;

import java.util.List;

import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.dao.entities.IndexModelNew;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.config.Direction;
import com.zfsoft.hrm.report.entity.ReportXmlFile;

/**
 * 报表对应菜单业务
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
public interface IMenuBusiness {
	
	/**
	 * 通过name获取
	 * @param id
	 * @return
	 */
	public IndexModel getByName(IndexModel indexModel);
	
	/**
	 * 通过name获取
	 * @param name
	 * @return
	 */
	public List<IndexModel> getByName(String name);
	/**
	 * 通过Id获取
	 * @param id
	 * @return
	 */
	public IndexModel getById(String id);
	/**
	 * 插入报表菜单
	 * @param model
	 */
	public void addReportMenu(ReportXmlFile reportXmlFile);
	
	/**
	 * 原封不动插入菜单（默认进行管理员授权）
	 * @param indexModel
	 */
	public void insertMenu(IndexModel indexModel);
	
	
	/**
	 * 插入菜单（默认进行管理员授权）
	 * @param indexModel
	 */
	public void addMenu(IndexModel indexModel);
	
	/**
	 * 插入菜单
	 * @param indexModel
	 * @param authorized 管理员是否授权
	 * @param excludeSiblingMenu 菜单排序时需要排除的菜单数组
	 */
	public void addMenu(IndexModel indexModel,boolean authorized,String[] excludeSiblingMenu);
	
	/**
	 * 删除菜单
	 * @param model
	 */
	public void remove(String name);
	/**
	 * 修改菜单
	 * @param model
	 */
	public void modify(IndexModel model);
	
	/**
	 * 菜单移动
	 * @param menuId
	 * @param direction
	 * @return
	 */
	public void menuMove(String menuId,Direction dirc);
	
	/**
	 * 获取父菜单所有下级子菜单
	 * @param menuId
	 * @return
	 */
	public List<IndexModel> getMenuByFartherId(String menuId);
	

	/**
	 * 插入三级菜单(审核用)
	 * @param b 
	 * @param model
	 */
	public void addMenuThreeforAudit(String name, String catalogName, String menuId, int startState, int endState);
	
	/**
	 * 通过Url查找
	 * @param name
	 * @return
	 */
	public IndexModel getByUrl(String url);
	
	/**
	 * 删除菜单
	 * @param model
	 */
	public void remove(String name,int level);
	
	
	/**
	 * 插入顶级菜单
	 * @param model
	 */
	public void addMenuSuper(String name);
	/**
	 * 插入二级菜单
	 * @param model
	 */
	public void addMenuTwo(String name);
	/**
	 * 插入三级菜单
	 * @param b 
	 * @param model
	 */
	public void addMenuThree(String name, String catalogName, String menuId, boolean commitable);
	
	/**
	 * 通过名称和层级获取
	 * @param name
	 * @param level
	 * @return
	 */
	public IndexModel getByNameAndLevel(String name, int level);
	

	/**
	 * 为admin用户添加操作权限
	 * @param id
	 */
	public void addCzForAdmin(String menuId);
	/**
	 * 通过层级获取
	 * @param i
	 * @return
	 */
	public List<IndexModel> getByLevel(int i);

	/** 
	* @author: zhangxu
	* @Title: getThirdMenuList 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return PageList<IndexModelNew>    返回类型 
	* @throws 
	*/
	public PageList<IndexModelNew> getThirdMenuList(IndexModelNew query);
}
