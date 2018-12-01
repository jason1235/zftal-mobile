package com.zfsoft.service.common;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.entities.BjdmModel;
import com.zfsoft.dao.entities.BmdmModel;
import com.zfsoft.dao.entities.NjdmModel;
import com.zfsoft.dao.entities.ZydmModel;

/**
* 
* 类名称：ICommonSqlService 
* 类描述： 公共服务类
* 创建人：caozf
* 创建时间：2012-6-26 上午08:41:27 
* @version 
*
*/
public interface ICommonSqlService {

	/**
	 * 查询所有学院
	 */
	public List<BmdmModel> queryAllXy() throws Exception;;
	/**
	 * 查询所有专业
	 */
	public List<ZydmModel> queryAllZy() throws Exception;;
	/**
	 * 查询所有年级
	 */
	public List<NjdmModel> queryAllNj() throws Exception;;
	/**
	 * 查询所有班级
	 */
	public List<BjdmModel> queryAllBj() throws Exception;;
	
	/**
	 * 按所属部门查询专业代码列表
	 * @return
	 * @throws Exception
	 */
	public List<ZydmModel> queryZydm(Map map) throws Exception;
	
	/**
	 * 按所属部门、专业查询班级代码列表
	 * @return
	 * @throws Exception
	 */
	public List<BjdmModel> queryBjdm(BjdmModel model) throws Exception;
}
