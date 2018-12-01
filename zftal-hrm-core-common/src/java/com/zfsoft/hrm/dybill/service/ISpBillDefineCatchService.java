package com.zfsoft.hrm.dybill.service;

import java.util.List;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.xml.XmlDefineCatch;

/**
 * 
 * @author ChenMinming
 * @date 2013-7-18
 * @version V1.0.0
 */
public interface ISpBillDefineCatchService {
	List<DynaBean> getListToView(XmlDefineCatch defineCatch,
			SpBillConfig spBillConfig, SpBillInstance spBillInstance);
}
