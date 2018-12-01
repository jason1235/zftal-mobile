package com.zfsoft.hrm.baseinfo.infoclass.action;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.query.CatalogQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.ICatalogService;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoClassService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.InfoClassType;
import com.zfsoft.hrm.core.util.HrmSessionFactory;
import com.zfsoft.util.base.StringUtil;

/**
 * 信息类管理Action
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-23
 * @version V1.0.0
 */
public class InfoClassAction extends HrmAction implements ModelDriven<InfoClass> {

	private static final long serialVersionUID = 6115002203907614671L;

	private static final String LIST_PAGE = "list";
	
	private static final String EDIT_PAGE = "edit";
	
	private InfoClass model = new InfoClass();

	private boolean fixedWin = true;
	
	private ICatalogService catalogService;

	private IInfoClassService infoClassService;
	
	
	/**
	 * 列表页面
	 * @return
	 */
	public String list() {
		try {
			
			CatalogQuery query=new CatalogQuery();
			query.setType( model.getCatalog().getType() );
			
			List<Catalog> list = catalogService.getFullList( query );
			InfoClass overall = InfoClassCache.getOverallInfoClass( model.getCatalog().getType() );
			getValueStack().set("overall", overall );
			getValueStack().set("list", list);
			
			/*
			 * 会话中的信息类ID表示当前操作的信息类ID
			 * 如果会回话中不存在信息类ID，表示第一次进入该页面，或是当前信息类做删除操作
			 * 因为系统将信息类定位到Overall信息类中去，避免信息类属性页面空白
			 */
			String _s_id = model.getGuid() == null ? HrmSessionFactory.getCurrentInfoClassSession().get( model.getCatalog().getType() ) : model.getGuid();
			
			if( _s_id == null || "".equals( _s_id )) {
				if(overall!=null )
					_s_id = overall.getGuid();
				else
					_s_id = InfoClassCache.getInfoClasses(model.getCatalog().getType()).get(0).getGuid();
			}
			//设置显示、编辑、必填、同步、虚拟条件检索
			String value= getRequest().getParameter("hdncheckboxSel");
			String express=null;
			if(value!=null){
				String[] operation = value.split(",");		
				express=getExpress(operation);
			}
			model = infoClassService.getFullInfoClass(_s_id,express);
			
			getValueStack().set( "init", value );
//			//信息类
//			InfoPropertyQuery query2 = new InfoPropertyQuery();
//			query2.setClassId( _s_id );
//			
//			List<InfoProperty> properties = propertyService.getInfoProperties( query2 );
//			
//			getValueStack().set( "properties", properties );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return LIST_PAGE;
	}
	
	
	private String getExpress(String[] oper){
		String express=" 1=1 ";
		
		for(String op:oper){
			if(op.equals("viewable")){
				express+="and KXS=1";
			}
			if(op.equals("editable")){
				express+="and KBJ=1 ";
			}
			if(op.equals("need")){
				express+="and BT=1 ";
			}
			if(op.equals("syncToField")){
				express+="and TBZD IS NOT NULL ";
			}
			if(op.equals("virtual")){
				express+="and XNZD=1 ";
			}
		}
	
		return express;
	}
	/**
	 * 编辑页面
	 * @return
	 */
	public String edit() {
		model.setIndex(99);
		if( model.getGuid() != null && !"".equals(model.getGuid()) ) {
			model = infoClassService.getFullInfoClass( model.getGuid() );
		}else{
			model.setPropertiesList("[{'guid':'2234566C1A9A84E1E050007F01002DDA','name':'全局ID','description':'全局ID','fieldName':'globalid','fieldType':'COMMON','viewable':false,'editable':false,'sourceInit':true,'need':false,'defaultValue':'','displayFormula':'','fieldLen':32,'digits':0,'codeId':'','unique':false,'syncToField':'','syncCondition':'','virtual':false,'classId':'2234566C1A9984E1E050007F01002DDA','index':0,'refer':'','referFunc':'','width':200,'height':30,'size':5,'viewStyle':''},{'guid':'2234566C1A9B84E1E050007F01002DDA','name':'最后修改时间','description':'最后修改时间','fieldName':'lastModifyTime','fieldType':'DATE','viewable':false,'editable':false,'sourceInit':true,'need':false,'defaultValue':'','displayFormula':'','fieldLen':32,'digits':0,'codeId':'','unique':false,'syncToField':'','syncCondition':'','virtual':false,'classId':'2234566C1A9984E1E050007F01002DDA','index':1,'refer':'','referFunc':'','width':200,'height':30,'size':5,'viewStyle':''},{'guid':'2234566C1A9C84E1E050007F01002DDA','name':'编号','description':'编号','fieldName':'bh','fieldType':'COMMON','viewable':false,'editable':false,'sourceInit':true,'need':false,'defaultValue':'','displayFormula':'','fieldLen':32,'digits':0,'codeId':'','unique':false,'syncToField':'','syncCondition':'','virtual':false,'classId':'2234566C1A9984E1E050007F01002DDA','index':2,'verifyType':'NOMAL','refer':'','referFunc':'','width':200,'height':30,'size':5,'viewStyle':''}]");
		}
		
		boolean s = model.isMoreThanOne();
		
		System.out.println( s );

		getValueStack().set("types", TypeFactory.getTypes( InfoClassType.class ) );
		
		return EDIT_PAGE;
	}
	
	/**
	 * 编辑页面V5
	 * @return
	 */
	public String editNew() {
		model.setIndex(99);
		if( model.getGuid() != null && !"".equals(model.getGuid()) ) {
			model = infoClassService.getFullInfoClass( model.getGuid() );
			
			String isOrNotLike = infoClassService.getProceduresMhcx(model.getGuid());
			String xxlall = infoClassService.getProceduresXxlall(model.getGuid());
			getValueStack().set("isOrNotLike", isOrNotLike);
			getValueStack().set("xxlall", xxlall);
		}else{
			model.setPropertiesList("[{'guid':'2234566C1A9A84E1E050007F01002DDA','name':'全局ID','description':'全局ID','fieldName':'globalid','fieldType':'COMMON','viewable':false,'editable':false,'sourceInit':true,'need':false,'defaultValue':'','displayFormula':'','fieldLen':32,'digits':0,'codeId':'','unique':false,'syncToField':'','syncCondition':'','virtual':false,'classId':'2234566C1A9984E1E050007F01002DDA','index':0,'refer':'','referFunc':'','width':200,'height':30,'size':5,'viewStyle':''},{'guid':'2234566C1A9B84E1E050007F01002DDA','name':'最后修改时间','description':'最后修改时间','fieldName':'lastModifyTime','fieldType':'DATE','viewable':false,'editable':false,'sourceInit':true,'need':false,'defaultValue':'','displayFormula':'','fieldLen':32,'digits':0,'codeId':'','unique':false,'syncToField':'','syncCondition':'','virtual':false,'classId':'2234566C1A9984E1E050007F01002DDA','index':1,'refer':'','referFunc':'','width':200,'height':30,'size':5,'viewStyle':''},{'guid':'2234566C1A9C84E1E050007F01002DDA','name':'编号','description':'编号','fieldName':'bh','fieldType':'COMMON','viewable':false,'editable':false,'sourceInit':true,'need':false,'defaultValue':'','displayFormula':'','fieldLen':32,'digits':0,'codeId':'','unique':false,'syncToField':'','syncCondition':'','virtual':false,'classId':'2234566C1A9984E1E050007F01002DDA','index':2,'verifyType':'NOMAL','refer':'','referFunc':'','width':200,'height':30,'size':5,'viewStyle':''}]");
		}
		
		boolean s = model.isMoreThanOne();
		
		System.out.println( s );

		getValueStack().set("types", TypeFactory.getTypes( InfoClassType.class ) );
		
		return "editNew";
	}
	
	/**
	 * 保存操作
	 * @return
	 */
	public String save() {
		try {
			if( model.getGuid() == null || "".equals(model.getGuid()) ) {
				infoClassService.add(model);
				
				//新增完成后调整回列表，对新增的信息类进行操作，需在回话中设置当前操作的信息类ID
				InfoClass clazz = InfoClassCache.getInfoClass( model.getGuid() );
				HrmSessionFactory.setCurrentInfoClassSession( clazz.getCatalog().getType(), clazz.getGuid() );
			} else {
				String pxfs = "";
				String[] pxfsList = model.getPxfsList();
				if(pxfsList != null && pxfsList.length > 0){
					for(String temp : model.getPxfsList()){
						pxfs += temp+",";
					}
				}
				if(pxfs.length() > 1){
					pxfs = pxfs.substring(0, pxfs.length()-1);
					model.setScanStyle(pxfs);
				}
				infoClassService.modify(model);
				//更新缓存信息
				InfoClassCache.refresh( model.getGuid() );
				
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			//String infoClassID = request.getParameter("infoClassID");
			String isOrNotLike = request.getParameter("isOrNotLike");
			isOrNotLike = StringUtil.isEmpty(isOrNotLike) ? "n" : "y";
			String xxlall = request.getParameter("xxlall");
			xxlall = StringUtil.isEmpty(xxlall) ? "n" : "y";
			Map<String, String> map = new HashMap<String, String>();
			map.put("classId", model.getGuid());
			map.put("isOrNotLike", isOrNotLike);
			map.put("xxlall", xxlall);
			infoClassService.modifyProcedure(map);
			
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("操作失败！<br />提示：" + e.getMessage() );
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
			InfoClass clazz = InfoClassCache.getInfoClass( model.getGuid() );
			//后台控制 仅允许对非系统初始信息类进行删除操作
			if(!"system".equals(clazz.getXxly()))
			{
				infoClassService.remove( model.getGuid() );
				
				/*
				 * 信息类删除成功后，当前的信息类需要重新定义，
				 * 因此需将会话中的信息类ID删除，在列表查询时进行重新获取
				 */
				HrmSessionFactory.setCurrentInfoClassSession( clazz.getCatalog().getType(), null );
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			setErrorMessage("操作失败！<br />提示："+e.getMessage());
		}
		
		getValueStack().set(DATA, getMessage());
		
		return DATA;
	}
	
	/**
	 * 导出
	 * @return
	 */
	public String expDate() {
		try {
			getResponse().reset();
			getResponse().setCharacterEncoding("utf-8");
			getResponse().setContentType("application/vnd.ms-excel");
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("数据字典.xls", "utf-8"));
			
			WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
			WritableSheet sheet1 = wwb.createSheet("主库", 0);
			
			sheet1.setColumnView(0, 20);
			sheet1.setColumnView(1, 20);
			sheet1.setColumnView(4, 40);
			
			WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			WritableCellFormat format = new WritableCellFormat(font);

			
			int r1 = 0;
			
			for (InfoClass infoClass : infoClassService.getFullList(null) ) {
				sheet1.addCell( new Label( 0, r1, infoClass.getName(), format ) );
				sheet1.addCell( new Label( 1, r1, infoClass.getIdentityName(), format ) );
				
				for (InfoProperty property : infoClass.getProperties() ) {
					r1++;
					
					sheet1.addCell( new Label( 0, r1, property.getName() ) );
					sheet1.addCell( new Label( 1, r1, property.getFieldName() ) );
					sheet1.addCell( new Label( 2, r1, property.getTypeInfo().getText() ) );
					sheet1.addCell( new Label( 3, r1, property.getFieldLen().toString() ) );
					sheet1.addCell( new Label( 4, r1, property.getDescription() ) );
				}
				
				r1 += 2;
			}
			
			wwb.write();
			wwb.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public InfoClass getModel() {
		return model;
	}

	public void setCatalogService(ICatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public void setInfoClassService(IInfoClassService infoClassService) {
		this.infoClassService = infoClassService;
	}

	public boolean isFixedWin() {
		return fixedWin;
	}

	public void setFixedWin(boolean fixedWin) {
		this.fixedWin = fixedWin;
	}
}