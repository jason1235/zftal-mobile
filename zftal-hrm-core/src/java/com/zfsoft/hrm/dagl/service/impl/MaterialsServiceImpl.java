package com.zfsoft.hrm.dagl.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.dagl.dao.IArchiveItemDao;
import com.zfsoft.hrm.dagl.dao.IMaterialsDao;
import com.zfsoft.hrm.dagl.entity.ArchiveItem;
import com.zfsoft.hrm.dagl.query.MaterialsAddItem;
import com.zfsoft.hrm.dagl.service.IMaterialsService;

public class MaterialsServiceImpl implements IMaterialsService{

	private IArchiveItemDao archiveItemDao;
	private IMaterialsDao   materialsDao;

	public void setArchiveItemDao(IArchiveItemDao archiveItemDao) {
		this.archiveItemDao = archiveItemDao;
	}

	public IArchiveItemDao getArchiveItemDao() {
		return archiveItemDao;
	}

	public void setMaterialsDao(IMaterialsDao materialsDao) {
		this.materialsDao = materialsDao;
	}

	public IMaterialsDao getMaterialsDao() {
		return materialsDao;
	}

	@Override
	public PageList<MaterialsAddItem> getList(MaterialsAddItem query) {
		// TODO Auto-generated method stub
		
		PageList<MaterialsAddItem> pageList = new PageList<MaterialsAddItem>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(materialsDao.getMaterialsCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<MaterialsAddItem> list = materialsDao.getList(query);
				pageList.addAll(list);
			}
		}
		
		return pageList;
	}

	@Override
	public PageList<ArchiveItem> getArchiveItemList(MaterialsAddItem query) {
		// TODO Auto-generated method stub
		ArchiveItem itenQuery = new ArchiveItem();
		
		if(query != null && query.getClassGh() != null)
			itenQuery.setGh(query.getClassGh());
		
		if(query != null && query.getClassXm() != null)
			itenQuery.setXm(query.getClassXm());
		
		if(query != null && query.getClassClmc() != null)
			itenQuery.setName(query.getClassClmc());

		
		PageList<ArchiveItem> pageList = new PageList<ArchiveItem>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(archiveItemDao.getArchiveItemCount(itenQuery));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				itenQuery.setStartRow(paginator.getBeginIndex());
				itenQuery.setEndRow(paginator.getEndIndex());
				List<ArchiveItem> list = archiveItemDao.getList(itenQuery);
				pageList.addAll(list);
			}
		}
		
		
		return pageList;
	}

	@Override
	public void insert(MaterialsAddItem materials) {
		// TODO Auto-generated method stub
		materialsDao.insert(materials);
	}

	@Override
	public void removeData(String classId) {
		// TODO Auto-generated method stub
		materialsDao.remove(classId);
	}

	@Override
	public void update(MaterialsAddItem materials) {
		// TODO Auto-generated method stub
		materialsDao.update(materials);
	}

}
