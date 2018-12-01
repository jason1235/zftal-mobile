package com.zfsoft.hrm.notice.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.hrm.file.biz.bizinterface.IAttachementBusiness;
import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.util.AttachementUtil;
import com.zfsoft.hrm.notice.entity.NoticeAttachement;

public class NoticeAttachementService {
	
	public static long FILE_UPLOAD_MAXIUMSIZE = 20971520;

	private IAttachementBusiness attachementBusiness;
	
	public void setAttachementBusiness(IAttachementBusiness attachementBusiness) {
		this.attachementBusiness = attachementBusiness;
	}
	
	private String getFileUploadSizeText(){
		return Math.floor(100*(FILE_UPLOAD_MAXIUMSIZE/(1024*1024)))/100 + " M ";
	}
	
	/**
	 * 保存附件
	 * @param info
	 */
	protected void addAttachements(NoticeAttachement info){
		if(info.getFiles() == null || info.getFiles().length == 0){
			return;
		}
		//-----------------------------------------
		int length = info.getFiles().length;
		File[] files = info.getFiles();
		for (int i = 0; i < length; i++) {
			Assert.isTrue(files[i].length() <= FILE_UPLOAD_MAXIUMSIZE, "附件  " + info.getFilesFileName()[i] + " 过大, 文件大小不应超过 " + getFileUploadSizeText());
		}
		//-----------------------------------------
		String userId = SessionFactory.getUser().getYhm();
		String userName = SessionFactory.getUser().getXm();
		Attachement[] attachements = AttachementUtil.create( info );
		
		for (Attachement attachement : attachements) {
			//Assert.isTrue(attachement.getSize() <= FILE_UPLOAD_MAXIUMSIZE, "附件  " + attachement.getName() + " 过大, 文件大小不应超过 " + getFileUploadSizeText());
			attachement.setUploadMan( userId );
			attachement.setUploadManName(userName);
			attachement.setFormId(info.getGuid());
			attachementBusiness.save(attachement); 
			System.out.println("attachementSize : --- " + attachement.getName() + " == " + attachement.getSize() + " ---");
		}
	}
	
	/**
	 * 删除附件
	 * @param info
	 */
	protected void removeAttachements(NoticeAttachement info){
		if(info.getRemoveAttachementIds() != null && info.getRemoveAttachementIds().size() != 0){
			for (String rid : info.getRemoveAttachementIds()) {
				attachementBusiness.removeById(rid);
			}
		}
	}
	
	/**
	 * 根据信息编号删除附件
	 * @param formId
	 */
	protected void removeAttachementsByFormId(String formId){
		attachementBusiness.removeByFormId(formId);
	}
	
	/**
	 * 根据信息编号获取附件
	 * @param formId
	 * @return
	 */
	protected List<Attachement> getAttachementsByFormId(String formId){
		return attachementBusiness.getFromAttachements(formId);
	}
}
