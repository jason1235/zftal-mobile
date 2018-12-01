package com.zfsoft.mobile.qrcode.action;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.qrcode.entity.QRcode;
import com.zfsoft.mobile.qrcode.service.IQRcodeService;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.dao.query.ServiceManagerQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.util.base.StringUtil;

public class QRcodeAction  extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4938697986976910061L;
	
	private IQRcodeService QRcodeService;
	
	private QRcode query = new QRcode();
	
	private QRcode model = new QRcode();
	
	private String op;
	private IMobileCommonService mobileCommonService;
	
	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}
	
	public String list(){
		PageList<QRcode> list = new PageList<QRcode>();
		list = QRcodeService.getList(query);
		this.getValueStack().set("list", list);
		return "list";
	}
	
	public String toAdd(){
		String imageHtml = ImageTagHtml.getImageHtml("codeimageid", Type.IMAGE, 1024, 150, 150, model.getCodeimageid(), true);
        getValueStack().set("imageHtml", imageHtml);
        String logoHtml = ImageTagHtml.getImageHtml("logoimageid", Type.IMAGE, 1024, 150, 150, model.getCodelogoid(), true);
        getValueStack().set("logoHtml", logoHtml);
		op = "add";
		return "edit";
	}
	
	public String toModify(){
		PageList<QRcode> list = QRcodeService.getList(query);
		model = list.get(0);
		String imageHtml = ImageTagHtml.getImageHtml("codeimageid", Type.IMAGE, 1024, 150, 150, model.getCodeimageid(), true);
        getValueStack().set("imageHtml", imageHtml);
        String logoHtml = ImageTagHtml.getImageHtml("logoimageid", Type.IMAGE, 1024, 150, 150, model.getCodelogoid(), true);
        getValueStack().set("logoHtml", logoHtml);
		op = "modify";
		return "edit";
	}
	
	
	
	public String save() throws Exception{
		if(StringUtil.isEmpty(model.getCodeid())){
			model.setCodeimageid(getRequest().getParameter("codeimageid"));
			model.setCodelogoid(getRequest().getParameter("logoimageid"));
			String fwtbdz = mobileCommonService.getMinUploadImagePath(model.getCodelogoid());
			model.setCodelogourl(fwtbdz);
			QRcodeService.insert(model);
			this.setSuccessMessage("成功插入数据！");
		}
		else{
			model.setCodeimageid(getRequest().getParameter("codeimageid"));
			model.setCodelogoid(getRequest().getParameter("logoimageid"));
			String fwtbdz = mobileCommonService.getMinUploadImagePath(model.getCodelogoid());
			model.setCodelogourl(fwtbdz);
			QRcodeService.update(model);
			this.setSuccessMessage("成功 更新数据！");
		}
	    
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove(){
		QRcodeService.remove(query);
		this.setSuccessMessage("删除成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public void setQRcodeService(IQRcodeService qRcodeService) {
		QRcodeService = qRcodeService;
	}

	public IQRcodeService getQRcodeService() {
		return QRcodeService;
	}

	public void setQuery(QRcode query) {
		this.query = query;
	}

	public QRcode getQuery() {
		return query;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	public void setModel(QRcode model) {
		this.model = model;
	}

	public QRcode getModel() {
		return model;
	}
	

}
