package com.zfsoft.hrm.download.service.impl;

import java.io.File;
import java.util.Date;

import org.springframework.util.Assert;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.download.dao.IFileDownDao;
import com.zfsoft.hrm.download.entity.FileDown;
import com.zfsoft.hrm.download.query.FileDownQuery;
import com.zfsoft.hrm.download.service.IFileDownService;
import com.zfsoft.hrm.file.biz.bizinterface.IAttachementBusiness;
import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.util.AttachementUtil;

/** 
 * @author jinjj
 * @date 2013-5-21 上午10:53:44 
 *  
 */
public class FileDownServiceImpl implements IFileDownService {

	private IFileDownDao fileDownDao;
	private IAttachementBusiness attachementBusiness;
	
	private static long FILE_UPLOAD_MAXIUMSIZE = 20*1024*1024;//20MB
	
	@Override
	public FileDown getById(String id){
		return fileDownDao.getById(id);
	}
	
	@Override
	public void save(FileDown file){
		processAttachement(file);
		fileDownDao.insert(file);
	}
	
	private void processAttachement(FileDown file){
		if(file.getFiles() == null || file.getFiles().length == 0){
			throw new RuleException("文件为空");
		}
		//-----------------------------------------
		int length = file.getFiles().length;
		File[] files = file.getFiles();
		for (int i = 0; i < length; i++) {
			Assert.isTrue(files[i].length() <= FILE_UPLOAD_MAXIUMSIZE, "附件  " + file.getFilesFileName()[i] + " 过大, 文件大小不应超过20M ");
		}
		//-----------------------------------------
		String userId = SessionFactory.getUser().getYhm();
		String userName = SessionFactory.getUser().getXm();
		Attachement[] attachements = AttachementUtil.create( file );
		Attachement attachement = attachements[0];
		attachement.setUploadMan( userId );
		attachement.setUploadManName(userName);
		attachement.setFormId("");
		attachementBusiness.save(attachement);
		
		file.setCreator(userId);
		file.setCreateTime(new Date());
		file.setFileId(attachement.getGuId());
	}
	
	@Override
	public void update(FileDown file){
		fileDownDao.update(file);
	}
	
	public void updateStatus(FileDown file){
		FileDown oldBean = fileDownDao.getById(file.getGuid());
		oldBean.setStatus(file.getStatus());
		fileDownDao.update(oldBean);
	}
	
	@Override
	public void delete(String id){
		FileDown file = getById(id);
		if(file == null){
			return;
//			throw new RuleException("文件信息不存在");
		}
		attachementBusiness.removeById(file.getFileId());
		fileDownDao.delete(id);
	}
	
	@Override
	public PageList<FileDown> getPagingList(FileDownQuery query){
		PageList<FileDown> pageList = new PageList<FileDown>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(fileDownDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(fileDownDao.getPagingList(query));
			}
		}
		return pageList;
	}

	public void setFileDownDao(IFileDownDao fileDownDao) {
		this.fileDownDao = fileDownDao;
	}

	public void setAttachementBusiness(IAttachementBusiness attachementBusiness) {
		this.attachementBusiness = attachementBusiness;
	}

	
}
