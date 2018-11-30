package com.zfsoft.hrm.authpost.auth.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.authpost.auth.entities.AuthDetail;
import com.zfsoft.hrm.authpost.auth.query.AuthDetailQuery;

/** 
 * @author jinjj
 * @date 2012-7-27 上午10:34:07 
 *  
 */
public interface IAuthDetailDao {

	public List<AuthDetail> getPagingList(AuthDetailQuery query) throws DataAccessException;
	
	public int getPagingCount(AuthDetailQuery query) throws DataAccessException;
}
