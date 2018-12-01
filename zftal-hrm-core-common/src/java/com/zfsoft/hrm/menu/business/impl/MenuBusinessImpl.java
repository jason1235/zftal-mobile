package com.zfsoft.hrm.menu.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.dao.entities.IndexModelNew;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.config.Direction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.menu.business.IMenuBusiness;
import com.zfsoft.hrm.menu.dao.IMenuDao;
import com.zfsoft.hrm.report.entity.ReportXmlFile;
import com.zfsoft.util.base.StringUtil;

public class MenuBusinessImpl implements IMenuBusiness{
	
	private IMenuDao menuDao;

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	
	@Override
	public IndexModel getByName(IndexModel indexModel) {
		List<IndexModel> list = menuDao.findByName(indexModel);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<IndexModel> getByName(String name) {
		IndexModel query = new IndexModel();
		query.setGnmkmc(name);
		return menuDao.findByName(query);
	}

	@Override
	public IndexModel getById(String id) {
		return menuDao.findById(id);
	}

	@Override
	public void addReportMenu(ReportXmlFile reportXmlFile) {
		List<IndexModel> models=menuDao.findByFatherId(IConstants.REPORT_ROOT_MENU);
		
		String gnmkdm = getGnmkdm(IConstants.REPORT_ROOT_MENU,models);
		String xssx = getLevelXssx(models);
		IndexModel indexModel=new IndexModel();
		indexModel.setGnmkdm(gnmkdm);
		indexModel.setGnmkmc(reportXmlFile.getReportName());
		indexModel.setXssx(xssx);
		indexModel.setFjgndm(IConstants.REPORT_ROOT_MENU);
		indexModel.setDyym("");
		menuDao.insert(indexModel);
		reportXmlFile.setMenuId(gnmkdm);
		menuDao.insertCzForAdmin(indexModel.getGnmkdm());
	}
	
	public void addMenu(IndexModel indexModel) {
		addMenu(indexModel, true, new String[0]);
	}
	
	@Override
	public void insertMenu(IndexModel indexModel){
		menuDao.insert(indexModel);
		menuDao.insertCzForAdmin(indexModel.getGnmkdm());
	}
	
	public void addMenu(IndexModel indexModel,boolean authorized,String[] excludeSiblingMenu) {
		indexModel.setIsAuto("1");
		List<IndexModel> allModels = menuDao.findByFatherIdForAuto(indexModel);
		List<IndexModel> models = new ArrayList<IndexModel>();
		for(IndexModel m : allModels){//过滤同级不参加排序的菜单
			boolean include = true;
			for(String str : excludeSiblingMenu){
				if(m.getGnmkdm().equals(str)){
					include = false;
					break;
				}
			}
			if(include){
				models.add(m);
			}
		}
		String gnmkdm = getGnmkdm(indexModel.getFjgndm(),models);
		String xssx = getLevelXssx(models);
		indexModel.setGnmkdm(gnmkdm);
		indexModel.setXssx(xssx);
		menuDao.insert(indexModel);
		if(gnmkdm.length()==7 && authorized){//3级菜单添加管理员默认授权
			menuDao.insertCzForAdmin(indexModel.getGnmkdm());
		}
	}

	@Override
	public void remove(String id) {
		IndexModel indexModel=menuDao.findById(id);
		if(indexModel!=null){
			menuDao.delete(indexModel);
			menuDao.deleteCzForAdmin(id);
		}
	}

	@Override
	public void modify(IndexModel model) {
		menuDao.update(model);
	}
	
	private String getLevelXssx(List<IndexModel> models) {
		Integer xssx=0;
		for (IndexModel model : models) {
			Integer modelIndex=0;
			if(("99").equals(model.getXssx())||("98").equals(model.getXssx())){
				continue;
			}
			if(!StringUtil.isEmpty(model.getXssx())){
				modelIndex=Integer.parseInt(model.getXssx());
			}
			if(new Integer(0).compareTo(modelIndex)<0){
				xssx=modelIndex;
			}
		}
		xssx++;
		return xssx.toString();
	}

	private String getGnmkdm(String fatherDm,List<IndexModel> models) {
		String gnmkdm="0";
		for (IndexModel model : models) {
			if(gnmkdm.compareTo(model.getGnmkdm())<0){
				gnmkdm=model.getGnmkdm();
			}
		}
		gnmkdm=gnmkdm.replaceAll(fatherDm, "");
		
		Integer dm=new Integer(gnmkdm);
		IndexModel m;
		
		do {
			dm++;
			Assert.isTrue(dm<100, "菜单序号已经被使用完毕！");
			
			if(dm<10){
				gnmkdm=fatherDm+"0"+dm;
			}else{
				gnmkdm=fatherDm+dm.toString();
			}
			m = menuDao.findById(gnmkdm);
		} while (m!=null);
		
		return gnmkdm;
	}
	
	
	public void menuMove(String menuId,Direction dirc){
		IndexModel model = new IndexModel();
		model = menuDao.findById(menuId);
		List<IndexModel> models=menuDao.findByFatherId(model.getFjgndm());
		if(dirc.equals(Direction.UP)){
			Assert.isTrue(!models.get(0).getGnmkdm().equals(menuId), "无法上移了");
		}
		if(dirc.equals(Direction.DOWN)){
			Assert.isTrue(!models.get(models.size()-1).getGnmkdm().equals(menuId), "无法下移了");
		}
		sortList(models,model,dirc);
		reindexMenuList(models);
	}
	
	private void sortList(List<IndexModel> models,IndexModel model,Direction dirc){
		for(int i=0;i<models.size();i++){
			if(models.get(i).getGnmkdm().equals(model.getGnmkdm())){
				if(dirc.equals(Direction.UP)){
					IndexModel target = models.get(i-1);
					models.set(i-1, model);
					models.set(i, target);
					break;
				}
				if(dirc.equals(Direction.DOWN)){
					IndexModel target = models.get(i+1);
					models.set(i, target);
					models.set(i+1, model);
					break;
				}
			}
		}
	}
	
	/**
	 * 重新序列菜单并保存数据（剔除空序列）
	 * @param models
	 */
	private void reindexMenuList(List<IndexModel> models){
		for(int i=0;i<models.size();i++){
			IndexModel model = models.get(i);
			model.setXssx(i+"");
			menuDao.update(model);
		}
	}
	
	public List<IndexModel> getMenuByFartherId(String menuId){
		return menuDao.findByFatherId(menuId);
	}


	@Override
	public void addMenuThreeforAudit(String name, String catalogName,
			String menuId, int startState, int endState) {
		IndexModel superMenu=menuDao.findByName(catalogName, "N____");
		
		List<IndexModel> models=menuDao.findByFatherId(superMenu.getGnmkdm());
		
		String gnmkdm = getGnmkdm(superMenu.getGnmkdm(),models);
		
		String xssx = getLevelXssx(models);
		
		IndexModel indexModel=new IndexModel();
		indexModel.setGnmkdm(gnmkdm);
		indexModel.setGnmkmc(name);
		indexModel.setXssx(xssx);
		indexModel.setFjgndm(superMenu.getGnmkdm());
		
		indexModel.setDyym("/baseinfo/infoclassmenu_list.html?menuId="+menuId+"&approve=true&startState="+startState+"&endState="+endState);
		
		menuDao.insert(indexModel);
		menuDao.insertCzForAdmin(indexModel.getGnmkdm());
	}


	@Override
	public IndexModel getByUrl(String url) {
		return menuDao.findByUrl(url);
	}


	@Override
	public void remove(String name, int level) {
		String levelStr="";
		for (int i = 0; i < level*2; i++) {
			levelStr+="_";
		}
		IndexModel model=menuDao.findByName(name, "N"+levelStr);
		
		if(model!=null){
			menuDao.delete(model);
		}
		
		//ResOpUtil.removeRd(model);
	}


	@Override
	public void addMenuSuper(String name) {
		List<IndexModel> models=menuDao.findByFatherId("N");
		
		String gnmkdm = getGnmkdm("N",models);
		String xssx = getLevelXssx(models);
		IndexModel indexModel=new IndexModel();
		indexModel.setGnmkdm(gnmkdm);
		indexModel.setGnmkmc(name);
		indexModel.setXssx(xssx);
		indexModel.setFjgndm("N");
		indexModel.setDyym("");
		menuDao.insert(indexModel);
	}


	@Override
	public void addMenuTwo(String name) {
		IndexModel superMenu=menuDao.findByName(name, "N__");
		
		String gnmkdm = superMenu.getGnmkdm()+"01";
		String xssx = "0";
		IndexModel indexModel=new IndexModel();
		indexModel.setGnmkdm(gnmkdm);
		indexModel.setGnmkmc(name);
		indexModel.setXssx(xssx);
		indexModel.setFjgndm(superMenu.getGnmkdm());
		indexModel.setDyym("");
		
		menuDao.insert(indexModel);
		
		//ResOpUtil.addRd(gnmkdm,name);
	}


	@Override
	public void addMenuThree(String name, String catalogName, String menuId,
			boolean commitable) {
		IndexModel superMenu=menuDao.findByName(catalogName, "N____");
		
		List<IndexModel> models=menuDao.findByFatherId(superMenu.getGnmkdm());
		
		String gnmkdm = getGnmkdm(superMenu.getGnmkdm(),models);
		
		String xssx = getLevelXssx(models);
		
		IndexModel indexModel=new IndexModel();
		indexModel.setGnmkdm(gnmkdm);
		indexModel.setGnmkmc(name);
		indexModel.setXssx(xssx);
		indexModel.setFjgndm(superMenu.getGnmkdm());
		if(!commitable){
			indexModel.setDyym("/baseinfo/infoclassmenu_list.html?menuId="+menuId);
		}else{
			indexModel.setDyym("/baseinfo/infoclassmenu_list.html?menuId="+menuId+"&req=true");
		}
		
		menuDao.insert(indexModel);
		
		menuDao.insertCzForAdmin(indexModel.getGnmkdm());
		
		//ResOpUtil.addRes(indexModel);
	}


	@Override
	public IndexModel getByNameAndLevel(String name, int level) {
		String levelStr="";
		for (int i = 0; i < level*2; i++) {
			levelStr+="_";
		}
		return menuDao.findByName(name, "N"+levelStr);
	}


	@Override
	public void addCzForAdmin(String menuId) {
		menuDao.insertCzForAdmin(menuId);
	}


	@Override
	public List<IndexModel> getByLevel(int i) {
		switch (i) {
		case 0:
			return menuDao.findByLevel("N");
		case 1:
			return menuDao.findByLevel("N__");
		case 2:
			return menuDao.findByLevel("N____");
		case 3:
			return menuDao.findByLevel("N______");
		default:
			return menuDao.findByLevel("N");
		}
		
	}


	/* (non-Javadoc)
	 * @see com.zfsoft.hrm.menu.business.IMenuBusiness#getThirdMenuList(com.zfsoft.dao.entities.IndexModelNew)
	 */
	@Override
	public PageList<IndexModelNew> getThirdMenuList(IndexModelNew query) {
		PageList<IndexModelNew> pageList = new PageList<IndexModelNew>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(menuDao.getListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<IndexModelNew> list = menuDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}


}

