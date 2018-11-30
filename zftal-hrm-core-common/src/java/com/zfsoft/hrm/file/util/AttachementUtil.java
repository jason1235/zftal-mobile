package com.zfsoft.hrm.file.util;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import com.zfsoft.hrm.core.file.FileRequest;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.dao.daointerface.IAttachementDao;
import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.common.spring.SpringHolder;

/**
 * 附件操作工具类
 * @author Administrator
 *
 */
public class AttachementUtil {

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static Attachement[] create( FileRequest request ) {
		int length = request.getFiles().length;
		File[] files = request.getFiles();
		String[] names = request.getFilesFileName();
		String[] types = request.getFilesContentType();
		
		Attachement[] result = new Attachement[length];
		
		for (int i = 0; i < length; i++) {
			result[i] = new Attachement();
			result[i].setContent( Byte_File_Object.getBytesFromFile( files[i] ) );
			result[i].setName(names[i]);
			result[i].setType( types[i] );
			result[i].setSize( files[i].length() );
			result[i].setUploadTime( new Date() );
			result[i].setUploadMan( "" );
			result[i].setRemark( "" );
		}
		
		return result;
	}

	public static Attachement getAttachementByGuid(String fileId) {
		IAttachementDao attachementDao=(IAttachementDao)SpringHolder.getBean("fileAttachementDao");
		return attachementDao.findById(fileId);
	}

	public static Attachement updateAttachement(String fileName,
			InputStream inputStream, Attachement attachement) {
		IAttachementDao attachementDao=(IAttachementDao)SpringHolder.getBean("fileAttachementDao");
		attachement.setContent(Byte_File_Object.getBytesFromFile(inputStream));
		attachement.setName(fileName);
		attachement.setUploadTime(new Date());
		attachement.setSize(new Long(attachement.getContent().length));
		attachement.setType(UploadFileUtil.checkedFileName(fileName));
		attachementDao.update(attachement);
		return attachement;
	}
	
	public static void removeAttachement(String id) {
		if(StringUtil.isEmpty(id)){
			return;
		}
		IAttachementDao attachementDao=(IAttachementDao)SpringHolder.getBean("fileAttachementDao");
		Attachement attachement=attachementDao.findById(id);
		if(attachement!=null){
			attachementDao.deleteById(id);
		}
	}

	public static Attachement saveAttachement(String fileName,
			InputStream inputStream) {
		IAttachementDao attachementDao=(IAttachementDao)SpringHolder.getBean("fileAttachementDao");
		byte[] content=Byte_File_Object.getBytesFromFile(inputStream);
		Attachement attachement=new Attachement();
		attachement.setName(fileName);
		attachement.setUploadTime(new Date());
		attachement.setContent(content);
		attachement.setSize(new Long(content.length));
		attachement.setType(UploadFileUtil.checkedFileName(fileName));
		attachementDao.insert(attachement);
		return attachement;
	}
}
