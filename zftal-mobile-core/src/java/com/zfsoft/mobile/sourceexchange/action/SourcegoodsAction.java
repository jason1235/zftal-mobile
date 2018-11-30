package com.zfsoft.mobile.sourceexchange.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.sourceexchange.entity.Sourcegoods;
import com.zfsoft.mobile.sourceexchange.query.SourcegoodsQuery;
import com.zfsoft.mobile.sourceexchange.service.ISourcegoodsService;
import com.zfsoft.util.base.StringUtil;

public class SourcegoodsAction extends HrmAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String op;
	private Sourcegoods model = new Sourcegoods();
	private SourcegoodsQuery query = new SourcegoodsQuery();
	private PageList<Sourcegoods> pageList;
	private ISourcegoodsService goodsService;
	private IMobileCommonService mobileCommonService;
	
	private List<String> sourcegoods;
	
	public String list(){
		pageList=goodsService.getPageList(query);
		return "list";
	}
	
	public String toAdd(){
		this.getValueStack().set("imageHtml", ImageTagHtml.getImageHtml("sourcegoods", Type.IMAGE, 1024, 2, 1, null, true));
		op = "add";
		return "edit";
	}
	
	public String saveOrUpdate(){
		StringBuilder picids = new StringBuilder();
		StringBuilder picpaths = new StringBuilder();
		//获取上传的图片
		int logoSize = 0;
		if(sourcegoods != null){
			for (int i = 0; i < sourcegoods.size(); i++) {
				if(StringUtil.isEmpty(sourcegoods.get(i))){
					logoSize++;
				}
			}
		}
		if(sourcegoods != null && logoSize != sourcegoods.size() && sourcegoods.size() > 0){
			for (int i = 0; i < sourcegoods.size(); i++) {
				if(StringUtil.isEmpty(sourcegoods.get(i))){
					this.setErrorMessage("请确认所有图片已上传!");
					this.getValueStack().set("data", getMessage());
					return "data";
				}
				if(i != sourcegoods.size()-1){
					picids.append(sourcegoods.get(i)).append(",");
					picpaths.append(mobileCommonService.getGoodsImagePath(sourcegoods.get(i),"home")).append(",");
				}else{
					picids.append(sourcegoods.get(i));
					picpaths.append(mobileCommonService.getGoodsImagePath(sourcegoods.get(i),"home"));
				}
			}
		}
		query.setPicids(picids.toString());
		query.setPicpaths(picpaths.toString());
		
		if(StringUtils.isNotBlank(query.getGoodsid())){
			this.setSuccessMessage("成功插入数据！");
			goodsService.update(query);
		}else{
			this.setSuccessMessage("成功修改数据！");
			goodsService.insert(query);
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	
	public String toModify(){
		List<Sourcegoods> list = goodsService.getPageList(query);
		if(list!=null&&list.size()>0){
			op="modify";
			model = list.get(0);
			if(StringUtil.isEmpty(model.getPicids())){
				getValueStack().set("imageHtmlArr", "");
				getValueStack().set("flag", false);
			}else{
				String[] logoIds   = model.getPicids().split(",");
				ArrayList<String> imageHtmlArr = new ArrayList<String>();
				for (int i = 0; i < logoIds.length; i++) {
					imageHtmlArr.add(ImageTagHtml.getImageHtml("sourcegoods", Type.IMAGE, 1024, 2, 1, logoIds[i], true));
				}
				getValueStack().set("imageHtmlArr", imageHtmlArr);
				getValueStack().set("flag", logoIds.length == 3 ? true : false);
			}
		}
		return "edit";
	}
	
	public String image() {
		this.getValueStack().set("data", ImageTagHtml.getImageHtml("sourcegoods", Type.IMAGE, 1024, 2, 1, null, true));
		return "data";
	}
	
	public String control(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		if("2".equals(query.getIsactive())){
			if(StringUtils.isNotBlank(query.getGoodsid())){
				model=goodsService.findById(query);
				if(model!=null&&StringUtils.isNotBlank(model.getPicids())){
					String[] picArr =StringUtils.split(model.getPicids(), ",");
					if(picArr!=null){
						for (int i=0;i<picArr.length;i++) {
							String picid=picArr[i];
							String picpath=model.getPicpaths().split(",")[i];
							ImageDBUtil.deleteFileToDB(picid,realPath+picpath);
						}
					}
				}
				goodsService.deleteGoods(query.getGoodsid());
				this.setSuccessMessage("成功删除数据！");
			}
		}else{
			int res=goodsService.goodsControl(query);
			if(res>0){
				this.setSuccessMessage("操作成功");
			}else{
				this.setErrorMessage("操作失败");
			}
		}
		this.getValueStack().set("data", getMessage());
		return "data";
	}
	

	public ISourcegoodsService getGoodsService() {
		return goodsService;
	}

	public void setGoodsService(ISourcegoodsService goodsService) {
		this.goodsService = goodsService;
	}

	public Sourcegoods getModel() {
		return model;
	}

	public void setModel(Sourcegoods model) {
		this.model = model;
	}

	public SourcegoodsQuery getQuery() {
		return query;
	}

	public void setQuery(SourcegoodsQuery query) {
		this.query = query;
	}

	public PageList<Sourcegoods> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<Sourcegoods> pageList) {
		this.pageList = pageList;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public List<String> getSourcegoods() {
		return sourcegoods;
	}

	public void setSourcegoods(List<String> sourcegoods) {
		this.sourcegoods = sourcegoods;
	}

	public IMobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	public void setMobileCommonService(IMobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}
	
	
	

}
