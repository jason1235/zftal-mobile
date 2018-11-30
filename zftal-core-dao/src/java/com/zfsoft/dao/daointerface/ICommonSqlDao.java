package com.zfsoft.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.entities.BjdmModel;
import com.zfsoft.dao.entities.BmdmModel;
import com.zfsoft.dao.entities.NjdmModel;
import com.zfsoft.dao.entities.ZydmModel;

/**
 * 类描述：通用公共Dao
 * 创建人：ZhenFei.Cao
 * 创建时间：2012-6-26
 * @version 
 */
public interface ICommonSqlDao {

	/**
	 * 
	* 方法描述: 查询所有学院
	* 参数 @return 参数说明
	* 返回类型 List<BmdmModel> 返回类型
	*/
	public List<BmdmModel> queryAllXy();

	/**
	 * 
	* 方法描述: 查询所有专业
	* 参数 @return 参数说明
	* 返回类型 List<ZydmModel> 返回类型
	*/
	public List<ZydmModel> queryAllZy();
	
	/**
	 * 
	* 方法描述: 查询所有年级
	* 参数 @return 参数说明
	* 返回类型 List<NjdmModel> 返回类型
	*/
	public List<NjdmModel> queryAllNj();
	
	/**
	 * 
	* 方法描述: 查询所有班级
	* 参数 @return 参数说明
	* 返回类型  List<BjdmModel> 返回类型
	*/
	public List<BjdmModel> queryAllBj() ;
	
	/**
	 * 按所属部门查询专业代码列表
	 * @return
	 * @throws Exception
	 */
	public List<ZydmModel> queryZydm(Map map) ;
	
	/**
	 * 按所属部门、专业查询班级代码列表
	 * @return
	 * @throws Exception
	 */
	public List<BjdmModel> queryBjdm(BjdmModel model);
	
	
}
