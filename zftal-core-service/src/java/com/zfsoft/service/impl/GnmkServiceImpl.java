package com.zfsoft.service.impl;

import java.util.List;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IGnmkDao;
import com.zfsoft.dao.entities.GnmkModel;
import com.zfsoft.service.svcinterface.IGnmkService;


/**
 * 
 * 
 * 类名称：GnmkServiceImpl
 * 类描述：功能模块service实现类
 * 创建人：huangxp
 * 创建时间：2012-6-28
 * 修改人：huangxp
 * 修改时间：2012-6-28
 * 修改备注： 
 * @version 
 *
 */
public class GnmkServiceImpl extends BaseServiceImpl<GnmkModel, IGnmkDao> implements IGnmkService {

/**
 * 
 * 查询操作代码
 *
 */
 public List<GnmkModel> cxCzdm(GnmkModel model){
     return dao.cxCzdm(model);
 }

}
