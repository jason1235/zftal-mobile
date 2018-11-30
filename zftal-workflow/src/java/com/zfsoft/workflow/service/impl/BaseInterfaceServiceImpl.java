package com.zfsoft.workflow.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.workflow.service.BaseInterface;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 *
 * @author <a href="mailto:zhangcs@hundsun.com">zhangcs</a>
 */
public class BaseInterfaceServiceImpl implements BaseInterface {
    /**
     * Log instance for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log    log = LogFactory.getLog(getClass());
//    private static Logger log = LoggerFactory.getLogger(PostInitializor.class);

//	@Override
//	public List getAll(Class clazz) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object get(Class clazz, Serializable id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object save(Object o) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void remove(Class clazz, Serializable id) {
//		// TODO Auto-generated method stub
//		
//	}

}
