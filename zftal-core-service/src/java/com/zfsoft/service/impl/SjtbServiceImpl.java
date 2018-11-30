package com.zfsoft.service.impl;

import java.util.Map;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.ISjtbDao;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.service.svcinterface.ISjtbService;


/**
 * 
 * 
 * 类名称：SjtbServiceImpl
 * 类描述：数据同步service实现类
 * 创建人：huangxp
 * 创建时间：2012-7-13
 * 修改备注： 
 * @version 
 *
 */
public class SjtbServiceImpl extends BaseServiceImpl<YhglModel, ISjtbDao> implements ISjtbService {

    @Override
    public void tbsj(Map map) {
        dao.sjtb(map);
    }

    

}
