package com.zfsoft.hrm.seniorreport.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.report.entity.ReportQuery;
import com.zfsoft.hrm.seniorreport.dao.ISeniorReportDao;
import com.zfsoft.hrm.seniorreport.dao.ISeniorReportHistroyDao;
import com.zfsoft.hrm.seniorreport.entity.Col;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportConfig;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportConfigData;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportHistroy;
import com.zfsoft.hrm.seniorreport.entity.SeniorReportView;
import com.zfsoft.hrm.seniorreport.entity.ViewItem;
import com.zfsoft.hrm.seniorreport.enums.SeniorReportType;
import com.zfsoft.hrm.seniorreport.enums.ViewItemType;
import com.zfsoft.hrm.seniorreport.service.ISeniorReportService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2013-12-24
 * @version V1.0.0
 */
public class SeniorReportServiceImpl implements ISeniorReportService {

	private ISeniorReportDao seniorReportDao;
	private ISeniorReportHistroyDao seniorReportHistroyDao;
	@Override
	public void add(SeniorReportConfigData seniorReportConfigData) {
		SeniorReportConfig config = new SeniorReportConfig();
		config.setTitle(seniorReportConfigData.getReportName());
		config.setSql(seniorReportConfigData.getSql());
		config.setHistorySql(seniorReportConfigData.getHistorySql());
		seniorReportConfigData.setSeniorReportConfig(config);
		if(StringUtil.isEmpty(seniorReportConfigData.getMenuId()))
		{
			seniorReportConfigData.setMenuId(SeniorReportType.OTHER_TYPE.getKey());
		}
		seniorReportDao.insert(seniorReportConfigData);
	}
	
	@Override
	public void add(SeniorReportConfig seniorReportConfig,SeniorReportType type) {
		SeniorReportConfigData seniorReportConfigData = new SeniorReportConfigData();
		seniorReportConfigData.setReportName(seniorReportConfig.getTitle());
		seniorReportConfigData.setSeniorReportConfig(seniorReportConfig);
		seniorReportConfigData.setMenuId(type.getKey());
		seniorReportDao.insert(seniorReportConfigData);
	}

	@Override
	public void addColumn(SeniorReportConfigData data, ViewItem viewItem,
			String pId) {
		SeniorReportConfig config = data.getSeniorReportConfig();
		viewItem.setItemId("item_"+new Date().getTime());
		if(StringUtil.isEmpty(pId)){
			if(config.getTitles()==null){
				config.setTitles(new ArrayList<ViewItem>());
			}
			config.getTitles().add(viewItem);
		}else{
			ViewItem pItem = config.findViewItemById(pId, true);
			if(pItem.getSubItem()==null){
				pItem.setSubItem(new ArrayList<ViewItem>());
			}
			pItem.getSubItem().add(viewItem);
		}
		data.setSeniorReportConfig(config);
		seniorReportDao.update(data);
	}

	@Override
	public void addRow(SeniorReportConfigData data, ViewItem viewItem,
			String pId) {
		SeniorReportConfig config = data.getSeniorReportConfig();
		viewItem.setItemId("item_"+new Date().getTime());
		if(StringUtil.isEmpty(pId)){
			if(config.getLeft()==null){
				config.setLeft(new ArrayList<ViewItem>());
			}
			config.getLeft().add(viewItem);
		}else{
			ViewItem pItem = config.findViewItemById(pId, false);
			if(pItem.getSubItem()==null){
				pItem.setSubItem(new ArrayList<ViewItem>());
			}
			pItem.getSubItem().add(viewItem);
		}
		data.setSeniorReportConfig(config);
		seniorReportDao.update(data);

	}

	@Override
	public void columnLeft(String id, ViewItem viewItem) {
		doMoveViewItem(id, viewItem, true, -1);

	}

	@Override
	public void columnRight(String id, ViewItem viewItem) {
		doMoveViewItem(id, viewItem, true, 1);
	}

	@Override
	public SeniorReportView getNullViewById(String id) {
		SeniorReportView view = new SeniorReportView();
		SeniorReportConfigData data = getReportById(id);
		if(data == null) return null;
		SeniorReportConfig config = data.getSeniorReportConfig();
		if(config == null){
			config = new SeniorReportConfig();
		}
		if(config.getLeft()==null)
		{
			config.setLeft(new ArrayList<ViewItem>());
		}
		view.setLeft(config.getLeft());
		if(config.getTitles()==null)
		{
			config.setTitles(new ArrayList<ViewItem>());
		}
		view.setLeft(config.getLeft());
		view.setTitles(config.getTitles());
		
		List<ViewItem> rows = findFloorList(view.getLeft());
		List<ViewItem> cols = findFloorList(view.getTitles());
		
		
		for (ViewItem r : rows) {
			List<Col> list = new ArrayList<Col>();
			for (ViewItem c : cols) {
				Col defaultCol = new Col();
				defaultCol.setValue(getValue(null, c, r, false, false));
				list.add(defaultCol);
			}
			view.getItemValueMaps().put(r, list);
		}
		view.setStartTitle(config.getStartTitle());
		view.setReportTitle(config.getTitle());
		return view;
	}
	
	
	/**
	 * 
	 * @param vwItemList
	 * @return
	 */
	private List<ViewItem> findFloorList(List<ViewItem> vwItemList){
		if(vwItemList == null) return null;
		ViewItem vwItem = new ViewItem();
		vwItem.setSql(null);
		vwItem.setSubItem(vwItemList);
		return findFloorList(vwItem);
	}
	/**
	 * 获取指定节点子条目底层展示对象【递归】
	 * @param vwItemList vwItem
	 * @return 所有最底层叶子节点（已经集合了上层sql）
	 */
	private List<ViewItem> findFloorList(ViewItem vwItem){
		if(vwItem == null) return null;
		List<ViewItem>  list= new ArrayList<ViewItem>();
		for (ViewItem viewItem : vwItem.getSubItem()) {
			if(ViewItemType.NULL_TYPE.getKey().equals(vwItem.getViewType())){
				viewItem.setViewType(vwItem.getViewType());
			}
			else if(ViewItemType.VALUE_TYPE.getKey().equals(vwItem.getViewType())
					&&ViewItemType.COUNT_TYPE.getKey().equals(viewItem.getViewType())){
				viewItem.setViewType(vwItem.getViewType());
				viewItem.setDefultValue(vwItem.getDefultValue());
			}
			else if(ViewItemType.DATA_TYPE.getKey().equals(vwItem.getViewType())){
				viewItem.setViewType(vwItem.getViewType());
				viewItem.setDataSql(vwItem.getDataSql());
				viewItem.setJoinSql(vwItem.getJoinSql());
			}
			
			if(!StringUtil.isEmpty(vwItem.getSql())){
				viewItem.setSql(vwItem.getSql()+" and "+viewItem.getSql());
			}
			if(viewItem.getSubItem()==null||viewItem.getSubItem().isEmpty()){
				list.add(viewItem);
			}else{
				list.addAll(findFloorList(viewItem));
			}
		}
		return list;
	}
	

	@Override
	public List<SeniorReportConfigData> getReportList(SeniorReportConfigData query) {
		return seniorReportDao.findList(query);
	}

	@Override
	public SeniorReportConfigData getReportById(String id) {
		SeniorReportConfigData query = new SeniorReportConfigData();
		query.setReportId(id);
		List<SeniorReportConfigData> list =  seniorReportDao.findList(query);
		if(list==null||list.isEmpty())
			return null;
		return list.get(0);
	}

	
	private String getValue(ReportQuery query,ViewItem col,ViewItem row,boolean exec,boolean throwable){
		if(ViewItemType.NULL_TYPE.getKey().equals(col.getViewType())
				||ViewItemType.NULL_TYPE.getKey().equals(row.getViewType())){
			return "";
		}
		if(ViewItemType.VALUE_TYPE.getKey().equals(col.getViewType())){
			if(StringUtil.isEmpty(col.getDefultValue()))
				return "";
			return col.getDefultValue();
		}
		if(ViewItemType.VALUE_TYPE.getKey().equals(row.getViewType())){
			if(StringUtil.isEmpty(row.getDefultValue()))
				return "";
			return row.getDefultValue();
		}
		if (exec) {
			try{
				query.setRowCondition(row.getSql());
				query.setColumnCondition(col.getSql());
				if(ViewItemType.DATA_TYPE.getKey().equals(col.getViewType())){
					return find(col,query);
				}
				if(ViewItemType.DATA_TYPE.getKey().equals(row.getViewType())){
					return find(row,query);
				}
				return seniorReportDao.count(query)+"";
			}catch (RuntimeException e) {
				e.printStackTrace();
				if(throwable){
					throw e;
				}
				return "error!";
			}
		} else {
			return "0";
		}
		
	}
	private String find(ViewItem viewItem,ReportQuery query){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sql", query.getSql());
		map.put("joinSql", viewItem.getJoinSql());
		map.put("dataSql", viewItem.getDataSql());
		map.put("columnCondition", query.getColumnCondition());
		map.put("rowCondition", query.getRowCondition());
		return seniorReportDao.find(map);
	}
	

	@Override
	public String tryToExecSqL(SeniorReportConfigData config,ViewItem viewItem) {
		if(config == null)return null;
		ReportQuery query = new ReportQuery();
		String sql = config.getSql();
		if(StringUtil.isEmpty(sql)){
			sql = config.getSeniorReportConfig().getSql();
		}
		query.setSql(sql);
		ViewItem item = new ViewItem();
		item.setSql("1=1");
		if(viewItem == null){
			viewItem = item;
		}
		return getValue(query, viewItem, item, true, true);
	}

	@Override
	public SeniorReportView getViewById(String id) {
		return getViewById(id, null);
	}

	@Override
	public SeniorReportView getViewById(String id, String snapTime) {
		
		SeniorReportView view = new SeniorReportView();
		SeniorReportConfigData data = getReportById(id);
		if(data == null) return null;
		SeniorReportConfig config = data.getSeniorReportConfig();
		if(config == null){
			config = new SeniorReportConfig();
		}
		if(config.getLeft()==null)
		{
			config.setLeft(new ArrayList<ViewItem>());
		}
		view.setLeft(config.getLeft());
		if(config.getTitles()==null)
		{
			config.setTitles(new ArrayList<ViewItem>());
		}
		view.setLeft(config.getLeft());
		view.setTitles(config.getTitles());
		
		List<ViewItem> rows = findFloorList(view.getLeft());
		List<ViewItem> cols = findFloorList(view.getTitles());
		
		ReportQuery queryV=new ReportQuery();
		
		if(StringUtil.isEmpty(snapTime)){
			queryV.setSql(config.getSql());
			
		}else{
			queryV.setSnapTime(snapTime);
			queryV.setSql(config.getHistorySql());
		}
		
		List<Col> values;
		Col valueCol=new Col();
		for(ViewItem row:rows){
			values=new ArrayList<Col>();
//			queryV.setRowCondition(row.getSql());
			for(ViewItem column:cols){
//				queryV.setColumnCondition(column.getSql());
				valueCol=new Col();
				valueCol.setCondition(column.getSql());
				valueCol.setValue(""+getValue(queryV, column, row, true, false));
				values.add(valueCol);
			}
			view.getItemValueMaps().put(row, values);
		}
		view.setStartTitle(config.getStartTitle());
		view.setReportTitle(config.getTitle());
		return view;
	}

	@Override
	public void modify(SeniorReportConfigData seniorReportConfigData) {
		SeniorReportConfigData data = getReportById(seniorReportConfigData.getReportId());
		SeniorReportConfig config = data.getSeniorReportConfig();
		config.setTitle(seniorReportConfigData.getReportName());
		config.setSql(seniorReportConfigData.getSql());
		config.setHistorySql(seniorReportConfigData.getHistorySql());
		seniorReportConfigData.setSeniorReportConfig(config);
		seniorReportDao.update(seniorReportConfigData);

	}

	@Override
	public void modifyColumn(SeniorReportConfigData data, ViewItem viewItem) {
		SeniorReportConfig config = data.getSeniorReportConfig();
		ViewItem oldItem = config.findViewItemById(viewItem.getItemId(), true);
		if(oldItem == null){
			return;
		}
		oldItem.setSql(viewItem.getSql());
		oldItem.setName(viewItem.getName());
		oldItem.setViewType(viewItem.getViewType());
		oldItem.setDefultValue(viewItem.getDefultValue());
		oldItem.setDataSql(viewItem.getDataSql());
		oldItem.setJoinSql(viewItem.getJoinSql());
		data.setSeniorReportConfig(config);
		seniorReportDao.update(data);

	}

	@Override
	public void modifyRow(SeniorReportConfigData data, ViewItem viewItem) {
		SeniorReportConfig config = data.getSeniorReportConfig();
		ViewItem oldItem = config.findViewItemById(viewItem.getItemId(), false);
		if(oldItem == null){
			return;
		}
		oldItem.setSql(viewItem.getSql());
		oldItem.setName(viewItem.getName());
		oldItem.setViewType(viewItem.getViewType());
		oldItem.setDefultValue(viewItem.getDefultValue());
		data.setSeniorReportConfig(config);
		seniorReportDao.update(data);
	}

	@Override
	public void remove(String id) {
		SeniorReportConfigData data =getReportById(id);
		seniorReportDao.delete(data);
	}

	@Override
	public void removeColumn(SeniorReportConfigData data, String itemId) {
		SeniorReportConfig config = data.getSeniorReportConfig();
		ViewItem oldItem = config.findViewItemById(itemId, true);
		if(oldItem == null){
			return;
		}
		oldItem.getpItem().getSubItem().remove(oldItem);
		data.setSeniorReportConfig(config);
		seniorReportDao.update(data);

	}

	@Override
	public void removeRow(SeniorReportConfigData data, String itemId) {
		SeniorReportConfig config = data.getSeniorReportConfig();
		ViewItem oldItem = config.findViewItemById(itemId, false);
		if(oldItem == null){
			return;
		}
		oldItem.getpItem().getSubItem().remove(oldItem);
		data.setSeniorReportConfig(config);
		seniorReportDao.update(data);
	}

	@Override
	public void rowDown(String id, ViewItem viewItem) {
		doMoveViewItem(id, viewItem, false, 1);
	}

	@Override
	public void rowUp(String id, ViewItem viewItem) {
		doMoveViewItem(id, viewItem, false, -1);
	}
	/**
	 * 表单移动具体方法
	 * @param id 操作的表单id
	 * @param viewItem 操作的标题对象
	 * @param isTitles 标题对象是否为行
	 * @param moveStep 移动距离 （+ :右/下 ； - :左/上）
	 */
	private void doMoveViewItem(String id, ViewItem viewItem, boolean isTitles,int moveStep){
		//根据id获取表单对象
		SeniorReportConfigData data = getReportById(id);
		SeniorReportConfig config = data.getSeniorReportConfig();
		//获取操作的标题对象实体
		viewItem = config.findViewItemById(viewItem.getItemId(), isTitles);
		if(viewItem == null){
			return;
		}
		//根据其父级对象来获得同级对象 
		List<ViewItem> list = viewItem.getpItem().getSubItem();
		int index= list.indexOf(viewItem);
		//如果移动距离超范围则不进行移动操作
		if(index+moveStep<0||index+moveStep>=list.size()){
			return;
		}
		//删除原位置的对象
		list.remove(index);
		//在目标位置插入
		list.add(index+moveStep,viewItem);
		//将表单xml对象重新设置到表单数据对象中
		data.setSeniorReportConfig(config);
		//修改
		seniorReportDao.update(data);
	}

	@Override
	public List<SeniorReportHistroy> getSnapTimeList(SeniorReportHistroy query) {
		return seniorReportHistroyDao.findList(query);
	}
	
	public void saveHistroy(SeniorReportHistroy histroy){
		seniorReportHistroyDao.insert(histroy);
	}
	
	public void removeHistroy(String id){
		seniorReportHistroyDao.delete(id);
	}
	
	public void setSeniorReportDao(ISeniorReportDao seniorReportDao) {
		this.seniorReportDao = seniorReportDao;
	}

	public void setSeniorReportHistroyDao(
			ISeniorReportHistroyDao seniorReportHistroyDao) {
		this.seniorReportHistroyDao = seniorReportHistroyDao;
	}

	

}
