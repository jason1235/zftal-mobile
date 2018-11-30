package com.zfsoft.hrm.dybill.dao;

import java.util.HashMap;
import java.util.List;
import com.zfsoft.hrm.dybill.xml.XmlDefineCatch;

/**
 * 
 * @author ChenMinming
 * @date 2013-7-18
 * @version V1.0.0
 */
public interface ISpBillDefineCatchDao {
	public List<HashMap<String, Object>> findList(XmlDefineCatch defineCatch);
}
