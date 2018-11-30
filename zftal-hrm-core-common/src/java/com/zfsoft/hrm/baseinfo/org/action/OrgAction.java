package com.zfsoft.hrm.baseinfo.org.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.zfsoft.common.service.BaseLog;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.entities.OrgPeople;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;
import com.zfsoft.hrm.baseinfo.org.service.svcinterface.IOrgService;
import com.zfsoft.hrm.baseinfo.org.util.OrgUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.config.Type;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.OrgType;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.service.impl.LogEngineImpl;
import com.zfsoft.util.base.StringUtil;

public class OrgAction extends HrmAction {

	private static final long serialVersionUID = -8637033016035808682L;
	
	private String oid;
	
	private boolean addIf;
	
	private OrgInfo info = new OrgInfo();
	
	private OrgQuery query = new OrgQuery();
	
	private List<OrgInfo> infoList;
	
	private List<Type> typeList;
	
	private String supperOid;
	
	private OrgPeople orgPeople = new OrgPeople();
	
	private String[] staffIds;
	
	private IOrgService orgService;
	
	private BaseLog baseLog = LogEngineImpl.getInstance();
	
	private static final String SHOWWINERROR = "showWinError";
	
	public String list(){
		infoList = orgService.getList(query);
		boolean flag = false;
		String on_off =SubSystemHolder.getPropertiesValue("org_scope_on_off");
		if((!(StringUtil.isNotEmpty(on_off) && "on".equalsIgnoreCase(on_off))) || YhglModel.INNER_USER_ADMIN.equals(getUser().getYhm())){
			flag = true;
		}
		getValueStack().set("flag", flag);
		return LIST_PAGE;
	}
	
	public String disusedlist(){
		infoList = orgService.getDisusedList(query);
//		System.out.print("###########################################################");
		return LIST_PAGE;
//		return "index";
	}
	
	public String edit(){
		try{
			if(!addIf){
				info = orgService.getById(info.getOid());
			}
			query.setLevel("1");
			infoList = orgService.getList(query);
			typeList = OrgUtil.getTypeList();
			return EDIT_PAGE;
		}catch(Exception e){
			getValueStack().set("message", e.getMessage());
			return SHOWWINERROR;
		}
	}
	
	public String save(){
		validateInfo();
		if(addIf){
			setMsg(orgService.add(info), "新增部门");
			baseLog.insert("新增组织机构", "组织机构管理", "新增组织机构 : " + info.getName() + "(部门代码 : " + info.getOid() + ")");
		}
		else{
			OrgInfo orign = orgService.getById(info.getOid());
			setMsg(orgService.modify(info), "修改部门");
			baseLog.update("更改组织机构", "组织机构管理", "更改组织机构 : " + compareOrg(orign, info));
		}
		return DATA;
	}
	
	public String disuse(){
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何信息");
		String name = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, oid);
		setMsg(orgService.disuse(oid));
		baseLog.update("废弃组织机构", "组织机构管理", "废弃组织机构 : " + name + "(部门代码  : " + oid + ")");
		return DATA;
	}
	
	public String use(){
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何信息");
		setMsg(orgService.use(oid));
		baseLog.update("重新启用组织机构", "组织机构管理", "重新启用组织机构 : " + CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, oid) + "(部门代码  : " + oid + ")");
		return DATA;
	}
	
	public String remove(){
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何信息");
		info = orgService.getById(oid);
		orgService.remove(oid);
		setSuccessMessage("删除组织机构成功");
		getValueStack().set(DATA, getMessage());
		baseLog.delete("删除组织机构", "组织机构管理", "删除组织机构 : " + (info != null && !StringUtil.isEmpty(info.getName()) ? info.getName() : "") + "(部门代码  : " + oid + ")");
		return DATA;
	}
	
	public String judgeOid(){
		boolean res = false;
		if(!StringUtil.isEmpty(oid)){
			OrgInfo org = orgService.getById(oid);
			if(org != null){
				res = true;
			}
		}
		getValueStack().set(DATA, res);
		return DATA;
	}
	
	public String getSonList(){
		Assert.notNull(query, "查询信息不可为空");
		Assert.notNull(query.getParent(), "未指定上级部门");
		Assert.isTrue(StringUtil.isEmpty(query.getParent().getOid()), "未指定上级部门");
		infoList = orgService.getList(query);
		getValueStack().set(DATA, infoList);
		return DATA;
	}
	
	public String getListByTypes(){
		Type[] types = TypeFactory.getTypes(OrgType.class);
		HashMap<Type, List<OrgInfo>> map = new HashMap<Type, List<OrgInfo>>();
		for (Type type : types) {
			query.setType(type.getName());
			List<OrgInfo> list = orgService.getList(query);
			if(list != null && list.size() > 0){
				map.put(type, list);
			}
		}
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String treePage(){
		return "org_ztree";
	}
	public String orgTree(){
		OrgInfo orgTree = orgService.getOrgTree(query);
		getValueStack().set(DATA, orgTree);
		return DATA;
	}
	
	public String orgztreeExport()throws Exception{
		List<Type> types= OrgUtil.getTypeList();
		Map<String,String> typeMap=new HashMap<String, String>();
		for(Type type:types){
			typeMap.put(type.getName(), type.getText());
		}
		
		OrgInfo orgTree = orgService.getOrgTree(query);
		
		List<OrgInfo> list=new ArrayList<OrgInfo>();
		
		for(OrgInfo node:orgTree.getChildren()){
			assembleTreeList(node,list);
		}
		
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		WritableSheet sheet1 = wwb.createSheet("组织机构", 0);
		sheet1.addCell(new Label(0,0,"序号"));
		sheet1.addCell(new Label(1,0,"部门代码"));
		sheet1.addCell(new Label(2,0,"部门名称"));
		sheet1.addCell(new Label(3,0,"部门类别"));
		sheet1.addCell(new Label(4,0,"上级部门"));
		sheet1.addCell(new Label(5,0,"主管"));
		sheet1.addCell(new Label(6,0,"负责人"));
		sheet1.addCell(new Label(7,0,"顺序码"));
		sheet1.addCell(new Label(8,0,"备注"));
		
		for(int i=0;i<list.size();i++){
			sheet1.addCell(new Label(0,i+1,String.valueOf(i+1)));
			
			if(StringUtils.isEmpty(list.get(i).getType())){
				sheet1.addCell(new Label(1,i+1,null));
			}else{
				sheet1.addCell(new Label(1,i+1,list.get(i).getOid()));
			}
			sheet1.addCell(new Label(2,i+1,list.get(i).getName()));
			sheet1.addCell(new Label(3,i+1,typeMap.get(list.get(i).getType())));
			if(list.get(i).getParent()==null){
				sheet1.addCell(new Label(4,i+1,null));
			}else{
				sheet1.addCell(new Label(4,i+1,list.get(i).getParent().getName()));
			}
			sheet1.addCell(new Label(5,i+1,list.get(i).getManager()));
			sheet1.addCell(new Label(6,i+1,list.get(i).getPrin()));
			sheet1.addCell(new Label(7,i+1,list.get(i).getOrderCode()));
			sheet1.addCell(new Label(8,i+1,list.get(i).getRemark()));
		}
		//设置编码、类型、文件名
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,"组织机构"+".xls");
		getResponse().setHeader("content-disposition", disposition);
		wwb.write();
		wwb.close();
		
		return null;
	}

	private void assembleTreeList(OrgInfo orgInfo,List<OrgInfo> list){
		list.add(orgInfo);
		List<OrgInfo> children=orgInfo.getChildren();
		if(children==null||children.size()==0){
			return;
		}
		for(OrgInfo node:children){
			this.assembleTreeList(node, list);
		}
	}

	private void validateInfo(){
		Assert.notNull(info, "部门信息不可为空");
		Assert.isTrue(!StringUtil.isEmpty(info.getOid()), "部门编号不可为空");
		Assert.isTrue(!StringUtil.isEmpty(info.getName()), "部门名称不可为空");
		Assert.isTrue(!StringUtil.isEmpty(info.getLevel()), "部门级别不可为空");
		Assert.isTrue(!StringUtil.isEmpty(info.getType()), "部门类型不可为空");
	}
	
	private void setMsg(boolean exp){
		setMsg(exp, null);
	}
	
	private void setMsg(boolean exp, String text){
		text = !StringUtil.isEmpty(text) ? text : "操作";
		if(exp){
			setSuccessMessage(text + "成功");
		}
		else{
			setErrorMessage(text + "失败");
		}
		getValueStack().set(DATA, getMessage());
	}
	
	private String compareOrg(OrgInfo pass, OrgInfo now){
		if(pass == null || now == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		compareParam(pass.getName(), now.getName(), "部门名称", sb);
		compareParam(pass.getType(), now.getType(), "部门类别", sb, pass.getOrgTypeInfo() != null ? pass.getOrgTypeInfo().getText() : null, now.getOrgTypeInfo() != null ? now.getOrgTypeInfo().getText() : null);
		compareParam(pass.getOrderCode(), now.getOrderCode(), "部门顺序码", sb);
		compareParam(pass.getPrin(), now.getPrin(), "部门负责人", sb, pass.getPrinInfo() != null ? (String)pass.getPrinInfo().getName() : null, now.getPrinInfo() != null ? (String)now.getPrinInfo().getName() : null);
		compareParam(pass.getParent() != null ? pass.getParent().getOid() : null, now.getParent() != null ? now.getParent().getOid() : null, "上级部门", sb, pass.getParent() != null ? CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, pass.getParent().getOid()) : null, now.getParent() != null ? CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, now.getParent().getOid()) : null);
		String result = sb.toString();
		if(StringUtil.isEmpty(result)){
			result = "未作改动";
		}
		return result;
	}
	
	private void compareParam(String param1, String param2, String paramName, StringBuffer sb){
		compareParam(param1, param2, paramName, sb, null, null);
	}
	
	private void compareParam(String param1, String param2, String paramName, StringBuffer sb, String pName1, String pName2){
		if(StringUtil.isEmpty(param2) || param2.equals(param1)){
			return;
		}
		if(StringUtil.isEmpty(param1)){
			sb.append(paramName + " 更改为 " + (!StringUtil.isEmpty(pName2) ? pName2 : param2));
		}
		else{
			sb.append(paramName + " 由 " + (!StringUtil.isEmpty(pName1) ? pName1 : param1) + " 更改为 " + (!StringUtil.isEmpty(pName2) ? pName2 : param2));
		}
		sb.append("; ");
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * 设置
	 * @param oid 
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<OrgInfo> getInfoList() {
		return infoList;
	}

	/**
	 * 设置
	 * @param infoList 
	 */
	public void setInfoList(List<OrgInfo> infoList) {
		this.infoList = infoList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public OrgInfo getInfo() {
		return info;
	}

	/**
	 * 设置
	 * @param info 
	 */
	public void setInfo(OrgInfo info) {
		this.info = info;
	}

	/**
	 * 返回
	 * @return 
	 */
	public OrgQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(OrgQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IOrgService getOrgService() {
		return orgService;
	}

	/**
	 * 设置
	 * @param orgService 
	 */
	public void setOrgService(IOrgService orgService) {
		this.orgService = orgService;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<Type> getTypeList() {
		return typeList;
	}

	/**
	 * 设置
	 * @param typeList 
	 */
	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public boolean isAddIf() {
		return addIf;
	}

	/**
	 * 设置
	 * @param addIf 
	 */
	public void setAddIf(boolean addIf) {
		this.addIf = addIf;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getSupperOid() {
		return supperOid;
	}

	/**
	 * 设置
	 * @param supperOid 
	 */
	public void setSupperOid(String supperOid) {
		this.supperOid = supperOid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public OrgPeople getOrgPeople() {
		return orgPeople;
	}

	/**
	 * 设置
	 * @param orgPeople 
	 */
	public void setOrgPeople(OrgPeople orgPeople) {
		this.orgPeople = orgPeople;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String[] getStaffIds() {
		return staffIds;
	}

	/**
	 * 设置
	 * @param staffIds 
	 */
	public void setStaffIds(String[] staffIds) {
		this.staffIds = staffIds;
	}

}
