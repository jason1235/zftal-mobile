package com.zfsoft.workflow.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.enums.BillConfigStatus;
import com.zfsoft.hrm.dybill.enums.BillType;
import com.zfsoft.hrm.dybill.enums.PrivilegeType;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.hrm.menu.service.IMenuService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.BusinessEnum;
import com.zfsoft.workflow.enumobject.BusinessTypeEnum;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.model.SpNodeBill;
import com.zfsoft.workflow.model.SpProcedure;
import com.zfsoft.workflow.model.query.SpBusinessQuery;
import com.zfsoft.workflow.service.ISpBusinessService;
import com.zfsoft.workflow.service.ISpProcedureService;

/**
 * 流程业务维护
 * 
 * @author Patrick Shen
 */
public class SpBusinessAction extends HrmAction {
	private static final long serialVersionUID = 6535431275595307300L;

	private ISpBusinessService spBusinessService;
	private ISpProcedureService spProcedureService;
	private ISpBillConfigService spBillConfigService;
	private SpBusiness spBusiness;
	private SpBusinessQuery query = new SpBusinessQuery();
	private PageList<SpBusiness> businessList=new PageList<SpBusiness>();
	private IMenuService menuService;
	private String[] classIds;
	private String[] classPrivilege;
	private String op = "add";
	private String sortFieldName = null;
	private String asc = "up";
	
	/**
	 * 获取流程业务列表
	 * 
	 * @return
	 */
/*	public String list() {
		List<SpBusiness> spBusinesss = spBusinessService.findSpBusiness(new SpBusiness());
		this.getValueStack().set("businessList", spBusinesss);
		return LIST_PAGE;
	}*/
	
	/**
	 * 获取流程业务列表，分页显示
	 * 
	 * @return
	 */
	public String list() {
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " BELONG_TO_SYS_NAME" );
		}
		businessList = spBusinessService.getPagingBusiness(query);
		int beginIndex = businessList.getPaginator().getBeginIndex();
		getValueStack().set("beginIndex",beginIndex);
		this.getValueStack().set("belongToSyses", menuService.getByLevel(1));
		return LIST_PAGE;
	}
	/**
	 * 请求修改业务
	 * 
	 * @return
	 */
	public String modifyBusiness() {
		op = "modify";
		spBusiness = spBusinessService.findSpBusinessByIdAndBType(spBusiness.getBid(),BillType.COMMIT.toString());
		try {
			SpBillConfig spBillConfig = new SpBillConfig();
			spBillConfig.setBillType(BillType.COMMIT);
			spBillConfig.setStatus(BillConfigStatus.USING);
			this.getValueStack().set("procedureList", spProcedureService.findSpProcedureList(new SpProcedure()));
			this.getValueStack().set("spBillConfigs", spBillConfigService.getSpBillConfigList(spBillConfig));
			this.getValueStack().set("billClassList", this.initXmlBillClass(spBusiness));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EDIT_PAGE;
	}
	
	public String getInfoClassList(){
		String type = getRequest().getParameter("type");
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("infoClasses", InfoClassCache.getInfoClasses(type));
		getValueStack().set(DATA, data);
		return DATA;
	}
	
	/**
	 * 请求添加业务
	 * 
	 * @return
	 */
	public String addBusiness() {
		
		try {
			SpBillConfig spBillConfig = new SpBillConfig();
			spBillConfig.setBillType(BillType.COMMIT);
			spBillConfig.setStatus(BillConfigStatus.USING);
			this.getValueStack().set("procedureList", spProcedureService.findSpProcedureList(new SpProcedure()));
			this.getValueStack().set("spBillConfigs", spBillConfigService.getSpBillConfigList(spBillConfig));
			this.getValueStack().set("billClassList", this.initXmlBillClass(spBusiness));
			this.getValueStack().set("belongToSyses", menuService.getByLevel(1));
			this.getValueStack().set("businessCode", BusinessEnum.values());
			this.getValueStack().set("businessType", BusinessTypeEnum.values());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "add";
	}
	
	/**
	 * 请求修改业务
	 * 
	 * @return
	 */
	public String findBillClass() {
		String pid = getRequest().getParameter("pid");
		List<XmlBillClass> classList = null;
		String billName = "";
		String billId = "";
		try {
			SpProcedure  sp = spProcedureService.findSpProcedureByPid(pid, false);
			if(sp!=null&&sp.getSpCommitBillList()!=null&&sp.getSpCommitBillList().size()>0){
				billId = sp.getSpCommitBillList().get(0).getBillId();
				SpBillConfig config = spBillConfigService.getSpBillConfigLastVersion(billId);
				if(config!=null){
					billName = config.getName();
					XmlBillClasses classes= config.getXmlBillClasses();
					if (classes!=null&&classes.getBillClasses()!=null) {
						classList = classes.getBillClasses();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("success", true);
		data.put("billId", billId);
		data.put("billName", billName);
		data.put("classList", classList);
		getValueStack().set(DATA, data);
		return DATA;
	}
	
	public String insertBusiness() {
		SpBusiness query = new SpBusiness();
		query.setBcode(spBusiness.getBcode());
		List<SpBusiness> list = spBusinessService.findSpBusiness(query);
		if(list!=null&&list.size()>0){
			this.setErrorMessage("已经存在相同业务编码的业务 请勿重复添加！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		spBusiness.setBstatus("1");
		spBusinessService.insert(spBusiness, classIds, classPrivilege);
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 保存修改业务
	 * 
	 * @return
	 */
	public String saveBusiness() {
		String pid = spBusiness.getPid();
		String billId = spBusiness.getBillId();
		spBusiness = spBusinessService.findSpBusinessByIdAndBType(spBusiness.getBid(),BillType.COMMIT.toString());
		spBusiness.setPid(pid);
		spBusiness.setBillId(billId);
		spBusinessService.update(spBusiness, classIds, classPrivilege);
		this.setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	private List<SpNodeBill> initXmlBillClass(SpBusiness sb) {
		List<SpNodeBill> resultList = null;
		if (sb != null && StringUtil.isNotEmpty(sb.getBillId())) {
			List<XmlBillClass> xmlBillClassList = spBillConfigService.getXmlBillClassListLastVersion(sb.getBillId());
			if (xmlBillClassList != null) {
				resultList = new ArrayList<SpNodeBill>();
				for (XmlBillClass billClass : xmlBillClassList) {
					SpNodeBill spNodeBill = new SpNodeBill();
					spNodeBill.setClassesPrivilege(PrivilegeType.SEARCH_ADD_DELETE_EDIT.toString());
					spNodeBill.setClassId(billClass.getId().toString());
					for (SpNodeBill nodeBill : sb.getSpCommitNodeBillList()) {
						if (nodeBill.getClassId().equals(spNodeBill.getClassId())) {
							spNodeBill = nodeBill;
							spNodeBill.setClassesPrivilege(nodeBill.getClassesPrivilege());
							spNodeBill.setChecked(true);
						}
					}
					spNodeBill.setClassName(billClass.getName());
					resultList.add(spNodeBill);
				}
			}
		}
		return resultList;
	}

	public SpBusiness getSpBusiness() {
		return spBusiness;
	}

	public void setSpBusiness(SpBusiness spBusiness) {
		this.spBusiness = spBusiness;
	}

	public String getOp() {
		return op;
	}

	/**
	 * @return classIds : return the property classIds.
	 */

	public String[] getClassIds() {
		return classIds;
	}

	/**
	 * @param classIds
	 *            : set the property classIds.
	 */

	public void setClassIds(String[] classIds) {
		this.classIds = classIds;
	}

	/**
	 * @return classPrivilege : return the property classPrivilege.
	 */

	public String[] getClassPrivilege() {
		return classPrivilege;
	}

	/**
	 * @param classPrivilege
	 *            : set the property classPrivilege.
	 */

	public void setClassPrivilege(String[] classPrivilege) {
		this.classPrivilege = classPrivilege;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public void setSpBusinessService(ISpBusinessService spBusinessService) {
		this.spBusinessService = spBusinessService;
	}

	public void setSpProcedureService(ISpProcedureService spProcedureService) {
		this.spProcedureService = spProcedureService;
	}

	/**
	 * @param spBillConfigService
	 *            : set the property spBillConfigService.
	 */

	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}

	public SpBusinessQuery getQuery() {
		return query;
	}

	public void setQuery(SpBusinessQuery query) {
		this.query = query;
	}

	public IMenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	public PageList<SpBusiness> getBusinessList() {
		return businessList;
	}

	public void setBusinessList(PageList<SpBusiness> businessList) {
		this.businessList = businessList;
	}
	public String getSortFieldName() {
		return sortFieldName;
	}
	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}
	public String getAsc() {
		return asc;
	}
	public void setAsc(String asc) {
		this.asc = asc;
	}
}
