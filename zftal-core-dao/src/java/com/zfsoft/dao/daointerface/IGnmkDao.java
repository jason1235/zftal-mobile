package com.zfsoft.dao.daointerface;

import java.util.HashMap;
import java.util.List;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.GnmkModel;


/**
 * 
 * 
 * 类名称：IGnmkDao
 * 类描述：功能模块dao
 * 创建人：huangxp
 * 创建时间：2012-6-28
 * 修改人：huangxp
 * 修改时间：2012-6-28
 * 修改备注： 
 * @version 
 *
 */
public interface IGnmkDao extends BaseDao<GnmkModel> {
    /**
     * 查询操作代码
     * @param model
     * @return
     * @since ZFTAL 1.0
     */
    public List<GnmkModel> cxCzdm(GnmkModel model);

	public List<HashMap<String, String>> getCurrentMenu(String path);
}
