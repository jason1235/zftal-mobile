package com.zfsoft.hrm.baseinfo.snapshot.query;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/** 
 * @author jinjj
 * @date 2012-11-5 上午09:14:47 
 *  
 */
public class SnapshotQuery extends DynaBeanQuery {

	private static final long serialVersionUID = 8853796988789143962L;

	public SnapshotQuery(InfoClass clazz){
		super(clazz);
		setPerPageSize(10);
	}
}
