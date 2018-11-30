package com.zfsoft.hrm.ddselect.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.ddselect.dao.daointerface.IDDSelectDao;
import com.zfsoft.hrm.ddselect.entities.DDSelect;
import com.zfsoft.hrm.ddselect.entities.Fields;
import com.zfsoft.hrm.ddselect.entities.Indexs;
import com.zfsoft.hrm.ddselect.entities.Keys;
import com.zfsoft.hrm.ddselect.entities.PFkeyRelation;
import com.zfsoft.hrm.ddselect.service.svcinterface.IDDSelectService;

/**
 * 
 * @author yxlong
 * 2013-8-2
 */
public class DDSelectServiceImpl implements IDDSelectService {

	private IDDSelectDao ddSelectDao;
	
	@Override
	public PageList<DDSelect> getPagedTable(DDSelect ddSelect)
			throws RuntimeException {
		PageList<DDSelect> pageList = new PageList<DDSelect>();
		List<DDSelect> ddseclects = ddSelectDao.getPagedTable(ddSelect);
		pageList.addAll(ddseclects);
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(ddSelect.getShowCount());
		paginator.setPage(ddSelect.getCurrentPage());
		paginator.setItems(ddSelect.getTotalResult());
		pageList.setPaginator(paginator);
		return pageList;
	}

	@Override
	public List<Fields> getField(String tableName) throws RuntimeException {
		return ddSelectDao.getField(tableName);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.hrm.ddselect.service.svcinterface.IDDSelectService#getKey(java.lang.String)
	 */
	
	@Override
	public List<Keys> getKey(String tableName) throws RuntimeException {
		return ddSelectDao.getKey(tableName);
	}
		
	/* (non-Javadoc)
	 * @see com.zfsoft.hrm.ddselect.service.svcinterface.IDDSelectService#getIndexs(java.lang.String)
	 */
	
	@Override
	public List<Indexs> getIndex(String tableName) throws DataAccessException {
		return ddSelectDao.getIndex(tableName);
	}
	
	/* (non-Javadoc)
	 * @see com.zfsoft.hrm.ddselect.service.svcinterface.IDDSelectService#getPFkeyRelation(java.lang.String)
	 */
	
	@Override
	public List<PFkeyRelation> getPFkeyRelation(String tableName) throws RuntimeException {
		return ddSelectDao.getPFkeyRelation(tableName);
	}

	/**
	 * @return the ddSelectDao
	 */
	public IDDSelectDao getDdSelectDao() {
		return ddSelectDao;
	}

	/**
	 * @param ddSelectDao the ddSelectDao to set
	 */
	public void setDdSelectDao(IDDSelectDao ddSelectDao) {
		this.ddSelectDao = ddSelectDao;
	}
}
