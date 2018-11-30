package com.zfsoft.hrm.baseinfo.infoclass.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.dyna.html.EditParse;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.html.ColumnTypeHtmlGenerator;
import com.zfsoft.hrm.baseinfo.infoclass.html.VirtualColumnHtmlGenerator;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoClassService;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.ExceptionMessageFactory;
import com.zfsoft.hrm.core.util.HrmSessionFactory;
import com.zfsoft.util.base.StringUtil;

/**
 * 信息类属性Action
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-24
 * @version V1.0.0
 */
public class InfoPropertyAction extends HrmAction implements ModelDriven<InfoProperty> {

	private static final long serialVersionUID = 2759743384104637327L;
	
	private static final String LIST_PAGE = "list";
	
	private InfoProperty model = new InfoProperty();
	
	private String classId;
	
	private String[] items;
	
	private IInfoPropertyService infoPropertyService;
	
	private IInfoClassService infoClassService;
	
	/**
	 * 列表页面
	 * @return
	 */
	public String list() {
		try {
			InfoClass infoClass = infoClassService.getInfoClass(
					classId);
			
			InfoPropertyQuery query = new InfoPropertyQuery();
			query.setClassId( infoClass.getGuid() );
			
			List<InfoProperty> list = infoPropertyService.getInfoProperties( query );
			
			HrmSessionFactory.setCurrentInfoClassSession( infoClass );
			
			model.setClasz(infoClass);
			getValueStack().set("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return LIST_PAGE;
	}
	
	/**
	 * 编辑页面
	 * @return
	 */
	/**public String edit() {
		try {
			
			if( model.getGuid() != null && !"".equals( model.getGuid() ) ) {
				model = infoPropertyService.getProperty(model.getGuid());
			}
			
			InfoPropertyQuery query = new InfoPropertyQuery();
			
			/*
			 * model.getClass_().getGuid()必然存在值
			 * 理由：如果是跳转增加页面，参数中必然包含classId，
			 * 如果是跳转到编辑页面，model重新获取实体信息后也将封装class_信息
			 * 注意：该行代码不得出现在model获取实体信息之前
			 //
			query.setClassId( model.getClass_().getGuid() );	
			query.setUnique(true);
			
			getValueStack().set("uniques", infoPropertyService.getInfoProperties(query));
			
			Type[] types = TypeFactory.getTypes( InfoPropertyType.class );
			
			InfoPropertyType[] properTypes = new InfoPropertyType[0];

			/*
			 * 在做新增时提供所有的字段类型，
			 * 在做修改时提供相同数据库字段类型的字段类型。
			 /
			for (int i = 0; i < types.length; i++) {
				String type = ((InfoPropertyType)types[i]).getDataType();

				if( model.getGuid() != null && !"".equals(model.getGuid()) ) {
					String dateType = model.getTypeInfo().getDataType();
					
					if( !type.equals( dateType ) ) {
						continue;
					}
				}
				properTypes = (InfoPropertyType[]) ArrayUtil.addElement( properTypes, types[i], InfoPropertyType.class );
			}
			
			getValueStack().set("types", properTypes );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return EDIT_PAGE;
	}*/
	public String doSyncView() {
		String cId = StringUtil.isEmpty(classId)?model.getClassId():classId;
		InfoClass infoClass = infoClassService.getInfoClass(cId);
		InfoPropertyQuery query = new InfoPropertyQuery();
		query.setClassId( infoClass.getGuid() );
		getValueStack().set("properties", infoPropertyService.getInfoProperties(query));
		getValueStack().set(DATA, getMessage());
		return "doSyncView";
	}
	
	public String syncInfo() {
		try {
			String cId = StringUtil.isEmpty(classId)?model.getClassId():classId;
			infoPropertyService.syncFieldValue(cId,model.getGuid());
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("操作失败！<br />提示：" + e.getMessage());
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String editNew(){
		if( model.getGuid() != null && !"".equals( model.getGuid() ) ) {
			model = infoPropertyService.getProperty(model.getGuid());
		}
		return "editNew";
	}
	
	public String edit(){
		if( model.getGuid() != null && !"".equals( model.getGuid() ) ) {
			model = infoPropertyService.getProperty(model.getGuid());
		}
		return "edit";
	}
	
	public String virtualStep(){
		if( model.getGuid() != null && !"".equals( model.getGuid() ) ) {
			model = infoPropertyService.getProperty(model.getGuid());
		}
		String html = new VirtualColumnHtmlGenerator(model).html();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", html);
		map.put("success", true);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String columnStep(){
		if( model.getGuid() != null && !"".equals( model.getGuid() ) ) {
			InfoProperty old = infoPropertyService.getProperty(model.getGuid());
			old.setFieldType(model.getFieldType());
			model = old;
		}
		String html = new ColumnTypeHtmlGenerator(model).html();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", html);
		map.put("success", true);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String getDefInputStyle(){
		StringBuilder sb = new StringBuilder();
		if (!com.zfsoft.hrm.baseinfo.dyna.html.Type.FILE.equals(model.getFieldType()) &&
				!com.zfsoft.hrm.baseinfo.dyna.html.Type.IMAGE.equals(model.getFieldType())&&
				!com.zfsoft.hrm.baseinfo.dyna.html.Type.PHOTO.equals(model.getFieldType())) {
			String str = model.getFieldName();
			model.setFieldName("defaultValue");
			sb.append(EditParse.parse(model, model.getDefaultValue()));
			model.setFieldName(str);
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", sb.toString());
		map.put("success", true);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String sync(){
		if( model.getGuid() != null && !"".equals( model.getGuid() ) ) {
			model = infoPropertyService.getProperty(model.getGuid());
		}
		
		/*
		 * model.getClass_().getGuid()必然存在值
		 * 理由：如果是跳转增加页面，参数中必然包含classId，
		 * 如果是跳转到编辑页面，model重新获取实体信息后也将封装class_信息
		 * 注意：该行代码不得出现在model获取实体信息之前
		 */
		InfoPropertyQuery query = new InfoPropertyQuery();
		query.setClassId( model.getClassId() );	
		query.setUnique(true);
		getValueStack().set("uniques", infoPropertyService.getInfoProperties(query));
		
		List<InfoProperty> pList = new ArrayList<InfoProperty>();
		for(InfoProperty p : InfoClassCache.getOverallInfoClass().getProperties()){
			if(p.getVirtual()){
				continue;
			}else{
				pList.add(p);
			}
		}
		getValueStack().set("pList", pList);
		return "sync";
	}
	
	/**
	 * 保持操作
	 * @return
	 */
	public String save() {
		try {
			if( model.getGuid() == null || "".equals(model.getGuid() ) ) {
				infoPropertyService.add(model);
			} else {
				infoPropertyService.modify(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage(ExceptionMessageFactory.getMessage( e.getMessage() ) );
			//setErrorMessage("操作失败！<br />提示："+ ExceptionMessageFactory.getMessage( e.getMessage() ) );
		}
		
		getValueStack().set(DATA, getMessage());
		
		return DATA;
	}
	
	/**
	 * 删除操作
	 * @return
	 */
	public String remove() {
		try {
			InfoProperty  p = infoPropertyService.getProperty(model.getGuid());
			//后台控制 仅允许对非系统初始化属性进行删除操作
			if (!p.getSourceInit()) {
				infoPropertyService.remove( model.getGuid() );
			}
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("操作失败！<br />提示：" + e.getMessage());
		}
		
		getValueStack().set(DATA, getMessage());
		
		return DATA;
	}
	
	public String updateSync(){
		if(StringUtil.isNotEmpty(model.getSyncToField())){
			InfoClass clazz = InfoClassCache.getInfoClass(model.getClassId());
			for (InfoProperty p : clazz.getProperties()) {
				if((!p.getGuid().equals(model.getGuid()))&&
						StringUtil.isNotEmpty(p.getSyncToField()) 
						&&p.getSyncToField().equals(model.getSyncToField())){
					setErrorMessage("该信息类已经存在属性【"+p.getFieldName()+"】同步到此字段！");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
			}
		}
		infoPropertyService.updateSync(model);
		InfoClassCache.refresh( model.getClassId() );
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 交换位置
	 * @return
	 */
	public String swap() {
		try {
			String[] guids = model.getGuid().split(",");
			
			infoPropertyService.swapIndex(guids);
		} catch (Exception e) {
			e.printStackTrace();
			
			setErrorMessage("操作失败！");
		}
		
		getValueStack().set(DATA, getMessage());
		
		return DATA;
	}
	
	@Override
	public InfoProperty getModel() {
		return model;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public void setInfoPropertyService(IInfoPropertyService infoPropertyService) {
		this.infoPropertyService = infoPropertyService;
	}

	public void setInfoClassService(IInfoClassService infoClassService) {
		this.infoClassService = infoClassService;
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

}
