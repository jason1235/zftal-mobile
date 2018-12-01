package com.zfsoft.hrm.file.action;

import java.io.IOException;
import java.io.OutputStream;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.service.svcinterface.IAttachementService;

/**
 * 附件操作Action
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-19
 * @version V1.0.0
 */
public class AttachementAction extends HrmAction implements ModelDriven<Attachement> {

	private static final long serialVersionUID = 3804073757727023327L;
	
	private Attachement model = new Attachement();
	
	private IAttachementService attachementService;
	
	/**
	 * 附件下载
	 */
	public String download() {
		model = attachementService.getById( model.getGuId() );
		
		try {
			String fileName = model.getName();
			
			String conType = model.getType();
			long fileSize = model.getSize();
			getResponse().setCharacterEncoding( IConstants.FILE_ENCODE );
			getResponse().setContentType( conType );
			getResponse().setContentLength( (int) fileSize );
			getResponse().setHeader( "Content-Disposition",  DownloadFilenameUtil.fileDisposition(getRequest().getHeader("user-agent"), fileName) );
			
			OutputStream out = getResponse().getOutputStream();
			out.write( model.getContent() );
			out.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void setAttachementService(IAttachementService attachementService) {
		this.attachementService = attachementService;
	}

	@Override
	public Attachement getModel() {
		return model;
	}

	public void setModel(Attachement model) {
		this.model = model;
	}
	

}
